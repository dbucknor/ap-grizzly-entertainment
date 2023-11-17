package com.grizzly.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Controller
public class Database {
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "root";
    static final String PASSWORD = "#h@rV3st";
    static final Logger logger = LogManager.getLogger(Database.class);
    static final ClassLoader loader = Database.class.getClassLoader();
    static String databaseScript;

    public Database() {
        databaseScript = "";
        try {
            List<String> lines = Files.readAllLines(Path.of(loader.getResource("script.sql").toURI()));

            for (String line : lines
            ) {
                databaseScript = databaseScript.concat("\n" + line);
            }
        } catch (URISyntaxException | IOException e) {
            logger.fatal(e.getMessage());
        }
        createDatabase();
    }

    public void runScript(String sql) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
            logger.info("SQL: " + sql);
            logger.info("SQL Successful.");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        ) {
            if (conn.getSchema() == null) {
                logger.info("Loading Schema");
                conn.setSchema(databaseScript);
                logger.info("Schema Loaded");
            }
            logger.info("Database Loaded: " + conn.getMetaData().toString());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


}
