package com.grizzly.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Driver {

    public static void main(String[] args) {
//        String query = "SELECT u FROM User u WHERE u.email = :email :email :email :email :j :man";
//        List<String> s = Set.copyOf(Arrays.stream(query.split(" ")).filter((str) -> str.contains(":")).toList()).stream().toList();
//        System.out.println(s);
//
//        AuthService service = new AuthService();
//        User user = service.logIn("bucknor.dorian@gmail.comm", "password");
//        System.out.println(user);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Driver.class);
        builder.headless(false);
        System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext context = builder.run(args);
    }
}
