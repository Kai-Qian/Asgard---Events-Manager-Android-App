package com.brynhildr.asgard.local;

import android.os.AsyncTask;
import android.util.Log;

import com.brynhildr.asgard.entities.Event;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lqshan on 11/19/15.
 */
public class CreateEventToRemote extends AsyncTask<Event, Integer, String> {

    private static final String TAG = "HttpGetTask";
    private static final String URL = "http://52.34.9.132/create-event";
    private static final String query = "";
    private String response = "";
    private boolean isSuccess = false;
    private int responseCode = 0;

    protected String doInBackground(Event... para1) {
        try {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(URL)
                    .openConnection();
            httpUrlConnection.setReadTimeout(15000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("HTTP_X_SKIP_CSRF", "True");
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            OutputStream os = httpUrlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Event event = para1[0];
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("name", event.getCOLUMN_NAME_EVENT_NAME());
            postDataParams.put("venue", event.getCOLUMN_NAME_VENUE());
            postDataParams.put("description", event.getCOLUMN_NAME_DESCRIPTION());
            postDataParams.put("dress_code", event.getCOLUMN_NAME_DRESS_CODE());
            postDataParams.put("target_audience", event.getCOLUMN_NAME_TARGET());
            postDataParams.put("max_people", event.getCOLUMN_NAME_MAX_PEOPLE());
            postDataParams.put("username", event.getCOLUMN_NAME_LAUNCHER_ID());
            postDataParams.put("time", event.getCOLUMN_NAME_DATEANDTIME());
            System.out.println(event.getCOLUMN_NAME_DATEANDTIME());

            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            responseCode = httpUrlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                isSuccess = true;
                System.out.println(responseCode + " " + response + isSuccess);
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response += line;
                }
            }
            else {
                isSuccess = false;
                response = "";
                System.out.println(responseCode + " " + response + isSuccess);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseCode + "";
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        if (result.equals("200")) {
            responseCode = 200;
        } else {
            responseCode = 500;
        }
//        showDialog("Downloaded " + result + " bytes");
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

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public boolean getResponse() {
        System.out.println("isSuccess--->" + isSuccess);
        return isSuccess;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
