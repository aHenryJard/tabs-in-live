package com.angeliquehenry.tabsinlive.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * One concert contains a list of tabs.
 */
public class Concert {

    public String label;
    public ArrayList<Tab> tabs=new ArrayList<Tab>();

    public String toString(){
        return "Concert{label:"+label+",tabs:"+tabs+"}";
    }
}
