package com.grizzly.application.views.screens;

import com.grizzly.application.controllers.TableController;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.Validator;
import com.grizzly.application.models.enums.ButtonSize;
import com.grizzly.application.models.interfaces.FieldListeners;
import com.grizzly.application.models.interfaces.ITableEntity;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.models.TableFormMode;
import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.fields.*;
import com.grizzly.application.views.components.fields.Button;
import com.grizzly.application.views.components.fields.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EntityEditForm<T extends ITableEntity, K extends Serializable> extends JDialog implements IView {
    protected final TableFormMode mode;
    protected final TableController<T, K> controller;
    protected JScrollPane scrollPane;
    protected Box panel, buttons;
    protected Button edit, cancel;
    protected final ThemeManager theme;
    protected String name;
    protected Dimension frameSize;
    protected final Logger logger;

    public EntityEditForm(String name, TableController<T, K> controller, TableFormMode mode) {
        super(MainWindow.getInstance(), mode.toString() + " RECORD", true);

        this.setLayout(new BorderLayout());
        this.name = name;
        this.frameSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 3, Toolkit.getDefaultToolkit().getScreenSize().height - 150);

        this.controller = controller;
        this.mode = mode;
        this.theme = ThemeManager.getInstance();

        this.logger = LogManager.getLogger(EntityEditForm.class);

        if (mode != TableFormMode.CREATE && !controller.getSelectedRecords().isEmpty()) {
            fetchRecord();
        }

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        panel = new Box(BoxLayout.Y_AXIS);
        panel.setMinimumSize(frameSize);
        panel.setOpaque(true);
        panel.setVisible(true);

        buttons = new Box(BoxLayout.X_AXIS);
        buttons.setAlignmentX(Box.RIGHT_ALIGNMENT);
        buttons.setOpaque(true);
        buttons.setBackground(Color.GRAY);
        buttons.setVisible(true);

        scrollPane = new JScrollPane(panel);
        String btnTxt = Arrays.stream((mode.toString()).split(" ")).map((s) -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()).reduce((s, c) -> s + " " + c).get();
        edit = new Button(btnTxt, ButtonSize.NORMAL);
        cancel = new Button("Cancel", ButtonSize.NORMAL);
        cancel.setButtonColor(theme.getCurrentScheme().getAccent1(), theme.getCurrentScheme().getPrimary());
    }

    @Override
    public void addComponents() {
        buttons.add(edit);
        buttons.add(Box.createRigidArea(new Dimension(20, 0)));
        buttons.add(cancel);
        buttons.add(Box.createRigidArea(new Dimension(20, 0)));

        panel.add(Box.createHorizontalStrut(50));
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        addFields();
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(Box.createHorizontalStrut(50));

        this.add(buttons, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void addListeners() {
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (mode) {
                    case CREATE -> {
                        handleCreate();
                    }
                    case UPDATE -> {
                        handleUpdate();
                    }
                    case DELETE -> {
                        handleDelete();
                    }
                    default -> {
                        closeForm();
                    }
                }
                closeForm();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Edit form closed;.");
                closeForm();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.setEditingRecord(null);
                logger.info("Edit form closed");
                super.windowClosing(e);
            }
        });
    }

    private void closeForm() {
        controller.setEditingRecord(null);
        dispose();
        setVisible(false);
    }

    private void handleCreate() {
        try {
            controller.insertRecord();
            JOptionPane.showMessageDialog(null, "Record Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            controller.refreshData();
            logger.info("Record created successfully.");
        } catch (Exception ex) {
            logger.error("Error creating record");
            logger.error(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error Creating Record!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        try {
            controller.updateRecord();
            JOptionPane.showMessageDialog(null, "Record Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            controller.refreshData();
            logger.info("Record updated successfully.");
        } catch (Exception ex) {
            logger.error("Error updating record");
            logger.error(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error Updating Record!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        try {
            controller.getCrudService().delete(controller.getSelectedRecords().get(0));
            JOptionPane.showMessageDialog(null, "Record Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            controller.refreshData();
            logger.info("Record deleted successfully.");
        } catch (Exception ex) {
            logger.error("Error deleting record");
            logger.error(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error Deleting Record!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void setProperties() {
        this.setPreferredSize(frameSize);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void fetchRecord() {
        try {
            T r = controller.readRecord(controller.getSelectedRecords().get(0));
            controller.setEditingRecord(r);
            logger.info("Record to update fetched successfully!");
        } catch (HibernateException e) {
            logger.error("Error creating " + mode.toString().toLowerCase() + " form for record!");
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "An error has occurred!", "Error Creating Form", JOptionPane.ERROR_MESSAGE);
            dispose();
            setVisible(false);
        }
    }

    private void addFields() {
        try {
            if (controller.getEditingRecord() == null) {
                controller.setEditingRecord((T) controller.getClazz().getConstructor().newInstance());
            }

            for (FieldConfig fc : controller.getTableConfig().getFieldConfigs()) {
                panel.add(Box.createRigidArea(new Dimension(0, 20)));
                switch (fc.getFieldType()) {
                    case NUMBER -> {
                        panel.add(configNumberField(fc));
                    }
                    case SELECT -> {
                        panel.add(configSelect(fc));
                    }
                    case TOGGLE -> {
                        panel.add(configToggle(fc));
                    }
                    case DATE -> {
                        panel.add(configDateField(fc));
                    }
                    default -> {
                        panel.add(configTextField(fc));
                    }
                }
            }
        } catch (InstantiationException | IllegalArgumentException | IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "An error has occurred!", "Error Loading Form", JOptionPane.ERROR_MESSAGE);
            closeForm();
        }
    }

    private Toggle configToggle(FieldConfig fc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Toggle field = new Toggle(false, fc.getLabel());
        field.setFieldEnabled(!(mode == TableFormMode.VIEW || mode == TableFormMode.DELETE));

        Method accessor = controller.getEditingRecord().getClass().getMethod(fc.getAccessorName());
        Method mutator = controller.getEditingRecord().getClass().getMethod(fc.getMutatorName(), fc.getClazz());

        Object value = accessor.invoke(controller.getEditingRecord());

        if (value != null) {
            field.setToggled((Boolean) value);
        }

        field.addListeners(new FieldListeners<Boolean>() {
            @Override
            public void onChange(Boolean fieldValue) {
                handleValueUpdate(fc, mutator, fieldValue);
            }

            @Override
            public void onBlur(Boolean fieldValue) {
            }
        });

        return field;
    }

    private TextField configTextField(FieldConfig fc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TextField field = new TextField(fc.getLabel());

        if (fc.isId()) {
            field.setEnabled(mode == TableFormMode.CREATE);
        } else {
            field.setEnabled(!(mode == TableFormMode.VIEW || mode == TableFormMode.DELETE));
        }

        Method accessor = controller.getEditingRecord().getClass().getMethod(fc.getAccessorName());
        Method mutator = controller.getEditingRecord().getClass().getMethod(fc.getMutatorName(), fc.getClazz());

        String value = (String) accessor.invoke(controller.getEditingRecord());

        if (value != null) {
            field.setValue(value);
        }

        field.addListeners(new FieldListeners<String>() {
            @Override
            public void onChange(String fieldValue) {
            }

            @Override
            public void onBlur(String fieldValue) {
                handleValueUpdate(fc, mutator, fieldValue);
            }
        });
        return field;
    }

    private SelectField<Object> configSelect(FieldConfig fc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final SelectField<Object> field = new SelectField<>(fc.getOptions(), fc.getOptions()[0], fc.getLabel());

        field.setEnabled(!(mode == TableFormMode.VIEW || mode == TableFormMode.DELETE));

        Method accessor = controller.getEditingRecord().getClass().getMethod(fc.getAccessorName());
        Method mutator = controller.getEditingRecord().getClass().getMethod(fc.getMutatorName(), fc.getClazz());

        Object value = accessor.invoke(controller.getEditingRecord());

        if (value != null && Arrays.asList(fc.getOptions()).contains(value)) {
            field.setSelectedItem(value);
        }
        field.addListeners(new FieldListeners<Object>() {
            @Override
            public void onChange(Object fieldValue) {
                handleValueUpdate(fc, mutator, fieldValue);
            }

            @Override
            public void onBlur(Object fieldValue) {
            }
        });

        return field;
    }

    private NumberField configNumberField(FieldConfig fc) throws NoSuchMethodException, IllegalArgumentException, InvocationTargetException, IllegalAccessException {
        NumberField field = new NumberField(fc.getClazz(), fc.getLabel(), (Number) fc.getMin(), (Number) fc.getMin(), (Number) fc.getMax());

        if (fc.isId()) {
            field.setEnabled(mode == TableFormMode.CREATE);
        } else {
            field.setEnabled(!(mode == TableFormMode.VIEW || mode == TableFormMode.DELETE));
        }

        Method mutator = controller.getEditingRecord().getClass().getMethod(fc.getMutatorName(), Number.class);
        Method accessor = controller.getEditingRecord().getClass().getMethod(fc.getAccessorName());

        Object value = accessor.invoke(controller.getEditingRecord());

        if (value instanceof String) {
            if (NumberUtils.isParsable((String) value)) {
                field.setValue((String) value);
            } else {
                logger.error("Invalid number value for NumberField!");
                throw new IllegalArgumentException("Invalid number value for NumberField!");
            }
        } else {
            field.setValue((value.toString()));
        }

        field.addNumberListeners(new FieldListeners<Number>() {
            @Override
            public void onChange(Number fieldValue) {
            }

            @Override
            public void onBlur(Number fieldValue) {
                handleValueUpdate(fc, mutator, fieldValue);
            }
        });

        return field;
    }

    private DateTimePicker configDateField(FieldConfig fc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DateTimePicker field = new DateTimePicker(fc.getLabel(), false, false, (LocalDateTime) fc.getMin(), (LocalDateTime) fc.getMax());

        field.setFieldEnabled(!(mode == TableFormMode.VIEW || mode == TableFormMode.DELETE));

        Method accessor = controller.getEditingRecord().getClass().getMethod(fc.getAccessorName());
        Method mutator = controller.getEditingRecord().getClass().getMethod(fc.getMutatorName(), LocalDateTime.class);

        Object value = accessor.invoke(controller.getEditingRecord());

        if (value instanceof LocalDateTime) {
            field.setValue((LocalDateTime) value);
        }

        field.addListeners(new FieldListeners<LocalDateTime>() {
            @Override
            public void onChange(LocalDateTime fieldValue) {
            }

            @Override
            public void onBlur(LocalDateTime fieldValue) {
                handleValueUpdate(fc, mutator, fieldValue);
            }
        });

        return field;
    }

    private void handleValueUpdate(FieldConfig fc, Method m, Object fieldValue) {
        try {
            Validator validator = new Validator(fieldValue);

            if (validator.validate(fc.getConstraintList())) {
                m.invoke(controller.getEditingRecord(), fc.getClazz().cast(fieldValue));
                logger.info("Record value set: " + fieldValue.toString());
            } else {
                logger.error("Invalid Input: " + validator.getErrorMessage());
                JOptionPane.showMessageDialog(null, validator.getErrorMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            logger.error(e.getMessage());
            JOptionPane.showMessageDialog(null, "An error has occurred!", "Error Loading Form", JOptionPane.ERROR_MESSAGE);
            closeForm();
        }
    }

    @Override
    public String toString() {
        return "EntityEditForm{" +
                "mode=" + mode +
                ", controller=" + controller +
                ", name='" + name + '\'' +
                '}';
    }
}
