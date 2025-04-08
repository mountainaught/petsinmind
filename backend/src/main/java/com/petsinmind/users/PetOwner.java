package com.petsinmind.users;

import com.petsinmind.JobOffer;
import com.petsinmind.Pet;
import com.petsinmind.messages.AppointmentMessage;
import com.petsinmind.messages.JobOfferMessage;


import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

// Tibet
public class PetOwner extends Customer {

	private List<Pet> PetList;

	/**
	 * 
	 * @param PetName
	 * @param PetType
	 * @param PetSize
	 * @param PetAge
	 */
	public void AddPet(String PetName, String PetType, String PetSize, Integer PetAge) {
		PetList.add(new Pet(PetName, PetType, PetSize, PetAge));
	}

	/**
	 * 
	 * @param pet
	 */
	public void RemovePet(Pet pet) {
		PetList.remove(pet);
	}

	/**
	 * 
	 * @param Location
	 */
	// public void SetLocation(String Location) {
	// this.Location = Location;
	// }

	// public String GetLocation() {
	// return Location;
	// }

	/**
	 * 
	 * @param PetList
	 * @param location
	 * @param date
	 * @param type
	 */
	// TODO - FINISH IMPLEMENTING THIS! Need to push it to db
	// public boolean CreateJobOffer(List<Pet> petList, String location, Calendar
	// startDate, Calendar endDate,
	// String type) {
	// JobOffer jobOffer = new JobOffer();

	// String jobOfferID = FirebaseWriter.pushJobOffer(this.getUserID(), petList,
	// type, startDate);

	// jobOffer.setPetOwner(this);
	// jobOffer.setPets(petList);
	// jobOffer.setLocation(location);
	// jobOffer.setStartDate(startDate);
	// jobOffer.setEndDate(endDate);
	// jobOffer.setType(type);
	// }

	/**
	 * 
	 * @param Pets
	 */
	public List<Pet> SelectPets(List<Pet> Pets) {
		// TODO - Is this class right? It's only returning whether all pets exist or not
		// right now.
		return new HashSet<>(PetList).containsAll(Pets) ? PetList : null;
	}

	/**
	 * 
	 * @param JobOfferID
	 */
	public boolean CancelJobOffer(String JobOfferID) {
		// TODO - implement PetOwner.CancelJobOffer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param JobOffer
	 * @param CaretakerID
	 */
	public boolean AcceptCaretaker(JobOffer JobOffer, UUID CaretakerID) {
		// TODO - implement PetOwner.AcceptCaretaker
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param JobOffer
	 * @param CaretakerID
	 */
	public boolean RejectCaretaker(JobOffer JobOffer, UUID CaretakerID) {
		// TODO - implement PetOwner.RejectCaretaker
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Details
	 * @param Rating
	 */
	public boolean AddReview(String Details, int Rating) {
		// TODO - implement PetOwner.AddReview
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessageJO(JobOfferMessage Message) {
		// TODO - implement PetOwner.SendMessageJO
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Message
	 */
	public boolean SendMessageApp(AppointmentMessage Message) {
		// TODO - implement PetOwner.SendMessageApp
		throw new UnsupportedOperationException();
	}

}