package com.grizzly.application.views.components.fields;

import com.grizzly.application.models.interfaces.FieldListeners;
import com.grizzly.application.models.interfaces.IFormField;
import com.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.function.Function;

public class TextField extends JPanel implements IFormField<String> {
    protected String label;
    protected String value;
    protected JTextField field;
    protected JLabel fieldLbl;
    protected final ThemeManager theme;
    protected boolean obscure;
    protected boolean isTextArea;

    private final ArrayList<FieldListeners<String>> listeners;

    public TextField() {
//        super(BoxLayout.Y_AXIS);
        listeners = new ArrayList<>();
        theme = ThemeManager.getInstance();
        value = "";
        label = "Default Label";
        initComponents();
        setProps();
    }

    public TextField(String label) {
//        super(BoxLayout.Y_AXIS);
        listeners = new ArrayList<>();
        theme = ThemeManager.getInstance();

        this.value = "";
        this.label = label;

        initComponents();
        addListeners();
        setProps();
    }

    public TextField(String label, boolean obscure) {
//        super(BoxLayout.Y_AXIS);
        listeners = new ArrayList<>();
        theme = ThemeManager.getInstance();

        this.value = "";
        this.label = label;
        this.obscure = obscure;

        initComponents();
        addListeners();
        setProps();
    }

    public TextField(String label, String value) {
//        super(BoxLayout.Y_AXIS);
        listeners = new ArrayList<>();
        theme = ThemeManager.getInstance();

        this.value = value;
        this.label = label;

        initComponents();
        addListeners();
        setProps();
    }

    protected void initComponents() {
        field = obscure ? new JPasswordField() : new JTextField();

        field.setOpaque(true);
        field.setBackground(theme.getCurrentScheme().getAccent1());
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
        fieldLbl.setVisible(label != null);
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
        if (field.hasFocus()) {
            value = field.getText().trim();
            updateChangeListeners();
        }
    }

    protected void handleBlur() {
        value = field.getText().trim();
        updateBlurListeners();
    }

    public void updateChangeListeners() {
        for (FieldListeners<String> fl : listeners
        ) {
            fl.onChange(value);
        }
    }

    public void updateBlurListeners() {
        for (FieldListeners<String> fl : listeners
        ) {
            fl.onBlur(value);
        }
    }

    protected void setProps() {
        if (value != null && !value.isEmpty()) {
            field.setText(value);
        }
        this.setLayout(new BorderLayout());

        this.add(fieldLbl, BorderLayout.NORTH);
        this.add(field, BorderLayout.CENTER);

        this.setMinimumSize(new Dimension(300, label == null ? 40 : 65));
        this.setPreferredSize(new Dimension(300, label == null ? 40 : 65));
        this.setMaximumSize(new Dimension(300, label == null ? 40 : 65));
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

    public boolean isObscured() {
        return obscure;
    }

    public void setObscured(boolean obscure) {
        this.obscure = obscure;
    }

    public void setValue(String value) {
        this.value = value;
        field.setText(value);
    }

    @Override
    public void setEnabled(boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            field.setEnabled(enabled);
//            field.setForeground(enabled ? theme.getCurrentScheme().getPrimary() : theme.getCurrentScheme().getNeutralDark());
//            field.setBackground(enabled ? theme.getCurrentScheme().getAccent1() : theme.getCurrentScheme().getNeutralLight());
        });

        super.setEnabled(enabled);
    }

    public boolean getFieldEnabled() {
        return field.isEnabled();
    }

    public ArrayList<FieldListeners<String>> getListeners() {
        return listeners;
    }

    @Override
    public String toString() {
        return "TextField{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public void addListeners(FieldListeners<String> fl) {
        listeners.add(fl);
    }

    @Override
    public void removeListeners(FieldListeners<String> fl) {
        listeners.remove(fl);
    }
}

