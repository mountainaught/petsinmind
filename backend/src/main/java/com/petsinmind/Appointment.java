package com.petsinmind;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
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
    private Registry registry;
    private ArrayList<Message> messageList;

    // Initial constructor
    public Appointment() {
        this.appointmentId = null;
        this.caretaker = null;
        this.PetOwner = null;
        this.pets = null;
        this.startDate = null;
        this.endDate = null;
        this.type = null;

        try { this.registry = Registry.getInstance(); } catch (Exception e) { e.printStackTrace(); }
    }

    public Appointment(UUID appID) {
        this.appointmentId = appID;

        try { this.registry = Registry.getInstance(); } catch (Exception e) { e.printStackTrace(); }
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

        try { this.registry = Registry.getInstance(); } catch (Exception e) { e.printStackTrace(); }
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

    public List<String> getPetIDs() {
        List<String> petIDs = new ArrayList<>();
        for (Pet pet : pets) {
            petIDs.add(pet.getPetID().toString());
        }
        return petIDs;
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

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void addMessage(Message message) throws SQLException {
        this.messageList.add(message);
        registry.createMessage(message);
    }

    /**
     *
     * @param Details
     * @param Rating
     */
    public boolean addReview(String Details, int Rating) throws SQLException {
        Review review = new Review(Details, Rating, this, this.PetOwner, this.caretaker);
        return registry.createReview(review);
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
