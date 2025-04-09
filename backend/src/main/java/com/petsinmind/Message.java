package com.petsinmind;

import java.util.Calendar;
import java.util.UUID;

// Falak
public class Message {
    private String details;
    private UUID senderId;
    private UUID receiverId;
    private UUID referenceID;
    private Calendar date;
    private Registry registry;

    public Message(String details, UUID senderId, UUID receiverId, UUID referenceID, Calendar date) {
        this.details = details;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.referenceID = referenceID;
        this.date = date;
        try {
            this.registry = Registry.getInstance(); // Singleton pattern
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSenderIDString() {
        return senderId.toString();
    }

    public String getReceiverIDString() {
        return receiverId.toString();
    }

    public String getReferenceIDString() {
        return referenceID.toString();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public UUID getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(UUID referenceID) {
        this.referenceID = referenceID;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

}
