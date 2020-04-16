package com.capstonewansook.ideaboard.recyclerview;

public class IdeaRecycletViewData {

    private  int iv_profill;
    private  String tv_autor;
    private  String tv_day;
    private int iv_recomand;
    private int iv_write;
    private String tv_recomand;
    private String tv_write;

    public IdeaRecycletViewData(int iv_profill, String tv_autor, String tv_day, int iv_recomand, int iv_write, String tv_recomand, String tv_write) {
        this.iv_profill = iv_profill;
        this.tv_autor = tv_autor;
        this.tv_day = tv_day;
        this.iv_recomand = iv_recomand;
        this.iv_write = iv_write;
        this.tv_recomand = tv_recomand;
        this.tv_write = tv_write;
    }

    public int getIv_profill() {
        return iv_profill;
    }

    public void setIv_profill(int iv_profill) {
        this.iv_profill = iv_profill;
    }

    public String getTv_autor() {
        return tv_autor;
    }

    public void setTv_autor(String tv_autor) {
        this.tv_autor = tv_autor;
    }

    public String getTv_day() {
        return tv_day;
    }

    public void setTv_day(String tv_day) {
        this.tv_day = tv_day;
    }

    public int getIv_recomand() {
        return iv_recomand;
    }

    public void setIv_recomand(int iv_recomand) {
        this.iv_recomand = iv_recomand;
    }

    public int getIv_write() {
        return iv_write;
    }

    public void setIv_write(int iv_write) {
        this.iv_write = iv_write;
    }

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
