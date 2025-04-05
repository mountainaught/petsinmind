package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.messages.TicketMessage;

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
		// TODO - implement Customer.CreateTicket
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param AppointmentID
	 */
	public void AddAppointment(int AppointmentID) {
		// TODO - implement Customer.AddAppointment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Appointment
	 */
	public Boolean CancelAppointment(Appointment Appointment) {
		// TODO - implement Customer.CancelAppointment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessage(TicketMessage Message) {
		// TODO - implement Customer.SendMessage
		throw new UnsupportedOperationException();
	}

}