package com.petsinmind;

// Falak
public class Registry {
    private static Registry instance = null;

    private Registry() {
    }

    public static Registry getInstance() {
        // Private constructor to prevent instantiation
        if (instance == null) {
            instance = new Registry();
        }
        return instance;
    }

    public void Login(String username, String password) {
        // Logic to authenticate user
        // This is a placeholder for the actual authentication logic
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }

    }
}
