package com.brynhildr.asgard.dblayout.relationships;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by lqshan on 11/13/15.
 */
public class RelationshipDatabaseHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RelationshipSchema.RelationshipEntry.TABLE_NAME + " (" +
                    RelationshipSchema.RelationshipEntry.COLUMN_NAME_ID + " TEXT primary key, " +
                    RelationshipSchema.RelationshipEntry.COLUMN_NAME_EVENT_ID + " TEXT, " +
                    RelationshipSchema.RelationshipEntry.COLUMN_NAME_USERNAME + " TEXT " +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RelationshipSchema.RelationshipEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "relationships";

    public RelationshipDatabaseHelper(Context context) {
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
