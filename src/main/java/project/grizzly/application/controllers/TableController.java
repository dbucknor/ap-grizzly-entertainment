package project.grizzly.application.controllers;

import project.grizzly.application.models.interfaces.ITableController;
import project.grizzly.application.models.interfaces.ITableEntity;
import project.grizzly.application.models.interfaces.TableUpdateListener;
import project.grizzly.application.services.Client;
import project.grizzly.application.services.CombinedQuery;
import project.grizzly.application.models.TableConfig;
import project.grizzly.server.Request;
import project.grizzly.server.Response;

import javax.persistence.Id;
import javax.swing.*;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Controller for Entity Table
 *
 * @param <T> Entity Type
 * @param <K> Primary key type of Entity
 */
public class TableController<T extends ITableEntity, K extends Serializable> implements ITableController<T, K> {
    protected TableConfig tableConfig;
    protected List<K> selectedRecords;
    protected Class<T> clazz;
    protected T editingRecord;
    protected List<T> records;
    protected List<T> filteredRecords;
    protected final ArrayList<TableUpdateListener> listeners;
    protected final Client client;
    protected ExecutorService executorService = Executors.newCachedThreadPool();
    protected Request request;

    /**
     * Creates a new Table Controller
     *
     * @param clazz Class<T> of Entity, Entity class
     */
    public TableController(Class<T> clazz) {
        this.listeners = new ArrayList<>();
        this.selectedRecords = new ArrayList<>();
        this.request = new Request("READ-ALL", clazz.getSimpleName().toUpperCase(), null);

        this.records = new ArrayList<>();
        this.editingRecord = null;
        this.clazz = clazz;

        this.client = Client.getInstance();

        this.tableConfig = getConfig();
    }

    public TableController(Class<T> clazz, Request fetchRequest) {
        this.listeners = new ArrayList<>();
        this.selectedRecords = new ArrayList<>();
        this.request = fetchRequest;
        this.records = new ArrayList<>();
        this.editingRecord = null;
        this.clazz = clazz;

        this.client = Client.getInstance();

        this.tableConfig = getConfig();
    }


    @Override
    public List<T> fetchRecords() {
        try {
            client.sendRequest(request);
            Response res = (Response) client.receiveResponse(request);

            Object o = res.getValue();
            return o != null ? (List<T>) o : new ArrayList<>();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void insertRecord(T record) {
        executorService.submit(() -> {
            try {
                Request r = new Request("ADD", clazz.getSimpleName().toUpperCase(), record);
                client.sendRequest(r);

                Object isCreated = ((Response) client.receiveResponse(r)).getValue();


                SwingUtilities.invokeLater(() -> {
                    if (isCreated instanceof Boolean && (Boolean) isCreated) {
                        JOptionPane.showMessageDialog(null, "Record Created!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error! Record Not Created!");
                    }
                });
                refreshData();
            } catch (Exception e) {
                logger.error("Error creating record");
                logger.error(e.getMessage());
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error! Record Not Created!");
                });
            }
        });

    }

