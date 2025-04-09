package com.petsinmind.messages;

// Falak
public class TicketMessage extends Message  {
    public TicketMessage(String detail, String timeSent, int senderId, int receiverId, int referenceId) {
        super(detail, timeSent, senderId, receiverId, receiverId);
    }
}
