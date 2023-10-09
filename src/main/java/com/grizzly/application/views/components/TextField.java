package com.grizzly.application.views.components;

import com.grizzly.application.views.ThemeManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Function;

public class TextField extends JPanel {
    private String label;
    private String value;
    private JTextField field;
    private JLabel fieldLbl;
    private ThemeManager theme = ThemeManager.getInstance();
    private Function<String, String> onChangeCallBack;
    private Function<String, String> onBlurCallBack;
    private String type;

    public TextField() {
        super();
        value = "";
        label = "Default Label";
        type = "text";
        initComponents();
        setProps();
    }

    public TextField(String label, String value, String type) {
        super();
        this.value = value;
        this.label = label;
        this.type = type;
        this.setLayout(new BorderLayout());

        initComponents();
        addListeners();
        setProps();
    }

    private void addListeners() {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (onBlurCallBack != null) {
                    onBlurCallBack.apply(value);
                }
            }
        });

        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleValueChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleValueChange();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleValueChange();

            }
        });
    }

    private void handleValueChange() {
        if (field.hasFocus() && field.getText().compareTo(value) != 0) {
//            System.out.println(field.getText());
            value = field.getText().trim();
        }
        if (onChangeCallBack != null) {
            onChangeCallBack.apply(field.getText().trim());
        }

    }

    private void initComponents() {
        field = new JTextField();

        if (type.toLowerCase().compareTo("password") == 0) {
            field = new JPasswordField();
        }

        field.setOpaque(true);
        field.setBackground(theme.getAccent1());
        field.setPreferredSize(new Dimension(200, 30));
//        field.setSize(new Dimension(200, 30));
        field.setBorder(null);
//        field.setMaximumSize(new Dimension(-1, -1));
        field.setVisible(true);

        fieldLbl = new JLabel();
        fieldLbl.setText(label);
        fieldLbl.setVisible(true);


    }

    private void setProps() {
        this.add(fieldLbl, BorderLayout.NORTH);
        this.add(field, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(200, 50));

        this.setVisible(true);


//        this.setMinimumSize(new Dimension(-1, -1));
//        this.setMaximumSize(new Dimension(-1, -1));

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

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
