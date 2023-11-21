

package project.grizzly.application.views.components.fields;

import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IFormField;
import project.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class TextArea extends JPanel implements IFormField<String> {
    protected String label;
    protected String value;
    protected JTextArea field;
    protected JLabel fieldLbl;

    protected final ThemeManager theme;
    private boolean resizable;
    private final ArrayList<FieldListeners<String>> listeners;

    public TextArea() {
        listeners = new ArrayList<>();
        theme = ThemeManager.getInstance();
        value = "";
        label = "Default Label";
        initComponents();
        setProps();
    }

    public TextArea(String label) {
        listeners = new ArrayList<>();
        theme = ThemeManager.getInstance();

        this.value = "";
        this.label = label;

        initComponents();
        addListeners();
        setProps();
    }

    public TextArea(String label, String value) {
        listeners = new ArrayList<>();
        theme = ThemeManager.getInstance();

        this.value = value;
        this.label = label;
        this.setLayout(new BorderLayout());

        initComponents();
        addListeners();
        setProps();
    }

    protected void initComponents() {
        this.setLayout(new BorderLayout());

        field = new JTextArea();
        field.setVisible(true);

        field.setOpaque(true);
        field.setBackground(theme.getCurrentScheme().getAccent1());
        field.setPreferredSize(new Dimension(300, 120));
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

        this.add(fieldLbl, BorderLayout.NORTH);
        this.add(field, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 145));
        this.setMaximumSize(new Dimension(300, 145));
        this.setMinimumSize(new Dimension(300, 65));
        this.setVisible(true);
    }

    public void setFieldEnabled(Boolean enabled) {
        field.setEnabled(enabled);
    }

    public boolean getFieldEnabled() {
        return field.isEnabled();
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

    public void setValue(String value) {
        this.value = value;
        field.setText(value);
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

