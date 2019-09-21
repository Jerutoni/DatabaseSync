package com.isf.departmentupload.utill;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Properties properties = new Properties();
    private static Connection connection = null;

    static {
        try (InputStream input = Connector.class.getClassLoader().getResourceAsStream("database.properties")) {


            assert input != null;
            properties.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName(properties.getProperty("driverClassName"));
            } catch (ClassNotFoundException e) {
                System.out.println("Ошибку");
            }
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
                    properties.getProperty("password"));
            return connection;
        } else {
            return connection;
        }
    }

}
