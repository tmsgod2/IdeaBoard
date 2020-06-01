package com.capstonewansook.ideaboard;

public class RankingTiimeData {
    String puid;
    String uid;

    int year;
    int month;
    int day;
    int star;

    public RankingTiimeData(String puid,String uid, int year, int month, int day,int star) {
        this.puid = puid;
        this.uid = uid;
        this.year = year;
        this.month = month;
        this.day = day;
        this.star=star;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
