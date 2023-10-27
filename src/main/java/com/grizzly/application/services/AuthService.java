package com.grizzly.application.services;

import com.grizzly.application.models.User;

import java.util.ArrayList;
import java.util.Arrays;

public class AuthService implements IAuth<User> {
    private User user = null;
    private final ArrayList<AuthChangedListener<User>> listeners;
    private final CRUDService<User, String> crudService;
    private static AuthService instance;

    private AuthService() {
        listeners = new ArrayList<>();
        crudService = new CRUDService<>(User.class); //TODO: change to bean
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    @Override
    public User getLoggedInUser() {
        return user;
    }

    @Override
    public void setLoggedInUser(User user) {
        System.out.println(
                "should update"
        );
        this.user = user;
        this.user.setLoggedIn(true);
        System.out.println(this.user);
        updateListeners();
    }


    @Override
    public void logOut() {
        setLoggedInUser(null);
        updateListeners();
    }

    @Override
    public User logIn(String email, String password) throws AuthException {
        try {
            User usr = crudService.findByEmail(email);
            System.out.println(usr);

            if (usr != null) {
                if (usr.getPassword().compareTo(password) != 0) {
                    throw new AuthException(AuthCode.INCORRECT_PASSWORD, "Incorrect Password!");
                } else {
                    setLoggedInUser(usr);
                }
            } else {
                throw new AuthException(AuthCode.USER_NOT_FOUND, "User not found!");
            }
            return user;
        } catch (AuthException e) {
            setLoggedInUser(null);
            throw e;
        }

    }

    private void updateListeners() {
        ArrayList<AuthChangedListener<User>> changedListeners = new ArrayList<>(listeners);
        System.out.println("1 S: " + listeners.size());
        for (AuthChangedListener<User> listener : changedListeners
        ) {
            System.out.println("2");
            if (listener != null) {
                listener.onAuthChanged(user);
                System.out.println(Arrays.toString(user.getClass().getDeclaredFields()));
               
            }
        }
    }

    @Override
    public void addAuthChangedListener(AuthChangedListener<User> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeAuthChangedListener(AuthChangedListener<User> listener) {
        listeners.remove(listener);
    }


}
