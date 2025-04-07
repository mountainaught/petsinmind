package com.petsinmind;

import java.util.Scanner;

public class Application {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String cv;
    private String phoneNumber;

    public static void main(String[] args) {
        Application app = new Application();
        app.collectUserData();
    }

    public Application() {
        // Constructor
    }

    public void collectUserData() {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        this.firstname = s.nextLine();

        System.out.print("Enter your last name: ");
        this.lastname = s.nextLine();

        System.out.print("Enter your username: ");
        this.username = s.nextLine();

        System.out.print("Enter your password: ");
        this.password = s.nextLine();

        System.out.print("Enter your email: ");
        this.email = s.nextLine();

        System.out.print("Enter your CV (file path or description): ");
        this.cv = s.nextLine();

        System.out.print("Enter your phone number: ");
        this.phoneNumber = s.nextLine();

        s.close();

        System.out.println("\nUser Data Collected:");
        System.out.println("First Name: " + this.firstname);
        System.out.println("Last Name: " + this.lastname);
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
        System.out.println("Email: " + this.email);
        System.out.println("CV: " + this.cv);
        System.out.println("Phone Number: " + this.phoneNumber);
    }
}
