package com.petsinmind.messages;

// Falak
public class TicketMessage extends Message  {
    public TicketMessage(String title, String text, String timeSent, int senderId, int receiverId) {
        super(title, text, timeSent, senderId, receiverId);
    }
}
