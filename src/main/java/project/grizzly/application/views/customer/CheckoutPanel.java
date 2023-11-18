package project.grizzly.application.views.customer;

import java.awt.*;
import javax.swing.*;

public class CheckoutPanel extends JPanel {

    public CheckoutPanel() {
        setupPanel();
        addComponents();
    }

    private void setupPanel() {
        setBackground(new Color(255, 255, 255));
        setPreferredSize(new Dimension(200, 520));
        setLayout(new GridBagLayout());
    }

    private void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addLabelAndComboBox("Delivery Option:", createDeliveryOptionComboBox(), gbc);
        add(generateSpacer(), gbc);

        addLabelAndTextField("Delivery Address:", createTextField(), gbc);
        addLabelAndTextField("Address Line 1:", createTextField(), gbc);
        addLabelAndTextField("Address Line 2:", createTextField(), gbc);
        addLabelAndTextField("Parish:", createTextField(), gbc);

        add(generateSpacer(), gbc);

        add(createContainerPanel(), gbc);

        add(generateSpacer(), gbc);

        add(createButton("Confirm", new Color(0, 39, 97)), gbc);
        add(createButton("Cancel", new Color(255, 0, 0)), gbc);
        add(createButton("Download Invoice", new Color(230, 244, 241), new Color(0, 58, 118)), gbc);
    }

    private JComboBox<String> createDeliveryOptionComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Deliver", "Other"});
        applyCommonStyles(comboBox);
        return comboBox;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(10);
        applyCommonStyles(textField);
        textField.setBorder(null);
        return textField;
    }

    private JPanel createContainerPanel() {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerPanel.setBackground(Color.WHITE);

//        add(createMainPanel("Sub-total:"), containerPanel, gbc);
//        gbc.gridy++;
//        add(createMainPanel("Delivery:"), containerPanel, gbc);
//        gbc.gridy++;
//        add(createMainPanel("Discount:"), containerPanel, gbc);
//        gbc.gridy++;
//        add(createMainPanel("Total:"), containerPanel, gbc);

        return containerPanel;
    }

    private JPanel createMainPanel(String labelText) {
        JPanel mainPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(0);
        mainPanel.setLayout(flowLayout);
        mainPanel.setBackground(Color.WHITE);

        JPanel labelPanel = createLabelPanel(labelText);
        JTextField textField = createTextField();

        mainPanel.add(labelPanel);
        mainPanel.add(textField);

        return mainPanel;
    }

    private JPanel createLabelPanel(String labelText) {
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(230, 244, 241));
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setPreferredSize(new Dimension(54, 15));

        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(0, 58, 118));

        labelPanel.add(label, BorderLayout.CENTER);

        return labelPanel;
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        applyCommonStyles(button);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    private JButton createButton(String text, Color backgroundColor, Color foregroundColor) {
        JButton button = createButton(text, backgroundColor);
        button.setForeground(foregroundColor);
        return button;
    }

    private void addLabelAndComboBox(String labelText, JComboBox<String> comboBox, GridBagConstraints gbc) {
        add(new JLabel(labelText), gbc);
        gbc.gridy++;
        add(comboBox, gbc);
        gbc.gridy++;
    }

    private void addLabelAndTextField(String labelText, JTextField textField, GridBagConstraints gbc) {
        add(new JLabel(labelText), gbc);
        gbc.gridy++;
        add(textField, gbc);
        gbc.gridy++;
    }

    private Component generateSpacer() {
        return new JLabel("");
    }

    private void applyCommonStyles(JComponent component) {
        component.setBackground(new Color(230, 244, 241));
        component.setForeground(new Color(0, 58, 118));
    }

}
