package com.brynhildr.asgard.local;


import android.os.AsyncTask;
import android.util.Log;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.DBLayout.relationships.RelationshipDatabase;
import com.brynhildr.asgard.entities.Relation;
import com.brynhildr.asgard.entities.RelationWithID;
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

            UpdateLocalRelationships updateLocalRelationships = new UpdateLocalRelationships(in);
            RelationshipDatabase relationshipDatabase = new RelationshipDatabase(MyApplication.getAppContext());
            updateLocalRelationships.compareAndUpdate(relationshipDatabase);

            ArrayList<RelationWithID> relationWithIDs = relationshipDatabase.readAllRows();


            for (int i = 0; i < relationWithIDs.size(); ++i) {
                System.out.println("Event ID = " + relationWithIDs.get(i).getEventID());
                System.out.println("user name = " + relationWithIDs.get(i).getUserName());
                System.out.println("Primary ID = " + relationWithIDs.get(i).getPrimaryID());
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