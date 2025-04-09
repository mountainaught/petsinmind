package com.petsinmind;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.UUID;

// Dimitris
public class Ticket {
    private UUID TicketID;
    private String Title;
    private String Details;
    private Calendar Date;
    private UUID CustomerID;
    private List<UUID> EmployeeIDs;
    private Boolean Status;
    private Registry registry;
    private ArrayList<Message> messageList;

    // Initial constructor
    public Ticket() {
        this.TicketID = null;
        this.Title = null;
        this.Details = null;
        this.Date = null;
        this.CustomerID = null;
        this.EmployeeIDs = null;
        this.Status = false;
        try {
            this.registry = Registry.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            this.registry = null; // Handle the exception by setting registry to null or another fallback
        }
    }

    public Ticket(String Title, String Details, UUID CustomerID) {
        this.TicketID = UUID.randomUUID();
        this.Title = Title;
        this.Details = Details;
        this.Date = Calendar.getInstance();
        this.CustomerID = CustomerID;
        this.EmployeeIDs = new ArrayList<>();
        this.Status = false;
    }

    public Ticket(UUID ticketID) {
        this.TicketID = ticketID;

    }

    // Constructor with parameters
    public Ticket(String Title, String Details, Calendar Date, UUID CustomerID,
            List<UUID> EmployeeIDs, Boolean Status) {
        this.TicketID = UUID.randomUUID();
        this.Title = Title;
        this.Details = Details;
        this.Date = Date;
        this.CustomerID = CustomerID;
        this.EmployeeIDs = EmployeeIDs;
        this.Status = Status;
    }

    // Getters and Setters
    public UUID getTicketID() {
        return TicketID;
    }

    public void setTicketID(UUID TicketID) {
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

    public UUID getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(UUID CustomerID) {
        this.CustomerID = CustomerID;
    }

    public List<UUID> getEmployeeIDs() {
        return EmployeeIDs;
    }

    public List<String> getEmployeeSIDs() {
        List<String> employeeSIDs = new ArrayList<>();
        for (UUID employeeID : EmployeeIDs) {
            employeeSIDs.add(employeeID.toString());
        }
        return employeeSIDs;
    }

    public void setEmployeeIDs(List<UUID> EmployeeIDs) {
        this.EmployeeIDs = EmployeeIDs;
    }

    public boolean addEmployee(UUID EmployID) {
        this.EmployeeIDs.add(EmployID);
        try {
            return registry.editTicket(this);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false or handle the error appropriately
        }
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean Status) {
        this.Status = Status;
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}
