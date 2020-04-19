package com.capstonewansook.ideaboard.recyclerview;

import java.util.Date;

public class ChatRecyclerViewData implements Comparable<ChatRecyclerViewData>{
    private String chatroomID;
    private int profileImage;
    private String name;
    private String message;
    private Date date;

    public ChatRecyclerViewData(String chatroomID,int profileImage, String name, String message, Date date) {
        this.chatroomID = chatroomID;
        this.profileImage = profileImage;
        this.name = name;
        this.message = message;
        this.date = date;

    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(String chatroomID) {
        this.chatroomID = chatroomID;
    }

    @Override
    public int compareTo(ChatRecyclerViewData rankRecyclerViewData) {
        return rankRecyclerViewData.date.compareTo(this.date);
    }
}
