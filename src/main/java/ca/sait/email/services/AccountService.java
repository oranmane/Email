package ca.sait.email.services;

import ca.sait.email.dataaccess.UserDB;
import ca.sait.email.models.User;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountService {
    
    public User login(String email, String password) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", email);
                
                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = "resetpassword.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                
                GmailService.sendMail(to, subject, template, tags);
                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
    
    public boolean resetPassword(String email, String path, String url) {
        String uuid = UUID.randomUUID().toString();
        UserDB userDB = new UserDB();
        String link = url + "?uuid=" + uuid;
        try {
            User user = userDB.get(email);
            if (user != null) {
                
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Reset password by {0}", email);
                
                String to = user.getEmail();
                String subject = "Notes App Reset Password";
                String template = path + "/emailtemplates/resetpassword.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                tags.put("link", link);
                
                GmailService.sendMail(to, subject, template, tags);
                
                //Add the uuid to the user database
                user.setResetPasswordUuid(uuid);
                userDB.update(user);
                return true;
            }
            else {
                throw new Exception("No such user");
            }
        } catch (Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Unsuccesfull reset request by " + email, e);
            return false;
        }
    }
    
    public boolean changePassword(String email, String uuid, String password) {
       UserDB userDB = new UserDB();

       try {
            User user = userDB.get(email);
            if (user != null && user.getResetPasswordUuid().equals(uuid)) {
                user.setPassword(password);
                user.setResetPasswordUuid(null);
                userDB.update(user);
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Password changed by {0}", email);
                return true;
            } else {
                throw new Exception("User does not exist or incorrect UUID");
            }
        } catch(Exception e) {
            Logger.getLogger(AccountService.class.getName()).log(Level.WARNING, "Unsuccesfull password change by " + email, e);
            return false;
        }
    }

}
