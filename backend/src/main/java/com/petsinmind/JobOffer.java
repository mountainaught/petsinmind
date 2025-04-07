package com.petsinmind;

import java.util.List;
import java.util.Calendar;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;

// Dimitris
public class JobOffer {
    private String JobOfferID;
    private PetOwner petOwner;
    private List<Pet> pets;
    private Calendar startDate;
    private Calendar endDate;
    private List<Caretaker> AcceptedCaretakers;
    private List<Caretaker> RejectedCaretakers;
    private String Type;

    // Initial constructor
    public JobOffer() {
        this.JobOfferID = null;
        this.petOwner = null;
        this.pets = null;
        this.startDate = null;
        this.endDate = null;
        this.AcceptedCaretakers = null;
        this.RejectedCaretakers = null;
        this.Type = null;
    }

    // Constructor with parameters
    public JobOffer(String JobOfferID, PetOwner petOwner, List<Pet> pets, Calendar startDate, Calendar endDate,
            List<Caretaker> AcceptedCaretakers, List<Caretaker> RejectedCaretakers, String Type) {
        this.JobOfferID = JobOfferID;
        this.petOwner = petOwner;
        this.pets = pets;
        this.startDate = startDate;
        this.endDate = endDate;
        this.AcceptedCaretakers = AcceptedCaretakers;
        this.RejectedCaretakers = RejectedCaretakers;
        this.Type = Type;
    }

    // Getters and Setters
    public String getJobOfferID() {
        return JobOfferID;
    }

    public void setJobOfferID(String JobOfferID) {
        this.JobOfferID = JobOfferID;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
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

    public List<Caretaker> getAcceptedCaretakers() {
        return AcceptedCaretakers;
    }

    public void setAcceptedCaretakers(List<Caretaker> AcceptedCaretakers) {
        this.AcceptedCaretakers = AcceptedCaretakers;
    }

    public void addAcceptedCaretaker(Caretaker caretaker) {
        this.AcceptedCaretakers.add(caretaker);
    }

    public void removeAcceptedCaretaker(Caretaker caretaker) {
        this.AcceptedCaretakers.remove(caretaker);
    }

    public List<Caretaker> getRejectedCaretakers() {
        return RejectedCaretakers;
    }

    public void setRejectedCaretakers(List<Caretaker> RejectedCaretakers) {
        this.RejectedCaretakers = RejectedCaretakers;
    }

    public void addRejectedCaretaker(Caretaker caretaker) {
        this.RejectedCaretakers.add(caretaker);
    }

    public void removeRejectedCaretaker(Caretaker caretaker) {
        this.RejectedCaretakers.remove(caretaker);
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public List<Caretaker> findCaretakers() {
        List<Caretaker> availableCaretakers = new ArrayList<>();

    }
}
