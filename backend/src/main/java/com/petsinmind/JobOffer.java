package com.petsinmind;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.UUID;

import com.petsinmind.messages.JobOfferMessage;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;

// Dimitris
public class JobOffer {
    private UUID JobOfferID;
    private PetOwner petOwner;
    private List<Pet> pets;

    private String location;
    private Calendar startDate;
    private Calendar endDate;
    private List<Caretaker> AcceptedCaretakers;
    private List<Caretaker> RejectedCaretakers;
    private String Type;

    private ArrayList<JobOfferMessage> messageList;

    // Initial constructor
    public JobOffer() {
        this.JobOfferID = null;
        this.petOwner = null;
        this.pets = null;
        this.location = null;
        this.startDate = null;
        this.endDate = null;
        this.AcceptedCaretakers = null;
        this.RejectedCaretakers = null;
        this.Type = null;
    }

    // Constructor with parameters
    public JobOffer(PetOwner petOwner, List<Pet> pets, String location, Calendar startDate, Calendar endDate,
            List<Caretaker> AcceptedCaretakers, List<Caretaker> RejectedCaretakers, String Type) {
        // this.JobOfferID = UUID.randomUUID();
        this.petOwner = petOwner;
        this.pets = pets;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.AcceptedCaretakers = AcceptedCaretakers;
        this.RejectedCaretakers = RejectedCaretakers;
        this.Type = Type;
    }

    // Getters
    public PetOwner getPetOwner() {
        return petOwner;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public String getLocation() {
        return location;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public List<Caretaker> getAcceptedCaretakers() {
        return AcceptedCaretakers;
    }

    public List<Caretaker> getRejectedCaretakers() {
        return RejectedCaretakers;
    }

    public List<JobOfferMessage> getMessageList() {
        return messageList;
    }

    public String getType() {
        return Type;
    }

    // Setters
    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setAcceptedCaretakers(List<Caretaker> AcceptedCaretakers) {
        this.AcceptedCaretakers = AcceptedCaretakers;
    }

    public void setRejectedCaretakers(List<Caretaker> RejectedCaretakers) {
        this.RejectedCaretakers = RejectedCaretakers;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    // Additional methods
    public void addAcceptedCaretaker(Caretaker caretaker) {
        this.AcceptedCaretakers.add(caretaker);
    }

    public void removeAcceptedCaretaker(Caretaker caretaker) {
        this.AcceptedCaretakers.remove(caretaker);
    }

    public void addRejectedCaretaker(Caretaker caretaker) {
        this.RejectedCaretakers.add(caretaker);
    }

    public void removeRejectedCaretaker(Caretaker caretaker) {
        this.RejectedCaretakers.remove(caretaker);
    }

    public void addMessage(JobOfferMessage jobOfferMessage) {
        this.messageList.add(jobOfferMessage);
    }
}
