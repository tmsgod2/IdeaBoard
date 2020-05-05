package com.capstonewansook.ideaboard.recyclerview;

import java.util.Date;

public class CommentRecyclerViewData implements Comparable<CommentRecyclerViewData> {
    private String uid;
    private String name;
    private Date date;
    private String content;

    public CommentRecyclerViewData(String uid, String name, Date date, String content) {
        this.uid = uid;
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(CommentRecyclerViewData c) {
        return c.date.compareTo(this.date);
    }
}
