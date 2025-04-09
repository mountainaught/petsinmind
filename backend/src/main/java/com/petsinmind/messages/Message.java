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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

}
