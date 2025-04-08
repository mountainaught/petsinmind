package com.petsinmind;

import java.util.HashMap;
import java.util.Map;

public class Registry {
    private static Registry instance = null;
    private Map<String, String[]> users; // Stores username and an array of user details

    private Registry() {
        users = new HashMap<>(); // Initialize the user storage
    }

    public static Registry getInstance() {
        if (instance == null) {
            instance = new Registry();
        }
        return instance;
    }

    // Method to authenticate a user
    // draft ---> will return user class
    public boolean authenticateUser(String username, String password) {
        if (users.containsKey(username) && users.get(username)[0].equals(password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // Method to add a new user
    public boolean addUser(String username, String password, String email, String phoneNumber, String firstName, String lastName) {
        // Check if the user already exists
        if (users.containsKey(username)) {
            System.out.println("User already exists.");
            return false;
        } else {
            // Create an array to store user details
            // draft ---> will store user class instead of array
            String[] newUser = {password, email, phoneNumber, firstName, lastName};
            users.put(username, newUser);
            System.out.println("User registered successfully!");
            return true;
        }
    }
}
