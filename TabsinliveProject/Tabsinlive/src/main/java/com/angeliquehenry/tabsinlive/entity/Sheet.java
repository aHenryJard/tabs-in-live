package com.angeliquehenry.tabsinlive.entity;


import android.renderscript.Element;
/**
 * One music sheet: one image that represent the tab, and the page number.
 */
public class Sheet {

    public int id;
    public int pageNumber;
    public byte[] image;
    public String imagePath;
    public Tab tab;

    public String toString(){
        return "Sheet{id:"+id+",pageNumber:"+pageNumber+",path:"+imagePath+"}";
    }

}
