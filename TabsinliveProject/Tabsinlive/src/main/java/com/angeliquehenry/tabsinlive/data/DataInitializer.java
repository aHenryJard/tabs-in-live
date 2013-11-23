package com.angeliquehenry.tabsinlive.data;


import com.angeliquehenry.tabsinlive.R;
import com.angeliquehenry.tabsinlive.entity.Tab;

import java.util.ArrayList;
import java.util.List;

/**
 * Init data for application.
 *
 */
public class DataInitializer {

    /**
     * Tab list on the application
     * @return
     */
    public List<Tab> getTabList(){
        ArrayList<Tab> tabs = new ArrayList<Tab>();
        tabs.add(getSuperbus());
        tabs.add(getNewYork());
        tabs.add(getSoWhat());
        tabs.add(getAboutAGirl());
        return tabs;
    }

    private Tab getSuperbus()
    {
        Tab superbus = new Tab();
        superbus.setTitle("Radio song");
        superbus.setSinger("Superbus");

        ArrayList<Integer> sheets = new ArrayList();
        sheets.add(R.drawable.superbus_radio_song_1);
        sheets.add(R.drawable.superbus_radio_song_2);
        sheets.add(R.drawable.superbus_radio_song_3);
        superbus.setSheets(sheets);

        return superbus;
    }

    private Tab getNewYork()
    {
        Tab tab = new Tab();
        tab.setTitle("New York avec toi");
        tab.setSinger("Telephone");

        ArrayList<Integer> sheets = new ArrayList();
        sheets.add(R.drawable.new_york_1);
        sheets.add(R.drawable.new_york_2);
        sheets.add(R.drawable.new_york_3);
        tab.setSheets(sheets);

        return tab;
    }

    private Tab getAboutAGirl()
    {
        Tab tab = new Tab();
        tab.setTitle("About a girl");
        tab.setSinger("Nirvana");

        ArrayList<Integer> sheets = new ArrayList();
        sheets.add(R.drawable.nirvana_about_a_girl);
        tab.setSheets(sheets);

        return tab;
    }

    private Tab getSoWhat()
    {
        Tab tab = new Tab();
        tab.setTitle("So what");
        tab.setSinger("Pink");

        ArrayList<Integer> sheets = new ArrayList();
        sheets.add(R.drawable.pink_so_what_1);
        sheets.add(R.drawable.pink_so_what_2);
        tab.setSheets(sheets);

        return tab;
    }
}
