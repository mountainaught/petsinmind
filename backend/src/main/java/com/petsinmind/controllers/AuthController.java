package com.petsinmind.controllers;

import com.petsinmind.Application;
import com.petsinmind.GUI;
import com.petsinmind.RegisterRequest;
import com.petsinmind.Registry;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.io.File;

@RestController
@RequestMapping("/api")
@CrossOrigin(
  origins = {"http://localhost:5173", "http://localhost:5174", "http://frontend:5173"},
  allowedHeaders = "*",
  methods = {RequestMethod.POST, RequestMethod.OPTIONS}
)   
public class AuthController {

    private final GUI gui = new GUI(Registry.getInstance());

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        boolean result = gui.login(username, password);
        if (result) {
            return "Login successful";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @PostMapping("/register-owner")
    public ResponseEntity<String> registerOwner(@Valid @RequestBody RegisterRequest request) {
        boolean result = gui.registerPetOwner(
            request.getUsername(),
            request.getPassword(),
            request.getEmail(),
            request.getPhoneNumber(),
            request.getFirstName(),
            request.getLastName(),
            request.getLocation(),
            request.getImageFile()
        );

        if (result) {
            return ResponseEntity.ok("Owner registration successful");
        } else {
            return ResponseEntity.badRequest().body("Owner already exists");
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
            // Save the CV to disk (e.g., under uploads/ directory)
            String uploadDir = "uploads";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            String savedPath = uploadDir + "/" + username + "_cv.pdf";
            File dest = new File(savedPath);
            cvFile.transferTo(dest); // Save uploaded file

            boolean result = gui.registerCaretaker(firstName, lastName, username, password, email, phoneNumber, location, savedPath);

            if (result) {
                return ResponseEntity.ok("Caretaker registration successful");
            } else {
                return ResponseEntity.badRequest().body("Caretaker already exists or registration failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }



}
