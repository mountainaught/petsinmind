package com.petsinmind.messages;

// Falak
public abstract class Message {
    private String detail;
    private String timeSent;
    private int senderId;
    private int receiverId;
    private int referenceId;


    public Message(String detail, String timeSent, int senderId, int receiverId, int referenceId) {
        this.detail = detail;
        this.timeSent = timeSent;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.referenceId = referenceId;
    }
    
    // getters
    public String getDetail() {
        return detail;
    }
    
    public String getTimeSent() {
        return timeSent;
    }
    
    public int getReceiverId() {
        return receiverId;
    }
    
    public int getSenderId() {
        return senderId;
    }

    public int getReferenceId() {
        return referenceId;
    }
    
    // setters
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }
    
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

}
