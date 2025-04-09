package com.petsinmind;

import java.util.UUID;
import java.io.File;

import com.petsinmind.users.PetOwner;

public class GUI {

    private Registry registry;

    // Constructor to initialize the registry
    public GUI(Registry registry) {
        this.registry = registry;
    }

    // Getter
    public Registry getRegistry() {
        return registry;
    }

    // Setter
    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    // Method for user login
    public boolean login(String username, String password) {
        // Check if the username and password are not empty
        if (username == null || username.isEmpty() || password == null ||
                password.isEmpty()) {
            return false;
        }
        // Call the registry to authenticate the user
        // return registry.authenticateUser(username, password);
        return true;
    }

    // Method for user registration
    public boolean registerPetOwner(String username, String password, String email, String phoneNumber,
            String firstName, String lastName, String location, File imageFile) {
        // Check if the username and password are not empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        try {
            // Call the registry to add a new user
            PetOwner newUser = new PetOwner(UUID.randomUUID(), username, password, email, phoneNumber, firstName,
                    lastName, location, null, null, null, null);
            return registry.createUser(newUser);

        } catch (Exception e) {
            // Handle any exceptions that may occur during the user creation process
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerCaretaker(String firstName, String lastName, String username, String password, String email,
            String phoneNumber, String location, String cv) {
        // Check if the username and password are not empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        // Create an application object for the caretaker
        Application application = new Application(firstName, lastName, username, password, email, null, phoneNumber,
                location);
        return registry.createApplication(application, cv);
    }
}
