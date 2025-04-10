package com.petsinmind;

import java.util.UUID;
import java.io.File;

import com.petsinmind.users.*;

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
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        try {
            if (registry.userNameExists(username)) {
                PetOwner user1 = new PetOwner(UUID.randomUUID(), username, password, null, null, null, null,
                        null, null, null, null, null);
                Caretaker user2 = new Caretaker(UUID.randomUUID(), username, password, null, null, null, null,
                        null, null, null, null, 0.0f, null, null);

                if (registry.findUser(user1) != null) {
                    // User is a PetOwner
                    User user = (PetOwner) registry.findUser(user1);
                    if (user.getUserPassword().equals(password)) {
                        return true; // Authentication successful
                    }
                } else if (registry.findUser(user2) != null) {
                    // User is a Caretaker
                    User user = (Caretaker) registry.findUser(user2);
                    if (user.getUserPassword().equals(password)) {
                        return true; // Authentication successful
                    }
                }
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during the authentication process
            e.printStackTrace();
        }

        return false; // Authentication failed
    }

    // Method for user registration
    public boolean registerPetOwner(String username, String password, String email, String phoneNumber,
            String firstName, String lastName, String location) {
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

    public boolean createTicket(String ticketTitle, String ticketText, UUID customerID) {
        // Check if the ticket title and text are not empty     
        if (ticketTitle == null || ticketTitle.isEmpty() || ticketText == null || ticketText.isEmpty()) {
            return false;
        }
        // Create a new ticket object
        Ticket ticket = new Ticket(ticketTitle, ticketText, customerID);
        try {
            // Call the registry to create a new ticket
            return registry.createTicket(ticket);
        } catch (Exception e) {
            // Handle any exceptions that may occur during the ticket creation process
            e.printStackTrace();
            return false;
        }
    }
}
