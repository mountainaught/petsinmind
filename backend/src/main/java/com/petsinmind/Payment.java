package com.petsinmind;

import java.util.Calendar;

// Dimitris
public class Payment {
    private String PaymentID;
    private String PaymentMethod;
    private Calendar PaymentDate;
    private float PaymentAmount;
    private String PaymentCurrency;
    private int SenderID;
    private int ReceiverID;

    // Initial constructor
    public Payment() {
        this.PaymentID = null;
        this.PaymentMethod = null;
        this.PaymentDate = null;
        this.PaymentAmount = 0.0f;
        this.PaymentCurrency = null;
        this.SenderID = 0;
        this.ReceiverID = 0;
    }

    // Constructor with parameters
    public Payment(String PaymentID, String PaymentMethod, Calendar PaymentDate, float PaymentAmount,
            String PaymentCurrency, int SenderID,
            int ReceiverID) {
        this.PaymentID = PaymentID;
        this.PaymentMethod = PaymentMethod;
        this.PaymentDate = PaymentDate;
        this.PaymentAmount = PaymentAmount;
        this.SenderID = SenderID;
        this.ReceiverID = ReceiverID;
    }

    // Getters and Setters
    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String PaymentID) {
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

    public int getSenderID() {
        return SenderID;
    }

    public void setSenderID(int SenderID) {
        this.SenderID = SenderID;
    }

    public int getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(int ReceiverID) {
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
