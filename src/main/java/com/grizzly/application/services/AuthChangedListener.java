package com.grizzly.application.services;

public interface AuthChangedListener<T> {
    public void onAuthChanged(T user);

}
