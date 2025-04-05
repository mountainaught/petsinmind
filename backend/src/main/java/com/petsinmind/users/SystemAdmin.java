package com.petsinmind.users;

import com.petsinmind.Application;
import com.petsinmind.ApplicationSA;
import com.petsinmind.Ticket;
import com.petsinmind.TicketSA;
import com.petsinmind.messages.TicketMessage;

import java.util.List;

// Tibet
public class SystemAdmin extends User implements TicketSA, ApplicationSA {

	private List<Ticket> MyTicketList;

	/**
	 * 
	 * @param TicketID
	 */
	public void SelectTicket(int TicketID) {
		// TODO - implement SystemAdmin.SelectTicket
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TicketID
	 * @param SystemAdminID
	 */
	public void ShareTicket(int TicketID, int SystemAdminID) {
		// TODO - implement SystemAdmin.ShareTicket
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TicketID
	 */
	public void CloseTicket(int TicketID) {
		// TODO - implement SystemAdmin.CloseTicket
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param userID
	 */
	public User FindUserByID(int userID) {
		// TODO - implement SystemAdmin.FindUserByID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 */
	public User FindUserByUserName(String username) {
		// TODO - implement SystemAdmin.FindUserByUserName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Application
	 */
	public void RejectApplication(Application Application) {
		// TODO - implement SystemAdmin.RejectApplication
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Application
	 */
	public Caretaker AcceptApplication(Application Application) {
		// TODO - implement SystemAdmin.AcceptApplication
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessage(TicketMessage Message) {
		// TODO - implement SystemAdmin.SendMessage
		throw new UnsupportedOperationException();
	}

}