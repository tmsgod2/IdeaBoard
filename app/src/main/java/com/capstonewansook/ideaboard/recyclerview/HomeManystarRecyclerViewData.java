package com.capstonewansook.ideaboard.recyclerview;

public class HomeManystarRecyclerViewData implements Comparable<HomeManystarRecyclerViewData> {
    private String title;
    private int stars;
    private String boardID;

    public HomeManystarRecyclerViewData(String boardID,String title, int stars) {
        this.boardID = boardID;
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

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    @Override
    public int compareTo(HomeManystarRecyclerViewData homeManystarRecyclerViewData) {
        return homeManystarRecyclerViewData.stars - this.stars;
    }
}
