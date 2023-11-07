package com.grizzly.application.models;

import com.grizzly.application.services.CRUDService;

import java.io.Serializable;
import java.util.ArrayList;

public class DatabaseTable<T extends Serializable, K extends Serializable> {
    private CRUDService<T, K> crudService;

    private ArrayList<T> records;

    public DatabaseTable() {
        crudService = new CRUDService<>();
        records = new ArrayList<>();
    }

    public CRUDService<T, K> getCrudService() {
        return crudService;
    }

    public void setCrudService(CRUDService<T, K> crudService) {
        this.crudService = crudService;
    }

    public ArrayList<T> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<T> records) {
        this.records = records;
    }
}
