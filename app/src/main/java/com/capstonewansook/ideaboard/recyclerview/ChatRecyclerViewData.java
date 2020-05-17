package com.capstonewansook.ideaboard.recyclerview;

import java.util.Date;

public class ChatRecyclerViewData implements Comparable<ChatRecyclerViewData>{
    private String chatroomID;
    private String uid2;
    private String name;
    private String message;
    private Date date;
    private String profile;

    public ChatRecyclerViewData(String chatroomID,String uid2, String name, String message, Date date) {
        this.chatroomID = chatroomID;
        this.uid2 = uid2;
        this.name = name;
        this.message = message;
        this.date = date;

    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
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
