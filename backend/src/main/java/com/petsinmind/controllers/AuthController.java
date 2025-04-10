package com.petsinmind.controllers;

import com.petsinmind.Application;
import com.petsinmind.GUI;
import com.petsinmind.RegisterRequest;
import com.petsinmind.Registry;
import com.petsinmind.Ticket;
import com.petsinmind.users.PetOwner;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(
  origins = {"http://localhost:5173", "http://localhost:5174", "http://frontend:5173"},
  allowedHeaders = "*",
  methods = {RequestMethod.POST, RequestMethod.OPTIONS}
)   
public class AuthController {

    private final GUI gui;

    public AuthController() {
        try {
            this.gui = new GUI(Registry.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize GUI", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
    
        try {
            boolean result = gui.login(username, password);
            if (result) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during login");
        }
    }

    @PostMapping("/register-owner")
    public ResponseEntity<String> registerOwner(@Valid @RequestBody RegisterRequest request) {
        try {
            boolean result = gui.registerPetOwner(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getFirstName(),
                request.getLastName(),
                request.getLocation()
            );
    
            if (result) {
                return ResponseEntity.ok("Owner registration successful");
            } else {
                return ResponseEntity.badRequest().body("Owner already exists or invalid data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }

    @PostMapping("/register-caretaker")
    public ResponseEntity<String> registerCaretaker(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("email") String email,
        @RequestParam("phoneNumber") String phoneNumber,
        @RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam("location") String location,
        @RequestParam("CV") MultipartFile cvFile
    ) {
        try {
            // Save the CV to disk
            String uploadDir = "uploads";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();
    
            String savedPath = uploadDir + "/" + username + "_cv.pdf";
            File dest = new File(savedPath);
            cvFile.transferTo(dest);
    
            // Delegate to GUI
            boolean result = gui.registerCaretaker(
                firstName, lastName, username, password, email, phoneNumber, location, savedPath
            );
    
            if (result) {
                return ResponseEntity.ok("Caretaker application submitted successfully");
            } else {
                return ResponseEntity.badRequest().body("Caretaker application failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }

    @PostMapping("/create-ticket")
    public ResponseEntity<String> createTicket(@RequestBody Map<String, String> body) {
        String ticketTitle = body.get("ticketTitle");
        String ticketText = body.get("ticketText");
        String customerID = body.get("customerID");
    
        try {
            boolean result = gui.createTicket(
                ticketTitle,
                ticketText,
                UUID.fromString(customerID)
            );
    
            if (result) {
                return ResponseEntity.ok("Ticket created successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to create ticket");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during ticket creation");
        }
    }

    @PostMapping("/create-message") 
    public ResponseEntity<String> createMessage(@RequestBody Map<String, String> body) {
        String messageText = body.get("messageText");
        String senderID = body.get("senderID");
        String receiverID = body.get("receiverID");
        String referenceID = body.get("referenceID");
    
        try {
            boolean result = gui.createMessage(
                messageText,
                UUID.fromString(senderID),
                UUID.fromString(receiverID),
                UUID.fromString(referenceID)
            );
    
            if (result) {
                return ResponseEntity.ok("Message sent successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to send message");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during message creation");
        }

    
    }   
}
