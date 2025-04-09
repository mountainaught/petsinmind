package com.petsinmind.messages;

import java.util.Calendar;
import java.util.UUID;

// Falak
public class TicketMessage extends Message  {
    public TicketMessage(String details, UUID senderId, UUID receiverId, UUID referenceID, Calendar date) {
        super(details, senderId, receiverId, referenceID, date);
    }
}
