package com.brynhildr.asgard.databaseLayout.events;

import android.provider.BaseColumns;

/**
 * Created by lqshan on 11/13/15.
 */
public class EventSchema {

    public EventSchema() {}

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Events";
        public static final String COLUMN_NAME_ENTRY_ID = "ID"; // String type of int ID.

        public static final String COLUMN_NAME_EVENT_NAME = "EventName";
        public static final String COLUMN_NAME_VENUE = "Venue";

        public static final String COLUMN_NAME_DATEANDTIME = "DateAndTime"; // Event and Time String Type

        public static final String COLUMN_NAME_DESCRIPTION = "Description";

        public static final String COLUMN_NAME_DRESS_CODE = "DressCode";

        public static final String COLUMN_NAME_POSTER = "Poster"; // Poster image URL

        public static final String COLUMN_NAME_TARGET_AUDIENCE = "TargetAudience";
        public static final String COLUMN_NAME_MAX_PEOPLE = "MaxPeople"; // int String type
        public static final String COLUMN_NAME_LAUNCHER_ID = "Launcher"; // Launcher user name
        public static final String COLUMN_NAME_TIMESTAMP = "Timestamp"; // long value String type
        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
