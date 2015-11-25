package com.brynhildr.asgard.entities;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by lqshan on 11/13/15.
 */
public class Event implements Serializable {

    //    private int COLUMN_NAME_ENTRY_ID;
    private String COLUMN_NAME_EVENT_NAME;
    private String COLUMN_NAME_VENUE;
    private String COLUMN_NAME_DATEANDTIME; // long type presented in string (timestamp)

    private String COLUMN_NAME_DESCRIPTION;

    private String COLUMN_NAME_DRESS_CODE;

    private String COLUMN_NAME_POSTER;
    private String COLUMN_NAME_TARGET;
    private String COLUMN_NAME_MAX_PEOPLE;
    private String COLUMN_NAME_LAUNCHER_ID; // This is Username
    private String COLUMN_NAME_TIMESTAMP;

    public Event() {

    }

//    public Event(String COLUMN_NAME_EVENT_NAME, String COLUMN_NAME_VENUE, String COLUMN_NAME_DATEANDTIME,
//                 String COLUMN_NAME_DESCRIPTION, String COLUMN_NAME_DRESS_CODE,
//                 String COLUMN_NAME_POSTER, String COLUMN_NAME_TARGET, String COLUMN_NAME_MAX_PEOPLE,
//                 String COLUMN_NAME_LAUNCHER_ID, String COLUMN_NAME_TIMESTAMP) {
////        this.COLUMN_NAME_ENTRY_ID = COLUMN_NAME_ENTRY_ID;
//        this.COLUMN_NAME_EVENT_NAME = COLUMN_NAME_EVENT_NAME;
//        this.COLUMN_NAME_VENUE = COLUMN_NAME_VENUE;
//        this.COLUMN_NAME_DATEANDTIME = COLUMN_NAME_DATEANDTIME;
//        this.COLUMN_NAME_DESCRIPTION = COLUMN_NAME_DESCRIPTION;
//        this.COLUMN_NAME_DRESS_CODE = COLUMN_NAME_DRESS_CODE;
//        this.COLUMN_NAME_POSTER = COLUMN_NAME_POSTER;
//        this.COLUMN_NAME_TARGET = COLUMN_NAME_TARGET;
//        this.COLUMN_NAME_MAX_PEOPLE = COLUMN_NAME_MAX_PEOPLE;
//        this.COLUMN_NAME_LAUNCHER_ID = COLUMN_NAME_LAUNCHER_ID;
//        this.COLUMN_NAME_TIMESTAMP = COLUMN_NAME_TIMESTAMP;
//    }

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

    public Event setCOLUMN_NAME_EVENT_NAME(String COLUMN_NAME_EVENT_NAME) {
        this.COLUMN_NAME_EVENT_NAME = COLUMN_NAME_EVENT_NAME;
        return this;
    }

    public String getCOLUMN_NAME_VENUE() {
        return COLUMN_NAME_VENUE;
    }

    public Event setCOLUMN_NAME_VENUE(String COLUMN_NAME_VENUE) {
        this.COLUMN_NAME_VENUE = COLUMN_NAME_VENUE;
        return this;
    }

    public String getCOLUMN_NAME_DATEANDTIME() {
        return COLUMN_NAME_DATEANDTIME;
    }

    public Event setCOLUMN_NAME_DATEANDTIME(String COLUMN_NAME_DATEANDTIME) {
        this.COLUMN_NAME_DATEANDTIME = COLUMN_NAME_DATEANDTIME;
        return this;
    }

    public String getCOLUMN_NAME_DESCRIPTION() {
        return COLUMN_NAME_DESCRIPTION;
    }

    public Event setCOLUMN_NAME_DESCRIPTION(String COLUMN_NAME_DESCRIPTION) {
        this.COLUMN_NAME_DESCRIPTION = COLUMN_NAME_DESCRIPTION;
        return this;
    }

    public String getCOLUMN_NAME_DRESS_CODE() {
        return COLUMN_NAME_DRESS_CODE;
    }

    public Event setCOLUMN_NAME_DRESS_CODE(String COLUMN_NAME_DRESS_CODE) {
        this.COLUMN_NAME_DRESS_CODE = COLUMN_NAME_DRESS_CODE;
        return this;
    }

    public String getCOLUMN_NAME_POSTER() {
        return COLUMN_NAME_POSTER;
    }

    public Event setCOLUMN_NAME_POSTER(String COLUMN_NAME_POSTER) {
        this.COLUMN_NAME_POSTER = COLUMN_NAME_POSTER;
        return this;
    }

    public String getCOLUMN_NAME_TARGET() {
        return COLUMN_NAME_TARGET;
    }

    public Event setCOLUMN_NAME_TARGET(String COLUMN_NAME_TARGET) {
        this.COLUMN_NAME_TARGET = COLUMN_NAME_TARGET;
        return this;
    }

    public String getCOLUMN_NAME_MAX_PEOPLE() {
        return COLUMN_NAME_MAX_PEOPLE;
    }

    public Event setCOLUMN_NAME_MAX_PEOPLE(String COLUMN_NAME_MAX_PEOPLE) {
        this.COLUMN_NAME_MAX_PEOPLE = COLUMN_NAME_MAX_PEOPLE;
        return this;
    }

    public Event setCOLUMN_NAME_LAUNCHER_ID(String COLUMN_NAME_LAUNCHER_ID) {
        this.COLUMN_NAME_LAUNCHER_ID = COLUMN_NAME_LAUNCHER_ID;
        return this;
    }

    public String getCOLUMN_NAME_LAUNCHER_ID() {
        return COLUMN_NAME_LAUNCHER_ID;
    }

    public Event setCOLUMN_NAME_TIMESTAMP(String COLUMN_NAME_TIMESTAMP) {
        this.COLUMN_NAME_TIMESTAMP = COLUMN_NAME_TIMESTAMP;
        return this;
    }

    public String getCOLUMN_NAME_TIMESTAMP() {
        return COLUMN_NAME_TIMESTAMP;
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
