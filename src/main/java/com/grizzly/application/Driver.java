package com.grizzly.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class Driver {

    Driver() {
        JFrame f = new JFrame("Componenets Test");
        f.setPreferredSize(new Dimension(800, 500));
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SpringApplication.run(Driver.class, args);
    }
}
