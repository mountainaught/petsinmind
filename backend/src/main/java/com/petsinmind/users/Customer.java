package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.messages.TicketMessage;

import java.util.List;

public abstract class Customer extends User {
    protected String userID;
    protected String userName;
    protected String userPassword;
    protected String userEmail;
    protected String phoneNumber;
    protected String firstName;
    protected String lastName;
    protected String location;

    private List<String> ListAppointmentIDs;
    private List<String> ListTicketIDs;
    private List<String> ListJobOfferIDs;


	public void setUserID(String userID) { this.userID = userID; }
	public void setUserName(String userName) { this.userName = userName; }
	public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
	public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
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
