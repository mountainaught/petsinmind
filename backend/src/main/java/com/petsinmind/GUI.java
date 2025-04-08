package com.petsinmind;

import com.petsinmind.users.User;

public class GUI {

    private Registry registry;

    // Constructor to initialize the registry
    public GUI(Registry registry) {
        this.registry = registry;
    }

    // Method for user login
    public boolean login(String username, String password) {
        // Check if the username and password are not empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        // Call the registry to authenticate the user
        return registry.authenticateUser(username, password);
    }

    // Method for user registration
    public boolean register(String username, String password, String email, String phoneNumber, String firstName, String lastName) {
        User newUser = new User(username, password, email, phoneNumber, firstName, lastName);
        return registry.createUser(firstName, lastName, username, password, email, phoneNumber);
    }
}
