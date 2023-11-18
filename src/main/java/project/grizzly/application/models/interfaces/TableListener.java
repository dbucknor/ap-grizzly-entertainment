package project.grizzly.application.models.interfaces;

public interface TableListener<K> {
    void onSelectedChanged(K[] selectedRows);
}
