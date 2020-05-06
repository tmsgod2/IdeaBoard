package com.capstonewansook.ideaboard;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class IdeaMainData implements Serializable {
    String boardId;
    String uid;
    String title;
    String content;
    ArrayList<Bitmap> images;
    int stars;
    int imgLength;

    public IdeaMainData(String boardId, String uid, String title, String content, ArrayList<Bitmap> images, int stars) {
        this.boardId = boardId;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.images = images;
        this.stars = stars;
    }

    public IdeaMainData(String boardId, String uid, String title, String content, int stars, int imgLength) {
        this.boardId = boardId;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.stars = stars;
        this.imgLength = imgLength;
    }

    public int getImgLength() {
        return imgLength;
    }

    public void setImgLength(int imgLength) {
        this.imgLength = imgLength;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
