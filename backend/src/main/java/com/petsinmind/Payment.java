package com.petsinmind;

import java.util.Calendar;
import java.util.UUID;

// Dimitris
public class Payment {
    private UUID PaymentID;
    private String PaymentMethod;
    private Calendar PaymentDate;
    private float PaymentAmount;
    private String PaymentCurrency;
    private UUID SenderID;
    private UUID ReceiverID;

    // Initial constructor
    public Payment() {
        this.PaymentID = null;
        this.PaymentMethod = null;
        this.PaymentDate = null;
        this.PaymentAmount = 0.0f;
        this.PaymentCurrency = null;
        this.SenderID = null;
        this.ReceiverID = null;
    }

    // Constructor with parameters
    public Payment(UUID PaymentID, String PaymentMethod, Calendar PaymentDate, float PaymentAmount,
            String PaymentCurrency, UUID SenderID,
            UUID ReceiverID) {
        this.PaymentID = PaymentID;
        this.PaymentMethod = PaymentMethod;
        this.PaymentDate = PaymentDate;
        this.PaymentAmount = PaymentAmount;
        this.SenderID = SenderID;
        this.ReceiverID = ReceiverID;
    }

    // Getters and Setters
    public UUID getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(UUID PaymentID) {
        this.PaymentID = PaymentID;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public Calendar getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(Calendar PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public float getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(float PaymentAmount) {
        this.PaymentAmount = PaymentAmount;
    }

    public String getPaymentCurrency() {
        return PaymentCurrency;
    }

    public void setPaymentCurrency(String PaymentCurrency) {
        this.PaymentCurrency = PaymentCurrency;
    }

    public UUID getSenderID() {
        return SenderID;
    }

    public void setSenderID(UUID SenderID) {
        this.SenderID = SenderID;
    }

    public UUID getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(UUID ReceiverID) {
        this.ReceiverID = ReceiverID;
    }
    // End of getters and setters

    public String toString() {
        return "Payment{" +
                "PaymentID='" + PaymentID + '\'' +
                ", PaymentMethod='" + PaymentMethod + '\'' +
                ", PaymentDate='" + PaymentDate + '\'' +
                ", PaymentAmount='" + PaymentAmount + '\'' +
                ", PaymentCurrency='" + PaymentCurrency + '\'' +
                ", SenderID='" + SenderID + '\'' +
                ", ReceiverID='" + ReceiverID + '\'' +
                '}';
    }
}
