package com.brynhildr.asgard.local;

import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;

import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.entities.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lqshan on 11/19/15.
 */
public class ParseEventsJson {

    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readEventsArray(reader);
        } finally {
                reader.close();
        }
    }

    public List readEventsArray(JsonReader reader) throws IOException {
        List events = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            events.add(readEvent(reader));
        }
        reader.endArray();
        return events;
    }

    public Message readEvent(JsonReader reader) throws IOException {
        long id = -1;
        String text = null;
        User user = null;
        List geo = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextLong();
            } else if (name.equals("text")) {
                text = reader.nextString();
            } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
                //geo = readDoublesArray(reader);
            } else if (name.equals("user")) {
                //user = readUser(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

//TODO        return new Event();
        return null;
    }

/*
    public List readDoublesArray(JsonReader reader) throws IOException {
        List doubles = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

    public User readUser(JsonReader reader) throws IOException {
        String username = null;
        int followersCount = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                username = reader.nextString();
            } else if (name.equals("followers_count")) {
                followersCount = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(username, followersCount);
    }
*/
}
