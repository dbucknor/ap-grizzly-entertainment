package com.grizzly.application.models.interfaces;

public interface TableListener<K> {
    void onSelectedChanged(K[] selectedRows);
}
