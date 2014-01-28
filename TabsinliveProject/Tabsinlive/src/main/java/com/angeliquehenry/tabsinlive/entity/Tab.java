package com.angeliquehenry.tabsinlive.entity;


import java.util.Collection;
import java.util.List;

/**
 * Represent one tab with several sheets.
 */
//@DatabaseTable(tableName = "tab")
public class Tab {

    //@DatabaseField(generatedId = true)
    public int id;

    //@DatabaseField
    public String title;

    //@DatabaseField
    public String singer;

    public String toString(){
        return "Tab{id:"+id+",title:"+title+",singer:"+singer+"}";
    }
}
