package com.petsinmind.users;

import com.petsinmind.JobOffer;
import com.petsinmind.Pet;
import com.petsinmind.messages.AppointmentMessage;
import com.petsinmind.messages.JobOfferMessage;

import java.util.Calendar;
import java.util.List;

// Tibet
public class PetOwner extends Customer {

	private List<Pet> PetList;
	private String Location;

	/**
	 * 
	 * @param PetName
	 * @param PetType
	 * @param PetSize
	 * @param PetAge
	 */
	public void AddPet(String PetName, String PetType, int PetSize, int PetAge) {
		// TODO - implement PetOwner.AddPet
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param pet
	 */
	public void RemovePet(Pet pet) {
		// TODO - implement PetOwner.RemovePet
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Location
	 */
	public void SetLocation(String Location) {
		// TODO - implement PetOwner.SetLocation
		throw new UnsupportedOperationException();
	}

	public String GetLocation() {
		// TODO - implement PetOwner.GetLocation
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param PetList
	 * @param Location
	 * @param Date
	 * @param Type
	 */
	public boolean CreateJobOffer(List<Pet> PetList, String Location, Calendar Date, String Type) {
		// TODO - implement PetOwner.CreateJobOffer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Pets
	 */
	public List<Pet> SelectPets(List<Pet> Pets) {
		// TODO - implement PetOwner.SelectPets
		throw new UnsupportedOperationException();
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
	public boolean AcceptCaretaker(JobOffer JobOffer, int CaretakerID) {
		// TODO - implement PetOwner.AcceptCaretaker
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param JobOffer
	 * @param CaretakerID
	 */
	public boolean RejectCaretaker(JobOffer JobOffer, int CaretakerID) {
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