package ca.sait.email.dataaccess;

import javax.persistence.EntityManager;
import ca.sait.email.models.User;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


public class UserDB {
    public User get(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }
    
    public boolean update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }
    
//    public User getByUUID(String uuid) {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        
//        try {
//            Query q = em.createNamedQuery("User.findByResetPasswordUuid");
//            User user = (User) q.setParameter("resetPasswordUuid", uuid).getSingleResult();
//            return user;
//        } catch (Exception e) {
//            em.close();
//            return null;
//        }
//    }
}
