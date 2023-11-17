package com.grizzly.server;

import com.grizzly.application.services.CRUDService;
import com.grizzly.application.services.CombinedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;

import javax.persistence.PersistenceException;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class ClientHandler12 {
    private Socket connection;
    private Logger logger = LogManager.getLogger(ClientHandler12.class);
    private HashMap<String, CRUDService> _cruds;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    public ClientHandler12(Socket connection, ApplicationContext applicationContext) {
        this._cruds = applicationContext.getAutowireCapableBeanFactory().getBean("createCruds", HashMap.class);
        this.connection = connection;
        this.getStreams();
    }

    private void getStreams() {
        try {
            this.outputStream = new ObjectOutputStream(connection.getOutputStream());
            this.inputStream = new ObjectInputStream(connection.getInputStream());

            while (true) {
                String request = (String) this.inputStream.readObject();

                if (request.compareTo("EXIT") == 0) {
                    this.closeStreams(outputStream, inputStream);
                    this.closeClientConnection(connection);
                    break;
                }
                handleRequest(request);
            }
        } catch (IOException | ClassNotFoundException ex) {
            this.logger.error(ex.getMessage());
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void handleRequest(String request) throws PersistenceException, IOException, ClassNotFoundException {

        this.logger.info(request);
        if (request == null) throw new PersistenceException("Invalid Request Action");

        String action = request.split(" ")[0].trim();
        String entity = request.split(" ")[1].trim();

        if (action.trim().isEmpty() || entity.trim().isEmpty()) {
            throw new PersistenceException("Invalid Request Action");
        }
        try {
            CRUDService crud = this._cruds.get(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        CRUDService crud = this._cruds.get(entity);
//        logger.info(request);

        if (action.compareTo("ADD") == 0) {
            Object o = inputStream.readObject();
            crud.insert((Serializable) crud.getClazz().cast(o));
            this.writeToStream(true, outputStream);
        }
        if (action.compareTo("UPDATE") == 0) {
            Object o = inputStream.readObject();
            crud.update((Serializable) o);
            this.writeToStream(true, outputStream);
        }
        if (action.compareTo("READ") == 0) {
            Object o = inputStream.readObject();
            Object obj = crud.read((Serializable) o);
            this.logger.info("READING");
            this.writeToStream(obj, outputStream);
        }
        if (action.compareTo("READ-ALL") == 0) {
            this.logger.info("READING All");
            List<Object> objs = crud.readAll();
            this.writeToStream(objs, outputStream);
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
            this.writeToStream(objs, outputStream);
        }
        if (action.compareTo("DELETE") == 0) {
            Object o = inputStream.readObject();
            crud.delete((Serializable) o);
            this.writeToStream(true, outputStream);
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
        }

    }

    private void writeToStream(Object o, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeClientConnection(Socket connection) {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    private void closeStreams(OutputStream os, InputStream is) {
        try {
            if (os != null) os.close();
            if (is != null) is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
