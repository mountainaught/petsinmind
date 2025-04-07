package com.petsinmind.messages;

// Falak
public class AppointmentMessage extends Message  {
    public AppointmentMessage(String title, String text, String timeSent, int senderId, int receiverId) {
        super(title, text, timeSent, senderId, receiverId);
    }
}
