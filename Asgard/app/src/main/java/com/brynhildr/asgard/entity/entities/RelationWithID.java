package com.brynhildr.asgard.entity.entities;

/**
 * Created by ShuqinYe on 30/11/15.
 */
public class RelationWithID {

    private String primaryID;
    private String userName;
    private String eventID; // long type presented in string (timestamp)

    public RelationWithID() {

    }

    public String getPrimaryID() {
        return primaryID;
    }

    public String getUserName() {
        return userName;
    }

    public String getEventID() {
        return eventID;
    }

    public RelationWithID setPrimaryID(String primaryID) {
        this.primaryID = primaryID;
        return this;
    }

    public RelationWithID setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public RelationWithID setEventID(String eventID) {
        this.eventID = eventID;
        return this;
    }
}
