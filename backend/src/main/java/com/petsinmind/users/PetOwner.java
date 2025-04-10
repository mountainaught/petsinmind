package com.petsinmind.users;
import com.petsinmind.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

// Tibet
public class PetOwner extends Customer {

	private List<Pet> PetList;

	public PetOwner(UUID userID, String userName, String userPassword, String userEmail, String phoneNumber,
			String firstName, String lastName, String location,
			List<UUID> listAppointmentIDs, List<UUID> listTicketIDs, List<UUID> listJobOfferIDs,
			List<Pet> petList) {
		super(userID, userName, userPassword, userEmail, phoneNumber, firstName, lastName, location, listAppointmentIDs,
				listTicketIDs, listJobOfferIDs);
		this.PetList = petList;
	}

	public PetOwner() {

	}

	public PetOwner(UUID petOwnerID) {
		setUserID(petOwnerID);
	}

	// getters
	public List<Pet> getPetList() {
		return PetList;
	}

	public List<String> getPetIDs() {
		List<String> petIDs = new ArrayList<>();
		for (Pet pet : PetList) {
			petIDs.add(pet.getPetID().toString());
		}
		return petIDs;
	}

	// setters
	public void setPetList(List<Pet> petList) {
		PetList = petList;
	}

	/**
	 * 
	 * @param PetName
	 * @param PetType
	 * @param PetSize
	 * @param PetAge
	 */
	public void addPet(String PetName, String PetType, String PetSize, Integer PetAge) throws SQLException {
		Pet pet = new Pet(PetName, PetType, PetSize, PetAge);
		PetList.add(pet);

		registry.createPet(pet, this);
		registry.editUser(this);
	}

	/**
	 * 
	 * @param pet
	 */
	public void removePet(Pet pet) throws SQLException {
		PetList.remove(pet);
		registry.deletePet(pet, this);
	}

	/**
	 * 
	 * @param petList
	 * @param location
	 * @param type
	 */
	 public boolean createJobOffer(List<Pet> petList, String location, Calendar startDate, Calendar endDate, String type) throws SQLException {
		 JobOffer jobOffer = new JobOffer(this, petList, startDate, endDate, type);
		 return registry.createJobOffer(jobOffer);
	 }

	/**
	 * 
	 * @param JobOfferID
	 */
	public boolean cancelJobOffer(UUID JobOfferID) throws SQLException {
		return registry.deleteJobOffer(new JobOffer(JobOfferID));
	}

	/**
	 * 
	 * @param jobOffer
	 * @param caretakerID
	 */
	public void acceptCaretaker(JobOffer jobOffer, UUID caretakerID) throws SQLException {
		Caretaker ct = (Caretaker) registry.findUser(new Caretaker(caretakerID));
		jobOffer.acceptCaretaker(ct);
	}

	/**
	 * 
	 * @param jobOffer
	 * @param caretakerID
	 */
	public void rejectCaretaker(JobOffer jobOffer, UUID caretakerID) throws SQLException {
		Caretaker ct = (Caretaker) registry.findUser(new Caretaker(caretakerID));
		jobOffer.rejectCaretaker(ct);
	}

	public Payment createPayment(float paymentAmount, String paymentCurrency, String paymentMethod, UUID receiverID) throws SQLException {
		Payment payment = new Payment();
		payment.setPaymentCurrency(paymentCurrency);
		payment.setReceiverID(receiverID);
		payment.setSenderID(this.getUserID());
		payment.setPaymentDate(Calendar.getInstance());
		payment.setPaymentMethod(paymentMethod);

		if (paymentAmount != 0.0f) {
			payment.setPaymentAmount(paymentAmount);
		} else {
			Caretaker ct = (Caretaker) registry.findUser(new Caretaker(receiverID));
			payment.setPaymentAmount(ct.getPay());
		}

		registry.createPayment(payment);
		return payment;
	}

}