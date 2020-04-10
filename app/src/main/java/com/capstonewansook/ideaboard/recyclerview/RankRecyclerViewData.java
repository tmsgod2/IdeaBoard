package com.capstonewansook.ideaboard.recyclerview;

public class RankRecyclerViewData implements Comparable<RankRecyclerViewData>{
    private int profileImage;
    private String name;
    private String office;
    private int star;
    private int grade=99999;

    public RankRecyclerViewData(int profileImage, String name, String office, int star) {
        this.profileImage = profileImage;
        this.name = name;
        this.office = office;
        this.star = star;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public int compareTo(RankRecyclerViewData rankRecyclerViewData) {
        return rankRecyclerViewData.star - this.star;
    }
}
