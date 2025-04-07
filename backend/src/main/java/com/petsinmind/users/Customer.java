package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.messages.TicketMessage;

import java.util.List;
import java.util.UUID;

// Tibet
public abstract class Customer extends User {

	private List<String> ListAppointmentIDs;

	/**
	 * 
	 * @param Title
	 * @param details
	 */
	public boolean CreateTicket(String Title, String details) {
		// TODO - Implement once TicketSA and Firebase is figured out
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param appointmentID
	 */
	public void AddAppointment(String appointmentID) { ListAppointmentIDs.add(appointmentID); }

	/**
	 * 
	 * @param appointment
	 */
	public Boolean CancelAppointment(Appointment appointment) { return ListAppointmentIDs.remove(appointment.getAppointmentId()); }

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessage(TicketMessage message) {
		throw new UnsupportedOperationException();
	}

}