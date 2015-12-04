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
public class GetUserInfoToRemote extends AsyncTask<String, Integer, String> {

    protected String doInBackground(String... para1) {
        String result = null;
        String charset = "UTF-8";
        String requestURL = "http://52.34.9.132/get-info";
        List<String> response = null;
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            String username = para1[0];

            multipart.addFormField("username", username);
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
                    "The event is launched successfully",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MyApplication.getAppContext(),
                    "The event can not be launched. Maybe there is an error in the input ",
                    Toast.LENGTH_LONG).show();
    }
}
