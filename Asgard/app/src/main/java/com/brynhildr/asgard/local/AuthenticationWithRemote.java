package com.brynhildr.asgard.local;

import android.os.AsyncTask;
import android.widget.Toast;

import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.global.MyApplication;

import java.io.File;
import java.util.List;

/**
 * Created by lqshan on 12/2/15.
 */
public class AuthenticationWithRemote extends AsyncTask<String, Integer, Boolean> {

    protected Boolean doInBackground(String... para1) {
        String charset = "UTF-8";
        String requestURL = "http://52.34.9.132/login";
        List<String> response = null;
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            String username = para1[0];
            String password = para1[1];

            multipart.addFormField("username", username);
            multipart.addFormField("password", password);
            response = multipart.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (response == null)
            return false;
        for (String temp : response) {
            stringBuilder.append(temp);
        }
        String responseContent = stringBuilder.toString();
        return responseContent.equalsIgnoreCase("OK");
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Boolean result) {
        if (result)
            Toast.makeText(MyApplication.getAppContext(),
                    "Login Succeeded",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MyApplication.getAppContext(),
                    "Login Failed",
                    Toast.LENGTH_LONG).show();
    }
}
