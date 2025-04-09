package com.petsinmind;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
// Dimitris
import com.petsinmind.messages.AppointmentMessage;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;

public class Appointment {
    private UUID appointmentId;
    private Caretaker caretaker;
    private PetOwner PetOwner;
    private List<Pet> pets;
    private Calendar startDate;
    private Calendar endDate;
    private String type;

    private ArrayList<AppointmentMessage> messageList;

    // Initial constructor
    public Appointment() {
        this.appointmentId = null;
        this.caretaker = null;
        this.PetOwner = null;
        this.pets = null;
        this.startDate = null;
        this.endDate = null;
        this.type = null;
    }

    // Constructor with parameters
    public Appointment(UUID appointmentId, Caretaker caretaker, PetOwner PetOwner, List<Pet> pets, Calendar startDate,
            Calendar endDate,
            String Type) {
        this.appointmentId = appointmentId;
        this.caretaker = caretaker;
        this.PetOwner = PetOwner;
        this.pets = pets;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = Type;
    }

    // Getters
    public UUID getAppointmentId() {
        return appointmentId;
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public PetOwner getPetOwner() {
        return PetOwner;
    }
    
    public List<Pet> getPets() {
        return pets;
    }

    // setters
    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }


    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }


    public void setPetOwner(PetOwner PetOwner) {
        this.PetOwner = PetOwner;
    }


    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    // methods
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
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public ArrayList<AppointmentMessage> getMessageList() {
        return messageList;
    }

    public void addMessage(AppointmentMessage message) {
        this.messageList.add(message);
    }

    public Review Review(String Details, int Rating) {
        return new Review(Details, Rating, this, this.PetOwner, this.caretaker);
    }

    // public Payment MakePayment(List<String> SenderInfo, List<String>
    // ReceiverInfo, PetOwner PetOwner,
    // Caretaker caretaker) {
    // return new Payment("123123123", "Credit Card", Calendar.getInstance(), 100.0,
    // "USD",
    // PetOwner.getUserID(), caretaker.getUserID());
    // }

    // toString method
    public String toString() {
        return "Appointment{" +
                "caretaker=" + caretaker +
                ", PetOwner=" + PetOwner +
                ", pets=" + pets +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", Type='" + type + '\'' +
                '}';
    }
}
