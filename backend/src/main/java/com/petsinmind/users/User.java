package com.petsinmind.users;

import com.petsinmind.messages.Message;
import com.petsinmind.Registry;

import java.sql.SQLException;
import java.util.*;

// Tibet
public abstract class User {

	Collection<Message> Sends;
	private UUID UserID;
	private String UserName;
	private String UserPassword;
	private String UserEmail;
	private String PhoneNumber;
	private String FirstName;
	private String LastName;

	Registry registry;

	public User() {
		try {
			this.registry = Registry.getInstance(); // Singleton pattern
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User(UUID caretakerID) {
		this.UserID = caretakerID;
		try {
			this.registry = Registry.getInstance(); // Singleton pattern
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor for User class.
	 *
	 * @param userID       Unique identifier for the user.
	 * @param userName     Username of the user.
	 * @param userPassword Password of the user.
	 * @param userEmail    Email of the user.
	 * @param phoneNumber  Phone number of the user.
	 * @param firstName    First name of the user.
	 * @param lastName     Last name of the user.
	 */
	public User(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber, String firstName, String lastName) {
		this.UserID = userID;
		this.UserName = userName;
		this.UserPassword = userPassword;
		this.UserEmail = userEmail;
		this.PhoneNumber = phoneNumber;
		this.FirstName = firstName;
		this.LastName = lastName;
		try {
			this.registry = Registry.getInstance(); // Singleton pattern
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// getters

	public UUID getUserID() {
		return UserID;
	}

	public String getUserName() {
		return UserName;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public String getFirstName() {
		return FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	// setters

	/**
	 *
	 * @param userID
	 */
	public void setUserID(UUID userID) {
		UserID = userID;
	}

	public void setUserPassword(String password) {
		UserPassword = password;
	}

	/**
	 * 
	 * @param email
	 */
	public void setUserEmail(String email) {
		UserEmail = email;
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * 
	 * @param Number
	 */
	public void setPhoneNumber(String Number) {
		PhoneNumber = Number;
	}

	/**
	 * 
	 * @param LastName
	 */
	public void setLastName(String LastName) {
		this.LastName = LastName;
	}

	/**
	 * 
	 * @param FirstName
	 */
	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}

	/**
	 * Generic method to change a user attribute and update the registry.
	 *
	 * @param Message
	 */
	// public boolean SendMessage(Message Message) {

	// }

	private boolean changeAttribute(String currentValue, String newValue, String attribute, Registry registry) {
		try {
			if (currentValue.equals(newValue)) {
				System.out.println("No change detected for " + attribute + ".");
				return false;
			} else {
				switch (attribute) {
					case "FirstName":
						this.FirstName = newValue;
						break;
					case "LastName":
						this.LastName = newValue;
						break;
					case "UserPassword":
						this.UserPassword = newValue;
						break;
					case "UserName":
						this.UserName = newValue;
						break;
					case "UserEmail":
						this.UserEmail = newValue;
						break;
					case "PhoneNumber":
						this.PhoneNumber = newValue;
						break;
					default:
						System.out.println("Invalid attribute: " + attribute);
						return false;
				}
				registry.editUser(this);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Refactored methods
	public boolean changeFirstName(String newFirstName, Registry registry) {
		return changeAttribute(this.FirstName, newFirstName, "FirstName", registry);
	}

	public boolean changeLastName(String newLastName, Registry registry) {
		return changeAttribute(this.LastName, newLastName, "LastName", registry);
	}

	public boolean changeUserPassword(String newUserPassword, Registry registry) {
		return changeAttribute(this.UserPassword, newUserPassword, "UserPassword", registry);
	}

	public boolean changeUserName(String newUserName, Registry registry) {
		return changeAttribute(this.UserName, newUserName, "UserName", registry);
	}

	public boolean changeUserEmail(String newUserEmail, Registry registry) {
		return changeAttribute(this.UserEmail, newUserEmail, "UserEmail", registry);
	}

	public boolean changeUserPhoneNumber(String newUserPhoneNumber, Registry registry) {
		return changeAttribute(this.PhoneNumber, newUserPhoneNumber, "PhoneNumber", registry);
	}
}