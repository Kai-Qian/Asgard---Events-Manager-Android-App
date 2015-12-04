package com.brynhildr.asgard.local;

import android.os.AsyncTask;
import android.widget.Toast;

import com.brynhildr.asgard.entities.User;
import com.brynhildr.asgard.global.MyApplication;

import java.util.List;

/**
 * Created by lqshan on 11/19/15.
 */
public class RegisterEventToRemote extends AsyncTask<String, Integer, String> {

    private static final String TAG = "HttpGetTask";

    protected String doInBackground(String... para1) {
        String result = null;
        String charset = "UTF-8";
        String requestURL = "http://52.34.9.132/register-event";
        List<String> response = null;
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            String event_id = para1[0];
            String username = para1[1];
            multipart.addFormField("username", username);
            multipart.addFormField("event_id", event_id);
            response = multipart.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (response == null)
            return "Failed";
        for (String temp : response) {
            stringBuilder.append(temp);
        }
        System.out.println("The output is: ");
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        if (result.equals("OK"))
            Toast.makeText(MyApplication.getAppContext(),
                    "You have registered successfully.",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MyApplication.getAppContext(),
                    "You have already registered this event! Fuck!",
                    Toast.LENGTH_LONG).show();
    }
}
