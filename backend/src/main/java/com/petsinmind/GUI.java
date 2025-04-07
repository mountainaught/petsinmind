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
        return registry.authenticateUser(username, password);
    }

    // Method for user registration
    public boolean register(String username, String password, String email, String phoneNumber, String firstName, String lastName) {
        return registry.addUser(username, password, email, phoneNumber, firstName, lastName);
    }
}
