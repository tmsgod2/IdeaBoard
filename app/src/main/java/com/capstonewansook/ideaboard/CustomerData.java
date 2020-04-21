package com.capstonewansook.ideaboard;

import java.io.Serializable;

public class CustomerData implements Serializable {
    private String name;
    private String locate;
    private String office;
    private String job;
    private String state;
    private int stars;
    private String profileURI;
    private boolean isFirst;

    //고객 정보 저장을 위한 대이터
    public CustomerData(String name, String locate, String office, String job, String state, int stars, String profileURI) {
        this.name = name;
        this.locate = locate;
        this.office = office;
        this.job = job;
        this.state = state;
        this.stars = stars;
        this.profileURI = profileURI;
        isFirst = false;
    }


    public CustomerData() {
        isFirst = true;
    }

    public boolean getisFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getProfileURI() {
        return profileURI;
    }

    public void setProfileURI(String profileURI) {
        this.profileURI = profileURI;
    }
}
