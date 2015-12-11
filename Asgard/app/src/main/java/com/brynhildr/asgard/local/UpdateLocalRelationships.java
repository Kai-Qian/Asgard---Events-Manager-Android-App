package com.brynhildr.asgard.local;

import android.util.JsonReader;

import com.brynhildr.asgard.databaseLayout.relationships.RelationshipDatabase;
import com.brynhildr.asgard.entities.RelationWithID;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ShuqinYe on 24/11/15.
 */
public class UpdateLocalRelationships {


    private InputStream in;

    public UpdateLocalRelationships(InputStream in) {
        this.in = in;
    }


    public List<RelationWithID> readJsonStream() throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return getRelationsArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<RelationWithID> getRelationsArray(JsonReader reader) throws IOException {
        List<RelationWithID> relations = new ArrayList<RelationWithID>();

        reader.beginArray();

        while (reader.hasNext()) {
            relations.add(getSingleRelation(reader));
        }
        reader.endArray();
        return relations;
    }

    /**
     * Get one single relationwithid object.
     * @param reader JasonReader
     * @return event object
     * @throws IOException
     */
    public RelationWithID getSingleRelation(JsonReader reader) throws IOException {
        String primaryID = null;
        String userName = null;
        String eventID = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();

            switch (name) {
                case "id": primaryID = reader.nextString();
                    break;
                case "username": userName = reader.nextString();
                    break;
                case "event_id": eventID = reader.nextString();
                    break;
                default: break;
            }
        }

        reader.endObject();

        RelationWithID relation = new RelationWithID();

        relation.setEventID(eventID).setPrimaryID(primaryID).setUserName(userName);

        return relation;
    }

    public void compareAndUpdate(RelationshipDatabase rdb) {
        // All entries from local database.
        List<RelationWithID> localList = rdb.readAllRows();
        HashMap<String, RelationWithID> localMap = new HashMap<String, RelationWithID>();

        // Info got form reomte database.
        List<RelationWithID> remoteList = null;
        HashMap<String, RelationWithID> remoteMap = new HashMap<String, RelationWithID>();

        try {
            remoteList = readJsonStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Build local map for compare.
        for (int i = 0; i < localList.size(); ++i) {
            localMap.put(localList.get(i).getPrimaryID(), localList.get(i));
        }

        // Build remote map for compare.
        for (int i = 0; i < remoteList.size(); ++i) {
            remoteMap.put(remoteList.get(i).getPrimaryID(), remoteList.get(i));
        }

        // Compare local database with remote database ID by ID.
        Iterator<String> iter = localMap.keySet().iterator();

        while(iter.hasNext()) {
            String curID = iter.next();
            if (remoteMap.get(curID) == null) rdb.deleteRow(localMap.get(curID));
            else {
                // Remove the entry after updating the local database.
                remoteMap.remove(curID);
            }
        }

        // If the remote map still has entries to be added, add them to local.
        if (!remoteMap.isEmpty()) {
            iter = remoteMap.keySet().iterator();
            while(iter.hasNext()) {
                String curID = iter.next();
                rdb.insertRow(remoteMap.get(curID));
            }
        }

        // Done with updating local database.
        return;
    }

}
