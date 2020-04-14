package com.capstonewansook.ideaboard.recyclerview;

import java.util.Date;

public class HomeNewideaRecyclerViewData implements Comparable<HomeNewideaRecyclerViewData> {
    private String title;
    private Date date;

    public HomeNewideaRecyclerViewData(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(HomeNewideaRecyclerViewData homeNewideaRecyclerViewData) {
        return homeNewideaRecyclerViewData.date.compareTo(this.date);
    }
}
