package com.grizzly.application.models.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface ITableController<T, K> {
    void insertRecord();

    T readRecord(K id);

    void updateRecord();

    void deleteRecords();

    void fetchTableData();

    ArrayList<Object[]> recordsAsObjects(List<T> records);

    void refreshData();

    public void addChangeListener(TableUpdateListener listener);

    public void removeChangeListener(TableUpdateListener listener);


}
