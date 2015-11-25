package com.brynhildr.asgard.local;

import android.database.Cursor;
import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.DBLayout.events.EventSchema;
import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.entities.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lqshan on 11/19/15.
 */
public class UpdateLocalDB {

    private InputStream in;

    public UpdateLocalDB(InputStream in) {
        this.in = in;
    }


    public List<EventWithID> readJsonStream() throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return getEventsArray(reader);
        } finally {
                reader.close();
        }
    }

    public List<EventWithID> getEventsArray(JsonReader reader) throws IOException {
        List<EventWithID> events = new ArrayList<EventWithID>();

        reader.beginArray();

        while (reader.hasNext()) {
            events.add(getSingleEvent(reader));
        }
        reader.endArray();
        return events;
    }

    /**
     * Convert timestamp to date and time string.
     * @param timestamp timestamp in long
     * @return date and time in string
     */
    private String getDateTime(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        String dateAndTime = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa").format(ts);
        return dateAndTime.substring(1);
    }

    // Example output from remote server.
//                "id":1,
//                "modified":1448349717,
//                "name":"Christmas Event 2",
//                "venue":"CMU INI",
//                "timestamp":1448341988,
//                "description":"This is another description",
//                "dress_code":"causal"
//                "launcher":"",
//                "target_audience":"human2",
//                "max_people":20,
//                "poster":media/poster2.jpg
    /**
     * Get one single event object.
     * @param reader JasonReader
     * @return event object
     * @throws IOException
     */
    public EventWithID getSingleEvent(JsonReader reader) throws IOException {
        String id = null;
        String modifiedTime = null;
        String eventName = null;
        String venue = null;
        String dataAndTime = null;
        String description = null;
        String dressCode = null;
        String launcher = null;
        String targetAudience = null;
        String maxPeople = null;
        String posterURL = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            System.out.println(name);
            switch (name) {
                case "id": id = Long.toString(reader.nextLong());
                    break;
                case "modified": modifiedTime = Long.toString(reader.nextLong());
                    break;
                case "name": eventName = reader.nextString();
                    break;
                case "venue": venue = reader.nextString();
                    break;
                case "timestamp": dataAndTime = getDateTime(reader.nextLong());
                    break;
                case "description": description = reader.nextString();
                    break;
                case "dress_code": dressCode = reader.nextString();
                    break;
                case "launcher": launcher = reader.nextString();
                    break;
                case "target_audience": targetAudience = reader.nextString();
                    break;
                case "max_people": maxPeople = Integer.toString(reader.nextInt());
                    break;
                case "poster": posterURL = reader.nextString();
                    break;
                default: break;
            }
        }
        reader.endObject();

        EventWithID event = new EventWithID();
        event.setID(id).setCOLUMN_NAME_TIMESTAMP(modifiedTime).setCOLUMN_NAME_EVENT_NAME(eventName);
        event.setCOLUMN_NAME_VENUE(venue).setCOLUMN_NAME_DATEANDTIME(dataAndTime);
        event.setCOLUMN_NAME_DESCRIPTION(description).setCOLUMN_NAME_DRESS_CODE(dressCode);
        event.setCOLUMN_NAME_TARGET(targetAudience).setCOLUMN_NAME_LAUNCHER_ID(launcher);
        event.setCOLUMN_NAME_MAX_PEOPLE(maxPeople).setCOLUMN_NAME_POSTER(posterURL);

        return event;
    }

//    /**
//     * Decides whether an event object needs to be updated.
//     * @param event the event to check in the local database.
//     * @return whether it needs to be updated in the local database.
//     */
//    private boolean needUpdate(EventWithID event) {
//        String table = "TABLE_NAME";
//        String[] columns = new String[]{EventSchema.EventEntry.COLUMN_NAME_TIMESTAMP};
//        String selection = EventSchema.EventEntry.COLUMN_NAME_ENTRY_ID + "= ?";
//        String[] selectionArgs = {event.getEventID()};
//
//
//        Cursor c = query(String table, String[] columns, String selection, String[] selectionArgs,
//                String groupBy, String having, String orderBy, String limit)
//
//        return false;
//    }


    public void compareAndUpdate(EventDatabase edb) {
        // All entries from local database.
        List<EventWithID> localList = edb.readRowWithID();
        HashMap<String, EventWithID> localMap = new HashMap<String, EventWithID>();

        // Info got form reomte database.
        List<EventWithID> remoteList = null;
        HashMap<String, EventWithID> remoteMap = new HashMap<String, EventWithID>();

        try {
            remoteList = readJsonStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Build local map for compare.
        for (int i = 0; i < localList.size(); ++i) {
            localMap.put(localList.get(i).getEventID(), localList.get(i));
        }

        // Build remote map for compare.
        for (int i = 0; i < remoteList.size(); ++i) {
            remoteMap.put(remoteList.get(i).getEventID(), remoteList.get(i));
        }

        // Compare local database with remote database ID by ID.
        Iterator<String> iter = localMap.keySet().iterator();

        while(iter.hasNext()) {
            String curID = iter.next();
            if (remoteMap.get(curID) == null)
                edb.deleteRow(localMap.get(curID));
            else if (!remoteMap.get(curID).getCOLUMN_NAME_TIMESTAMP().equals(localMap.get(curID).getCOLUMN_NAME_TIMESTAMP())) {
                // If the entry is different, update the local database.
                edb.updateRow(remoteMap.get(curID));

                // Remove the event after updating the local database.
                remoteMap.remove(curID);
            } else if (remoteMap.get(curID).getCOLUMN_NAME_TIMESTAMP().equals(localMap.get(curID).getCOLUMN_NAME_TIMESTAMP())) {
                remoteMap.remove(curID);
            }
        }

        // If the remote map still has events to be added, add them to local.
        if (!remoteMap.isEmpty()) {
            iter = remoteMap.keySet().iterator();
            while(iter.hasNext()) {
                String curID = iter.next();
                edb.insertRow(remoteMap.get(curID));
                //remoteMap.remove(curID);
            }
        }



        // Done with updating local database.
        return;
    }

}
