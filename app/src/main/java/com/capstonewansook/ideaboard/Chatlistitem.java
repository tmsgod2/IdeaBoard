package com.capstonewansook.ideaboard;

import android.graphics.drawable.Drawable;

public class Chatlistitem {
    private Drawable iconDrawable ;
    private Drawable iconDrawable2 ;
    private String titleStr ;
    private int check = 0;


    public Drawable getIconDrawable() {
        return iconDrawable;
    }
    public Drawable getIconDrawable2() {
        return iconDrawable2;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }
    public void setIconDrawable2(Drawable iconDrawable2) {
        this.iconDrawable2 = iconDrawable2;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public int isCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
