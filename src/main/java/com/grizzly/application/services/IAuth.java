package com.grizzly.application.services;

import java.util.function.Function;

public interface IAuth<T> {

    public T getLoggedInUser();

    public void setLoggedInUser(T user);

    public void logOut();

    public T logIn(String email, String password) throws AuthException;

    public void addAuthChangedListener(AuthChangedListener<T> listener);

    public void removeAuthChangedListener(AuthChangedListener<T> listener);
}