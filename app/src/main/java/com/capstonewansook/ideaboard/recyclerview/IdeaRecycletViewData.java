package com.capstonewansook.ideaboard.recyclerview;

import java.util.Date;

public class IdeaRecycletViewData {

    private  String iv_profill;
    private  String tv_story;
    private Date tv_day;
//    private int iv_recomand;
//    private int iv_write;
    private String tv_recomand;
    private String tv_write;
    private String idpost;

    public String getIdpost() {
        return idpost;
    }

    public void setIdpost(String idpost) {
        this.idpost = idpost;
    }

    public IdeaRecycletViewData(String iv_profill, String tv_story, Date tv_day, String tv_recomand, String tv_write,String idpost) {
        this.iv_profill = iv_profill;
        this.tv_story = tv_story;
        this.tv_day = tv_day;
//        this.iv_recomand = iv_recomand;
//        this.iv_write = iv_write;
        this.tv_recomand = tv_recomand;
        this.tv_write = tv_write;
        this.idpost = idpost;
    }

    public String getIv_profill() {
        return iv_profill;
    }

    public void setIv_profill(String iv_profill) {
        this.iv_profill = iv_profill;
    }

    public String getTv_story() {
        return tv_story;
    }

    public void setTv_story(String tv_autor) {
        this.tv_story = tv_autor;
    }

    public Date getTv_day() {
        return tv_day;
    }

    public void setTv_day(Date tv_day) {
        this.tv_day = tv_day;
    }

//    public int getIv_recomand() {
//        return iv_recomand;
//    }
//
//    public void setIv_recomand(int iv_recomand) {
//        this.iv_recomand = iv_recomand;
//    }
//
//    public int getIv_write() {
//        return iv_write;
//    }
//
//    public void setIv_write(int iv_write) {
//        this.iv_write = iv_write;
//    }

    public String getTv_recomand() {
        return tv_recomand;
    }

    public void setTv_recomand(String tv_recomand) {
        this.tv_recomand = tv_recomand;
    }

    public String getTv_write() {
        return tv_write;
    }

    public void setTv_write(String tv_write) {
        this.tv_write = tv_write;
    }
}
