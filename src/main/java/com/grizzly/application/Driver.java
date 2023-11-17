package com.grizzly.application;

import com.grizzly.application.controllers.AppController;
import com.grizzly.application.models.User;
import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.enums.UserType;
import com.grizzly.application.models.equipment.Light;
import com.grizzly.application.models.equipment.Sound;
import com.grizzly.application.services.Client;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@SpringBootApplication
public class Driver {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                // Number of core threads in the pool
                1,
                // Maximum number of threads in the pool
                2,
                // Keep-alive time for idle threads
                60L,
                TimeUnit.SECONDS,
                // Blocking queue used to store tasks
                new LinkedBlockingQueue<>()
        );

        executor.submit(() -> {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(Driver.class);
            builder.headless(false);
            System.setProperty("spring.devtools.restart.enabled", "false");
            ConfigurableApplicationContext context = builder.run(args);
        });

//        Future<Object> future = executor.submit((Callable<Object>) () -> {
//            Client c = Client.getInstance();
//            try {
//                System.out.println("Client 1");
//                c.sendAction("READ-ALL LIGHT");
//                return c.receiveResponse();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        });
//
//        Future<Object> f2 = executor.submit((Callable<Object>) () -> {
//            Client c = Client.getInstance();
//            try {
//                System.out.println("Client 2");
//                c.sendAction("READ-ALL USER");
//                return c.receiveResponse();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        });

//        Future<Object> f3 = executor.submit((Callable<Object>) () -> {
//            Client c = Client.getInstance();
//            try {
//                System.out.println("Client 3");
//                c.sendAction("READ-ALL RENTALREQUESTS");
//                return c.receiveResponse();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        });

//        while (true) {
//            try {
//                System.out.println("results: " + future.get());
//                System.out.println("results: " + f2.get());
//                System.out.println("results: " + f3.get());
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//            break;
////            break;
//        }
    }
}
