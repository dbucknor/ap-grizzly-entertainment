package com.grizzly.application.views.employee;

import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.CustomCardLayout;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.views.components.fields.Button;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeHome extends JPanel implements IView {
    private Button requests, chats, tables;
    private final ThemeManager theme;
    JComponent cardContainer;
    CustomCardLayout cardLayout;

    public EmployeeHome(JComponent cardContainer, CustomCardLayout cardLayout) {
        super(new GridBagLayout());
        theme = ThemeManager.getInstance();
        this.cardContainer = cardContainer;
        this.cardLayout = cardLayout;
        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }


    @Override
    public void initializeComponents() {
        requests = new Button(FontAwesomeSolid.FILE_INVOICE);
        requests.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());
        tables = new Button(FontAwesomeSolid.SERVER);
        tables.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());
        chats = new Button(Ionicons4IOS.CHATBUBBLES);
        chats.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());

        requests.setIconSize(90);
        tables.setIconSize(90);
        chats.setIconSize(90);
    }

    @Override
    public void addComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(50, 50, 50, 50);
        this.add(bigButton(requests, "Requests"), constraints);

        constraints.gridx = 1;
        this.add(bigButton(tables, "Tables"), constraints);

        constraints.gridx = 2;
        this.add(bigButton(chats, "Chats"), constraints);
    }

    private JPanel bigButton(Button button, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(200, 250));
        panel.setBackground(theme.getCurrentScheme().getNeutralLight());

        JLabel lbl = new JLabel(label);
        lbl.setLabelFor(panel);
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(18f));
        lbl.setForeground(theme.getCurrentScheme().getPrimary());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(button, constraints);
        constraints.insets = new Insets(20, 0, 0, 0);
        constraints.gridy = 1;
        panel.add(lbl, constraints);

        return panel;
    }

    @Override
    public void addListeners() {
        requests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Requests");
            }
        });
        chats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Chats");
            }
        });
        tables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Tables");
            }
        });
    }

    @Override
    public void setProperties() {
        this.setVisible(true);
    }
}
