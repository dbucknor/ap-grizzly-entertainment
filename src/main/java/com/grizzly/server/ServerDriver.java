package com.grizzly.server;

import com.grizzly.application.models.*;
import com.grizzly.application.models.equipment.*;
import com.grizzly.application.services.CRUDService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

        cruds.put("CHAT", new CRUDService<Chat, String>(Chat.class));
        cruds.put("MESSAGE", new CRUDService<Message, String>(Message.class));
        return cruds;
    }

    @Bean
    public int port() {
        return 8888;
    }


    public static void main(String[] args) {
        System.setProperty("server.port", Integer.toString(9001));
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ServerDriver.class);
        logger.info("Server has Started");
    }
}
