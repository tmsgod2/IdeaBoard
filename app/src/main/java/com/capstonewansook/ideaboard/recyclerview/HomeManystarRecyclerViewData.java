package com.capstonewansook.ideaboard.recyclerview;

public class HomeManystarRecyclerViewData implements Comparable<HomeManystarRecyclerViewData> {
    private String title;
    private int stars;

    public HomeManystarRecyclerViewData(String title, int stars) {
        this.title = title;
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public int compareTo(HomeManystarRecyclerViewData homeManystarRecyclerViewData) {
        return homeManystarRecyclerViewData.stars - this.stars;
    }
}
