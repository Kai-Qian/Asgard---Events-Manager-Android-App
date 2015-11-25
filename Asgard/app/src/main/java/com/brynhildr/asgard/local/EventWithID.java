package com.brynhildr.asgard.local;

import com.brynhildr.asgard.entities.Event;

/**
 * Created by ShuqinYe on 24/11/15.
 */
public class EventWithID extends Event {
    String eventID = null;

    public EventWithID() {
        super();
    }

    public EventWithID setID(String eventID) {
        this.eventID = eventID;
        return this;
    }

    public String getEventID() {
        return eventID;
    }

    public boolean equals(EventWithID that) {
        return this.getEventID().equals(that.getEventID()) &&
                this.getCOLUMN_NAME_EVENT_NAME().equals(that.getCOLUMN_NAME_EVENT_NAME()) &&
                this.getCOLUMN_NAME_VENUE().equals(that.getCOLUMN_NAME_VENUE()) &&
                this.getCOLUMN_NAME_DATEANDTIME().equals(that.getCOLUMN_NAME_DATEANDTIME()) &&
                this.getCOLUMN_NAME_DESCRIPTION().equals(that.getCOLUMN_NAME_DESCRIPTION()) &&
                this.getCOLUMN_NAME_DRESS_CODE().equals(that.getCOLUMN_NAME_DRESS_CODE()) &&
                this.getCOLUMN_NAME_POSTER().equals(that.getCOLUMN_NAME_POSTER()) &&
                this.getCOLUMN_NAME_TARGET().equals(that.getCOLUMN_NAME_TARGET()) &&
                this.getCOLUMN_NAME_MAX_PEOPLE().equals(that.getCOLUMN_NAME_MAX_PEOPLE()) &&
                this.getCOLUMN_NAME_LAUNCHER_ID().equals(that.getCOLUMN_NAME_LAUNCHER_ID()) &&
                this.getCOLUMN_NAME_TIMESTAMP().equals(that.getCOLUMN_NAME_TIMESTAMP());
    }

}
