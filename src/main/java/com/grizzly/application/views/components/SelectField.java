package com.grizzly.application.views.components;

import com.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Function;

public class SelectField<T> extends JPanel {

    private T[] items;
    private T selectedItem;
    private JComboBox<T> comboBox;
    private String label;
    protected JLabel fieldLbl;
    protected Function<T, ?> onChangeCallBack;
    protected Function<T, ?> onBlurCallBack;
    protected ThemeManager theme = ThemeManager.getInstance();


    public SelectField(T[] items) {
        label = "Default Label";
        this.items = items;
        this.setLayout(new GridBagLayout());

        initComponents();
        addListeners();
        setProps();
    }

    public SelectField(T[] items, T defaultSelected, String label) {
        this.items = items;
        this.selectedItem = defaultSelected;
        this.label = label;
        this.setLayout(new GridBagLayout());

        initComponents();
        addListeners();
        setProps();
    }

    protected void initComponents() {
        comboBox = new JComboBox<T>(items);
        comboBox.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 5));
        comboBox.setBackground(theme.getCurrentScheme().getAccent1());
        comboBox.setPreferredSize(new Dimension(300, 40));
        comboBox.setMaximumSize(new Dimension(300, 40));
        comboBox.setFont(new Font("Montserrat Regular", Font.PLAIN, 18));
        comboBox.setForeground(Color.DARK_GRAY);
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        comboBox.setVisible(true);

        fieldLbl = new JLabel();
        fieldLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        fieldLbl.setFont(new Font("Montserrat Light", Font.PLAIN, 18));
        fieldLbl.setLabelFor(comboBox);
        fieldLbl.setForeground(Color.DARK_GRAY);
        fieldLbl.setText(label);
        fieldLbl.setAlignmentX(SwingConstants.LEFT);
        fieldLbl.setVisible(label != null);
    }

    private void addListeners() {
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedItem = (T) comboBox.getSelectedItem();
                if (onChangeCallBack != null) {
                    onChangeCallBack.apply(selectedItem);
                }

            }
        });

        comboBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                handleBlur();
            }
        });
    }

    protected void handleBlur() {
        if (onBlurCallBack != null) {
            onBlurCallBack.apply(selectedItem);
        }
    }

    private void setProps() {
        for (int i = 0; i < comboBox.getComponentCount(); i++) {
            Component component = comboBox.getComponent(i);
            if (component instanceof JComponent) {
                ((JComponent) component).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                if (component instanceof AbstractButton) {
                    ((AbstractButton) component).setBorderPainted(false);
                }
            }
        }

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 100;
        constraints.gridx = 0;
        this.add(fieldLbl, constraints);

        constraints.gridy = 200;
        this.add(comboBox, constraints);
        setMinimumSize(new Dimension(300, 80));
        this.setVisible(true);
    }

    public T[] getItems() {
        return items;
    }

    public void setItems(T[] items) {
        this.items = items;
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void onChange(Function<T, ?> onChangeCallBack) {
        this.onChangeCallBack = onChangeCallBack;
    }

    public void onBlur(Function<T, ?> onBlurCallBack) {
        this.onBlurCallBack = onBlurCallBack;
    }
}
