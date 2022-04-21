package ca.sait.email.servlets;

import ca.sait.email.services.AccountService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Seungjin Moon
 */
public class ResetPasswordServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        
        // If the UUID exists, send the user to the change password page
        if (uuid != null) {
            request.setAttribute("uuid", uuid);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String uuid = request.getParameter("uuid");
        String message = "";
        AccountService as = new AccountService();

        // If there is a uuid parameter, we need to update the user password
        if (uuid != null) {
            String password = request.getParameter("password");

            // Check if the user input an email and password and if the password could be changed
            if (email != null && password != null && as.changePassword(email, password, uuid)) {
                message = "Your password was changed successfully";
            } else {
                message = "Your password could not be changed. Please try again";
                request.setAttribute("uuid", uuid);
            }

            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        } else {
            String url = request.getRequestURL().toString();
            String path = getServletContext().getRealPath("/WEB-INF");

            // For security, the user does not need to know if the reset request was successful
            as.resetPassword(email, path, url);
            message = "Your request has been processed. Please check your email for the next steps.";
            request.setAttribute("message", message);

            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        }
    }
}
