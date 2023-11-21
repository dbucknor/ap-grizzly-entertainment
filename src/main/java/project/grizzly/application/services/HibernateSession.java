package project.grizzly.application.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import project.grizzly.application.models.*;
import project.grizzly.application.models.equipment.*;

import java.util.EnumSet;

public class HibernateSession {
    private static final Configuration config = new Configuration();
    private static SessionFactory sessionFactory = null;
    private final static Logger logger = LogManager.getLogger(HibernateSession.class);

    public static SessionFactory getSessionFactory() throws HibernateException {
        try {
            if (sessionFactory == null) {
                config.configure("hibernate.cfg.xml");
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties());

                ServiceRegistry serviceRegistry = builder.build();
                MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                metadataSources.addAnnotatedClass(Equipment.class)
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
                        .addAnnotatedClass(Transaction.class);
                Metadata metadata = metadataSources.buildMetadata();
                SchemaExport schemaExport = new SchemaExport();
                schemaExport.setFormat(true);
                schemaExport.setOutputFile("C:\\JDK\\schema.sql");
                schemaExport.create(EnumSet.of(TargetType.SCRIPT), metadata);
                sessionFactory = metadata.buildSessionFactory();
            }
            return sessionFactory;
        } catch (Error e) {
            logger.error(e.getMessage());
            return null;
        }


    }

    public static Session getSession() throws HibernateException {
        SessionFactory factory = getSessionFactory();
        if (factory != null) {
            return factory.openSession();
        } else {
            throw new HibernateException("Error creating session!");
        }
    }
}
