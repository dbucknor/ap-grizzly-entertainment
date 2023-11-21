package project.grizzly.application.views.components;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.enums.ButtonSize;

import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;
import project.grizzly.application.views.screens.MainWindow;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;

public class CartItem extends JPanel implements IView {

    private JPanel checkPanel;
    private JLabel name, price, image, periodLbl;
    private Button button;
    private ThemeManager theme;
    private InvoiceItem item;

    public CartItem(InvoiceItem item) {
        super(new GridBagLayout());
        this.theme = ThemeManager.getInstance();
        this.item = item;

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    public void initializeComponents() {
        periodLbl = new JLabel(item.getRentalStartDate().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy HH:mm a")) + " - " + item.getRentalEndDate().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy HH:mm a")));

        button = new Button("Remove", ButtonSize.SMALL);
        price = new JLabel(Double.toString(item.getTotalPrice()));

        checkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        checkPanel.setBackground(theme.getCurrentScheme().getAccent1());

        checkPanel.add(periodLbl);
        checkPanel.add(price);
        checkPanel.add(button);

        name = new JLabel(item.getEquipment().getName());
        image = new JLabel(new ImageIcon(item.getEquipment().getImage().getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));//todo
//        image = new JLabel(image);//todo
    }

    public void addComponents() {
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 5;
        c.gridwidth = 5;
        c.insets = new Insets(0, 0, 0, 5);
        this.add(image, c);

        c.gridx = 6;
        c.gridy = 0;
        c.gridheight = 3;
        c.gridwidth = 15;
        this.add(name, c);

        c.gridx = 6;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 15;
        this.add(checkPanel, c);
    }

    public void addListeners() {

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerScreenController.getInstance().removeFromRequest(item);
                setVisible(false);
            }
        });
    }

    public void setProperties() {
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLUE);
        Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, bottomBorder);


        this.setBackground(theme.getCurrentScheme().getNeutralLight());
        this.setBorder(compoundBorder);
        this.setVisible(true);
    }
}

/*
*

    private JPanel listIem(){
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLUE);
        Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, bottomBorder);

        JPanel itemPanel = new JPanel(new GridBagLayout());
                itemPanel.setBackground(new Color(255, 255, 255));
                itemPanel.setBorder(compoundBorder);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 5);

        Image image = Toolkit.getDefaultToolkit().getImage("");
        ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        JTextArea nameTextArea = new JTextArea(ITEM_NAME);
       nameTextArea.setFont(new Font("Arial", Font.BOLD, 12));
       nameTextArea
       nameTextArea.setWrapStyleWord(true);
       nameTextArea.setPreferredSize(new Dimension(990, 150));
       itemPanel.add(nameTextArea, c);


        JLabel imageLabel = new JLabel(icon);
        itemPanel.add(imageLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        // JPanel NamePanel=new JPanel();

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2; // span two columns
        c.fill = GridBagConstraints.HORIZONTAL; // allow horizontal expansion
        c.insets = new Insets(5, 0, 0, 0);

        JPanel checkPanel = new JPanel(new FlowLayout());
        checkPanel.setBackground(color);

        JTextField qtyLabel = new JTextField(ITEM_QTY);
        qtyLabel.setPreferredSize(new Dimension(190, 30));
        qtyLabel.setForeground(color5);
        qtyLabel.setBackground(color);
        qtyLabel.setBorder(null);
        qtyLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        checkPanel.add(qtyLabel);

        JTextField periodLabel = new JTextField(ITEM_PERIOD);
        periodLabel.setPreferredSize(new Dimension(200, 30));
        periodLabel.setForeground(color5);
        periodLabel.setBackground(color);
        periodLabel.setBorder(null);
        periodLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        checkPanel.add(periodLabel);

        JTextField priceLabel = new JTextField(ITEM_PRICE);
        priceLabel.setPreferredSize(new Dimension(200, 30));
        priceLabel.setForeground(color5);
        priceLabel.setBackground(color);
        priceLabel.setBorder(null);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        checkPanel.add(priceLabel);

        JButton removeButton = new JButton(REMOVE_BUTTON_TEXT);
        removeButton.setBackground(color2);
        removeButton.setForeground(Color.WHITE);
        checkPanel.add(removeButton);

        itemPanel.add(checkPanel, c);
        return  panel;
    }

*
* */