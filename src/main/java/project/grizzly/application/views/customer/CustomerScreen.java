package project.grizzly.application.views.customer;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.views.components.CustomCardLayout;
import project.grizzly.application.views.screens.Screen;
import org.apache.logging.log4j.LogManager;

public class CustomerScreen extends Screen {

    private CustomerHome hone;
    private CustomCardLayout cardLayout;
    private final ClassLoader loader;
    public static final String CUSTOMER_HOME = "Customer-Home";
    public static final String REQUEST_CART = "Request-Cart";
    private RequestCart requestCart;
    private CustomerScreenController controller;
    private static CustomerScreen instance;

    private CustomerScreen() {
        logger = LogManager.getLogger(Screen.class);
        loader = this.getClass().getClassLoader();
    }

    public static CustomerScreen getInstance() {
        if (instance == null) {
            instance = new CustomerScreen();
        }
        return instance;
    }

    public void changeScreen(String screen) {
        cardLayout.show(container, screen);
    }

    @Override
    public void initializeComponents() {
        controller = CustomerScreenController.getInstance();

        super.initializeComponents();
        cardLayout = new CustomCardLayout();
        container.setLayout(cardLayout);

        requestCart = new RequestCart();
        hone = new CustomerHome(controller);

    }

    @Override
    public void addComponents() {
        container.add(hone);
        cardLayout.addLayoutComponent(hone, CUSTOMER_HOME);

        container.add(requestCart);
        cardLayout.addLayoutComponent(requestCart, REQUEST_CART);

        cardLayout.show(container, REQUEST_CART);
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
