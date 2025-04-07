package com.petsinmind;

import com.petsinmind.messages.Message;
import com.petsinmind.messages.TicketMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

// Dimitris
public class Ticket {
    private int TicketID;
    private String Title;
    private String Details;
    private Calendar Date;
    private int CustomerID;
    private List<Integer> EmployeeIDs;
    private Boolean Status;

    private ArrayList<TicketMessage> messageList;

    // Initial constructor
    public Ticket() {
        this.TicketID = 0;
        this.Title = null;
        this.Details = null;
        this.Date = null;
        this.CustomerID = 0;
        this.EmployeeIDs = null;
        this.Status = false;
    }

    // Constructor with parameters
    public Ticket(int TicketID, String Title, String Details, Calendar Date, int CustomerID,
            List<Integer> EmployeeIDs, Boolean Status) {
        this.TicketID = TicketID;
        this.Title = Title;
        this.Details = Details;
        this.Date = Date;
        this.CustomerID = CustomerID;
        this.EmployeeIDs = EmployeeIDs;
        this.Status = Status;
    }

    // Getters and Setters
    public int getTicketID() {
        return TicketID;
    }

    public void setTicketID(int TicketID) {
        this.TicketID = TicketID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String Details) {
        this.Details = Details;
    }

    public Calendar getDate() {
        return Date;
    }

    public void setDate(Calendar Date) {
        this.Date = Date;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public List<Integer> getEmployeeIDs() {
        return EmployeeIDs;
    }

    public void setEmployeeIDs(List<Integer> EmployeeIDs) {
        this.EmployeeIDs = EmployeeIDs;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean Status) {
        this.Status = Status;
    }

    public void addMessage(TicketMessage message) {
        this.messageList.add(message);
    }

    public List<TicketMessage> getMessageList() {return messageList;}
}
