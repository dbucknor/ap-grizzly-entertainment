package com.grizzly.application.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableConfig {
    private String[] titles;
    private ArrayList<Object[]> tableData;
    private List<FieldConfig> fieldConfigs;

    public TableConfig(String[] titles, List<FieldConfig> fieldConfigs) {
        this.titles = titles;
        this.fieldConfigs = fieldConfigs;
    }

    public TableConfig(String[] titles, ArrayList<Object[]> tableData, List<FieldConfig> fieldConfigs) {
        this.titles = titles;
        this.fieldConfigs = fieldConfigs;
        this.tableData = tableData;
    }

    public Object[][] getEntriesAsArray() {
        Object[][] objectArray = new Object[tableData.size()][];

        for (int i = 0; i < tableData.size(); i++) {
            objectArray[i] = tableData.get(i);
        }
        return objectArray;
    }

    public void addEntry(Object[] entry) {
        tableData.add(entry);
    }

    public void addEntry(List<?> entry) {
        tableData.add(entry.toArray());
    }

    public void removeEntry(Object[] entry) {
        tableData.remove(entry);
    }

    public void removeEntry(int index) {
        tableData.remove(index);
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public ArrayList<Object[]> getTableData() {
        return tableData;
    }


    public void setTableData(ArrayList<Object[]> tableData) {
        this.tableData = tableData;
    }

    public List<FieldConfig> getFieldConfigs() {
        return fieldConfigs;
    }

    public void setFieldConfigs(List<FieldConfig> fieldConfigs) {
        this.fieldConfigs = fieldConfigs;
    }


}
