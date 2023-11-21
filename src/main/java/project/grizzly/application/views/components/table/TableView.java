package project.grizzly.application.views.components.table;

import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.TableConfig;
import project.grizzly.application.models.interfaces.ITableEntity;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.models.interfaces.TableListener;
import project.grizzly.application.models.interfaces.TableUpdateListener;
import project.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Viewport for Database Entity
 *
 * @param <T> entity type
 * @param <K> entity's id type
 */
public class TableView<T extends ITableEntity, K extends Serializable> implements IView {
    private DefaultTableModel tableModel;
    private TableConfig tableConfig;
    private Class<T> classType;
    private JTable table;
    private final JScrollPane scrollPane;
    private final ArrayList<TableListener<K>> listeners;
    private final TableController<T, K> tableController;
    private final ThemeManager theme;

    /**
     * Creates a new table view
     *
     * @param classType       class reference for entity
     * @param tableController controller for table operations
     */
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
        tableModel = new DefaultTableModel(tableConfig.getEntriesAsArray(), tableConfig.getTitles());
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setVisible(true);
        resetDisplay();
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
                //updateSelectedIdListener((K[]) ids.toArray());
            }
        });

        table.setSelectionModel(selectionModel);

        tableController.addChangeListener(new TableUpdateListener() {
            @Override
            public void onTableUpdate(String[] titles, ArrayList<Object[]> tableData, List<FieldConfig> fieldConfigs) {
                setTableConfig(new TableConfig(titles, tableData, fieldConfigs));
                resetDisplay();
            }
        });

    }

    /**
     * Adds column colors etc
     */
    private void resetDisplay() {
        for (int cIndex = 0; cIndex < table.getColumnCount(); cIndex++) {
            TableColumn column = table.getColumnModel().getColumn(cIndex);
            column.setPreferredWidth(350);
//            for (int rIndex = 0; rIndex < table.getRowCount(); rIndex++) {
//                TableCellRenderer cr = table.getCellRenderer(rIndex, cIndex);//Todo
//                System.out.println(rIndex + " " + cIndex);
//                System.out.println((rIndex + 1) % 2 == 0);
//
//                if ((rIndex + 1) % 2 == 0) {
//                    table.prepareRenderer(cr, rIndex, cIndex).setBackground(theme.getCurrentScheme().getNeutralLight());
//                } else {
//                    table.prepareRenderer(cr, rIndex, cIndex).setBackground(theme.getCurrentScheme().getAccent1());
//                }
//            }
        }

        table.setDefaultEditor(Object.class, null);

        JTableHeader jth = table.getTableHeader();
        jth.setResizingAllowed(false);

        jth.setForeground(theme.getCurrentScheme().getNeutralLight());
        jth.setBackground(theme.getCurrentScheme().getPrimary());
        jth.setFont(jth.getFont().deriveFont(Font.BOLD));

        table.setTableHeader(jth);
    }

    private void updateSelectedIdListener(K[] selection) {
        for (TableListener<K> tl :
                listeners) {
            if (tl != null) tl.onSelectedChanged(selection);
        }
    }

    public JScrollPane getPane() {
        return scrollPane;
    }

    @Override
    public void setProperties() {
        scrollPane.setAutoscrolls(true);
        scrollPane.setVisible(true);
    }

    public void addListener(TableListener<K> tl) {
        listeners.add(tl);
    }

    public void removeListener(TableListener<K> tl) {
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
