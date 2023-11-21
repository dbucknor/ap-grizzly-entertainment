package project.grizzly.application.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * Interface for Create, Read, Update, Delete classes
 *
 * @param <T> objet class type
 * @param <K> object primary key class type
 */
public interface ICRUD<T extends Serializable, K extends Serializable> {
    Logger logger = LogManager.getLogger(ICRUD.class);

    /**
     * Insert record
     *
     * @param record record to be inserted
     * @throws Exception error if inserting record
     */
    void insert(T record) throws Exception;

    /**
     * Read record
     *
     * @param id record id
     * @return record
     * @throws Exception error if reading record
     */
    T read(K id) throws Exception;

    /**
     * Read records with a query
     *
     * @param callback provides the session object and a return value with the query
     * @return records
     * @throws Exception error if reading records
     */
    List<T> readWhere(Function<Session, Query<T>> callback) throws Exception;

    /**
     * Updates a record
     *
     * @param record record to update
     * @throws Exception error if updating record
     */
    void update(T record) throws Exception;

    /**
     * Updates records with a query
     *
     * @param callback provides the session object and a return value with the query
     * @throws Exception error if uodating records
     */
    void update(Function<Session, Query<T>> callback) throws Exception;

    void delete(K id) throws Exception;

    /**
     * Deletes records with a query
     *
     * @param callback provides the session object and a return value with the query
     * @throws Exception error if deleting records
     */
    void delete(Function<Session, Query<T>> callback) throws Exception;

}
