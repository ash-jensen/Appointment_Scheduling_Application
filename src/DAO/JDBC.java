package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // Local
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    private static Connection connection; // Connection Interface

    // Open connection to DB
    public static void openConnection() {
            try {
                Class.forName(driver); // Local Driver
                connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
                System.out.println("Connection successful!");
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    // Get Connection
    public static Connection getConnection() {
        return connection;
    }


    // Close connection to DB
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch (Exception e) {
// This can possibly be set nothing "// do nothing" because we are closing the program, it no longer matters
            System.out.println("Error: " + e.getMessage());
        }
    }

}
