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
public class CreateEventToRemote extends AsyncTask<Event, Integer, String> {

    private static final String TAG = "HttpGetTask";
    private static final String URL = "http://52.34.9.132/create-event";
    private static final String query = "";
    private String response = "";
    private String BOUNDARY = java.util.UUID.randomUUID ( ).toString ( ) ;
    private String PREFIX = "--" , LINEND = "\r\n" ;
    private String MULTIPART_FROM_DATA = "multipart/form-data" ;
    private String CHARSET = "UTF-8" ;

    protected String doInBackground(Event... para1) {
        try {

            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(URL)
                    .openConnection();
            httpUrlConnection.setReadTimeout(15000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("HTTP_X_SKIP_CSRF", "True");
            //httpUrlConnection.setRequestProperty("Content-type", "multipart/form-data");
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

            //HashMap< String, File > files = new HashMap<>();
            //files.put("tempAndroid.mp3" , new File()) ;
            /**************************************************************/

            /*
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 4;
            options.inPurgeable = true;
            Bitmap bm = BitmapFactory.decodeFile("res/drawable/asgard.png", options);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
            // bitmap object
            byte [] byteImage_photo = baos.toByteArray();

            //generate base64 string of image

            String encodedImage =Base64.encodeToString(byteImage_photo, Base64.DEFAULT);
            postDataParams.put("picture", encodedImage);
            */
            /***************************************************************/

            System.out.println(getPostDataStringNew(postDataParams));

            writer.write(getPostDataStringNew(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = httpUrlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response += line;
                }
            }
            else {
                response = "";
            }
            System.out.println(responseCode + " " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
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
