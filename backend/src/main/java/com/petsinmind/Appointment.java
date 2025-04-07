package com.petsinmind;

import java.util.Calendar;
import java.util.List;
// Dimitris
import com.petsinmind.Review;
import com.petsinmind.Pet;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;

public class Appointment {
    private Integer AppointmentId;
    private Caretaker caretaker;
    private PetOwner PetOwner;
    private List<Pet> pets;
    private Calendar startDate;
    private Calendar endDate;
    private String Type;

    // Initial constructor
    public Appointment() {
        this.caretaker = null;
        this.PetOwner = null;
        this.pets = null;
        this.startDate = null;
        this.endDate = null;
        this.Type = null;
    }

    // Constructor with parameters
    public Appointment(Caretaker caretaker, PetOwner PetOwner, List<Pet> pets, Calendar startDate, Calendar endDate,
            String Type) {
        this.caretaker = caretaker;
        this.PetOwner = PetOwner;
        this.pets = pets;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Type = Type;
    }

    // Getters and Setters
    public Integer getAppointmentId() { return AppointmentId; }

    public void setAppointmentId(Integer appointmentId) { AppointmentId = appointmentId; }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }

    public PetOwner getPetOwner() {
        return PetOwner;
    }

    public void setPetOwner(PetOwner PetOwner) {
        this.PetOwner = PetOwner;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public Review Review(String Details, int Rating) {
        return new Review(Details, Rating, this, this.PetOwner, this.caretaker);
    }

    public Payment MakePayment(List<String> SenderInfo, List<String> ReceiverInfo, PetOwner PetOwner,
            Caretaker caretaker) {
        return new Payment("123123123", "Credit Card", Calendar.getInstance(), 100.0, "USD",
                PetOwner.getUserID(), caretaker.getUserID());
    }

    // toString method
    public String toString() {
        return "Appointment{" +
                "caretaker=" + caretaker +
                ", PetOwner=" + PetOwner +
                ", pets=" + pets +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", Type='" + Type + '\'' +
                '}';
    }
}
