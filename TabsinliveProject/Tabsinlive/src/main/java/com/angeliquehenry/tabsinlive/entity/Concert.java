package com.angeliquehenry.tabsinlive.entity;

import java.util.List;

/**
 * One concert contains a list of tabs.
 */
public class Concert {

    private String label;

    private List<Tab> tabs;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }
}
