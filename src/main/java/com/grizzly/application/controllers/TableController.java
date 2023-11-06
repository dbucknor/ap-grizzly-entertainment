package com.grizzly.application.controllers;

import com.grizzly.application.models.*;
import com.grizzly.application.models.interfaces.ITableController;
import com.grizzly.application.models.interfaces.ITableEntity;
import com.grizzly.application.models.interfaces.TableUpdateListener;
import com.grizzly.application.services.CRUDService;
import com.grizzly.application.services.CombinedQuery;
import org.hibernate.HibernateException;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Controller for Entity Table
 *
 * @param <T> Entity Type
 * @param <K> Primary key type of Entity
 */
public class TableController<T extends ITableEntity, K extends Serializable> implements ITableController<T, K> {
    private TableConfig tableConfig;
    private List<K> selectedRecords;
    private CRUDService<T, K> crudService;
    private Class<T> clazz;
    private T editingRecord;
    private List<T> allRecords;
    private List<T> filteredRecords;
    private final ArrayList<TableUpdateListener> listeners;

    /**
     * Creates a new Table Controller
     *
     * @param clazz Class<T> of Entity, Entity.class
     */
    public TableController(Class<T> clazz) {
        crudService = new CRUDService<>(clazz);
        listeners = new ArrayList<>();
        selectedRecords = new ArrayList<>();
        allRecords = new ArrayList<>();
        editingRecord = null;
        this.clazz = clazz;

        fetchTableData();

        tableConfig = getConfig();
        tableConfig.setTableData(recordsAsObjects(allRecords));
    }

    /**
     * Retrieves form field configurations
     *
     * @return field configurations
     */
    private TableConfig getConfig() {
        try {
            return createObject(clazz).createEntityTableCfg();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves column titles
     *
     * @return column titles
     */
    private String[] getTitles() {
        try {
            return createObject(clazz).getTableTitles();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertRecord() {
        try {
            crudService.insert(editingRecord);
            refreshData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T readRecord(K id) {
        try {
            return crudService.read(id);
        } catch (HibernateException e) {
            throw e;
        }
    }


    @Override
    public void updateRecord() {
        try {
            crudService.update(editingRecord);
            refreshData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRecords() {
        try {
            for (K id : selectedRecords
            ) {
                crudService.delete(id);
                refreshData();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetchTableData() {
        try {
            allRecords = crudService.readALL();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Convert table data to 2d array
     *
     * @return Collection of row data
     */
    public ArrayList<Object[]> recordsAsObjects(List<T> records) {
        ArrayList<Object[]> list = new ArrayList<>();

        for (T record : records) {
            Object[] o = record.getValues();
            list.add(o);
        }
        return list;
    }

    /**
     * Refreshes table data
     */
    public void refreshData() {
        fetchTableData();
        tableConfig.setTableData(recordsAsObjects(allRecords));
        updateListeners();
    }

    @Override
    public void addChangeListener(TableUpdateListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(TableUpdateListener listener) {
        listeners.remove(listener);
    }

    public static <T> T createObject(Class<T> clazz)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    /**
     * Update listeners
     */
    private void updateListeners() {
        ArrayList<TableUpdateListener> changedListeners = new ArrayList<>(listeners);
        for (TableUpdateListener listener : changedListeners
        ) {
            if (listener != null) {
                listener.onTableUpdate(tableConfig.getTitles(), tableConfig.getTableData(), tableConfig.getFieldConfigs());
            }
        }
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }

    public List<K> getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(List<K> selectedRecords) {
        System.out.println("Selected: " + selectedRecords.toString());
        this.selectedRecords = selectedRecords;
    }

    public void setSelectedRecords(Object[] selectedRecords) {
        for (Object o : selectedRecords
        ) {
            this.selectedRecords.add((K) o);
        }
    }

    public void filter(K id) {
        try {
            String idName = findIdField().getName();
            System.out.println("Id Name: " + idName);
            filteredRecords = crudService.readWhere((s) ->
                    new CombinedQuery<T>("SELECT t FROM " + clazz.getSimpleName() + " t")
                            .like("t." + idName, " LIKE :value", id)
                            .getQuery(s));

            tableConfig.setTableData(recordsAsObjects(filteredRecords));
            updateListeners();
        } catch (HibernateException he) {
            System.out.println(he.getMessage());//TODO logging
        }

    }

    private Field findIdField() {
        Class<?> c = clazz;

        while (c.getSuperclass() != null) {
            for (Field f : c.getDeclaredFields()
            ) {
                if (f.isAnnotationPresent(Id.class)) {
                    System.out.println(f);
                    return f;
                }
            }
            c = c.getSuperclass();
        }

        return null;

    }

    public void resetTableData() {
        tableConfig.setTableData(recordsAsObjects(allRecords));
        updateListeners();
    }

    public CRUDService<T, K> getCrudService() {
        return crudService;
    }

    public void setCrudService(CRUDService<T, K> crudService) {
        this.crudService = crudService;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> getFilteredRecords() {
        return filteredRecords;
    }

    public void setFilteredRecords(List<T> filteredRecords) {
        this.filteredRecords = filteredRecords;
    }

    public T getEditingRecord() {
        return editingRecord;
    }

    public void setEditingRecord(T editingRecord) {
        System.out.println("Fetched: " + editingRecord);
        this.editingRecord = editingRecord;
    }

    public List<T> getAllRecords() {
        return allRecords;
    }

    public void setAllRecords(List<T> allRecords) {
        this.allRecords = allRecords;
    }

    public ArrayList<TableUpdateListener> getListeners() {
        return listeners;
    }

    @Override
    public String toString() {
        return "TableViewController{" +
                "entries=" + tableConfig +
                ", crud=" + crudService +
                ", type=" + clazz +
                '}';
    }
}
