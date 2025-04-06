package com.petsinmind.messages;

// Falak
public class JobOfferMessage extends Message  {
    public JobOfferMessage(String title, String text, String timeSent, int senderId, int receiverId) {
        super(title, text, timeSent, senderId, receiverId);
    }
}
