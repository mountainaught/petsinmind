package com.petsinmind.firebase;

import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

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
     * Write pet owner to /users/petOwners/{uid}
     */
    public static void writePetOwner(String uid, Map<String, Object> data) {
        db.getReference("users/petOwners").child(uid).setValueAsync(data);
        System.out.println("Pet owner written: " + uid);
    }

    /**
     * Write caretaker to /users/caretakers/{uid}
     */
    public static void writeCaretaker(String uid, Map<String, Object> data) {
        // TODO: Implement caretaker structure
        db.getReference("users/caretakers").child(uid).setValueAsync(data);
    }

    /**
     * Write pet to /pets/{petId}
     */
    public static void writePet(String petId, Map<String, Object> data) {
        // TODO: Implement pet object structure
        db.getReference("pets").child(petId).setValueAsync(data);
    }

    /**
     * Push a new job offer to /jobOffers
     */
    public static String pushJobOffer(Map<String, Object> data) {
        // TODO: Implement job offer structure
        DatabaseReference ref = db.getReference("jobOffers").push();
        ref.setValueAsync(data);
        return ref.getKey();
    }

    /**
     * Push a new appointment to /appointments
     */
    public static String pushAppointment(Map<String, Object> data) {
        // TODO: Implement appointment structure
        DatabaseReference ref = db.getReference("appointments").push();
        ref.setValueAsync(data);
        return ref.getKey();
    }

    /**
     * Write/update multiple values under a path
     */
    public static void writeMap(String path, Map<String, Object> data) {
        db.getReference(path).updateChildrenAsync(data);
    }
}
