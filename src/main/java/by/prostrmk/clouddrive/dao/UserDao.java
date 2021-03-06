package by.prostrmk.clouddrive.dao;

import by.prostrmk.clouddrive.model.entity.User;
import by.prostrmk.clouddrive.model.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserDao extends AbstractDao implements Dao{

    public boolean checkUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", user.getUsername()));
        User baseUser = (User)criteria.uniqueResult();
        boolean equals;
        try{
            user.setId(baseUser.getId());
            equals = baseUser.equals(user);
            return equals;
        }catch (Exception e){
            return false;
        }

    }

    public boolean checkUser(String username){
        User user = new User(username);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", user.getUsername()));
        User baseUser = (User)criteria.uniqueResult();
        try{
            return username.equals(baseUser.getUsername());
        }catch (NullPointerException e){
            return false;
        }
    }


}
