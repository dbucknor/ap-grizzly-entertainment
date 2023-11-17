package com.grizzly.application.services;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;


/**
 * ICreate, Read, Update, Delete implementation
 *
 * @param <T> objet class type
 * @param <K> object primary key class type
 */
public class CRUDService<T extends Serializable, K extends Serializable> implements ICRUD<T, K> {
    private Class<T> clazz = null;

    public CRUDService(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void insert(T record) throws HibernateException {
        Session session = HibernateSession.getSession();
        Transaction transaction = null;

        try {
            assert session != null;
            transaction = session.beginTransaction();
            session.save(record);
            transaction.commit();
            session.close();
            logger.info("Server Inserted record: " + record);
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public T read(K id) throws HibernateException {
        Session session = HibernateSession.getSession();
        Transaction transaction = null;

        try {
            assert session != null;
            transaction = session.beginTransaction();
            T obj = session.get(clazz, id);
            System.out.println(id);
            transaction.commit();
            session.close();
            logger.info("Server Read record: " + obj);
            return obj;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public List<T> readWhere(Function<Session, Query<T>> callback) throws HibernateException {

        try {
            Session session = HibernateSession.getSession();
            Transaction transaction = null;

            if (callback == null) throw new HibernateException("Query callback is null!");
            Query<T> q = callback.apply(session);

            assert session != null;
            transaction = session.beginTransaction();

            assert q != null;
            List<T> obj = q.getResultList();
            transaction.commit();
            session.close();
            logger.info("Server Read records: " + obj);
            return obj;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    public List<T> readAll() throws HibernateException {
        try {
            Session session = HibernateSession.getSession();
            assert session != null;
            Transaction transaction = session.beginTransaction();

            Query<T> q = session.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t");
            List<T> results = q.getResultList();

            transaction.commit();
            session.close();
            logger.info("Server Read records: " + results);
            return results;
        } catch (Exception he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public void update(T record) throws HibernateException {
        try {
            Session session = HibernateSession.getSession();
            assert session != null;
            Transaction transaction = session.beginTransaction();

            session.update(record);
            transaction.commit();

            session.close();
            logger.info("Server Updated record: " + record);
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public void update(Function<Session, Query<T>> callback) throws HibernateException {
        try {
            Session session = HibernateSession.getSession();
            Transaction transaction = null;

            assert session != null;
            transaction = session.beginTransaction();
            if (callback == null) throw new HibernateException("Query callback is null!");

            Query<T> q = callback.apply(session);
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }


    @Override
    public void delete(K id) throws HibernateException {
        try {
            Session session = HibernateSession.getSession();
            Transaction transaction = null;

            assert session != null;
            transaction = session.beginTransaction();
            session.delete(read(id));

            transaction.commit();
            session.close();
            logger.info("Server Deleted record with id: " + id);
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public void delete(Function<Session, Query<T>> callback) throws HibernateException {
        try {
            Session session = HibernateSession.getSession();
            Transaction transaction = null;

            assert session != null;
            transaction = session.beginTransaction();
            if (callback == null) throw new HibernateException("Query callback is null!");

            Query<T> q = callback.apply(session);
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    public void delete(K id, T record) throws HibernateException {
        try {
            Session session = HibernateSession.getSession();
            Transaction transaction = null;

            assert session != null;
            transaction = session.beginTransaction();
            session.delete(record);

            transaction.commit();
            session.close();
            logger.info("Server Deleted record with id: " + id);
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
