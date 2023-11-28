package project.grizzly.application.services;

import project.grizzly.application.models.User;
import project.grizzly.server.Request;
import project.grizzly.server.Response;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class AuthService implements IAuth<User> {
    private User user = null;
    private final ArrayList<AuthChangedListener<User>> listeners;

    private static AuthService instance;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private Client client;

    private AuthService() {
        listeners = new ArrayList<>();
        client = Client.getInstance();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public void loadUserFromFile() {
        ClassLoader loader = this.getClass().getClassLoader();
        try {
            FileInputStream fi = new FileInputStream(Objects.requireNonNull(loader.getResource("user/current.txt")).toURI().getPath());
            ObjectInputStream os = new ObjectInputStream(fi);

            Object o = os.readObject();

            if (o instanceof User) {
                user = (User) o;
                user.setLoggedIn(true);

                os.close();
                fi.close();
            } else {
                os.close();
                fi.close();
                user = null;
            }
            updateListeners();

        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
            user = null;
            updateListeners();
        }
    }

    private void writeUserToFile() {
        ClassLoader loader = this.getClass().getClassLoader();
        try (FileOutputStream fo = new FileOutputStream(Objects.requireNonNull(loader.getResource("user/current.txt")).toURI().getPath());
             ObjectOutputStream os = new ObjectOutputStream(fo);
        ) {
            System.out.println("writing");
            os.writeObject(this.user);
            os.flush();
        } catch (IOException | URISyntaxException e) {
            //todo
            e.printStackTrace();
        }
    }

    @Override
    public User getLoggedInUser() {
        return user;
    }

    @Override
    public void setLoggedInUser(User user) {
        this.user = user;
        writeUserToFile();

        if (user != null) {
            this.user.setLoggedIn(true);
        }

        updateListeners();
    }


    @Override
    public void logOut() {
        setLoggedInUser(null);
        updateListeners();
    }

    @Override
    public void logIn(String userId, String password) throws AuthException {
        executor.submit(() -> {
            try {
                CombinedQuery<User> combinedQuery = new CombinedQuery<User>("SELECT u FROM User u")
                        .where("u.userId", "=:userId", userId);
                Request r = new Request("READ-WHERE", "USER", combinedQuery);
                client.sendRequest(r);

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Signing you in. Please wait!");
                });

                Object res = ((Response) client.receiveResponse(r)).getValue();

                List<User> results = (List<User>) res;
                User usr = results == null || results.isEmpty() ? null : results.get(0);

                if (usr != null) {
                    if (usr.getPassword().compareTo(password) != 0) {
                        throw new AuthException(AuthCode.INCORRECT_PASSWORD, "Incorrect Password!");
                    } else {
                        setLoggedInUser(usr);
                    }
                } else {
                    throw new AuthException(AuthCode.USER_NOT_FOUND, "User not found!");
                }

            } catch (AuthException e) {
                setLoggedInUser(null);
                //todo logging
            } catch (InterruptedException e) {
                setLoggedInUser(null);
//todo
            }
        });
    }

    private void updateListeners() {
        ArrayList<AuthChangedListener<User>> changedListeners = new ArrayList<>(listeners);
        for (AuthChangedListener<User> listener : changedListeners
        ) {
            if (listener != null) {
                listener.onAuthChanged(user);
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
