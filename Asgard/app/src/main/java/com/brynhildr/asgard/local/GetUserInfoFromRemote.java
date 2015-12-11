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
public class GetUserInfoFromRemote extends AsyncTask<String, Integer, String> {

    protected String doInBackground(String... para1) {
        String result = null;
        String charset = "UTF-8";
        String requestURL = RemoteServerInformation.URL_SERVER
                + RemoteServerInformation.URL_GET_USER_INFO;
        List<String> response = null;
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            String username = para1[0];

            multipart.addFormField("username", username);
            response = multipart.finish();
//            result = multipart.isSucceeded()?"True":"False";
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
        //
        //return stringBuilder.toString();
        return stringBuilder.toString();
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
    }
}
