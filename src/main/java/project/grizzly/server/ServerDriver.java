package project.grizzly.server;

import project.grizzly.application.services.CRUDService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import project.grizzly.application.models.*;
import project.grizzly.application.models.equipment.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

@SpringBootApplication
public class ServerDriver {
    private static final Logger logger = LogManager.getLogger(ServerDriver.class);

    @Bean
    public HashMap<String, CRUDService> createCruds() {
        HashMap<String, CRUDService> cruds = new HashMap<>();

        cruds.put("USER", new CRUDService<User, String>(User.class));
        cruds.put("EMPLOYEE", new CRUDService<Employee, String>(Employee.class));
        cruds.put("CUSTOMER", new CRUDService<Customer, String>(Customer.class));

        cruds.put("EQUIPMENT", new CRUDService<Equipment, String>(Equipment.class));
        cruds.put("POWER", new CRUDService<Power, String>(Power.class));
        cruds.put("LIGHT", new CRUDService<Light, String>(Light.class));
        cruds.put("STAGE", new CRUDService<Stage, String>(Stage.class));
        cruds.put("SOUND", new CRUDService<Sound, String>(Sound.class));

        cruds.put("INVOICE", new CRUDService<Invoice, Integer>(Invoice.class));
        cruds.put("INVOICEITEM", new CRUDService<InvoiceItem, Integer>(InvoiceItem.class));
        cruds.put("RENTALREQUEST", new CRUDService<RentalRequest, Integer>(RentalRequest.class));
        cruds.put("TRANSACTION", new CRUDService<Transaction, Integer>(Transaction.class));

        cruds.put("CHAT", new CRUDService<Chat, String>(Chat.class));
        cruds.put("MESSAGE", new CRUDService<Message, String>(Message.class));
        return cruds;
    }

    @Bean
    public int port() {
        return 8888;
    }

    @Bean
    public Connection getDBConnection() {
        String DB_URL = "jdbc:mysql://localhost:3306/";
        String USER = "root";
        String PASSWORD = "#h@rV3st";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        ) {
            logger.info("Database Loaded: " + conn.getMetaData().toString());
            return conn;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    public static void main(String[] args) {
        System.setProperty("server.port", Integer.toString(9001));
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ServerDriver.class);
        logger.info("Server has Started");
    }
}
