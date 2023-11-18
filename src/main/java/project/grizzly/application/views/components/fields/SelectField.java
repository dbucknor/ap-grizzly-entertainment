package project.grizzly.application.views.components.fields;

import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IFormField;
import project.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SelectField<T> extends JPanel implements IFormField<T> {

    private T[] items;
    private T selectedItem;
    private JComboBox<T> field;
    private String label;
    protected JLabel fieldLbl;
    protected ThemeManager theme = ThemeManager.getInstance();
    private final ArrayList<FieldListeners<T>> listeners;


    public SelectField(T[] items) {
        listeners = new ArrayList<>();
        label = "Default Label";
        this.items = items;
        this.setLayout(new GridBagLayout());

        initComponents();
        addListeners();
        setProps();
    }

    public SelectField(T[] items, T defaultSelected, String label) {
        listeners = new ArrayList<>();

        this.items = items;
        this.label = label;
        this.selectedItem = defaultSelected;
        this.setLayout(new GridBagLayout());

        initComponents();
        addListeners();
        setProps();
    }

    protected void initComponents() {
        field = new JComboBox<>(items);

        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 5));
        field.setBackground(theme.getCurrentScheme().getAccent1());
        field.setPreferredSize(new Dimension(300, 40));

        field.setMaximumSize(new Dimension(300, 40));
        field.setFont(new Font("Montserrat Regular", Font.PLAIN, 18));
        field.setForeground(field.isEnabled() ? Color.DARK_GRAY : theme.getCurrentScheme().getNeutralDark());

        ((JLabel) field.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        field.setVisible(true);

        fieldLbl = new JLabel();
        fieldLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        fieldLbl.setFont(new Font("Montserrat Light", Font.PLAIN, 18));

        fieldLbl.setLabelFor(field);
        fieldLbl.setForeground(Color.DARK_GRAY);

        fieldLbl.setText(label);
        fieldLbl.setAlignmentX(SwingConstants.LEFT);
        fieldLbl.setVisible(label != null);
    }

    private void addListeners() {
        field.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!field.isEnabled()) return;
                handleChange();
            }
        });

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                handleBlur();
            }
        });
    }

    private void setProps() {
        for (Component component : field.getComponents()
        ) {
            if (component instanceof JComponent f) {
                ((JComponent) component).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }
            if (component instanceof AbstractButton btn) {
                btn.setBorderPainted(false);
            }
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridy = 0;
        constraints.gridx = 0;
        this.add(fieldLbl, constraints);

        constraints.gridy = 1;
        this.add(field, constraints);
        this.setOpaque(false);
        this.setMaximumSize(new Dimension(300, 60));
        this.setBackground(null);
        this.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            this.field.setSelectedItem(selectedItem != null ? selectedItem : items[0]);

            handleChange();
            handleBlur();
        });
    }

    protected void handleChange() {
        selectedItem = (T) field.getSelectedItem();
        updateChangeListeners();
    }

    protected void handleBlur() {
        selectedItem = (T) field.getSelectedItem();
        updateBlurListeners();
    }

    public void updateChangeListeners() {
        for (FieldListeners<T> fl : listeners
        ) {
            fl.onChange(selectedItem);
        }
    }

    public void updateBlurListeners() {
        for (FieldListeners<T> fl : listeners
        ) {
            fl.onBlur(selectedItem);
        }
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
        this.field.setSelectedItem(selectedItem);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void addListeners(FieldListeners<T> fl) {
        this.listeners.add(fl);
    }

    @Override
    public void setEnabled(boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            for (Component component : field.getComponents()
            ) {
                this.field.setEnabled(enabled);

                if (component instanceof JComponent f) {
                    ((JComponent) component).setEnabled(enabled);
                }
            }
        });
        super.setEnabled(enabled);
    }

    public boolean getFieldEnabled() {
        return this.field.isEnabled();
    }

    @Override
    public void removeListeners(FieldListeners<T> fl) {
        listeners.remove(fl);

    }
}
