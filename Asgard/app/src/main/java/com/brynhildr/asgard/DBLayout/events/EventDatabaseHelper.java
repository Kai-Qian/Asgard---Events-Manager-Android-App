package com.brynhildr.asgard.DBLayout.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lqshan on 11/13/15.
 */
public class EventDatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EventSchema.EventEntry.TABLE_NAME + " (" +
                    EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + " integer primary key, " +
                    EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_VENUE + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_POSTER + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE + " TEXT, " +
                    EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID + "TEXT" +
                    EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP + "TEXT" +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventSchema.EventEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "events2";

    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void deleteTable(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    //    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        if (!db.isReadOnly())
//        {
//            // Enable foreign key constraints
//            db.execSQL("PRAGMA foreign_keys=ON;");
//        }
////        db.execSQL(SQL_DELETE_ENTRIES);
////        db.execSQL(SQL_CREATE_ENTRIES);
//    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

