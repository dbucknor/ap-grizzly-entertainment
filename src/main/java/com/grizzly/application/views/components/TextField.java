package com.grizzly.application.views.components;

import com.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Function;

public class TextField extends JPanel {
    protected String label;
    protected String value;
    protected JTextField field;
    protected JLabel fieldLbl;
    protected ThemeManager theme = ThemeManager.getInstance();
    protected Function<String, String> onChangeCallBack;
    protected Function<String, String> onBlurCallBack;
    protected boolean obscure;

    public TextField() {
        value = "";
        label = "Default Label";
        initComponents();
        setProps();
    }

    public TextField(String label) {
        this.value = "";
        this.label = label;

        initComponents();
        addListeners();
        setProps();
    }

    public TextField(String label, boolean obscure) {
        this.value = "";
        this.label = label;
        this.obscure = obscure;

        initComponents();
        addListeners();
        setProps();
    }

    public TextField(String label, String value) {
        this.value = value;
        this.label = label;
        this.setLayout(new BorderLayout());

        initComponents();
        addListeners();
        setProps();
    }

    protected void handleBlur() {
        if (onBlurCallBack != null) {
            onBlurCallBack.apply(value);
        }
    }

    protected void addListeners() {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                handleBlur();
            }
        });

        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleChange();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleChange();
            }
        });
    }

    protected void handleChange() {
        if (field.hasFocus() && field.getText().compareTo(value) != 0) {
            value = field.getText().trim();
        }
        if (onChangeCallBack != null) {
            onChangeCallBack.apply(field.getText().trim());
        }

    }

    protected void initComponents() {
        this.setLayout(new BorderLayout());

        field = obscure ? new JPasswordField() : new JTextField();
        field.setVisible(true);

        field.setOpaque(true);
        field.setBackground(theme.getCurrentScheme().getAccent1());
        field.setPreferredSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 5));
        field.setFont(new Font("Montserrat Light", Font.PLAIN, 18));
        field.setForeground(theme.getCurrentScheme().getPrimary());
        field.setVisible(true);

        fieldLbl = new JLabel();
        fieldLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        fieldLbl.setFont(new Font("Montserrat Light", Font.PLAIN, 18));
        fieldLbl.setLabelFor(field);
        fieldLbl.setForeground(Color.DARK_GRAY); //TODO: impleme
        fieldLbl.setText(label);
        fieldLbl.setVisible(true);
    }


    protected void setProps() {
        if (value != null && !value.isEmpty()) {
            field.setText(value);
        }

        this.add(fieldLbl, BorderLayout.NORTH);
        this.add(field, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 65));
        this.setMaximumSize(new Dimension(300, 65));
        this.setMinimumSize(new Dimension(300, 65));
        this.setVisible(true);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void onChange(Function<String, String> onChange) {
        onChangeCallBack = onChange;
    }

    public void setOnBlurCallBack(Function<String, String> onBlur) {
        onBlurCallBack = onBlur;
    }

    public boolean isObscured() {
        return obscure;
    }

    public void setObscured(boolean obscure) {
        this.obscure = obscure;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TextField{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

