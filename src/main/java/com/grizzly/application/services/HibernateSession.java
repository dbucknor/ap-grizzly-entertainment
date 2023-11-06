package com.grizzly.application.services;

import com.grizzly.application.models.*;
import com.grizzly.application.models.equipment.*;
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
            sessionFactory = config
                    .addAnnotatedClass(Equipment.class)
                    .addAnnotatedClass(Sound.class)
                    .addAnnotatedClass(Light.class)
                    .addAnnotatedClass(Power.class)
                    .addAnnotatedClass(Stage.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Chat.class)
                    .addAnnotatedClass(Message.class)
                    .addAnnotatedClass(RentalRequest.class)
                    .addAnnotatedClass(Invoice.class)
                    .addAnnotatedClass(InvoiceItem.class)
//                    .addAnnotatedClass(MaintenanceLog.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession() throws HibernateException {
        SessionFactory factory = getSessionFactory();
        if (factory != null) {
            return factory.openSession();
        } else {
            return null;
        }
    }
}
