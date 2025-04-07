package com.petsinmind.firebase;

import com.google.firebase.database.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FirebaseReader {

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();

    /**
     * Generic method to read data from a given Firebase path.
     * Returns a CompletableFuture that completes when data is read.
     */
    public static CompletableFuture<DataSnapshot> read(String path) {
        CompletableFuture<DataSnapshot> future = new CompletableFuture<>();
        db.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                future.complete(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(new RuntimeException("Read failed: " + error.getMessage()));
            }
        });
        return future;
    }

    /**
     * Get a Pet Owner's data by UID
     */
    public static CompletableFuture<Map<String, Object>> getPetOwner(String uid) {
        return read("users/petOwners/" + uid).thenApply(snapshot -> (Map<String, Object>) snapshot.getValue());
    }

    /**
     * Get a Caretaker's data by UID
     */
    public static CompletableFuture<Map<String, Object>> getCaretaker(String uid) {
        return read("users/caretakers/" + uid).thenApply(snapshot -> (Map<String, Object>) snapshot.getValue());
    }

    /**
     * Get a Job Offer by job ID
     */
    public static CompletableFuture<Map<String, Object>> getJobOffer(String jobId) {
        return read("jobOffers/" + jobId).thenApply(snapshot -> (Map<String, Object>) snapshot.getValue());
    }

    /**
     * Get an Appointment by appointment ID
     */
    public static CompletableFuture<Map<String, Object>> getAppointment(String appointmentId) {
        return read("appointments/" + appointmentId).thenApply(snapshot -> (Map<String, Object>) snapshot.getValue());
    }

    public static CompletableFuture<String> getStoredApiKey() {
        return read("apikey").thenApply(snapshot -> snapshot.getValue(String.class));
    }
    

}
