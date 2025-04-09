package com.petsinmind.users;

import com.petsinmind.Application;
import com.petsinmind.Ticket;
import com.petsinmind.Registry;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.io.File;

// Tibet
public class SystemAdmin extends User {

	private List<Ticket> MyTicketList;
	private Registry registry;

	public SystemAdmin() {}

	public SystemAdmin(UUID systemAdminID) {
		super(systemAdminID);
	}

	public SystemAdmin(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber,
			String firstName, String lastName, List<Ticket> myTicketList) {
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
	 * @param ticketID
	 */
	public void selectTicket(UUID ticketID) throws SQLException {
		MyTicketList.add(registry.getTicket(new Ticket(ticketID)));
	}

	/**
	 * 
	 * @param ticketID
	 * @param systemAdminID
	 */
	public void shareTicket(UUID ticketID, UUID systemAdminID) throws SQLException {
		SystemAdmin systemAdmin = (SystemAdmin) registry.findUser(new SystemAdmin(systemAdminID));
		systemAdmin.selectTicket(ticketID);
		registry.editUser(systemAdmin);
	}

	/**
	 * 
	 * @param TicketID
	 */
	public void closeTicket(UUID TicketID) throws SQLException {
		registry.deleteTicket(new Ticket(TicketID));
	}

	/**
	 * 
	 * @param userID
	 */
	public User findUserByID(UUID userID) throws SQLException {
		User user = new User(){};
		user.setUserID(userID);

		return registry.findUser(user);
	}

	/**
	 * 
	 * @param application
	 */
	public void rejectApplication(Application application) {
		registry.deleteApplication(application);
	}

	/**
	 * 
	 * @param application
	 */
	public Caretaker acceptApplication(Application application) throws SQLException {
		Caretaker caretaker = new Caretaker();

		caretaker.setUserName(application.getUserName());
		caretaker.setUserPassword(application.getUserPassword());
		caretaker.setUserEmail(application.getUserEmail());
		caretaker.setPhoneNumber(application.getPhoneNumber());
		caretaker.setFirstName(application.getFirstName());
		caretaker.setLastName(application.getLastName());
		caretaker.setLocation(application.getLocation());

		registry.createUser(caretaker);
		registry.deleteApplication(application);
		return caretaker;
	}


	public boolean createCaretaker(String userName, String userPassword, String userEmail, String phoneNumber,
			String firstName, String lastName, String location, float pay, File imageFile) {
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
					null // reviews (placeholder)
			);

			// Use the Registry to create the caretaker account
			return registry.createUser(caretaker);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}