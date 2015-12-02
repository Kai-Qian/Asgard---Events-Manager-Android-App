package com.brynhildr.asgard.local;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.DBLayout.relationships.RelationshipDatabase;
import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.global.MyApplication;
import com.brynhildr.asgard.global.SimplifiedUserAuthentication;

import java.util.ArrayList;

/**
 * Created by willQian on 2015/12/1.
 */
public class GetRegisteredEvents {

    public ArrayList<Event> getRegisteredEvents() {
        String userName = SimplifiedUserAuthentication.getUsername();
        new GetEventsFromRemote().execute();
        new GetRelationsFromRemote().execute();
        EventDatabase edb = new EventDatabase(MyApplication.getAppContext());
        RelationshipDatabase rdb = new RelationshipDatabase(MyApplication.getAppContext());
        ArrayList<String> listEventIDs = rdb.getRegisteredEventIDs(userName);
        return edb.getRegisteredEvents(listEventIDs);
    }

}
