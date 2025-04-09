package com.petsinmind.messages;

import java.util.Calendar;
import java.util.UUID;

// Falak
public class AppointmentMessage extends Message  {
    public AppointmentMessage(String details, UUID senderId, UUID receiverId, UUID referenceID, Calendar date) {
        super(details, senderId, receiverId, referenceID, date);
    }
}
