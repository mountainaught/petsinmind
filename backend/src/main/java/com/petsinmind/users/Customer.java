package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.Ticket;
import com.petsinmind.messages.TicketMessage;

import java.time.Instant;
import java.util.List;

// Tibet
public abstract class Customer extends User {

	private List<Integer> ListAppointmentIDs;

	/**
	 * 
	 * @param Title
	 * @param details
	 */
	public boolean CreateTicket(String Title, String details) {
		return new Ticket(Title, details, Instant.now().toString(), super.getUserID(), );
	}

	/**
	 * 
	 * @param AppointmentID
	 */
	public void AddAppointment(Appointment appointment) { ListAppointmentIDs.add(appointment); }

	/**
	 * 
	 * @param Appointment
	 */
	public Boolean CancelAppointment(Appointment appointment) { return ListAppointmentIDs.remove(appointment); }

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessage(TicketMessage Message) {

	}

}