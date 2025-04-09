package com.petsinmind.users;

import com.petsinmind.Appointment;
import com.petsinmind.JobOffer;
import com.petsinmind.Pet;
import com.petsinmind.Ticket;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
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
	 * @param Pets
	 */
	public List<Pet> selectPets(List<Pet> Pets) {
		// TODO - Is this class right? It's only returning whether all pets exist or not

	}

	/**
	 * 
	 * @param JobOfferID
	 */
	public boolean cancelJobOffer(UUID JobOfferID) throws SQLException {
		registry.deleteJobOffer(new JobOffer(JobOfferID));
	}

	/**
	 * 
	 * @param jobOffer
	 * @param caretakerID
	 */
	public boolean acceptCaretaker(JobOffer jobOffer, UUID caretakerID) throws SQLException {
		Caretaker ct = (Caretaker) registry.findUser(new Caretaker(caretakerID));
		jobOffer.addAcceptedCaretaker(ct);
		return registry.editJobOffer(jobOffer);
	}

	/**
	 * 
	 * @param jobOffer
	 * @param caretakerID
	 */
	public boolean rejectCaretaker(JobOffer jobOffer, UUID caretakerID) throws SQLException {
		Caretaker ct = (Caretaker) registry.findUser(new Caretaker(caretakerID));
		jobOffer.addRejectedCaretaker(ct);
		return registry.editJobOffer(jobOffer);
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessageJO(JobOfferMessage Message) {
		// TODO - implement PetOwner.SendMessageJO
		throw new UnsupportedOperationException();
	}

}