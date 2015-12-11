package com.brynhildr.asgard.databaseLayout.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.local.EventWithID;

import java.util.ArrayList;

/**
 * Created by lqshan on 11/13/15.
 */
public class EventDatabase {

    static private EventDatabaseHelper eventDatabaseHelper = null;

    public EventDatabase(Context context) {
        eventDatabaseHelper = new EventDatabaseHelper(context);
    }

    public void deleteTable() {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();
        eventDatabaseHelper.deleteTable(db);
    }

    public long insertRow(EventWithID event) {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID, event.getEventID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME, event.getCOLUMN_NAME_EVENT_NAME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME, event.getCOLUMN_NAME_DATEANDTIME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_VENUE, event.getCOLUMN_NAME_VENUE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE, event.getCOLUMN_NAME_DRESS_CODE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE, event.getCOLUMN_NAME_TARGET());
        values.put(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE, event.getCOLUMN_NAME_MAX_PEOPLE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION, event.getCOLUMN_NAME_DESCRIPTION());
        values.put(EventSchema.EventEntry.COLUMN_NAME_POSTER, event.getCOLUMN_NAME_POSTER());
        values.put(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID, event.getCOLUMN_NAME_LAUNCHER_ID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP, event.getCOLUMN_NAME_TIMESTAMP());
        long newRowId;
        newRowId = db.insert(
                EventSchema.EventEntry.TABLE_NAME,
                EventSchema.EventEntry.COLUMN_NAME_NULLABLE,
                values);
        System.out.println("newRowId---->" + newRowId);
        return newRowId;
    }


    /**
     * Delete the entry if remote database doesn't have it.
     * @param event the event to be deleted.
     * @return the number of rows affected.
     */
    public long deleteRow(EventWithID event) {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();
        String whereClause = EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + "= ?";
        String[] whereArgs = {
                event.getEventID()
        };

        return db.delete(EventSchema.EventEntry.TABLE_NAME, whereClause, whereArgs);

    }


    /**
     * Update the row with an event ID
     * @param event the event to be updated.
     * @return the number of rows affected.
     */
    public long updateRow(EventWithID event) {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID, event.getEventID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME, event.getCOLUMN_NAME_EVENT_NAME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME, event.getCOLUMN_NAME_DATEANDTIME());
        values.put(EventSchema.EventEntry.COLUMN_NAME_VENUE, event.getCOLUMN_NAME_VENUE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE, event.getCOLUMN_NAME_DRESS_CODE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE, event.getCOLUMN_NAME_TARGET());
        values.put(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE, event.getCOLUMN_NAME_MAX_PEOPLE());
        values.put(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION, event.getCOLUMN_NAME_DESCRIPTION());
        values.put(EventSchema.EventEntry.COLUMN_NAME_POSTER, event.getCOLUMN_NAME_POSTER());
        values.put(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID, event.getCOLUMN_NAME_LAUNCHER_ID());
        values.put(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP, event.getCOLUMN_NAME_TIMESTAMP());

        String whereClause = EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + "= ?";
        String[] whereArgs = {
                event.getEventID()
        };

        return db.update(EventSchema.EventEntry.TABLE_NAME, values, whereClause, whereArgs);

    }

    public ArrayList<EventWithID> readRowWithID() {
        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(EventSchema.EventEntry.TABLE_NAME,
                new String[]{
                        EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID,
                        EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME,
                        EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME,
                        EventSchema.EventEntry.COLUMN_NAME_VENUE,
                        EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE,
                        EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE,
                        EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE,
                        EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION,
                        EventSchema.EventEntry.COLUMN_NAME_POSTER,
                        EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID,
                        EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP
                },
                null, null, null, null, null);
        ArrayList<EventWithID> events = new ArrayList<EventWithID>();
        while (cursor.moveToNext()) {
            // Create each event object.
            EventWithID event = new EventWithID();
            event.setID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID)));
            event.setCOLUMN_NAME_EVENT_NAME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
            event.setCOLUMN_NAME_VENUE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
            event.setCOLUMN_NAME_DATEANDTIME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
            event.setCOLUMN_NAME_DESCRIPTION(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
            event.setCOLUMN_NAME_DRESS_CODE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
            event.setCOLUMN_NAME_POSTER(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
            event.setCOLUMN_NAME_TARGET(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
            event.setCOLUMN_NAME_MAX_PEOPLE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
            event.setCOLUMN_NAME_LAUNCHER_ID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID)));
            event.setCOLUMN_NAME_TIMESTAMP(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP)));

            events.add(event);
        }

        return events;
    }

    public ArrayList<Event> readRow() {

        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(EventSchema.EventEntry.TABLE_NAME,
                new String[]{EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME,
                        EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME,
                        EventSchema.EventEntry.COLUMN_NAME_VENUE,
                        EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE,
                        EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE,
                        EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE,
                        EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION,
                        EventSchema.EventEntry.COLUMN_NAME_POSTER,
                        EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID,
                        EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP},
                null, null, null, null, null);
        ArrayList<Event> events = new ArrayList<Event>();
        while (cursor.moveToNext()) {
            // Create each event object.
            Event event = new Event();
            event.setCOLUMN_NAME_EVENT_NAME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
            event.setCOLUMN_NAME_VENUE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
            event.setCOLUMN_NAME_DATEANDTIME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
            event.setCOLUMN_NAME_DESCRIPTION(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
            event.setCOLUMN_NAME_DRESS_CODE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
            event.setCOLUMN_NAME_POSTER(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
            event.setCOLUMN_NAME_TARGET(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
            event.setCOLUMN_NAME_MAX_PEOPLE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
            event.setCOLUMN_NAME_LAUNCHER_ID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID)));
            event.setCOLUMN_NAME_TIMESTAMP(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP)));

            events.add(event);
        }

        return events;

    }


    /**
     * Get the list of registered events objects.
     * @param eventIDs list of event IDs for the events
     * @return the list of events
     */
    public ArrayList<EventWithID> getRegisteredEvents(ArrayList<String> eventIDs) {

        if (eventIDs.size() == 0) return new ArrayList<EventWithID>();
        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < eventIDs.size() - 1; ++i) {
            sb.append("'").append(eventIDs.get(i)).append("'").append(", ");
        }

        sb.append("'").append(eventIDs.get(eventIDs.size() - 1)).append("'");

        String selection = EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + " in (" + sb.toString() + ")";

        Cursor cursor = db.query(EventSchema.EventEntry.TABLE_NAME,
                new String[]{
                        EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID,
                        EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME,
                        EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME,
                        EventSchema.EventEntry.COLUMN_NAME_VENUE,
                        EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE,
                        EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE,
                        EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE,
                        EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION,
                        EventSchema.EventEntry.COLUMN_NAME_POSTER,
                        EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID,
                        EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP},
                selection, null, null, null, null);

        ArrayList<EventWithID> events = new ArrayList<EventWithID>();
        while (cursor.moveToNext()) {
            // Create each event object.
            EventWithID event = new EventWithID();
            event.setID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID)));
            event.setCOLUMN_NAME_EVENT_NAME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
            event.setCOLUMN_NAME_VENUE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
            event.setCOLUMN_NAME_DATEANDTIME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
            event.setCOLUMN_NAME_DESCRIPTION(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
            event.setCOLUMN_NAME_DRESS_CODE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
            event.setCOLUMN_NAME_POSTER(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
            event.setCOLUMN_NAME_TARGET(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
            event.setCOLUMN_NAME_MAX_PEOPLE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
            event.setCOLUMN_NAME_LAUNCHER_ID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID)));
            event.setCOLUMN_NAME_TIMESTAMP(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP)));

            events.add(event);
        }

        return events;
    }

    /**
     * Get all the events hosted by a user
     * @param userName the user name of the user
     * @return the list of events hosted by this user
     */
    public ArrayList<EventWithID> getHostingEvents(String userName) {

        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase();

        String selection = EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID + " = ?";
        String[] whereArgs = {userName};

        Cursor cursor = db.query(EventSchema.EventEntry.TABLE_NAME,
                new String[]{
                        EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID,
                        EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME,
                        EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME,
                        EventSchema.EventEntry.COLUMN_NAME_VENUE,
                        EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE,
                        EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE,
                        EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE,
                        EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION,
                        EventSchema.EventEntry.COLUMN_NAME_POSTER,
                        EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID,
                        EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP},
                selection, whereArgs, null, null, null);

        ArrayList<EventWithID> events = new ArrayList<EventWithID>();
        while (cursor.moveToNext()) {
            // Create each event object.
            EventWithID event = new EventWithID();
            event.setID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID)));
            event.setCOLUMN_NAME_EVENT_NAME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
            event.setCOLUMN_NAME_VENUE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
            event.setCOLUMN_NAME_DATEANDTIME(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
            event.setCOLUMN_NAME_DESCRIPTION(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
            event.setCOLUMN_NAME_DRESS_CODE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
            event.setCOLUMN_NAME_POSTER(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
            event.setCOLUMN_NAME_TARGET(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
            event.setCOLUMN_NAME_MAX_PEOPLE(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
            event.setCOLUMN_NAME_LAUNCHER_ID(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_LAUNCHER_ID)));
            event.setCOLUMN_NAME_TIMESTAMP(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP)));

            events.add(event);
        }

        return events;
    }

    public void close() {
        eventDatabaseHelper.close();
    }
}