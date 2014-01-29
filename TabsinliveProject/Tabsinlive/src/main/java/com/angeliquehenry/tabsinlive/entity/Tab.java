package com.angeliquehenry.tabsinlive.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represent one tab with several sheets.
 */
public class Tab {

    public int id;
    public String title;
    public String singer;
    public ArrayList<Sheet> sheets=new ArrayList<Sheet>();

    public String toString(){
        return title;
    }
}
