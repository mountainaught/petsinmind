package com.petsinmind.users;

import com.petsinmind.Application;
import com.petsinmind.ApplicationSA;
import com.petsinmind.Ticket;
import com.petsinmind.TicketSA;
import com.petsinmind.messages.TicketMessage;
import com.petsinmind.Registry;

import java.util.List;
import java.util.UUID;
import java.io.File;

// Tibet
public class SystemAdmin extends User implements TicketSA, ApplicationSA {

	private List<Ticket> MyTicketList;
	private Registry registry;

// Constructor
	public SystemAdmin(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber, String firstName, String lastName, List<Ticket> myTicketList) {
		super(userID, userName, userPassword, userEmail, phoneNumber, firstName, lastName);
		this.MyTicketList = myTicketList;
		try {
			this.registry = Registry.getInstance(); // Singleton pattern
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// getter
	public List<Ticket> getMyTicketList() {
		return MyTicketList;
	}

	// setter
	public void setMyTicketList(List<Ticket> myTicketList) {
		MyTicketList = myTicketList;
	}

	/**
	 * 
	 * @param TicketID
	 */
	public void SelectTicket(UUID TicketID) {
		// TODO - implement SystemAdmin.SelectTicket
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TicketID
	 * @param SystemAdminID
	 */
	public void ShareTicket(UUID TicketID, UUID SystemAdminID) {
		// TODO - implement SystemAdmin.ShareTicket
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TicketID
	 */
	public void CloseTicket(UUID TicketID) {
		// TODO - implement SystemAdmin.CloseTicket
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param userID
	 */
	public User FindUserByID(UUID userID) {
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

	// Falak - Khaled said something about viewing all applications:
	public void viewAllApplications() {
		// TODO - implement SystemAdmin.viewAllApplications
		throw new UnsupportedOperationException();
	}
	
	public boolean createCaretaker(String userName, String userPassword, String userEmail, String phoneNumber, String firstName, String lastName, String location, float pay, File imageFile) {
        try {
            // Create a new Caretaker object
            Caretaker caretaker = new Caretaker(
                UUID.randomUUID(),
                userName,
                userPassword,
                userEmail,
                phoneNumber,
                firstName,
                lastName,
                location,
                null, // Ticket IDs (placeholder)
                null, // Job Offer IDs (placeholder)
                null, // Appointment IDs (placeholder)
                pay,
                null, // availability (placeholder)
                null  // reviews (placeholder)
            );

            // Use the Registry to create the caretaker account
            return registry.createUser(caretaker, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}