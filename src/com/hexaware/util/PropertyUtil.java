package com.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	public static String getPropertyString(String fileName) {
        Properties properties = new Properties();
        String connectionString = null;

        try (FileInputStream fileInput = new FileInputStream(fileName)) {
            properties.load(fileInput);

            String hostname = properties.getProperty("hostname", "localhost");
            String port = properties.getProperty("port", "3306");
            String dbname = properties.getProperty("dbname", "ECOM");
            String username = properties.getProperty("username", "root");
            String password = properties.getProperty("password", "m#P52s@ap$V");

            connectionString = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the property file: " + fileName);
        }

        return connectionString;
    }
}

