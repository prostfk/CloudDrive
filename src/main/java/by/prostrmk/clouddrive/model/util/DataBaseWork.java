package by.prostrmk.clouddrive.model.util;

import by.prostrmk.clouddrive.model.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;
import java.util.List;

public class DataBaseWork {

    private static final Logger logger = Logger.getLogger(DataBaseWork.class);

    public static void addToDataBase(Object entity) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Hibernate Connection Error");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static void deleteFromDataBase(Object entity){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            logger.debug("Something Was Deleted From Database.");
        }catch (Exception e){
            logger.error(e);
        }finally {
            assert session != null;
            session.close();
        }

    }

    public static List search(String entityName, String column, String searchString) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = new StringBuilder().append("FROM ").append(entityName).append(" WHERE ").append(column).append(" LIKE '%").append(searchString).append("%'").toString();//
            Query query = session.createQuery(hql);//
            return query.list();
        } catch (Exception e) {
            logger.error("Search Error: " + e);
            return new ArrayList();
        } finally {
            assert session != null;
            session.close();
        }
    }

    public static boolean checkUser(User user) {
        user.setPassword(HibernateUtil.hashString(user.getPassword()));
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", user.getUsername()));
        List list = criteria.list();
        User inBaseUser;
        try {
            inBaseUser = (User) list.get(0);
        } catch (Exception e) {
            return false;
        }
        return inBaseUser.equals(user);
    }



    public static User getFullParamsUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", user.getUsername()));
        return (User) criteria.uniqueResult();
    }

    public static List getUsers(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class);
        return (List<User>) criteria.list();
    }

}
