package com.brynhildr.asgard.entities;

/**
 * Created by ShuqinYe on 30/11/15.
 */
public class Relation {

    private String userName;
    private String eventID; // long type presented in string (timestamp)

    public Relation() {

    }

    public String getUserName() {
        return userName;
    }

    public String getEventID() {
        return eventID;
    }

    public Relation setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Relation setEventID(String eventID) {
        this.eventID = eventID;
        return this;
    }
}
