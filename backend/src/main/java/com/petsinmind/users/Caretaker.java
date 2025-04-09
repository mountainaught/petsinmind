package com.petsinmind.users;

import com.petsinmind.JobOffer;
import com.petsinmind.JobOfferCT;
import com.petsinmind.Review;
import com.petsinmind.messages.AppointmentMessage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Tibet
public class Caretaker extends Customer implements JobOfferCT {
	private float Pay;
	
	private boolean[][] availability = new boolean[7][24]; // 7 days a week, 24 hours a day 

	private List<Review> ListReviews;

	public Caretaker(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber, String firstName, String lastName, String location,
			List<String> listAppointmentIDs, List<String> listTicketIDs, List<String> listJobOfferIDs, float pay, boolean[][] availability, List<Review> ListReviews) {
		super(userID, userName, userPassword, userEmail, phoneNumber, firstName, lastName, location, listAppointmentIDs,
				listTicketIDs, listJobOfferIDs);
		this.Pay = pay;
		this.availability = availability;
		this.ListReviews = ListReviews;
	}

	public boolean[][] getAvailability() {
		return availability;
	}
	/**
	 * 
	 * @param availability sets the availability for the caretaker for the week (7 days, 24 hours)
	 */
	
	public void setAvailability(boolean[][] availability) {
		if (availability.length == 7 && availability[0].length == 24) {
			this.availability = availability;
		} else {
			throw new IllegalArgumentException("Availability must be a 7x24 boolean array.");
		}
	}
	/**
	 * 
	 * @param day sets avaliability for a specific day of the week (0-6)
	 * @param hour sets avaliability for a specific hour of the day (0-23) 
	 */

	public void setAvailable(int day, int hour) {
		if (day >= 0 && day < 7 && hour >= 0 && hour < 24) {
			availability[day][hour] = true;
		}
	}
	
	public void setUnavailable(int day, int hour) {
		if (day >= 0 && day < 7 && hour >= 0 && hour < 24) {
			availability[day][hour] = false;
		}
	}
	
	public boolean isAvailable(int day, int hour) {
		if (day >= 0 && day < 7 && hour >= 0 && hour < 24) {
			return availability[day][hour];
		}
		return false;
	}

	public float getPay() {
		return Pay;
	}

	/**
	 * 
	 * @param Pay
	 */

	public void setPay(float Pay) {
		this.Pay = Pay;
	}
	
	public void setReviews(List<Review> ListReviews) {
		this.ListReviews = ListReviews;
	}

	public List<Review> getReviews() {
		return ListReviews;
	}

	/**
	 * 
	 * @param offer
	 */
	// TODO - Probably should add code to remove JO from DB and send Appointment
	// messages
	// public Appointment AcceptJobOffer(JobOffer offer) {
	// 	Appointment appointment = new Appointment(offer.getJobOfferID(), this, offer.getPetOwner(), offer.getPets(),
	// 			offer.getStartDate(), offer.getEndDate(), offer.getType());

	// 	super.AddAppointment(appointment.getAppointmentId());
	// 	appointment.getPetOwner().AddAppointment(appointment.getAppointmentId());
	// 	return appointment;
	// }

	/**
	 * 
	 * @param JobOffer
	 */
	public Void RejectJobOffer(JobOffer JobOffer) {
		// TODO - implement Caretaker.RejectJobOffer - Do we remove Caretaker from
		// availableCaretakers?
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @param Review
	 */
	public void AddReview(Review Review) {
		ListReviews.add(Review);
	}

	/**
	 * 
	 * @param Message
	 */
	// public boolean SendMessageJO(JobOffer offer, JobOfferMessage Message) {
	// offer.addMessage(Message);
	// // TODO - PUSH UPDATED JO TO FIREBASE
	// }

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessageApp(AppointmentMessage Message) {
		// TODO - implement Caretaker.SendMessageApp
		throw new UnsupportedOperationException();
	}

}