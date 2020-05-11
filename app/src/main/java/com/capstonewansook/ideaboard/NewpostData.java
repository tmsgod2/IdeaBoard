package com.capstonewansook.ideaboard;

import java.io.Serializable;

public class NewpostData implements Serializable {
    private String uid;
    private String title;
    private String content;
    private Object date;
    private int stars;
    private int images;

    public NewpostData(String uid, String title, String content, Object date,int images) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.date = date;
        this.images = images;
    }

    public String getUid() {        return uid;    }
    public String getTitle() {        return title;    }
    public String getContent() {        return content;    }
    public Object getDate() {        return date;    }
    public int getStars() {        return stars;    }
    public void setUid(String uid) {        this.uid = uid;    }
    public void setTitle(String title) {        this.title = title;    }
    public void setContent(String content) {        this.content = content;    }
    public void setDate(String date) {        this.date = date;    }
    public void setStars(int stars) {        this.stars = stars;    }
    public int getImages() {
        return images;
    }
    public void setImages(int images) {
        this.images = images;
    }
}
