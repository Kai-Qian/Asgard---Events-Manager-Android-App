package com.brynhildr.asgard.entities;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by lqshan on 11/13/15.
 */
public class Event implements Serializable{

//    private int COLUMN_NAME_ENTRY_ID;

    private String COLUMN_NAME_EVENT_NAME;
    private String COLUMN_NAME_VENUE;
    private String COLUMN_NAME_DATEANDTIME;
//    private int COLUMN_NAME_MONTH;
//    private int COLUMN_NAME_DAY;

//    private int COLUMN_NAME_HOUR;
//
//    private int COLUMN_NAME_MINUTE;

    private String COLUMN_NAME_DESCRIPTION;

    private String COLUMN_NAME_DRESS_CODE;

    private String COLUMN_NAME_POSTER;
    private String COLUMN_NAME_TARGET;
    public String COLUMN_NAME_MAX_PEOPLE;

    public Event() {
    }

    public Event(String COLUMN_NAME_EVENT_NAME, String COLUMN_NAME_VENUE, String COLUMN_NAME_DATEANDTIME, String COLUMN_NAME_DESCRIPTION, String COLUMN_NAME_DRESS_CODE,
String COLUMN_NAME_POSTER, String COLUMN_NAME_TARGET, String COLUMN_NAME_MAX_PEOPLE) {
//        this.COLUMN_NAME_ENTRY_ID = COLUMN_NAME_ENTRY_ID;
        this.COLUMN_NAME_EVENT_NAME = COLUMN_NAME_EVENT_NAME;
        this.COLUMN_NAME_VENUE = COLUMN_NAME_VENUE;
        this.COLUMN_NAME_DATEANDTIME = COLUMN_NAME_DATEANDTIME;
        this.COLUMN_NAME_DESCRIPTION = COLUMN_NAME_DESCRIPTION;
        this.COLUMN_NAME_DRESS_CODE = COLUMN_NAME_DRESS_CODE;
        this.COLUMN_NAME_POSTER = COLUMN_NAME_POSTER;
        this.COLUMN_NAME_TARGET = COLUMN_NAME_TARGET;
        this.COLUMN_NAME_MAX_PEOPLE = COLUMN_NAME_MAX_PEOPLE;
    }

//    public int getCOLUMN_NAME_ENTRY_ID() {
//        return COLUMN_NAME_ENTRY_ID;
//    }
//
//    public void setCOLUMN_NAME_ENTRY_ID(int COLUMN_NAME_ENTRY_ID) {
//        this.COLUMN_NAME_ENTRY_ID = COLUMN_NAME_ENTRY_ID;
//    }

    public String getCOLUMN_NAME_EVENT_NAME() {
        return COLUMN_NAME_EVENT_NAME;
    }

    public void setCOLUMN_NAME_EVENT_NAME(String COLUMN_NAME_EVENT_NAME) {
        this.COLUMN_NAME_EVENT_NAME = COLUMN_NAME_EVENT_NAME;
    }

    public String getCOLUMN_NAME_VENUE() {
        return COLUMN_NAME_VENUE;
    }

    public void setCOLUMN_NAME_VENUE(String COLUMN_NAME_VENUE) {
        this.COLUMN_NAME_VENUE = COLUMN_NAME_VENUE;
    }

    public String getCOLUMN_NAME_DATEANDTIME() {
        return COLUMN_NAME_DATEANDTIME;
    }

    public void setCOLUMN_NAME_DATEANDTIME(String COLUMN_NAME_DATEANDTIME) {
        this.COLUMN_NAME_DATEANDTIME = COLUMN_NAME_DATEANDTIME;
    }

    public String getCOLUMN_NAME_DESCRIPTION() {
        return COLUMN_NAME_DESCRIPTION;
    }

    public void setCOLUMN_NAME_DESCRIPTION(String COLUMN_NAME_DESCRIPTION) {
        this.COLUMN_NAME_DESCRIPTION = COLUMN_NAME_DESCRIPTION;
    }

    public String getCOLUMN_NAME_DRESS_CODE() {
        return COLUMN_NAME_DRESS_CODE;
    }

    public void setCOLUMN_NAME_DRESS_CODE(String COLUMN_NAME_DRESS_CODE) {
        this.COLUMN_NAME_DRESS_CODE = COLUMN_NAME_DRESS_CODE;
    }

    public String getCOLUMN_NAME_POSTER() {
        return COLUMN_NAME_POSTER;
    }

    public void setCOLUMN_NAME_POSTER(String COLUMN_NAME_POSTER) {
        this.COLUMN_NAME_POSTER = COLUMN_NAME_POSTER;
    }

    public String getCOLUMN_NAME_TARGET() {
        return COLUMN_NAME_TARGET;
    }

    public void setCOLUMN_NAME_TARGET(String COLUMN_NAME_TARGET) {
        this.COLUMN_NAME_TARGET = COLUMN_NAME_TARGET;
    }

    public String getCOLUMN_NAME_MAX_PEOPLE() {
        return COLUMN_NAME_MAX_PEOPLE;
    }

    public void setCOLUMN_NAME_MAX_PEOPLE(String COLUMN_NAME_MAX_PEOPLE) {
        this.COLUMN_NAME_MAX_PEOPLE = COLUMN_NAME_MAX_PEOPLE;
    }

    public Event(String name, String picName)
    {
        this.COLUMN_NAME_EVENT_NAME = name;
        this.COLUMN_NAME_POSTER = picName;
    }

    public int getImageResourceId( Context context )
    {
        try
        {
//            System.out.println(R.drawable.p1);
            int i = context.getResources().getIdentifier(this.COLUMN_NAME_POSTER, "drawable", context.getPackageName());
            System.out.println("this.COLUMN_NAME_POSTER---->" + this.COLUMN_NAME_POSTER);
            return i;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }



}
