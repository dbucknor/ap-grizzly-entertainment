package project.grizzly.application.models.interfaces;

import project.grizzly.application.models.FieldConfig;

import java.util.ArrayList;
import java.util.List;

public interface TableUpdateListener {
    void onTableUpdate(String[] titles, ArrayList<Object[]> tableData, List<FieldConfig> fieldConfigs);
}
