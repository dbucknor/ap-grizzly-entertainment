package project.grizzly.application.models.interfaces;

import project.grizzly.application.models.TableConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.grizzly.application.models.FieldConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Entity Table
 *
 * @param <T> entity type
 * @param <K> entity id type
 */
public interface ITableController<T, K> {
    Logger logger = LogManager.getLogger(ITableController.class);

    /**
     * Insert record to table in database
     */
    void insertRecord(T record);

    /**
     * Retrieves a record from the table in the database
     *
     * @param id the id for the record
     * @return <T> record
     */
    T readRecord(K id);

    /**
     * Updates a record in the database
     *
     * @param record the record to be updated
     */
    void updateRecord(T record);

    /**
     * Deletes records from database table
     *
     * @param recordIds the id of the records to delete
     */
    void deleteRecords(List<K> recordIds);

    /**
     * Retrieves all records for table
     *
     * @return table records
     */
    List<T> fetchTableData();

    /**
     * Converts records to a list of Object[] values
     *
     * @param records records to convert
     * @return collection of record values in an array
     */
    ArrayList<Object[]> recordsAsObjects(List<T> records);

    /**
     * Refreshes table records
     */
    void refreshData();

    /**
     * Retrieves form field configurations from entity
     *
     * @return field configurations
     * @see FieldConfig FieldConfig
     * @see TableConfig TableConfig
     */
    TableConfig getConfig();

    /**
     * Filters table records
     *
     * @param id id to filter by
     */
    void filter(K id);

    /**
     * Resets Table Data
     */
    void resetTableData();

    /**
     * Add listeners to controller
     *
     * @param listener new Table Update Listener
     */
    public void addChangeListener(TableUpdateListener listener);

    /**
     * Removes listeners to controller
     *
     * @param listener Table Update Listener
     */
    public void removeChangeListener(TableUpdateListener listener);


}
