package com.isf.departmentupload.utill;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Logger log = Logger.getLogger(Connector.class);
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Connector.class.getClassLoader().getResourceAsStream("database.properties")) {


            assert input != null;
            properties.load(input);

        } catch (IOException e) {
            log.error("Properties load error: " + e);
        }
    }

    public static Connection openConnection() throws SQLException {
        try {
            Class.forName(properties.getProperty("driverClassName"));
        } catch (ClassNotFoundException e) {
            log.error("Open connection error: " + e);
        }
        return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
                properties.getProperty("password"));

    }

}
