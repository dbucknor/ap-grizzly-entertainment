package project.grizzly.application.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.concurrent.*;

public class Client {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket connection;
    private static Client instance;
    private final Logger logger = LogManager.getLogger(Client.class);
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final BlockingQueue<Object> requestQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Object> responseQueue = new LinkedBlockingQueue<>();

    public Client() {
//        executor.submit(() -> {
        createConnection();
        getStreams();
        process();
//        });
    }

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void createConnection() {
        try {
            connection = new Socket(InetAddress.getLocalHost(), 8888);
            logger.info("Connected to Server");
        } catch (SocketException ex) {
            logger.error("Connection Error");
            logger.error(ex);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void getStreams() {
        try {
            if (connection != null && !connection.isClosed()) {
                os = new ObjectOutputStream(connection.getOutputStream());
                is = new ObjectInputStream(connection.getInputStream());
                logger.info("Connected to object streams");
            }
        } catch (IOException e) {
            logger.error("Error connecting to object streams");
            logger.error(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendAction(String request) throws InterruptedException {
//        executor.submit(() -> {
        try {
            requestQueue.put(request);
            logger.info("Sent Request: " + request);
        } catch (Exception e) {
            throw e;
        }
//        });
    }

    public void send(Object obj) throws InterruptedException {
//        executor.submit(() -> {
        try {
            requestQueue.put(obj);
            logger.info("Sent object: " + obj);
        } catch (InterruptedException e) {
            throw e;
        }
        //        });
    }

    private void process() {
        executor.submit(() -> {
            while (true) {
                try {
                    Object o = requestQueue.take();
                    logger.info("Sent queue object: " + o);
                    os.writeObject(o);
                    processResponse();
                } catch (IOException | InterruptedException ex) {
                    logger.error(ex.getMessage());
                }
            }
        });
    }

    public void processResponse() {
        try {
            Object o = is != null ? is.readObject() : "NULL";
            o = o == null ? "NULL" : o;
            responseQueue.put(o);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public Object receiveResponse() {
//        Future<Object> f = executor.submit((Callable<Object>) () -> {
        try {
            Object o = responseQueue.take();
            logger.info("Response  received: " + o);
            return o instanceof String && ((String) o).compareTo("NULL") == 0 ? null : o;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
//        });
//
//        try {
//            return f.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

}