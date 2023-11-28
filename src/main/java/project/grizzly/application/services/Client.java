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

public class Client extends Thread {
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket connection;
    private static Client instance;
    private final Logger logger = LogManager.getLogger(Client.class);
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<>();

    public Client() {
        start();
    }

    @Override
    public void run() {
        createConnection();
        getStreams();
        processRequests();
        super.run();
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
//            if (requestQueue.contains(request)) return;
            requestQueue.put(request);
            logger.info("Added to queue Request: " + request + "\n");
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
                    Request o = requestQueue.take();
                    os.writeObject(o);
                    logger.info("Sent queue object: " + o + "\n");
//                    System.out.println("Request Queue: " + requestQueue + "\n\n");
                    processResponse();
                } catch (IOException | InterruptedException ex) {
                    logger.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    public void processResponse() {
        try {
            if (is != null) {
                Response r = (Response) is.readObject();
                responseQueue.put(r);
            } else {
                responseQueue.put(new Response(null, null));
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public Object receiveResponse(Request request) {
        Future<Object> f = executor.submit((Callable<Object>) () -> {
            Response o = responseQueue.take();
            logger.info("Response  received: " + o + "\n");
            return o;
        });

        try {
            return f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object getResponseFromQueue(Request request, Integer retry) {
        try {
//            if (retry == 10) {
//                return resendRequest(request, retry);
//            }
//            if (retry == 30) {
//                return new Response(request, null);
//            }
//
//            if (responseQueue.peek() != null && isResponseForRequest(request)) {
            Response o = responseQueue.take();
            logger.info("Response  received: " + o + "\n");
            return o;
//            } else {
//                Thread.sleep(1000L * retry);
//                retry++;
//                System.out.println("Waiting for my response for request: \n" + request + "\n\n");
//                System.out.println("Queue : " + responseQueue + "\n\n");
//                return getResponseFromQueue(request, retry);
//            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    private Object resendRequest(Request request, Integer retry) throws InterruptedException {
        sendRequest(request);
        retry++;

        return getResponseFromQueue(request, retry);
    }

    private boolean isResponseForRequest(Request request) {
        assert responseQueue.peek() != null;
        return responseQueue.peek().getRequest().equals(request);
    }

}