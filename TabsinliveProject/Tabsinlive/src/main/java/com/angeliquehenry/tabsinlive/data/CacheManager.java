package com.angeliquehenry.tabsinlive.data;

import com.angeliquehenry.tabsinlive.entity.Concert;

import java.util.ArrayList;

/**
 * Memory cache for data (concert, tabs, etc..)
 */
public class CacheManager {
    private static volatile CacheManager instance = null;

    private ArrayList<Concert> concerts;

    public ArrayList<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(ArrayList<Concert> concerts) {
        this.concerts = concerts;
    }

    private CacheManager() {
        super();
        concerts = new ArrayList<Concert>();
    }
    public final static CacheManager getInstance() {
        if (CacheManager.instance == null) {
            synchronized(CacheManager.class) {
                if (CacheManager.instance == null) {
                    CacheManager.instance = new CacheManager();
                }
            }
        }
        return CacheManager.instance;
    }

}
