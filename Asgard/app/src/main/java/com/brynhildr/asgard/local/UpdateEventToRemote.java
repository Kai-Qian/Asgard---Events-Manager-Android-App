package com.brynhildr.asgard.local;

import android.os.AsyncTask;
import android.widget.Toast;

import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.global.MyApplication;
import com.brynhildr.asgard.global.RemoteServerInformation;

import java.io.File;
import java.util.List;

/**
 * Created by lqshan on 11/19/15.
 */
public class UpdateEventToRemote extends AsyncTask<EventWithID, Integer, String> {

    private static final String URL = RemoteServerInformation.URL_SERVER
            + RemoteServerInformation.URL_UPDATE_EVENT;

    protected String doInBackground(EventWithID... para1) {
        String result = null;
        String charset = "UTF-8";
        List<String> response = null;
        try {
            MultipartUtility multipart = new MultipartUtility(URL, charset);
            EventWithID event = para1[0];

            multipart.addFormField("event_id", event.getEventID());
            multipart.addFormField("name", event.getCOLUMN_NAME_EVENT_NAME());
            multipart.addFormField("venue", event.getCOLUMN_NAME_VENUE());
            multipart.addFormField("description", event.getCOLUMN_NAME_DESCRIPTION());
            multipart.addFormField("dress_code", event.getCOLUMN_NAME_DRESS_CODE());
            multipart.addFormField("target_audience", event.getCOLUMN_NAME_TARGET());
            multipart.addFormField("max_people", event.getCOLUMN_NAME_MAX_PEOPLE());
            multipart.addFormField("username", event.getCOLUMN_NAME_LAUNCHER_ID());
            multipart.addFormField("time", event.getCOLUMN_NAME_DATEANDTIME());
            //multipart.addFilePart("picture", new File(filePath));
            multipart.addFilePart("picture", new File(event.getCOLUMN_NAME_POSTER()));
            response = multipart.finish();
            result = multipart.isSucceeded()?"True":"False";
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (response == null)
            return "False";
        for (String temp : response) {
            stringBuilder.append(temp);
        }
        System.out.println(stringBuilder);
        //return stringBuilder.toString();
        return result;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        //showDialog("Downloaded " + result + " bytes");
        if (result.equals("True"))
            Toast.makeText(MyApplication.getAppContext(),
                    "The event is updated successfully",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MyApplication.getAppContext(),
                    "The event can not be updated. Maybe there is an error in the input ",
                    Toast.LENGTH_LONG).show();
    }
}
