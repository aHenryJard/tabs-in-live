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
    public List<Tab> getTablist(){
        ArrayList<Tab> tabs = new ArrayList<Tab>();
        tabs.add(getSuperbus());

        return tabs;
    }

    public Tab getSuperbus()
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
}
