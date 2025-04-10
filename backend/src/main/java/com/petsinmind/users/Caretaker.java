package com.petsinmind.users;
import com.petsinmind.*;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

// Tibet
public class Caretaker extends Customer implements JobOfferCT {
	private float Pay;
    private boolean[][] availability = new boolean[7][24]; // 7 days a week, 24 hours a day
	private List<Review> ListReviews;

    public Caretaker() {}

    public Caretaker(UUID uuid) {
        super(uuid);
    }

	public Caretaker(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber, String firstName, String lastName, String location,
			List<UUID> listAppointmentIDs, List<UUID> listTicketIDs, List<UUID> listJobOfferIDs, float pay, boolean[][] availability, List<Review> ListReviews) throws SQLException {
		super(userID, userName, userPassword, userEmail, phoneNumber, firstName, lastName, location, listAppointmentIDs,
				listTicketIDs, listJobOfferIDs);
		this.Pay = pay;
		this.availability = availability;
		this.ListReviews = ListReviews;
	}

	// Getters
	public float getPay() {
		return Pay;
	}

	public List<Review> getReviews() {
		return ListReviews;
	}

	public boolean[][] getAvailability() {
		return availability;
	}

	// Setters

	public void setPay(float Pay) {
		this.Pay = Pay;
	}

	public void setReviews(List<Review> ListReviews) {
		this.ListReviews = ListReviews;
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

	/**
	 * 
	 * @param offer
	 */
	//
	public Appointment acceptJobOffer(JobOffer offer) throws SQLException {
		 Appointment appointment = new Appointment(offer.getJobOfferID(), this, offer.getPetOwner(), offer.getPets(),
				offer.getStartDate(), offer.getEndDate(), offer.getType());

		 super.addAppointment(appointment.getAppointmentId());
		 appointment.getPetOwner().addAppointment(appointment.getAppointmentId());

		 registry.createAppointment(appointment);
		 registry.deleteJobOffer(offer);
		 return appointment;
	}

	/**
	 * 
	 * @param jobOffer
	 */
	public void rejectJobOffer(JobOffer jobOffer) throws SQLException {
		jobOffer.addRejectedCaretaker(this);
		registry.editJobOffer(jobOffer);
	}


	/**
	 * 
	 * @param Review
	 */
	public void addReview(Review Review) throws SQLException {
		ListReviews.add(Review);
		registry.editUser(this);
	}

}