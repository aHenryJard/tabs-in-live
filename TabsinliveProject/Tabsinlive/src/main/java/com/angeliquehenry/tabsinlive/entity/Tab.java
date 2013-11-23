package com.angeliquehenry.tabsinlive.entity;

import java.util.List;

/**
 * Represent one tab with several sheets.
 */
public class Tab {

    private String title;
    private String singer;
    private List<Integer> sheets;

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public List<Integer> getSheets() {
        return sheets;
    }

    public void setSheets(List<Integer> sheets) {
        this.sheets = sheets;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString(){
        return title+"-"+singer;
    }
}
