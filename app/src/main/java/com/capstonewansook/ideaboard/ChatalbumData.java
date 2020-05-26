package com.capstonewansook.ideaboard;

import android.widget.ImageView;

public class ChatalbumData implements Comparable<ChatalbumData> {
    private ImageView imageView;
    private int a;

    public ChatalbumData(ImageView imageView, int a) {
        this.imageView = imageView;
        this.a = a;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    public int getA() {
        return a;
    }
    public void setA(int a) {
        this.a = a;
    }

    @Override
    public int compareTo(ChatalbumData o) {
        return o.a;
    }
}
