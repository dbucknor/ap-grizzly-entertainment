package com.grizzly.application.models.interfaces;

public interface FieldListeners<T> {
    void onChange(T fieldValue);

    void onBlur(T fieldValue);

}
