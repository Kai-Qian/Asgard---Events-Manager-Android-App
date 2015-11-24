package com.brynhildr.asgard.DBLayout.relationships;

import android.provider.BaseColumns;

/**
 * Created by lqshan on 11/13/15.
 */
public class RelationshipSchema {

    public RelationshipSchema() {}

    public static abstract class RelationshipEntry implements BaseColumns {
        public static final String TABLE_NAME = "Relationships";

        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_EVENT_ID = "Event_ID";
        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
