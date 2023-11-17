package com.grizzly.application.views.components.table;

import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.TableFormMode;
import com.grizzly.application.models.Validator;
import com.grizzly.application.models.interfaces.FieldListeners;
import com.grizzly.application.models.interfaces.TableFrameListener;
import com.grizzly.application.controllers.TableController;
import com.grizzly.application.models.interfaces.ITableEntity;
import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.views.components.fields.NumberField;
import com.grizzly.application.views.components.fields.TextField;
import com.grizzly.application.views.screens.EntityEditForm;
import com.grizzly.application.views.components.fields.Button;
import org.apache.commons.lang3.math.NumberUtils;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TableFrame<T extends ITableEntity, K extends Serializable> extends JInternalFrame implements IView {
    private JPanel header, searchPanel;
    private Box btnPanel;
    private TextField searchField;
    private Button refresh, searchBtn, delete, update, create, exit;
    private final TableController<T, K> tableController;
    private TableView<T, K> tableView;
    private final Class<T> type;
    private ArrayList<TableFrameListener> listeners;
    private final ThemeManager themeManager;
    private EntityEditForm<T, K> editForm;
    private String name;
    private K searchString;

    public TableFrame(String name, Class<T> type) {
        super(name);

        themeManager = ThemeManager.getInstance();
        this.name = name;
        tableController = new TableController<>(type);

        this.type = type;
        this.listeners = new ArrayList<>();
        this.setLayout(new BorderLayout());

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 60));

        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel = new Box(BoxLayout.X_AXIS);

        searchField = new com.grizzly.application.views.components.fields.TextField(null, "Search ID");

        searchBtn = new Button(FontAwesomeSolid.SEARCH);
        searchBtn.setIconSize(20);

        update = new Button(FontAwesomeSolid.EDIT);
        update.setIconSize(20);
        create = new Button(FontAwesomeSolid.FILE_MEDICAL);
        create.setIconSize(20);
        delete = new Button(FontAwesomeSolid.TRASH);
        delete.setIconSize(20);
        refresh = new Button(FontAwesomeSolid.UNDO);
        refresh.setIconSize(20);

        exit = new Button(FontAwesomeSolid.ARROW_LEFT);
        exit.setIconSize(20);
        exit.setButtonColor(themeManager.getCurrentScheme().getNeutralLight(), themeManager.getCurrentScheme().getPrimary());

        tableView = new TableView<>(type, tableController);
    }

    @Override
    public void addComponents() {

        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        btnPanel.add(create);
        btnPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        btnPanel.add(update);
        btnPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        btnPanel.add(delete);
        btnPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        btnPanel.add(refresh);
        btnPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        header.add(exit, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.CENTER);
        header.add(btnPanel, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);
        this.add(tableView.getPane(), BorderLayout.CENTER);
    }

    @Override
    public void addListeners() {
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editForm = new EntityEditForm<T, K>(name, tableController, TableFormMode.CREATE);
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (recordSelected()) {
                    editForm = new EntityEditForm<T, K>(name, tableController, TableFormMode.UPDATE);
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (recordSelected()) {
                    tableController.deleteRecords(tableController.getSelectedRecords());
                }
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableController.refreshData();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateListeners();
                tableController.getClient().createConnection();
            }
        });
        searchField.addListeners(new FieldListeners<String>() {
            @Override
            public void onChange(String fieldValue) {
                if (fieldValue == null || fieldValue.trim().isEmpty()) {
                    tableController.resetTableData();
                }
            }

            @Override
            public void onBlur(String fieldValue) {
                if (NumberUtils.isParsable(fieldValue)) {
                    searchString = (K) Long.valueOf(fieldValue);
                } else {
                    searchString = (K) fieldValue;
                }
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((searchString instanceof String && !((String) searchString).trim().isEmpty()) || searchString instanceof Number) {
                    tableController.filter(searchString);
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("Table shown");
//                tableController.getClient().connect();
                tableController.refreshData();
                super.componentShown(e);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("Table hidden");
//                tableController.getClient().closeConnection();
                super.componentHidden(e);
            }
        });

    }

    public void removeHeader(boolean remove) {
        this.putClientProperty("JInternalFrame.isPalette", remove);
    }

    private boolean recordSelected() {
        if (tableController.getSelectedRecords().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Record Selected To Edit/View!", "No Record", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void updateListeners() {
        for (TableFrameListener tl :
                listeners) {
            if (tl != null) tl.onClose();
        }
    }


    public void addListener(TableFrameListener tl) {
        listeners.add(tl);
    }

    public void removeListener(TableFrameListener tl) {
        listeners.remove(tl);
    }

    @Override
    public void setProperties() {
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(false);
    }


}
