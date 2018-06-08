package by.prostrmk.clouddrive.dao;

import by.prostrmk.clouddrive.model.entity.IEntity;
import by.prostrmk.clouddrive.model.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class AbstractDao implements Dao {

    private static final Logger LOGGER = Logger.getLogger(AbstractDao.class);

    @Override
    public IEntity getById(Long id, Class clazz) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.eq("id", id));
        return (IEntity) criteria.uniqueResult();
    }

    @Override
    public IEntity getByStringParamUnique(String paramName, String paramValue, Class clazz) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.eq(paramName, paramValue));
        return (IEntity) criteria.uniqueResult();
    }

    @Override
    public List getByStringParamList(String paramName, String paramValue, Class clazz) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(clazz);
        criteria.add(Restrictions.eq(paramName,paramValue));
        return criteria.list();
    }

    @Override
    public List getAll(String param, Class clazz) {
        return HibernateUtil.getSessionFactory().openSession().createCriteria(clazz).list();

    }

    @Override
    public List getLatest(String tableName, int count) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT * FROM " + tableName + "ORDER BY id DESC").setMaxResults(count).list();

    }

    @Override
    public void saveEntity(IEntity entity) {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }catch (Exception e){
            LOGGER.error(e);
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public void deleteEntity(IEntity entity) {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }catch (Exception e){
            LOGGER.error(e);
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateEntity(IEntity entity) {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch (Exception e){
            LOGGER.error(e);
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }




}
