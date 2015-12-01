package com.brynhildr.asgard.local;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.Event;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lqshan on 11/19/15.
 */
public class CreateEventToRemoteV2 extends AsyncTask<Event, Integer, String> {

    private final String filePath = "/storage/emulated/0/DCIM/Camera/burger_king_icon.png";
    private static final String TAG = "HttpGetTask";
    private static final String URL = "http://52.34.9.132/create-event";
    private static final String query = "";
    //private String response = "";
    private String BOUNDARY = java.util.UUID.randomUUID ( ).toString ( ) ;
    private String PREFIX = "--" , LINEND = "\r\n" ;
    private String MULTIPART_FROM_DATA = "multipart/form-data" ;
    private String CHARSET = "UTF-8" ;

    protected String doInBackground(Event... para1) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
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

    private String getPostDataStringNew(HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder ( ) ;
        for ( Map.Entry < String , String > entry : params.entrySet ( ) ) {
            System.out.println("??????????????????" + entry.getValue());
            sb.append ( PREFIX ) ;
            sb.append ( BOUNDARY ) ;
            sb.append ( LINEND ) ;
            sb.append ( "Content-Disposition: form-data; name=\""
                    + entry.getKey ( ) + "\"" + LINEND ) ;
            sb.append ( "Content-Type: text/plain; charset="
                    + CHARSET + LINEND ) ;
            sb.append ( "Content-Transfer-Encoding: 8bit" + LINEND ) ;
            sb.append ( LINEND ) ;
            sb.append ( entry.getValue ( ) ) ;
            sb.append ( LINEND ) ;
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(sb.toString());
        String la = sb.toString();
        System.out.println(la);
        /*
        DataOutputStream outStream = new DataOutputStream (
                conn.getOutputStream ( ) ) ;
        outStream.write ( sb.toString ( ).getBytes ( ) ) ;
        */
        return sb.toString();
    }

    private String getPostFileString(HashMap<String, File> files) throws UnsupportedEncodingException {
        for ( Map.Entry < String , File > file : files.entrySet ()) {
            String BOUNDARY = java.util.UUID.randomUUID().toString() ;
            String PREFIX = "--" , LINEND = "\r\n" ;
            String MULTIPART_FROM_DATA = "multipart/form-data" ;
            String CHARSET = "UTF-8" ;
            StringBuilder sb1 = new StringBuilder ( ) ;
            sb1.append (PREFIX) ;
            sb1.append (BOUNDARY) ;
            sb1.append (LINEND) ;
            sb1.append ( "Content-Disposition: form-data; name=\"file\"; filename=\""
                    + file.getKey( ) + "\"" + LINEND ) ;
            sb1.append ( "Content-Type: application/octet-stream; charset="
                    + CHARSET + LINEND ) ;
            sb1.append ( LINEND ) ;
            return sb1.toString();
            //outStream.write ( sb1.toString ( ).getBytes ( ) ) ;

            /*
            InputStream is = new FileInputStream(
                    file.getValue ( ) ) ;
            byte [ ] buffer = new byte [ 1024 ] ;
            int len = 0 ;
            while ( ( len = is.read ( buffer ) ) != - 1 )
            {
                outStream.write ( buffer , 0 , len ) ;
            }

            is.close ( ) ;
            outStream.write ( LINEND.getBytes ( ) ) ;
            */
        }
        return null;
    }
}
