package com.petsinmind;

public class Application {
    private String firstName;
    private String lastName;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String cv;
    private String phoneNumber;
    private String location;

    // Default constructor
    public Application() {}

    // All-args constructor (optional)
    public Application(String firstName, String lastName, String userName, String userPassword,
                       String userEmail, String cv, String phoneNumber, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.cv = cv;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    //  Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUserName() { return userName; }
    public String getUserPassword() { return userPassword; }
    public String getUserEmail() { return userEmail; }
    public String getCv() { return cv; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getLocation() { return location; }

    //  Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setCv(String cv) { this.cv = cv; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setLocation(String location) { this.location = location; }
}
