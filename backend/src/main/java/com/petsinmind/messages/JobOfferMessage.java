package com.petsinmind.messages;

// Falak
public class JobOfferMessage extends Message  {
    public JobOfferMessage(String detail, String timeSent, int senderId, int receiverId, int referenceId) {
        super(detail, timeSent, senderId, receiverId, referenceId);
    }
}
