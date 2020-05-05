package com.capstonewansook.ideaboard.recyclerview;

public class postsRecyclerViewData{

    private int image2;
    private String name;
    private String content;
    private String yymmdd;
    private int star;
    private String uid;

    public postsRecyclerViewData(int image2, String name, String yymmdd, int star,String uid, String content) {

        this.image2 = image2;
        this.name = name;
        this.yymmdd = yymmdd;
        this.star = star;
        this.uid = uid;
        this.content = content;
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
