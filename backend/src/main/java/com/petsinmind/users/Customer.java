package com.petsinmind.users;
import com.petsinmind.Appointment;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Customer extends User {
    private String location;

    private List<UUID> ListAppointmentIDs;
    private List<UUID> ListTicketIDs;
    private List<UUID> ListJobOfferIDs;

    public Customer() { }

    public Customer(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber,
            String firstName, String lastName, String location,
            List<UUID> listAppointmentIDs, List<UUID> listTicketIDs, List<UUID> listJobOfferIDs) {
        // Call the constructor of the superclass (User)
        super(userID, userName, userPassword, userEmail, phoneNumber, firstName, lastName);
        this.location = location;
        ListAppointmentIDs = listAppointmentIDs;
        ListTicketIDs = listTicketIDs;
        ListJobOfferIDs = listJobOfferIDs;
    }

    public Customer(UUID uuid) {
        setUserID(uuid);
    }

    // getters
    public String getLocation() {
        return location;
    }

    public List<UUID> getAppointmentIDs() {
        return ListAppointmentIDs;
    }

    public List<String> getAppointmentIDsAsString() {
        List<String> appointmentIDsAsString = new ArrayList<>();
        for (UUID id : ListAppointmentIDs) {
            String idString = id.toString();
            appointmentIDsAsString.add(idString);
        }
        return appointmentIDsAsString;
    }

    public List<UUID> getTicketIDs() {
        return ListTicketIDs;
    }

    public List<String> getTicketIDsAsString() {
        List<String> ticketIDsAsString = new ArrayList<>();
        for (UUID id : ListTicketIDs) {
            String idString = id.toString();
            ticketIDsAsString.add(idString);
        }
        return ticketIDsAsString;
    }

    public List<UUID> getJobOfferIDs() {
        return ListJobOfferIDs;
    }

    public List<String> getJobOfferIDsAsString() {
        List<String> jobOfferIDsAsString = new ArrayList<>();
        for (UUID id : ListJobOfferIDs) {
            String idString = id.toString();
            jobOfferIDsAsString.add(idString);
        }
        return jobOfferIDsAsString;
    }

    public void addTicketID(UUID ticketID) {
        ListTicketIDs.add(ticketID);
        try {
            registry.editUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTicketID(UUID ticketID) {
        ListTicketIDs.remove(ticketID);
        try {
            registry.editUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addJobOfferID(UUID jobOfferID) {
        ListJobOfferIDs.add(jobOfferID);
        try {
            registry.editUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeJobOfferID(UUID jobOfferID) {
        ListJobOfferIDs.remove(jobOfferID);
        try {
            registry.editUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // setters
    public void setLocation(String location) {
        this.location = location;
    }

    public void changeLocation(String newLocation) throws SQLException {
        this.location = location;
        registry.editUser(this);
    }

    public void setAppointmentIDs(List<UUID> listAppointmentIDs) {
        ListAppointmentIDs = listAppointmentIDs;
    }

    public void setTicketIDs(List<UUID> listTicketIDs) {
        ListTicketIDs = listTicketIDs;
    }

    public void setJobOfferIDs(List<UUID> listJobOfferIDs) {
        ListJobOfferIDs = listJobOfferIDs;
    }

    // Methods to manage appointments
    public void addAppointment(UUID appointmentID) {
        ListAppointmentIDs.add(appointmentID);
        try {
            registry.editUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(Appointment appointment) {
        ListAppointmentIDs.remove(appointment.getAppointmentId());
        try {
            registry.editUser(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createTicket(String Title, String details) {
        throw new UnsupportedOperationException();
    }
}
