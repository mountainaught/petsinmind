package com.petsinmind.controllers;

import com.petsinmind.GUI;
import com.petsinmind.RegisterRequest;
import com.petsinmind.Registry;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

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
        boolean result = gui.registerOwner(
            request.getUsername(),
            request.getPassword(),
            request.getEmail(),
            request.getPhoneNumber(),
            request.getFirstName(),
            request.getLastName()
        );

        if (result) {
            return ResponseEntity.ok("Owner registration successful");
        } else {
            return ResponseEntity.badRequest().body("Owner already exists");
        }
    }

    @PostMapping("/register-caretaker")
    public ResponseEntity<String> registerCaretaker(@Valid @RequestBody RegisterRequest request) {
        boolean result = gui.registerCaretaker(
            request.getUsername(),
            request.getPassword(),
            request.getEmail(),
            request.getPhoneNumber(),
            request.getFirstName(),
            request.getLastName()
        );

        if (result) {
            return ResponseEntity.ok("Caretaker registration successful");
        } else {
            return ResponseEntity.badRequest().body("Caretaker already exists");
        }
    }

}
