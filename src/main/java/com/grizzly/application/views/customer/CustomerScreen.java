package com.grizzly.application.views.customer;

import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.CustomCardLayout;
import com.grizzly.application.views.screens.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerScreen extends Screen {

    private CustomerHome hone;
    private CustomCardLayout cardLayout;
    private final ClassLoader loader;
    private final String CUSTOMER_HOME = "Customer-Home";

    public CustomerScreen() {
        logger = LogManager.getLogger(Screen.class);
        loader = this.getClass().getClassLoader();
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        cardLayout = new CustomCardLayout();
        container.setLayout(cardLayout);

        hone = new CustomerHome();
    }

    @Override
    public void addComponents() {
        container.add(hone);
        cardLayout.addLayoutComponent(hone, CUSTOMER_HOME);
        cardLayout.show(container, CUSTOMER_HOME);

        super.addComponents();
    }

    @Override
    public void addListeners() {
        super.addListeners();
    }

    @Override
    public void setProperties() {
        super.setProperties();
    }
}
