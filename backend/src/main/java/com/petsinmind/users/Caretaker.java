package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.JobOffer;
import com.petsinmind.JobOfferCT;
import com.petsinmind.Review;
import com.petsinmind.messages.AppointmentMessage;
import com.petsinmind.messages.JobOfferMessage;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;



import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// Tibet
public class Caretaker extends Customer implements JobOfferCT {

	private float Pay;
	private HashMap<Calendar, Boolean> schedule;
	private List<Review> ListReviews;

	public static void readCaretakers(Connection conn) {
    try {
        String sql = "SELECT * FROM caretaker";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Caretaker ct = new Caretaker();

            // From Customer
            ct.userID = UUID.fromString(rs.getString("UserID"));
            ct.userName = rs.getString("UserName");
            ct.userPassword = rs.getString("UserPassword");
            ct.userEmail = rs.getString("UserEmail");
            ct.phoneNumber = rs.getString("PhoneNumber");
            ct.firstName = rs.getString("FirstName");
            ct.lastName = rs.getString("LastName");
            ct.location = rs.getString("Location");

            // From Caretaker
            ct.SetPay(rs.getFloat("Pay"));

            System.out.println("Caretaker: " + ct.userName + " (" + ct.GetPay() + " KD)");
        }

        rs.close();
        stmt.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

	public static void insertCaretaker(Connection conn, Caretaker ct) {
    try {
        String sql = "INSERT INTO caretaker (UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName, Location, Pay) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ct.userID.toString());
        ps.setString(2, ct.userName);
        ps.setString(3, ct.userPassword);
        ps.setString(4, ct.userEmail);
        ps.setString(5, ct.phoneNumber);
        ps.setString(6, ct.firstName);
        ps.setString(7, ct.lastName);
        ps.setString(8, ct.location);
        ps.setFloat(9, ct.GetPay());

        int rows = ps.executeUpdate();
        System.out.println("✅ Rows inserted: " + rows);

        ps.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


	public float GetPay() {
		return Pay;
	}

	/**
	 * 
	 * @param Pay
	 */
	public void SetPay(float Pay) {
		this.Pay = Pay;
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
	 * @param schedule
	 */
	public void editAvailability(HashMap<Calendar, Boolean> schedule) {
		this.schedule = schedule;
	}

	public HashMap<Calendar, Boolean> getSchedule() {
		return this.schedule;
	}

	/**
	 * 
	 * @param Review
	 */
	public void AddReview(Review Review) {
		ListReviews.add(Review);
	}

	public List<Review> GetReviews() {
		return ListReviews;
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