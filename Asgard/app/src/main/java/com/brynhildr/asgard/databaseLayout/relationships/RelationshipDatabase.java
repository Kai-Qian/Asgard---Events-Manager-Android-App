package com.brynhildr.asgard.databaseLayout.relationships;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brynhildr.asgard.entities.RelationWithID;

import java.util.ArrayList;

/**
 * Created by ShuqinYe on 24/11/15.
 */
public class RelationshipDatabase {


    static private RelationshipDatabaseHelper relationshipDatabaseHelper = null;

    public RelationshipDatabase(Context context) {
        relationshipDatabaseHelper = new RelationshipDatabaseHelper(context);
    }

    public void deleteTable() {
        SQLiteDatabase db = relationshipDatabaseHelper.getWritableDatabase();
        relationshipDatabaseHelper.deleteTable(db);
    }

    public long insertRow(RelationWithID relation) {
        SQLiteDatabase db = relationshipDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RelationshipSchema.RelationshipEntry.COLUMN_NAME_ID, relation.getPrimaryID());
        values.put(RelationshipSchema.RelationshipEntry.COLUMN_NAME_USERNAME, relation.getUserName());
        values.put(RelationshipSchema.RelationshipEntry.COLUMN_NAME_EVENT_ID, relation.getEventID());

        long newRowId;
        newRowId = db.insert(
                RelationshipSchema.RelationshipEntry.TABLE_NAME,
                RelationshipSchema.RelationshipEntry.COLUMN_NAME_NULLABLE,
                values);

        System.out.println("newRowId---->" + newRowId);

        return newRowId;
    }

    public long deleteRow(RelationWithID relation) {
        SQLiteDatabase db = relationshipDatabaseHelper.getWritableDatabase();
        String whereClause = RelationshipSchema.RelationshipEntry.COLUMN_NAME_ID + "= ?";
        String[] whereArgs = {
                relation.getPrimaryID()
        };

        return db.delete(RelationshipSchema.RelationshipEntry.TABLE_NAME, whereClause, whereArgs);

    }

    public ArrayList<RelationWithID> readAllRows() {

        SQLiteDatabase db = relationshipDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(RelationshipSchema.RelationshipEntry.TABLE_NAME,
                new String[]{
                        RelationshipSchema.RelationshipEntry.COLUMN_NAME_ID,
                        RelationshipSchema.RelationshipEntry.COLUMN_NAME_USERNAME,
                        RelationshipSchema.RelationshipEntry.COLUMN_NAME_EVENT_ID
                },
                null, null, null, null, null);

        ArrayList<RelationWithID> relations = new ArrayList<RelationWithID>();

        while (cursor.moveToNext()) {
            // Create each event object.
            RelationWithID relation = new RelationWithID();
            relation.setPrimaryID(cursor.getString(cursor.getColumnIndex(RelationshipSchema.RelationshipEntry.COLUMN_NAME_ID)));
            relation.setUserName(cursor.getString(cursor.getColumnIndex(RelationshipSchema.RelationshipEntry.COLUMN_NAME_USERNAME)));
            relation.setEventID(cursor.getString(cursor.getColumnIndex(RelationshipSchema.RelationshipEntry.COLUMN_NAME_EVENT_ID)));

            relations.add(relation);
        }

        return relations;

    }

    /**
     * Get the list of registered event IDs of the userName
     * @param userName the user name
     * @return a list of event IDs that the user will attend
     */
    public ArrayList<String> getRegisteredEventIDs(String userName) {

        SQLiteDatabase db = relationshipDatabaseHelper.getReadableDatabase();

        String selection = RelationshipSchema.RelationshipEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {userName};

        Cursor cursor = db.query(RelationshipSchema.RelationshipEntry.TABLE_NAME,
                new String[]{

                        RelationshipSchema.RelationshipEntry.COLUMN_NAME_EVENT_ID
                },

                selection, selectionArgs, null, null, null);

        ArrayList<String> eventIDs = new ArrayList<String>();

        while (cursor.moveToNext()) {
            // Create each event object.
            String eventID;
            eventID = cursor.getString(cursor.getColumnIndex(RelationshipSchema.RelationshipEntry.COLUMN_NAME_EVENT_ID));

            eventIDs.add(eventID);
        }

        return eventIDs;
    }

    public void close() {
        relationshipDatabaseHelper.close();
    }


}
