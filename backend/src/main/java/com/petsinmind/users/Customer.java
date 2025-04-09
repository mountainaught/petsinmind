package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.messages.TicketMessage;

import java.util.List;
import java.util.UUID;

public abstract class Customer extends User {
    private String location;

    public Customer() {}

    public Customer(UUID caretakerID) {
        super.setUserID(caretakerID);
    }

    private List<String> ListAppointmentIDs;
    private List<String> ListTicketIDs;
    private List<String> ListJobOfferIDs;

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

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
