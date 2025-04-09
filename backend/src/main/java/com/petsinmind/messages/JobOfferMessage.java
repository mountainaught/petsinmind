package com.petsinmind.messages;

import java.util.Calendar;
import java.util.UUID;

// Falak
public class JobOfferMessage extends Message  {
    public JobOfferMessage(String details, UUID senderId, UUID receiverId, UUID referenceID, Calendar date) {
        super(details, senderId, receiverId, referenceID, date);
    }
}
