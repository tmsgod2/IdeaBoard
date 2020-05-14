package com.capstonewansook.ideaboard.recyclerview;

import java.util.Date;

public class ChatingRecyclerViewData {
    String chatId;
    String name;
    String uid;
    String message;
    Date date;

    public ChatingRecyclerViewData(String chatId, String name, String uid, String message, Date date) {
        this.chatId = chatId;
        this.name = name;
        this.uid = uid;
        this.message = message;
        this.date = date;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}
