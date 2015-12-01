package com.brynhildr.asgard.local;

import android.os.AsyncTask;
import android.widget.Toast;

import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.global.MyApplication;

import java.io.File;
import java.util.List;

/**
 * Created by lqshan on 11/19/15.
 */
public class CreateEventToRemote extends AsyncTask<Event, Integer, String> {

    private final String filePath = "/storage/emulated/0/DCIM/Camera/burger_king_icon.png";
    private static final String TAG = "HttpGetTask";
    private static final String URL = "http://52.34.9.132/create-event";
    private static final String query = "";
    //private String response = "";
    private String BOUNDARY = java.util.UUID.randomUUID().toString();
    private String PREFIX = "--" , LINEND = "\r\n" ;
    private String MULTIPART_FROM_DATA = "multipart/form-data" ;
    private String CHARSET = "UTF-8" ;

    protected String doInBackground(Event... para1) {
        String result = null;
        String charset = "UTF-8";
        String requestURL = "http://52.34.9.132/create-event";
        List<String> response = null;
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            Event event = para1[0];

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
                    "The event is launched successfully",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MyApplication.getAppContext(),
                    "The event can not be launched. Maybe there is an error in the input ",
                    Toast.LENGTH_LONG).show();
    }
}
