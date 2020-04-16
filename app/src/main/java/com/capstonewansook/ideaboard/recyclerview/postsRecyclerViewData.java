package com.capstonewansook.ideaboard.recyclerview;

public class postsRecyclerViewData{
    private int image;
    private int image2;
    private String name;
    private String yymmdd;
    private int star;

    public postsRecyclerViewData(int image, int image2, String name, String yymmdd, int star) {
        this.image = image;
        this.image2 = image2;
        this.name = name;
        this.yymmdd = yymmdd;
        this.star = star;
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

    public int getImage() {
        return image;
    }
}
