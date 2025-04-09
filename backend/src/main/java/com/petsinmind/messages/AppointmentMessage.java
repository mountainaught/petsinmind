package com.petsinmind.messages;

// Falak
public class AppointmentMessage extends Message  {
    public AppointmentMessage(String detail, String timeSent, int senderId, int receiverId, int referenceId) {
        super(detail, timeSent, senderId, receiverId, referenceId);
    }
}
