package com.grizzly.application;

import com.grizzly.application.controllers.AppController;
import com.grizzly.application.views.MainWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class Driver {

    public static void main(String[] args) {
        AppController controller = new AppController();
        controller.showGUI();
//        SpringApplication.run(Driver.class, args);
    }
}
