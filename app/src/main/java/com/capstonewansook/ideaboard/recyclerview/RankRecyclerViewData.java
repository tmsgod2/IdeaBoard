package com.capstonewansook.ideaboard.recyclerview;

public class RankRecyclerViewData implements Comparable<RankRecyclerViewData>{
    private String name;
    private String office;
    private int star;
    private int grade=99999;
    private String uid;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;

    }

    public RankRecyclerViewData(String name, String office, int star, String Uid) {
        this.name = name;
        this.office = office;
        this.star = star;
        this.uid = Uid;
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
        this.star =star;
    }

    public void setStar(int star,String a) {
        this.star =this.star+star;
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
