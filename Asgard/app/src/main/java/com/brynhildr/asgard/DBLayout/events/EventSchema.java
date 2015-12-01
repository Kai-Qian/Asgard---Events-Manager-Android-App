package com.brynhildr.asgard.DBLayout.events;

import android.provider.BaseColumns;

/**
 * Created by lqshan on 11/13/15.
 */
public class EventSchema {

    public EventSchema() {}

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Events5";
        public static final String COLUMN_NAME_ENTRY_ID = "ID";

        public static final String COLUMN_NAME_EVENT_NAME = "EventName";
        public static final String COLUMN_NAME_VENUE = "Venue";

        public static final String COLUMN_NAME_DATEANDTIME = "DateAndTime";

        public static final String COLUMN_NAME_DESCRIPTION = "Description";

        public static final String COLUMN_NAME_DRESS_CODE = "DressCode";

        public static final String COLUMN_NAME_POSTER = "Poster";

        public static final String COLUMN_NAME_TARGET_AUDIENCE = "TargetAudience";
        public static final String COLUMN_NAME_MAX_PEOPLE = "MaxPeople";
        public static final String COLUMN_NAME_LAUNCHER_ID = "Launcher";
        public static final String COLUMN_NAME_TIMESTAMP = "Timestamp";
        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
