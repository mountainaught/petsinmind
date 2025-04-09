package com.petsinmind;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.UUID;

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

    private List<Caretaker> availableCaretakers;
    private ArrayList<Message> messageList;
    private Registry registry;

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
        try {
            this.registry = Registry.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JobOffer(UUID jobOfferID) {
        this.JobOfferID = jobOfferID;
        try {
            this.registry = Registry.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JobOffer fetchedJobOffer = registry.getJobOffer(this);
            if (fetchedJobOffer != null) {
                this.JobOfferID = fetchedJobOffer.JobOfferID;
                this.petOwner = fetchedJobOffer.petOwner;
                this.pets = fetchedJobOffer.pets;
                this.location = fetchedJobOffer.location;
                this.startDate = fetchedJobOffer.startDate;
                this.endDate = fetchedJobOffer.endDate;
                this.AcceptedCaretakers = fetchedJobOffer.AcceptedCaretakers;
                this.RejectedCaretakers = fetchedJobOffer.RejectedCaretakers;
                this.Type = fetchedJobOffer.Type;
            }
            this.availableCaretakers = this.availableCaretakers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JobOffer(PetOwner petOwner, List<Pet> pets, Calendar startDate, Calendar endDate, String Type) {
        this.JobOfferID = UUID.randomUUID();
        this.petOwner = petOwner;
        this.pets = pets;
        this.location = petOwner.getLocation(); // Assuming PetOwner has a getLocation() method
        this.startDate = startDate;
        this.endDate = endDate;
        this.AcceptedCaretakers = new ArrayList<>();
        this.RejectedCaretakers = new ArrayList<>();
        this.Type = Type;
        try {
            this.registry = Registry.getInstance(); // Singleton pattern
            this.registry.createJobOffer(this);
            this.petOwner.addJobOfferID(this.JobOfferID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Caretaker> availableCaretakers() {
        List<Caretaker> availableCaretakers = new ArrayList<>();
        try {
            availableCaretakers = registry.findAvailableCaretakers(this);
            for (Caretaker caretaker : availableCaretakers) {
                if (this.AcceptedCaretakers.contains(caretaker)) {
                    availableCaretakers.remove(caretaker);
                }
            }
            for (Caretaker caretaker : availableCaretakers) {
                if (this.RejectedCaretakers.contains(caretaker)) {
                    availableCaretakers.remove(caretaker);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableCaretakers;
    }

    public void acceptCaretaker(Caretaker caretaker) {
        try {
            this.AcceptedCaretakers.add(caretaker);
            this.availableCaretakers.remove(caretaker);
            this.registry.editJobOffer(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rejectCaretaker(Caretaker caretaker) {
        try {
            this.RejectedCaretakers.add(caretaker);
            this.availableCaretakers.remove(caretaker);
            this.registry.editJobOffer(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    // // Getters and Setters
    public UUID getJobOfferID() {
        return JobOfferID;
    }

    public void setJobOfferID(UUID JobOfferID) {
        this.JobOfferID = JobOfferID;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public List<UUID> getPetIDs() {
        List<UUID> petIDs = new ArrayList<>();
        for (Pet pet : pets) {
            petIDs.add(pet.getPetID());
        }
        return petIDs;
    }

    public List<String> getPetIDsAsString() {
        List<String> petIDsAsString = new ArrayList<>();
        for (Pet pet : pets) {
            String idString = pet.getPetID().toString();
            petIDsAsString.add(idString);
        }
        return petIDsAsString;
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

    public List<String> getAcceptedCaretakerIDs() {
        List<String> acceptedCaretakerIDs = new ArrayList<>();
        for (Caretaker caretaker : AcceptedCaretakers) {
            acceptedCaretakerIDs.add(caretaker.getUserID().toString());
        }
        return acceptedCaretakerIDs;
    }

    public List<Caretaker> getRejectedCaretakers() {
        return RejectedCaretakers;
    }

    public List<String> getRejectedCaretakerIDs() {
        List<String> rejectedCaretakerIDs = new ArrayList<>();
        for (Caretaker caretaker : RejectedCaretakers) {
            rejectedCaretakerIDs.add(caretaker.getUserID().toString());
        }
        return rejectedCaretakerIDs;
    }

    public List<Message> getMessageList() {
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

    public void addMessage(Message Message) {
        this.messageList.add(Message);
    }
}
