package com.brynhildr.asgard.dblayout.relationships;

import android.provider.BaseColumns;

/**
 * Created by lqshan on 11/13/15.
 */
public class RelationshipSchema {

    public RelationshipSchema() {}

    public static abstract class RelationshipEntry implements BaseColumns {
        public static final String TABLE_NAME = "Relationships";

        // primary key of the relationship table, pulled from remote.
        public static final String COLUMN_NAME_ID = "ID";

        // unique user name
        public static final String COLUMN_NAME_USERNAME = "Username";

        // unique event ID
        public static final String COLUMN_NAME_EVENT_ID = "Event_ID";

        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
