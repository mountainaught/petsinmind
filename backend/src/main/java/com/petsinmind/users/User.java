package com.petsinmind.users;

import com.petsinmind.messages.Message;

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

	/**
	 * 
	 * @param Password
	 */
	public void ChangePassword(String Password) {
		UserPassword = Password;
	}

	public UUID getUserID() {
		return UserID;
	}

	public String getUserName() {
		return UserName;
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
	 * 
	 * @param Message
	 */
	// public boolean SendMessage(Message Message) {

	// }

}