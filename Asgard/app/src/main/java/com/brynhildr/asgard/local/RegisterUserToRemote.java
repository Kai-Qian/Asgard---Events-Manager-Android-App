package com.brynhildr.asgard.local;

import android.os.AsyncTask;
import android.widget.Toast;

import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.entities.User;
import com.brynhildr.asgard.global.MyApplication;

import java.io.File;
import java.util.List;

/**
 * Created by lqshan on 11/19/15.
 */
public class RegisterUserToRemote extends AsyncTask<User, Integer, String> {

    private static final String TAG = "HttpGetTask";

    protected String doInBackground(User... para1) {
        String result = null;
        String charset = "UTF-8";
        String requestURL = "http://52.34.9.132/register";
        List<String> response = null;
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
            User user = para1[0];
            multipart.addFormField("email", user.getEmail());
            multipart.addFormField("username", user.getUserName());
            multipart.addFormField("password", user.getPassWord());
            multipart.addFormField("phone", user.getPhoneNum());
            multipart.addFormField("gender", user.getGender());

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
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        //showDialog("Downloaded " + result + " bytes");
        if (result.equalsIgnoreCase("OK"))
            Toast.makeText(MyApplication.getAppContext(),
                    "Register Succeeded",
                    Toast.LENGTH_LONG).show();
        else if (result.equalsIgnoreCase("This username has been registered."))
            Toast.makeText(MyApplication.getAppContext(),
                    "This username has been registered.",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MyApplication.getAppContext(),
                    "Failed.",
                    Toast.LENGTH_LONG).show();
    }
}
