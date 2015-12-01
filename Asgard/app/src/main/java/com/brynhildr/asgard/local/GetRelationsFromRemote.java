package com.brynhildr.asgard.local;


import android.os.AsyncTask;
import android.util.Log;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.global.MyApplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lqshan on 11/18/15.
 */

public class GetRelationsFromRemote extends AsyncTask<Void, Integer, String> {

    private static final String TAG = "HttpGetTask";
    private static final String URL = "http://52.34.9.132/get-relationships";

    protected String doInBackground(Void... para1) {
        String data = "";
        HttpURLConnection httpUrlConnection = null;
        System.out.println("Start to perform background task.");
        try {
            httpUrlConnection = (HttpURLConnection) new URL(URL)
                    .openConnection();

            InputStream in = new BufferedInputStream(
                    httpUrlConnection.getInputStream());

            UpdateLocalDB updateLocalDB = new UpdateLocalDB(in);
            EventDatabase eventDatabase = new EventDatabase(MyApplication.getAppContext());
            updateLocalDB.compareAndUpdate(eventDatabase);

            ArrayList<EventWithID> events = eventDatabase.readRowWithID();

            for (int i = 0; i < events.size(); ++i) {
                System.out.println("ID = " + events.get(i).getEventID());
                System.out.println("Event name = " + events.get(i).getCOLUMN_NAME_EVENT_NAME());
                System.out.println("Venue = " + events.get(i).getCOLUMN_NAME_VENUE());
                System.out.println("Date and Time = " + events.get(i).getCOLUMN_NAME_DATEANDTIME());
                System.out.println("Description = " + events.get(i).getCOLUMN_NAME_DESCRIPTION());
                System.out.println("Dress code = " + events.get(i).getCOLUMN_NAME_DRESS_CODE());
                System.out.println("Launcher ID = " + events.get(i).getCOLUMN_NAME_LAUNCHER_ID());
                System.out.println("Max people = " + events.get(i).getCOLUMN_NAME_MAX_PEOPLE());
                System.out.println("poster URL = " + events.get(i).getCOLUMN_NAME_POSTER());
                System.out.println("Target = " + events.get(i).getCOLUMN_NAME_TARGET());
                System.out.println("Timestamp = " + events.get(i).getCOLUMN_NAME_TIMESTAMP());
            }

            //data = readStream(in);

            in.close();
        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException");
        } catch (IOException exception) {
            Log.e(TAG, "IOException");
        } finally {
            if (null != httpUrlConnection)
                httpUrlConnection.disconnect();
        }
        //return data;
        System.out.println(data);
        return data;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        //showDialog("Downloaded " + result + " bytes");
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer("");
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }
}