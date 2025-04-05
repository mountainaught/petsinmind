package com.petsinmind.users;

import com.petsinmind.messages.Message;

import java.util.*;

// Tibet
public abstract class User {

	Collection<Message> Sends;
	private int UserID;
	private String UserName;
	private String UserPassword;
	private String UserEmail;
	private String PhoneNumber;
	private String FirstName;
	private String LastName;

	/**
	 * 
	 * @param Password
	 */
	public void ChangePassword(String Password) {
		// TODO - implement User.ChangePassword
		throw new UnsupportedOperationException();
	}

	public int getUserID() {
		// TODO - implement User.getUserID
		throw new UnsupportedOperationException();
	}

	public String getUserName() {
		// TODO - implement User.getUserName
		throw new UnsupportedOperationException();
	}

	public String getPhoneNumber() {
		// TODO - implement User.getPhoneNumber
		throw new UnsupportedOperationException();
	}

	public String getFirstName() {
		// TODO - implement User.getFirstName
		throw new UnsupportedOperationException();
	}

	public String getLastName() {
		// TODO - implement User.getLastName
		throw new UnsupportedOperationException();
	}

	public String getUserEmail() {
		// TODO - implement User.getUserEmail
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param email
	 */
	public void setUserEmail(String email) {
		// TODO - implement User.setUserEmail
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		// TODO - implement User.setUserName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Number
	 */
	public void setPhoneNumber(String Number) {
		// TODO - implement User.setPhoneNumber
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param LastName
	 */
	public void setLastName(String LastName) {
		// TODO - implement User.setLastName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param FirstName
	 */
	public void setFirstName(String FirstName) {
		// TODO - implement User.setFirstName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessage(Message Message) {
		// TODO - implement User.SendMessage
		throw new UnsupportedOperationException();
	}

}