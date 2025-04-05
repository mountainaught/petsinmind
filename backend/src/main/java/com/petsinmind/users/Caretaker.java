package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.JobOffer;
import com.petsinmind.JobOfferCT;
import com.petsinmind.Review;
import com.petsinmind.messages.AppointmentMessage;
import com.petsinmind.messages.JobOfferMessage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

// Tibet
public class Caretaker extends Customer implements JobOfferCT {

	private int Pay;
	private HashMap<Calendar, Boolean> schedule;
	private List<Review> ListReviews;

	public int GetPay() {
		// TODO - implement Caretaker.GetPay
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Pay
	 */
	public void SetPay(int Pay) {
		// TODO - implement Caretaker.SetPay
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param JobOffer
	 */
	public Appointment AcceptJobOffer(JobOffer JobOffer) {
		// TODO - implement Caretaker.AcceptJobOffer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param JobOffer
	 */
	public Void RejectJobOffer(JobOffer JobOffer) {
		// TODO - implement Caretaker.RejectJobOffer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param schedule
	 */
	public void editAvailability(HashMap<Calendar, Boolean> schedule) {
		// TODO - implement Caretaker.editAvailability
		throw new UnsupportedOperationException();
	}

	public HashMap<Calendar, Boolean> getSchedule() {
		return this.schedule;
	}

	/**
	 * 
	 * @param Review
	 */
	public void AddReview(Review Review) {
		// TODO - implement Caretaker.AddReview
		throw new UnsupportedOperationException();
	}

	public List<Review> GetReviews() {
		// TODO - implement Caretaker.GetReviews
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessageJO(JobOfferMessage Message) {
		// TODO - implement Caretaker.SendMessageJO
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessageApp(AppointmentMessage Message) {
		// TODO - implement Caretaker.SendMessageApp
		throw new UnsupportedOperationException();
	}

}