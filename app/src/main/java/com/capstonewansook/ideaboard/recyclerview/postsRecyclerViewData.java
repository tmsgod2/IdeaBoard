package com.capstonewansook.ideaboard.recyclerview;

public class postsRecyclerViewData{

    private String boardId;
    private int image2;
    private String name;
    private String content;
    private String yymmdd;
    private int star;
    private String uid;

    public postsRecyclerViewData(String boardId, int image2, String name, String content, String yymmdd, int star, String uid) {
        this.boardId = boardId;
        this.image2 = image2;
        this.name = name;
        this.content = content;
        this.yymmdd = yymmdd;
        this.star = star;
        this.uid = uid;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getImage2() {
        return image2;
    }

    public int getStar() {
        return star;
    }

    public String getName() {
        return name;
    }

    public String getYymmdd() {
        return yymmdd;
    }

    public String getUid() { return uid; }

    public String getContent() { return content; }
}
