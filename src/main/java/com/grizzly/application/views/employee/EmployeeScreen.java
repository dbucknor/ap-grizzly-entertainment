package com.grizzly.application.views.employee;

import com.grizzly.application.views.chats.Chats;
import com.grizzly.application.views.components.CustomCardLayout;
import com.grizzly.application.views.screens.Screen;
import com.grizzly.application.views.components.fields.Button;
import com.grizzly.application.views.Tables;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main screen for employee application
 */
public class EmployeeScreen extends Screen {
    private JPanel sidePanel;
    private CustomCardLayout cardLayout;
    private Button home, requests, tables, chats;
    private EmployeeHome employeeHome;
    private RentalRequests requestsView;
    private Tables tablesViews;

    @Override
    public void initializeComponents() {
        cardLayout = new CustomCardLayout();

        requestsView = new RentalRequests();
        tablesViews = new Tables();

        sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 10, Toolkit.getDefaultToolkit().getScreenSize().height));
        sidePanel.setBackground(theme.getCurrentScheme().getNeutralLight());
        sidePanel.setVisible(true);

        initializeButtons();
        super.initializeComponents();

        employeeHome = new EmployeeHome(container, cardLayout);
    }

    private void initializeButtons() {
        home = new Button(Ionicons4IOS.HOME);
        home.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());
        requests = new Button(FontAwesomeSolid.FILE_INVOICE);
        requests.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());
        tables = new Button(FontAwesomeSolid.DATABASE);
        tables.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());
        chats = new Button(Ionicons4IOS.CHATBUBBLES);
        chats.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());

        home.setIconSize(40);
        requests.setIconSize(40);
        tables.setIconSize(40);
        chats.setIconSize(40);
    }

    @Override
    public void addComponents() {
        container.setLayout(cardLayout);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(15, 10, 15, 10);
        sidePanel.add(home, constraints);

        constraints.gridy = 1;
        sidePanel.add(requests, constraints);

        constraints.gridy = 2;
        sidePanel.add(tables, constraints);

        constraints.gridy = 3;
        sidePanel.add(chats, constraints);

        container.add(employeeHome);
        container.add(requestsView);
        container.add(tablesViews.getMainPanel());

        cardLayout.addLayoutComponent(employeeHome, "Home");
        cardLayout.addLayoutComponent(requestsView, "Requests");
        cardLayout.addLayoutComponent(tablesViews.getMainPanel(), "Tables");
        cardLayout.show(container, "Requests");


        this.add(sidePanel, BorderLayout.WEST);
        super.addComponents();
    }


    @Override
    public void addListeners() {
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "Home");
            }
        });
        requests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "Requests");
            }
        });
        chats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "Chats");
            }
        });
        tables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "Tables");
            }
        });
        super.addListeners();
    }

    @Override
    public void setProperties() {
        super.setProperties();
    }

    public CustomCardLayout getCardLayout() {
        return cardLayout;
    }

}
