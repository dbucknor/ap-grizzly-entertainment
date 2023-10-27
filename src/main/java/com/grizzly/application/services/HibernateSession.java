package com.grizzly.application.services;

import com.grizzly.application.models.*;
import com.grizzly.application.models.equipment.Equipment;
import com.grizzly.application.models.equipment.MaintenanceLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {
    private static final Configuration config = new Configuration();
    private static SessionFactory sessionFactory = null;
    private final static Logger logger = LogManager.getLogger(HibernateSession.class);


    public static SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            config.configure("hibernate.cfg.xml");
            sessionFactory = config.addAnnotatedClass(Equipment.class).addAnnotatedClass(User.class).addAnnotatedClass(MaintenanceLog.class).addAnnotatedClass(Message.class).buildSessionFactory();
        }
        return sessionFactory;

    }

    public static Session getSession() {
        SessionFactory factory = getSessionFactory();
        if (factory != null) {
            return factory.openSession();
        } else {
            return null;
        }
    }
}
