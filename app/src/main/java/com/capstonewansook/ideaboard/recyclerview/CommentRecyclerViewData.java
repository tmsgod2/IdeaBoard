package com.capstonewansook.ideaboard.recyclerview;

import java.util.Date;

public class CommentRecyclerViewData implements Comparable<CommentRecyclerViewData> {
    private String boardId;
    private String commentId;
    private String uid;
    private String name;
    private Date date;
    private String content;

    public CommentRecyclerViewData(String boardId, String commentId, String uid, String name, Date date, String content) {
        this.boardId = boardId;
        this.commentId = commentId;
        this.uid = uid;
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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
        return this.date.compareTo(c.date);
    }
}
