package project.grizzly.server;

import project.grizzly.application.services.CRUDService;
import project.grizzly.application.services.CombinedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

@Component
public class Server extends Thread {
    private ServerSocket servSock;
    private final int PORT = 8888;
    private final Logger logger = LogManager.getLogger(Server.class);
    private HashMap<String, CRUDService> cruds;
    private int clientCount = 0;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public Server(HashMap<String, CRUDService> cruds) {
        this.cruds = cruds;
        start();
    }

    @Override
    public void run() {
        this.createServerSocket();
//        executorService.submit(() -> {
        waitForRequests();
//        });
        super.run();
    }

    /**
     * Create server socket
     */
    public void createServerSocket() {
        try {
            servSock = new ServerSocket(PORT, 1);
            logger.info("Server Listening on PORT:" + PORT);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Listen for a client connection
     */
    public void waitForRequests() {
        while (true) {
            try {
                Socket connection = servSock.accept();

                executorService.submit(() -> {
                    clientCount++;
                    logger.info("Clients connected: " + clientCount);

                    new ClientHandler(cruds, connection);

                    clientCount--;
                    logger.info("Clients connected: " + clientCount);
                });

            } catch (SocketException e) {
                logger.error(e.getMessage());
                clientCount--;
                logger.info("Clients connected: " + clientCount);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}

/**
 * Handles client requests
 */
class ClientHandler {
    private Socket connection;
    private Logger logger = LogManager.getLogger(ClientHandler.class);
    private HashMap<String, CRUDService> cruds;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<>(1);

    /**
     * Constructor
     *
     * @param cruds      map of available crud services
     * @param connection the client connection
     */
    public ClientHandler(HashMap<String, CRUDService> cruds, Socket connection) {
        this.cruds = cruds;
        this.connection = connection;
        getStreams();
    }

    /**
     * Creates object streams
     */
    private void getStreams() {
        try {
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            inputStream = new ObjectInputStream(connection.getInputStream());
            System.out.println("Strems connected");
            while (true) {
                Request request = (Request) inputStream.readObject();
//                System.out.println(request);

                if (request.getAction().compareTo("EXIT") == 0) {
                    closeStreams();
                    closeClientConnection();
                    break;
                }
//                System.out.println("hererr");

                try {
                    processRequest(request);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }

                executorService.submit(() -> {
                    try {
                        handleRequest();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void processRequest(Request request) throws IOException {
        try {
            logger.info(request);
            if (request == null) throw new IOException("Invalid Request");

            if (request.getAction().trim().isEmpty() || request.getEntity().trim().isEmpty()) {
                throw new IOException("Invalid Request!");
            }
            System.out.println("Servre processing");
            requestQueue.put(request);

        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Handles client request
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void handleRequest() throws IOException, ClassNotFoundException {

        try {
            Request req = requestQueue.take();

            logger.info("Processing: " + req);

            var crud = cruds.get(req.getEntity());

            if (req.getAction().compareTo("ADD") == 0) {
                crud.insert((Serializable) crud.getClazz().cast(req.getInputObject()));
                addToResponseQueue(new Response(req, true));
                processResponse();
                return;
            }
            if (req.getAction().compareTo("UPDATE") == 0) {
                crud.update((Serializable) req.getInputObject());
                addToResponseQueue(new Response(req, true));
                processResponse();
                return;
            }
            if (req.getAction().compareTo("READ") == 0) {
                Object obj = crud.read((Serializable) req.getInputObject());
                logger.info("READING");
                addToResponseQueue(new Response(req, obj));
                processResponse();
                return;
            }
            if (req.getAction().compareTo("READ-ALL") == 0) {
                logger.info("READING All");
                List<Object> objs = crud.readAll();
                addToResponseQueue(new Response(req, objs));
                processResponse();
                return;
            }
            if (req.getAction().compareTo("READ-WHERE") == 0) {
                List<Object> objs = new ArrayList<>();

                objs = crud.readWhere((s) ->
                        ((CombinedQuery) req.getInputObject()).getQuery((Session) s)
                );
                addToResponseQueue(new Response(req, objs));

                processResponse();
                return;
            }

            if (req.getAction().compareTo("DELETE") == 0) {
                crud.delete((Serializable) req.getInputObject());
                addToResponseQueue(new Response(req, true));
                processResponse();
                return;
            }

            if (req.getAction().compareTo("DELETE-WHERE") == 0) {
                if (req.getInputObject() instanceof CombinedQuery) {
                    crud.delete((s) ->
                            ((CombinedQuery) req.getInputObject()).getQuery((Session) s)
                    );
                }
                addToResponseQueue(new Response(req, true));
                processResponse();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);//todo
        }
    }

    private void processResponse() {
        try {
            Response o = responseQueue.take();
            writeToStream(o);
        } catch (InterruptedException e) {
            e.printStackTrace();//todo
        }
    }

    /**
     * Write object to output stream
     *
     * @param o object to write
     */
    private void writeToStream(Object o) {
        try {
            outputStream.writeObject(o);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void addToResponseQueue(Response response) {
        try {
            responseQueue.put(response);
        } catch (InterruptedException e) {
            e.printStackTrace();//todo
        }
    }

    /**
     * Closes client connection
     */
    public void closeClientConnection() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Closes object streams
     */
    private void closeStreams() {
        try {
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

