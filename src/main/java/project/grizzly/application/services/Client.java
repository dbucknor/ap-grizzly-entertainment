package project.grizzly.application.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.grizzly.server.Request;
import project.grizzly.server.Response;

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
    private final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<>();

    public Client() {
        createConnection();
        getStreams();
        processRequests();
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

    public void sendRequest(Request request) throws InterruptedException {
//        executor.submit(() -> {
        try {
            requestQueue.put(request);
            logger.info("Added to queue Request: " + request);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        });
    }

//    public void send(Object obj) throws InterruptedException {
////        executor.submit(() -> {
//        try {
//            requestQueue.put(obj);
//            logger.info("Added to queue object: " + obj);
//        } catch (InterruptedException e) {
//            throw e;
//        }
//        //        });
//    }

    private void processRequests() {
        executor.submit(() -> {
            while (true) {
                try {
                    System.out.println("processss");
                    Request o = requestQueue.take();
                    os.writeObject(o);
                    logger.info("Sent queue object: " + o);
                    processResponse(o);
                } catch (IOException | InterruptedException ex) {
                    logger.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    public void processResponse(Request request) {
        try {
            Response o = is != null ? (Response) is.readObject() : null;
            responseQueue.put(o);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public Object receiveResponse() {
        Future<Object> f = executor.submit((Callable<Object>) () -> {
            try {
//            if(isResponseForRequest(request)){
//
//            }
                Response o = responseQueue.take();
                logger.info("Response  received: " + o);
                return o;
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                return null;
            }

        });

        try {
            return f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isResponseForRequest(Request request) {
        return responseQueue.peek().getRequest().equals(request);
    }

}