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

	public int GetPay() { return Pay; }

	/**
	 * 
	 * @param Pay
	 */
	public void SetPay(int Pay) { this.Pay = Pay; }

	/**
	 * 
	 * @param offer
	 */
	// TODO - Probably should add code to remove JO from DB and send Appointment messages
	public Appointment AcceptJobOffer(JobOffer offer) {
		Appointment appointment = new Appointment(offer.getJobOfferID(), this, offer.getPetOwner(), offer.getPets(), offer.getStartDate(), offer.getEndDate(), offer.getType());

		super.AddAppointment(appointment.getAppointmentId());
		appointment.getPetOwner().AddAppointment(appointment.getAppointmentId());
		return appointment;
	}

	/**
	 * 
	 * @param JobOffer
	 */
	public Void RejectJobOffer(JobOffer JobOffer) {
		// TODO - implement Caretaker.RejectJobOffer - Do we remove Caretaker from availableCaretakers?
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param schedule
	 */
	public void editAvailability(HashMap<Calendar, Boolean> schedule) { this.schedule = schedule; }

	public HashMap<Calendar, Boolean> getSchedule() {
		return this.schedule;
	}

	/**
	 * 
	 * @param Review
	 */
	public void AddReview(Review Review) { ListReviews.add(Review); }

	public List<Review> GetReviews() { return ListReviews; }

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