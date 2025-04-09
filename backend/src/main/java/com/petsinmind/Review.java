package com.petsinmind;

import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;

// Falak
public class Review {
    private String details;
    private int rating;
    private Appointment appointment;
    private PetOwner petOwner;
    private Caretaker caretaker;

    public Review() {}

    public Review(String details, int rating, Appointment appointment, PetOwner petOwner, Caretaker caretaker) {
        this.details = details;
        this.rating = rating;
        this.appointment = appointment;
        this.petOwner = petOwner;
        this.caretaker = caretaker;
    }

    // Getters
    public String getDetails() {
        return details;
    }

    public int getRating() {
        return rating;
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    // Setters
    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }
}
