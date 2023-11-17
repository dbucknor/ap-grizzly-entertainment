package com.grizzly.application.models.interfaces;

import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.TableConfig;

import java.io.Serializable;

public interface ITableEntity extends Serializable {
    Object[] getValues();

    String[] getTableTitles();

    TableConfig createEntityTableCfg();
    
}
