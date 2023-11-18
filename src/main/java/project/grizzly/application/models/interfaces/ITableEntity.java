package project.grizzly.application.models.interfaces;

import project.grizzly.application.models.TableConfig;

import java.io.Serializable;

public interface ITableEntity extends Serializable {
    Object[] getValues();

    String[] getTableTitles();

    TableConfig createEntityTableCfg();
    
}
