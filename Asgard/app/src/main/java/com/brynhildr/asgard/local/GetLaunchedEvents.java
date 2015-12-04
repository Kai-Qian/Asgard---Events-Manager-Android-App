package com.brynhildr.asgard.local;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.global.MyApplication;
import com.brynhildr.asgard.global.SimplifiedUserAuthentication;

import java.util.ArrayList;

/**
 * Created by willQian on 2015/12/1.
 */
public class GetLaunchedEvents {

    public ArrayList<EventWithID> getLaunchedEvents() {
        String userName = SimplifiedUserAuthentication.getUsername();
        new GetEventsFromRemote().execute();
        EventDatabase edb = new EventDatabase(MyApplication.getAppContext());
        return edb.getHostingEvents(userName);
    }
}