    @Override
    public T readRecord(K id) {
        try {
            Future<T> future = executorService.submit((Callable<T>) () -> {
                try {
                    Request r = new Request("READ", clazz.getSimpleName().toUpperCase(), id);
                    client.sendRequest(r);
                    Object o = ((Response) client.receiveResponse(r)).getValue();

                    return (T) o;

                } catch (Exception e) {
                    logger.error("Error reading record");
                    logger.error(e.getMessage());

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Error! Record Not Read!");
                    });

                    return null;
                }
            });

            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    @Override
    public void updateRecord(T record) {
        executorService.submit(() -> {
            try {
                Request r = new Request("UPDATE", clazz.getSimpleName().toUpperCase(), record);
                client.sendRequest(r);
                Object isUpdated = ((Response) client.receiveResponse(r)).getValue();

                SwingUtilities.invokeLater(() -> {
                    if (isUpdated instanceof Boolean && (Boolean) isUpdated) {
                        JOptionPane.showMessageDialog(null, "Record Updated!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error! Record Not Updated!");
                    }
                });
                refreshData();
            } catch (Exception e) {
                logger.error("Error updating record");
                logger.error(e.getMessage());
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error! Record Not Updated!");
                });
            }
        });

    }

    @Override
    public void deleteRecords(List<K> recordIds) {
        for (K id : recordIds
        ) {
            executorService.submit(() -> {
                try {
                    Request r = new Request("DELETE", clazz.getSimpleName().toUpperCase(), id);
                    client.sendRequest(r);
                    Response res = (Response) client.receiveResponse(r);
                    Object isDeleted = res.getValue();

                    SwingUtilities.invokeLater(() -> {
                        if (isDeleted instanceof Boolean && (Boolean) isDeleted) {
                            JOptionPane.showMessageDialog(null, "Record Deleted!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error! Record Not Deleted!");
                        }
                    });

                    refreshData();
                } catch (Exception e) {
                    logger.error("Error deleting record(s)");
                    logger.error(e.getMessage());
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Error! Record Not Deleted! Record: " + id);
                    });
                }
            });
        }

    }


    @Override
    public ArrayList<Object[]> recordsAsObjects(List<T> records) {
        ArrayList<Object[]> list = new ArrayList<>();
        if (records == null) return list;

        for (T record : records) {
            Object[] o = record.getValues();
            list.add(o);
        }
        return list;
    }

    @Override

    public void refreshData() {
        executorService.submit(() -> {
            List<T> records = this.fetchRecords();

            SwingUtilities.invokeLater(() -> {
                this.records = records.stream().map((u) -> clazz.
                        cast(u)).toList();
                tableConfig.setTableData(recordsAsObjects(this.records));
                updateListeners();
            });
        });
    }

    public void filter(K id) {
        try {
            String idName = Objects.requireNonNull(findIdField()).getName();
            executorService.submit(() -> {
                try {
                    Request r = new Request("READ-WHERE", clazz.getSimpleName().toUpperCase(), new CombinedQuery<T>("SELECT t FROM " + clazz.getSimpleName() + " t")
                            .like("t." + idName, " LIKE :value", id));
                    client.sendRequest(r);

                    Object res = ((Response) client.receiveResponse(r)).getValue();

                    SwingUtilities.invokeLater(() -> {
                        filteredRecords = res != null ? (List<T>) res : new ArrayList<>();
                        tableConfig.setTableData(recordsAsObjects(filteredRecords));
                        updateListeners();
                    });
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());

                }
            });
        } catch (NullPointerException ne) {
            logger.error(ne.getMessage());
        }
    }

    protected Field findIdField() {
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
        tableConfig.setTableData(recordsAsObjects(records));
        updateListeners();
    }

    @Override
    public TableConfig getConfig() {
        try {
            return createObject(clazz).createEntityTableCfg();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            logger.error(e.getMessage());
            return new TableConfig();
        }
    }

    public static <T> T createObject(Class<T> clazz)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }


    @Override
    public void addChangeListener(TableUpdateListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(TableUpdateListener listener) {
        listeners.remove(listener);
    }


    /**
     * Update listeners
     */
    protected void updateListeners() {
        ArrayList<TableUpdateListener> changedListeners = new ArrayList<>(listeners);
        for (TableUpdateListener listener : changedListeners
        ) {
            if (listener != null) {
                listener.onTableUpdate(tableConfig.getTitles(), tableConfig.getTableData(), tableConfig.getFieldConfigs());
            }
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
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
        System.out.println("Selected: " + selectedRecords.toString());//todo-remove
        this.selectedRecords = selectedRecords;
    }

    public void setSelectedRecords(Object[] selectedRecords) {
        for (Object o : selectedRecords
        ) {
            this.selectedRecords.add((K) o);
        }
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

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public ArrayList<TableUpdateListener> getListeners() {
        return listeners;
    }

    public Client getClient() {
        return client;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public String toString() {
        return "TableViewController{" +
                "entries=" + tableConfig +
//                ", crud=" + crudService +
                ", type=" + clazz +
                '}';
    }
}
