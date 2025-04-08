package com.petsinmind;

public class Application {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String cv;
    private String phoneNumber;

    public Application(String firstname, String lastname, String username, String password, String email, String cv, String phoneNumber) {
        // Constructor
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.cv = cv;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public String getCv() {
        return cv;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
