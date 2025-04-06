package com.petsinmind.messages;

// Falak
public class Message {
    private String title;
    private String text;
    private String timeSent;
    private int senderId;
    private int receiverId;


    public Message(String title, String text, String timeSent, int senderId, int receiverId) {
        this.title = title;
        this.text = text;
        this.timeSent = timeSent;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
