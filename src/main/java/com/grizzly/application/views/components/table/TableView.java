package com.grizzly.application.views.components.table;

import com.grizzly.application.controllers.TableController;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.TableConfig;
import com.grizzly.application.models.interfaces.*;
import com.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class TableView<T extends ITableEntity, K extends Serializable> implements IView {
    private DefaultTableModel tableModel;
    private TableConfig tableConfig;
    private Class<T> classType;
    private JTable table;
    private JScrollPane scrollPane;
    private final ArrayList<TableListener> listeners;
    private ScrollPaneLayout scrollPaneLayout;
    private final TableController<T, K> tableController;
    private ThemeManager theme;

    public TableView(Class<T> classType, TableController<T, K> tableController) {
        this.classType = classType;
        this.listeners = new ArrayList<>();
        this.tableController = tableController;
        this.scrollPane = new JScrollPane();
        this.theme = ThemeManager.getInstance();

        if (tableController != null) {
            this.tableConfig = tableController.getTableConfig();
        }

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        scrollPaneLayout = new ScrollPaneLayout();

        tableModel = new DefaultTableModel(tableConfig.getEntriesAsArray(), tableConfig.getTitles());
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

//        TableColumnModel tcm = table.getTableHeader().getColumnModel();
//        table.setTableHeader(new JTableHeader());
        TableColumn column;
        TableCellRenderer cr;

        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(350);

//            for (int t = 0; t < table.getRowCount(); t++) {
//                cr = table.getCellRenderer(t, i);
//                System.out.println(t + " " + i);
//                System.out.println((t + 1) % 2 == 0);
//
//                if ((t + 1) % 2 == 0) {
//                    table.prepareRenderer(cr, t, i).setBackground(theme.getCurrentScheme().getNeutralLight());
//                } else {
//                    table.prepareRenderer(cr, t, i).setBackground(theme.getCurrentScheme().getAccent1());
//                }
//            }
        }

        table.setDefaultEditor(Object.class, null);

        JTableHeader jth = table.getTableHeader();
        jth.setResizingAllowed(false);

//        jth.getPreferredSize();
        jth.setForeground(theme.getCurrentScheme().getNeutralLight());
        jth.setBackground(theme.getCurrentScheme().getPrimary());
        jth.setFont(jth.getFont().deriveFont(Font.BOLD));

        table.setTableHeader(jth);

//        table.setPreferredSize(new Dimension(5000, 800));
        table.setVisible(true);
    }

    @Override
    public void addComponents() {
        scrollPane.getViewport().setViewSize(table.getPreferredSize());
        scrollPane.getViewport().setView(table);
    }

    @Override
    public void addListeners() {
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ArrayList<K> ids = new ArrayList<>();

                for (int rowIndex : table.getSelectedRows()
                ) {
                    Object o = table.getValueAt(rowIndex, 0);
                    if (o instanceof Serializable) {
                        ids.add((K) o);
                    }
                }
                tableController.setSelectedRecords(ids);
            }
        });

        table.setSelectionModel(selectionModel);

        tableController.addChangeListener(new TableUpdateListener() {
            @Override
            public void onTableUpdate(String[] titles, ArrayList<Object[]> tableData, List<FieldConfig> fieldConfigs) {
                setTableConfig(new TableConfig(titles, tableData, fieldConfigs));
            }
        });

    }

    private void updateSelectedIdListener(int[] selection) {
        for (TableListener tl :
                listeners) {
            if (tl != null) tl.onSelectedChanged(selection);
        }
    }

    public JScrollPane getPane() {
        return scrollPane;
    }

    @Override
    public void setProperties() {
//        scrollPane.setPreferredSize(table.getPreferredSize());
        scrollPane.setAutoscrolls(true);
        scrollPane.setVisible(true);
    }

    public void addListener(TableListener tl) {
        listeners.add(tl);
    }

    public void removeListener(TableListener tl) {
        listeners.remove(tl);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
        tableModel.setDataVector(tableConfig.getEntriesAsArray(), tableConfig.getTitles());
        table.setModel(tableModel);
    }

    public Class<T> getClassType() {
        return classType;
    }

    public void setClassType(Class<T> classType) {
        this.classType = classType;
    }
}
