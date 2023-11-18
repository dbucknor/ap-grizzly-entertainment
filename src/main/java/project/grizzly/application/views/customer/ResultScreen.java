package project.grizzly.application.views.customer;

import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.screens.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResultScreen extends JPanel {
    private JScrollPane scrollPane;
    private JPanel uiContainer;
    private static final Color CATEGORY_COLOR = new Color(68, 138, 255);  // For example blue color
    private ThemeManager themeManager;

    public ResultScreen() {
        uiContainer = new JPanel();
        uiContainer.setLayout(new GridLayout(3, 4, 10, 10)); // 3 rows, 4 columns, with 10px horizontal and vertical gap
        uiContainer.setBackground(Color.WHITE); // Set the background color to white

        themeManager = ThemeManager.getInstance();
        uiContainer.setBackground(themeManager.getCurrentScheme().getNeutralLight());

        Product[] products = fetchProducts();

        addResultsToUI(products);

        scrollPane = new JScrollPane(uiContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);

        // Set the frame size and visibility
//        this.setSize(800, 600); // Set an appropriate size for the frame
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
//        this.setVisible(true); // Make the frame visible
    }

    private void addResultsToUI(Product[] products) {
        uiContainer.setLayout(new BorderLayout());

        JPanel resultsHeader = new JPanel(new BorderLayout());
        resultsHeader.setBackground(CATEGORY_COLOR);

        JLabel resultsLabel = new JLabel("Results");
        resultsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultsLabel.setForeground(Color.WHITE);

        resultsHeader.add(resultsLabel, BorderLayout.WEST);

        // Increase the height of the results header
        resultsHeader.setPreferredSize(new Dimension(uiContainer.getWidth(), 50));

        uiContainer.add(resultsHeader, BorderLayout.NORTH);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(0, 4, 10, 10)); // 4 columns, with 10px horizontal and vertical gap
        resultsPanel.setBackground(Color.WHITE); // Set the background color to white

        for (Product product : products) {
            JPanel productPanel = createProductPanel(product);
            resultsPanel.add(productPanel);
        }

        uiContainer.add(resultsPanel, BorderLayout.CENTER);
    }


    private JPanel createProductPanel(Product product) {
        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel productImageLabel = new JLabel(new ImageIcon("images/Screenshot 2023-10-31 201217.png")); // Assuming all products have the same image
        productImageLabel.setPreferredSize(new Dimension(350, 350)); // Adjust the size of the product image
        productImageLabel.setMaximumSize(new Dimension(350, 350)); // Set maximum size for the product image
        JLabel titleLabel = new JLabel(product.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel priceLabel = new JLabel(String.format("<html><div style='text-align: center; width: 100px; font-family: Arial; font-size: 16px; color: #000080;'>$%.2f/d</div></html>", product.getPrice()));
        JButton addButton = new JButton("Add");

        JLabel detailsLabel = new JLabel(product.getDetails());
        detailsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        detailsLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Styling the Add button to match the image
        addButton.setBackground(CATEGORY_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);

        JPanel priceAndButtonPanel = new JPanel(new BorderLayout());
        priceAndButtonPanel.setPreferredSize(new Dimension(350, 40));
        priceAndButtonPanel.setBackground(new Color(173, 216, 230)); // Sky blue color
        priceAndButtonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(135, 206, 250))); // Sky blue streak at the base

        priceAndButtonPanel.add(priceLabel, BorderLayout.WEST);
        priceAndButtonPanel.add(addButton, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 20, 10, 20); // Adjust padding as needed
        productPanel.add(priceAndButtonPanel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        productPanel.add(productImageLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 5, 10);  // top padding
        productPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(60, 10, 5, 10);  // bottom padding
        productPanel.add(detailsLabel, gbc);
        /*
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 10, 5);  // bottom padding and left padding
        productPanel.add(priceLabel, gbc);
*/
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 0, 128)); // Navy blue color
        addButton.setPreferredSize(new Dimension(80, 30)); // Fixed size for the button
        //gbc.gridx = 1;
        //gbc.gridy = 2;
        // gbc.insets = new Insets(5, 10, 10, 10); // bottom padding and right padding
        //productPanel.add(addButton, gbc);


        return productPanel;
    }


    private Product[] fetchProducts() {
        // Placeholder, updated to match the image details
        return new Product[]{
                // Lighting Products
                new Product("Lighting", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Lighting", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Lighting", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Lighting", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                // Sound Products
                new Product("Sound", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Sound", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Sound", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Sound", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                // Power Products
                new Product("Power", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Power", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Power", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Power", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                // Stage Products
                new Product("Stage", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Stage", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Stage", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light..."),
                new Product("Stage", "MLD DJ Par Light 60 LED RCB", 5000.0, "Slim Strobe Stage Wash Light...")
        };
    }

    public static void main(String[] args) {
        new ResultScreen();
    }
}
