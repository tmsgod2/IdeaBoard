package com.capstonewansook.ideaboard.recyclerview;

import android.graphics.Bitmap;

import java.util.Date;

public class ChatingRecyclerViewData implements Comparable<ChatingRecyclerViewData> {
    String chatId;
    String name;
    String uid;
    String message;
    Date date;
    Bitmap prifleImage;
    int type;

    public Bitmap getPrifleImage() {
        return prifleImage;
    }

    public void setPrifleImage(Bitmap prifleImage) {
        this.prifleImage = prifleImage;
    }

    public ChatingRecyclerViewData(String chatId, String name, String uid, String message, Date date, int type) {
        this.chatId = chatId;
        this.name = name;
        this.uid = uid;
        this.message = message;
        this.date = date;
        this.type = type;
    }

    public ChatingRecyclerViewData(String chatId, String name, String uid, String message, Date date) {
        this.chatId = chatId;
        this.name = name;
        this.uid = uid;
        this.message = message;
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public int compareTo(ChatingRecyclerViewData chatingRecyclerViewData) {
        return 0;
    }
}
