package com.brynhildr.asgard.DBLayout.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brynhildr.asgard.entities.Event;

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


    public long insertRow(ArrayList<String> eventDetails) {
        SQLiteDatabase db = eventDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME, eventDetails.get(0));
        values.put(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME, eventDetails.get(1));
        values.put(EventSchema.EventEntry.COLUMN_NAME_VENUE, eventDetails.get(2));
        values.put(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE, eventDetails.get(3));
        values.put(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE, eventDetails.get(4));
        values.put(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE, eventDetails.get(5));
        values.put(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION, eventDetails.get(6));
        values.put(EventSchema.EventEntry.COLUMN_NAME_POSTER, eventDetails.get(7));
        long newRowId;
        newRowId = db.insert(
                EventSchema.EventEntry.TABLE_NAME,
                EventSchema.EventEntry.COLUMN_NAME_NULLABLE,
                values);
        System.out.println("newRowId---->" + newRowId);
        return newRowId;
    }

    public long deleteRow() {
        return -1;
    }

    public long updateRow() {
        return -1;
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
                        EventSchema.EventEntry.COLUMN_NAME_POSTER},
                null, null, null, null, null);
        ArrayList<String> dateAndTime = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> address = new ArrayList<String>();
        ArrayList<String> dressCode = new ArrayList<String>();
        ArrayList<String> target = new ArrayList<String>();
        ArrayList<String> max = new ArrayList<String>();
        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String> poster = new ArrayList<String>();
        System.out.println("getColumnCount" + cursor.getColumnCount());
        System.out.println("getCount" + cursor.getCount());
        while (cursor.moveToNext()) {
            // create a new TableRow
            dateAndTime.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DATEANDTIME)));
            name.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_EVENT_NAME)));
            address.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_VENUE)));
            dressCode.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DRESS_CODE)));
            target.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_TARGET_AUDIENCE)));
            max.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_MAX_PEOPLE)));
            description.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_DESCRIPTION)));
            poster.add(cursor.getString(cursor.getColumnIndex(EventSchema.EventEntry.COLUMN_NAME_POSTER)));
        }

        ArrayList<Event> event = new ArrayList<Event>();
        for (int i = 0; i < name.size(); i++) {
            event.add(new Event(name.get(i), address.get(i), dateAndTime.get(i), description.get(i), dressCode.get(i), poster.get(i), target.get(i), max.get(i)));
        }
        return event;
    }

    public void close() {
        eventDatabaseHelper.close();
    }
}