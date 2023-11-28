package project.grizzly.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Database {

    static final Logger logger = LogManager.getLogger(Database.class);
    private Connection dbConnection;

    public Database(Connection connection) {
        this.dbConnection = connection;
    }

//    public List<Object> read(String sql) throws SQLException {
//        Statement statement = dbConnection.createStatement();
//        statement.execute(sql);
//        ResultSet resultSet = statement.getResultSet();
//        List<Object> results = new ArrayList<>();
//
//        while (resultSet.next()) {
////results.add(resultSet.get)
//        }
//    }


    public Connection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
