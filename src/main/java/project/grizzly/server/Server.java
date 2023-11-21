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

            while (true) {
                String request = (String) inputStream.readObject();

                if (request.compareTo("EXIT") == 0) {
                    closeStreams();
                    closeClientConnection();
                    break;
                }
                handleRequest(request);
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Handles client request
     *
     * @param request request received
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void handleRequest(String request) throws IOException, ClassNotFoundException {

        logger.info(request);
        if (request == null) throw new IOException("Invalid Request Action");

        String action = request.split(" ")[0].trim();
        String entity = request.split(" ")[1].trim();

        if (action.trim().isEmpty() || entity.trim().isEmpty()) {
            throw new IOException("Invalid Request Action");
        }

        var crud = cruds.get(entity);

        if (action.compareTo("ADD") == 0) {
            Object o = inputStream.readObject();
            crud.insert((Serializable) crud.getClazz().cast(o));
            writeToStream(true);
        }
        if (action.compareTo("UPDATE") == 0) {
            Object o = inputStream.readObject();
            crud.update((Serializable) o);
            writeToStream(true);
        }
        if (action.compareTo("READ") == 0) {
            Object o = inputStream.readObject();
            Object obj = crud.read((Serializable) o);
            logger.info("READING");
            writeToStream(obj);
        }
        if (action.compareTo("READ-ALL") == 0) {
            logger.info("READING All");
            List<Object> objs = crud.readAll();
            writeToStream(objs);
        }
        if (action.compareTo("READ-WHERE") == 0) {
            List<Object> objs;

            Object o = inputStream.readObject();
            if (o instanceof CombinedQuery) {
                objs = crud.readWhere((s) ->
                        ((CombinedQuery) o).getQuery((Session) s)
                );
            } else {
                objs = crud.readWhere((s) -> o);
            }
            writeToStream(objs);
        }

        if (action.compareTo("DELETE") == 0) {
            Object o = inputStream.readObject();
            crud.delete((Serializable) o);
            writeToStream(true);
        }

        if (action.compareTo("DELETE-WHERE") == 0) {
            Object o = inputStream.readObject();
            if (o instanceof CombinedQuery) {
                crud.delete((s) ->
                        ((CombinedQuery) o).getQuery((Session) s)
                );
            } else {
                crud.delete((s) ->
                        o
                );
            }
            writeToStream(true);
        }

    }

    /**
     * Wtire object to output stream
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