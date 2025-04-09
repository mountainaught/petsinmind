package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.messages.TicketMessage;

import java.util.List;
import java.util.UUID;

public abstract class Customer extends User {
    private String location;

    private List<String> ListAppointmentIDs;
    private List<String> ListTicketIDs;
    private List<String> ListJobOfferIDs;

    public Customer(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber, String firstName, String lastName, String location, 
        List<String> listAppointmentIDs, List<String> listTicketIDs, List<String> listJobOfferIDs) {
        // Call the constructor of the superclass (User)
        super(userID, userName, userPassword, userEmail, phoneNumber, firstName, lastName);
        this.location = location;
        ListAppointmentIDs = listAppointmentIDs;
        ListTicketIDs = listTicketIDs;
        ListJobOfferIDs = listJobOfferIDs;
    }

    // getters
    public String getLocation() { 
        return location;
    }

    public List<String> getAppointmentIDs() { 
        return ListAppointmentIDs; 
    }

    
    public List<String> getTicketIDs() { 
        return ListTicketIDs; 
    }
    
    public List<String> getJobOfferIDs() { 
        return ListJobOfferIDs; 
    }
    
    // setters
    public void setLocation(String location) { 
        this.location = location; 
    }

    public void setAppointmentIDs(List<String> listAppointmentIDs) { 
        ListAppointmentIDs = listAppointmentIDs; 
    }


    public void setTicketIDs(List<String> listTicketIDs) { 
        ListTicketIDs = listTicketIDs; 
    }

    public void setJobOfferIDs(List<String> listJobOfferIDs) { 
        ListJobOfferIDs = listJobOfferIDs; 
    }

    // Methods to manage appointments
    public void AddAppointment(String appointmentID) {
        ListAppointmentIDs.add(appointmentID);
    }

    public Boolean CancelAppointment(Appointment appointment) {
        return ListAppointmentIDs.remove(appointment.getAppointmentId());
    }

    public boolean CreateTicket(String Title, String details) {
        throw new UnsupportedOperationException();
    }

    public boolean SendMessage(TicketMessage message) {
        throw new UnsupportedOperationException();
    }
}
