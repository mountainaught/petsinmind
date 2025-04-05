package com.petsinmind.firebase;

import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * FirebaseWriter is a centralized utility for writing and reading data
 * to/from Firebase Realtime Database.
 *
 * Each method in this class should be called from the relevant service layer.
 */
public class FirebaseWriter {

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();

    // =========================
    // TEST METHODS
    // =========================

    /**
     * Test write to Firebase under /test
     */
    public static void testWrite() {
        db.getReference("test").setValueAsync("Hello from backend");
        System.out.println("Test value written to Firebase.");
    }

    /**
     * Test read from /test
     */
    public static void testRead() {
        DatabaseReference ref = db.getReference("test");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                System.out.println("Test value read from Firebase: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Test read failed: " + error.getMessage());
            }
        });
    }

    // =========================
    // DOMAIN WRITERS (TO IMPLEMENT)
    // =========================

    /**
     * Write a pet owner to /users/petOwners/{uid}
     *
     * @param uid Firebase Auth user ID
     * @param name Full name of the pet owner
     * @param email Email address
     * @param phone Phone number
     * @param petList Map of petId -> pet details
     */
    public static void writePetOwner(String uid, String name, String email, String phone, Map<String, Map<String, Object>> petList) {
        Map<String, Object> ownerData = new HashMap<>();
        ownerData.put("name", name);
        ownerData.put("email", email);
        ownerData.put("phone", phone);
        ownerData.put("petList", petList);

        db.getReference("users/petOwners").child(uid).setValueAsync(ownerData);
        System.out.println("Pet owner written with UID: " + uid);
    }

        /**
     * Write a caretaker profile to /users/caretakers/{uid}
     *
     * @param uid Firebase Auth user ID
     * @param name Full name of the caretaker
     * @param email Email address
     * @param phone Phone number
     * @param schedule Map of day -> list of available time slots (e.g., "monday" -> ["09:00-12:00"])
     * @param profile Map containing profile info like experience, bio, etc.
     */
    public static void writeCaretaker(
        String uid,
        String name,
        String email,
        String phone,
        Map<String, Object> schedule,
        Map<String, Object> profile
    ) {
        Map<String, Object> caretakerData = new HashMap<>();
        caretakerData.put("name", name);
        caretakerData.put("email", email);
        caretakerData.put("phone", phone);
        caretakerData.put("schedule", schedule);
        caretakerData.put("profile", profile);

        db.getReference("users/caretakers").child(uid).setValueAsync(caretakerData);
        System.out.println("Caretaker written with UID: " + uid);
    }

    /**
     * Push a new job offer to /jobOffers
     *
     * @param ownerId Firebase UID of the pet owner
     * @param pets List of pet IDs selected for the job
     * @param serviceType Type of service (e.g., walk, groom)
     * @param date Job date (ISO string or format you use)
     * @param time Job time (e.g., "14:00")
     * @param timestamp CreatedAt timestamp (System.currentTimeMillis())
     * @return jobOfferId
     */
    public static String pushJobOffer(String ownerId, List<String> pets, String serviceType, String date, String time, long timestamp) {
        Map<String, Object> jobData = new HashMap<>();
        jobData.put("ownerId", ownerId);
        jobData.put("pets", pets);
        jobData.put("serviceType", serviceType);
        jobData.put("date", date);
        jobData.put("time", time);
        jobData.put("status", "pending");
        jobData.put("confirmedCaretakerId", null);
        jobData.put("availableCaretakers", new HashMap<>()); // filled later
        jobData.put("createdAt", timestamp);

        DatabaseReference ref = db.getReference("jobOffers").push();
        ref.setValueAsync(jobData);
        System.out.println("Job offer created: " + ref.getKey());
        return ref.getKey();
    }

    /**
     * Push a new appointment to /appointments
     *
     * @param jobOfferId ID of the original job offer
     * @param ownerId UID of the pet owner
     * @param caretakerId UID of the confirmed caretaker
     * @param pets List of pet IDs for the appointment
     * @param serviceType Type of service (e.g., walk, groom)
     * @param date Date of appointment (e.g., "2025-04-10")
     * @param time Time of appointment (e.g., "14:00")
     * @param timestamp CreatedAt timestamp
     * @return appointmentId
     */
    public static String pushAppointment(
        String jobOfferId,
        String ownerId,
        String caretakerId,
        List<String> pets,
        String serviceType,
        String date,
        String time,
        long timestamp
    ) {
        Map<String, Object> appointmentData = new HashMap<>();
        appointmentData.put("jobOfferId", jobOfferId);
        appointmentData.put("ownerId", ownerId);
        appointmentData.put("caretakerId", caretakerId);
        appointmentData.put("pets", pets);
        appointmentData.put("serviceType", serviceType);
        appointmentData.put("date", date);
        appointmentData.put("time", time);
        appointmentData.put("status", "upcoming");
        appointmentData.put("createdAt", timestamp);

        DatabaseReference ref = db.getReference("appointments").push();
        ref.setValueAsync(appointmentData);
        System.out.println("Appointment created: " + ref.getKey());
        return ref.getKey();
    }


    /**
     * Write/update multiple values under a path
     */
    public static void writeMap(String path, Map<String, Object> data) {
        db.getReference(path).updateChildrenAsync(data);
    }

    public static void updateField(String path, Object value) {
        db.getReference(path).setValueAsync(value);
    }

    public static void delete(String path) {
        db.getReference(path).removeValueAsync();
    }
    


}
