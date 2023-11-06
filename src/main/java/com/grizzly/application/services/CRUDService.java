package com.grizzly.application.services;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Creates a CRUD service
 *
 * @param <T> Data Class
 * @param <K> Id type of Data Class
 */
public class CRUDService<T extends Serializable, K extends Serializable> implements ICRUD<T, K> {
    private Class<T> typeParameterClass = null;

    public CRUDService() {
        // Get the superclass of the CRUDService class
        Type superclass = this.getClass().getGenericSuperclass();

        // Check if the superclass is a ParameterizedType
        if (superclass instanceof ParameterizedType) {
            // Get the type arguments of the superclass
            Type[] typeArguments = ((ParameterizedType) superclass).getActualTypeArguments();

            // Get the type parameter class of the CRUDService class
            typeParameterClass = (Class<T>) typeArguments[0];
        }
    }

    public CRUDService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
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
            T obj = session.get(typeParameterClass, id);
            System.out.println(obj);
            transaction.commit();
            session.close();
            return obj;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public T read(String query, List<?> paramters) throws HibernateException {
//        Session session = HibernateSession.getSession();
//        Transaction transaction = null;
//
//        try {
//            assert session != null;
//            transaction = session.beginTransaction();
//            T obj = session.delete(read(id));
//            transaction.commit();
//            session.close();
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        }
        return null;
    }

    public List<T> readWhere(Function<Session, Query<T>> callback) throws HibernateException {
        Session session = HibernateSession.getSession();
        Transaction transaction = null;

        Query q = null;
        if (callback != null) {
            q = callback.apply(session);
        }
        try {
            assert session != null;
            transaction = session.beginTransaction();

            assert q != null;
            List<T> obj = q.getResultList();
            transaction.commit();
            session.close();
            return obj;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public List<T> readALL(String sql, List<?> parameters) throws HibernateException {
        List<String> s = Arrays.stream(sql.split(" ")).filter((str) -> str.contains(":")).toList();
        try {
            return readWhere((session -> {
                Query<T> q = session.createQuery(sql);
                int i = 1;
                for (Object o : parameters
                ) {
                    q.setParameter(i, o);
                }
                return q;
            }));
        } catch (HibernateException he) {
            throw he;
        }

    }


    public List<T> readALL() throws HibernateException {
        Session session = HibernateSession.getSession();
        Transaction transaction = null;
        try {
            assert session != null;
            transaction = session.beginTransaction();

            Query<T> q = session.createQuery("SELECT t FROM " + typeParameterClass.getName() + " t");

            List<T> obj = q.getResultList();
            transaction.commit();
            session.close();
            return obj;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

//    public T findByEmail(String email) {
//        List<T> results = readWhere((session -> {
//            CombinedQuery<T> q = new CombinedQuery<User>("SELECT u FROM User u");
//            return q.where("u.email", "=:email", email).getQuery(session);
//        }));
//
//        if (results.isEmpty()) {
//            return null;
//        } else {
//            logger.info(results.get(0));
//            return results.get(0);
//        }
//    }

    @Override
    public void update(T record) throws Exception {
        Session session = HibernateSession.getSession();
        Transaction transaction = null;

        try {
            assert session != null;
            transaction = session.beginTransaction();
            session.update(record);
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

//    @Override
//    public void update(K id) throws Exception {
//        Session session = HibernateSession.getSession();
//        Transaction transaction = null;
//
//        try {
//            assert session != null;
//            transaction = session.beginTransaction();
//            session.update(read(id));
//            transaction.commit();
//            session.close();
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        }
//    }

    @Override
    public void update(String query, List<?> paramters) throws Exception {

    }


    @Override
    public void delete(K id) throws HibernateException {
        Session session = HibernateSession.getSession();
        Transaction transaction = null;

        try {
            assert session != null;
            transaction = session.beginTransaction();
            session.delete(read(id));
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    public void delete(K id, T record) throws HibernateException {
        Session session = HibernateSession.getSession();
        Transaction transaction = null;

        try {
            assert session != null;
            transaction = session.beginTransaction();
            session.delete(record);
            transaction.commit();
            session.close();
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @Override
    public void delete(String query, List<?> paramters) throws HibernateException {

    }

    public Class<T> getTypeParameterClass() {
        return typeParameterClass;
    }

    public void setTypeParameterClass(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }
}
