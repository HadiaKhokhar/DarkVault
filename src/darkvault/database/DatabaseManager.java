package darkvault.database;

import java.sql.*;

public class DatabaseManager {
//    ASK THE ADMIN FOR THIS INFO

    private static final String URL  = ""; 
    private static final String USER = "";
    private static final String PASS = "";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }

    public static boolean testConnection() {
        try {
            getConnection();
            System.out.println("Database connected successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            System.err.println("Error closing: " + e.getMessage());
        }
    }
}