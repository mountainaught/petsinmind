package com.petsinmind.utils;

import com.petsinmind.Config;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
import com.petsinmind.users.User;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    // The database connection
    private Connection connection;

    // Private constructor to prevent instantiation
    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(
                Config.get("db.url"),
                Config.get("db.user"),
                Config.get("db.password")
        );
    }

    public Connection getConnection() {
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


    public User findByID(User user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        if (user.getClass() == Caretaker.class) {
            ps = connection.prepareStatement("SELECT * FROM caretaker WHERE UserID = ?");
            ps.setString(1, user.getUserID().toString());
            rs = ps.executeQuery();
    
            if (rs.next()) {
                Caretaker ct = new Caretaker();
                ct = (Caretaker) parseUser(rs, ct);
                ct = getCaretaker(rs, ct);
                return ct;
            } else {
                System.out.println("❌ No caretaker found with ID: " + user.getUserID());
                return null;
            }
        }
    
        return user;
    }
    

    private User parseUser(ResultSet rs, User user) throws SQLException {
        user.setUserID(UUID.fromString(rs.getString("UserID")));
        user.setUserName(rs.getString("UserName"));
        user.setUserPassword(rs.getString("UserPassword"));
        user.setUserEmail(rs.getString("UserEmail"));
        user.setPhoneNumber(rs.getString("PhoneNumber"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));

        return user;
    }

    private Caretaker getCaretaker(ResultSet rs, Caretaker ct) throws SQLException {
        // From Customer
        ct.setLocation(rs.getString("Location"));
        ct.setPay(rs.getFloat("Pay"));
        return ct;
    }

    public static void insertCaretaker(Connection conn, Caretaker ct) {
        try {
            String sql = "INSERT INTO caretaker (UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName, Location, Pay) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ct.getUserID().toString());
            ps.setString(2, ct.getUserEmail());
            ps.setString(3, ct.getUserPassword());
            ps.setString(4, ct.getUserName());
            ps.setString(5, ct.getPhoneNumber());
            ps.setString(6, ct.getFirstName());
            ps.setString(7, ct.getLastName());
            ps.setString(8, ct.getLocation());
            ps.setFloat(9, ct.getPay());

            int rows = ps.executeUpdate();
            System.out.println("✅ Rows inserted: " + rows);

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}