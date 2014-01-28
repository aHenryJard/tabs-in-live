package com.angeliquehenry.tabsinlive.entity;


/**
 * One music sheet: one image that represent the tab, and the page number.
 */
//@DatabaseTable(tableName = "sheet")
public class Sheet {

    //@DatabaseField(generatedId = true)
    public int id;

    //@DatabaseField
    public int pageNumber;

    //@DatabaseField(dataType = DataType.BYTE_ARRAY)
    public byte[] image;

    //@DatabaseField(canBeNull = false, foreign = true)
    public Tab tab;

    public String toString(){
        return "Sheet{id:"+id+",pageNumber:"+pageNumber+"}";
    }

}
