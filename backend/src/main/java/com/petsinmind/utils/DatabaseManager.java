package com.petsinmind.utils;

import com.petsinmind.Config;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
import com.petsinmind.users.User;

import java.sql.*;

public class DatabaseManager {
    // Singleton instance
    private static DatabaseManager instance;

    // The database connection
    private Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseManager() {}

    // Get the singleton instance
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // Connect to the database
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    Config.get("db.url"),
                    Config.get("db.user"),
                    Config.get("db.password")
            );
        }
        return connection;
    }

    // Close the connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void findByID(User user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (user.getClass() == Caretaker.class) {
            ps = connection.prepareStatement("SELECT * FROM caretaker WHERE UserID = ?");
            ps.setString(1, user.getUserID());
            rs = ps.executeQuery();


        } else if (user.getClass() == PetOwner.class) {

        }
    }

    private ResultSet
}