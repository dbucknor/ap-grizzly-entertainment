package com.grizzly.server;

import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;


public interface IRestController<T extends Serializable, K extends Serializable> {


    boolean insertRecord(String serializedRecord);

    String getRecord(K id);

    String getAllRecords();

    List<T> getRecordsWhere(String serializedQuery);

    boolean updateRecord(String serializedRecord);

    boolean deleteRecord(K id);

    boolean deleteRecordsWhere(String serializedRecord);

}
