package by.prostrmk.clouddrive.model.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static String hashString(String stringForHashing){
        return DigestUtils.md5Hex(stringForHashing);
    }

}
