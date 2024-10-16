package com.hexaware.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class DBConnection {
//    private static Connection connection = null;
//
//    // Method to establish and return a database connection
//    public static Connection getConnection(String propertyFileName) throws SQLException {
//        if (connection == null) {
//            try {
//                // Fetch the connection string using DBPropertyUtil
//                String connectionString = PropertyUtil.getPropertyString(propertyFileName);
//                if (connectionString != null) {
//                    connection = DriverManager.getConnection(connectionString);
//                } else {
//                    throw new SQLException("Unable to fetch connection string");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return connection;
//    }
//}


public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ECOM";
    private static final String USER = "root";
    private static final String PASSWORD = "m#P52s@ap$V"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
