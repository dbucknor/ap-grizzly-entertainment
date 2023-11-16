package com.grizzly.application.views.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ProductScreen extends Screen {
	private JScrollPane scrollPane;
	private JPanel uiContainer;
	private static final Color CATEGORY_COLOR = new Color(68, 138, 255);  // For example blue color
	    
	    
    public ProductScreen() {
        uiContainer = new JPanel();
        uiContainer.setLayout(new BoxLayout(uiContainer, BoxLayout.Y_AXIS));

        ThemeManager themeManager = ThemeManager.getInstance();
        uiContainer.setBackground(themeManager.getBackgroundColor());

        Product[] products = fetchProducts();
        Map<String, List<Product>> categorizedProducts = groupProductsByCategory(products);

        for (Map.Entry<String, List<Product>> entry : categorizedProducts.entrySet()) {
            addCategoryToUI(entry.getKey(), entry.getValue());
        }
        
        scrollPane = new JScrollPane(uiContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        container.add(scrollPane, BorderLayout.CENTER);
    }

    private void addCategoryToUI(String category, List<Product> products) {
        JPanel categoryHeader = new JPanel(new BorderLayout());
        categoryHeader.setBackground(CATEGORY_COLOR);
        
        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set the font size and style
        categoryLabel.setForeground(Color.WHITE);
        
        JButton seeAllButton = new JButton("See All");
        seeAllButton.setForeground(Color.WHITE);
        seeAllButton.setBackground(new Color(0, 0, 128)); // Navy blue color
        categoryHeader.add(seeAllButton, BorderLayout.EAST);
        categoryHeader.add(categoryLabel, BorderLayout.WEST);

        uiContainer.add(categoryHeader);

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(0, 4)); 

        for (Product product : products) {
            JPanel productPanel = createProductPanel(product);
            categoryPanel.add(productPanel);
        }

        uiContainer.add(categoryPanel);
        uiContainer.add(Box.createRigidArea(new Dimension(0, 20)));  // Spacer between categories
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
    	return new Product[] {
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

    private Map<String, List<Product>> groupProductsByCategory(Product[] products) {
        Map<String, List<Product>> map = new HashMap<>();
        for (Product product : products) {
            map.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);
        }
        return map;
    }
    
    public static void main(String[] args) {
        new ProductScreen() {};  // Anonymous instance to initiate the abstract class for testing
    }
}
