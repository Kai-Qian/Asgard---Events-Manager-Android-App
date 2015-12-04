package com.brynhildr.asgard.local;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.entities.User;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.testView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        /******************************************************************
         *
         * Usage: get events from remote
         * @para: none
         * @return: none
         *
         * new GetEventsFromRemote().execute();
         ******************************************************************/


        /******************************************************************
         *
         * Usage: Launch event to remote
         * @para: none
         * @return: none
         *
         * Event event = new Event();
         * event.setCOLUMN_NAME_DESCRIPTION("DESCRIPTION TEST").setCOLUMN_NAME_DATEANDTIME("1449878400")
         *       .setCOLUMN_NAME_DRESS_CODE("DRESS CODE").setCOLUMN_NAME_EVENT_NAME("KTV")
         *       .setCOLUMN_NAME_LAUNCHER_ID("test").setCOLUMN_NAME_MAX_PEOPLE("7")
         *       .setCOLUMN_NAME_VENUE("huoguo").setCOLUMN_NAME_TARGET("humans")
         *       .setCOLUMN_NAME_POSTER("/storage/emulated/0/DCIM/Camera/burger_king_icon.png");
         * new CreateEventToRemote().execute(event);
         *
         ******************************************************************/
        Event event = new Event();
        event.setCOLUMN_NAME_DESCRIPTION("DESCRIPTION TEST").setCOLUMN_NAME_DATEANDTIME("1449878400")
                .setCOLUMN_NAME_DRESS_CODE("DRESS CODE").setCOLUMN_NAME_EVENT_NAME("KTV")
                .setCOLUMN_NAME_LAUNCHER_ID("test").setCOLUMN_NAME_MAX_PEOPLE("7")
                .setCOLUMN_NAME_VENUE("huoguo").setCOLUMN_NAME_TARGET("humans")
                .setCOLUMN_NAME_POSTER("/storage/emulated/0/DCIM/Camera/burger_king_icon.png");
        new CreateEventToRemote().execute(event);


        /******************************************************************
         *
         * Usage: get relations from remote
         * @para: none
         * @return: none
         *
         * new GetRelationsFromRemote().execute();
         *
         ******************************************************************/


        /******************************************************************
         *
         * Usage: download and display image from remote
         * @para: string, the url of image.
         *        e.g. event.getCOLUMN_NAME_POSTER()
         * @return: none
         *
         * try {
         *     Bitmap posterBitmap = new DownloadImageFromRemote().execute("media/poster1.jpg").get();
         *     System.out.println("Bitmap got!");
         *     imageView.setImageBitmap(posterBitmap);
         * } catch (Exception e) {
         *     e.printStackTrace();
         * }
         *
         ******************************************************************/


        /******************************************************************
         *
         * Usage: Login to remote. A toast will be shown.
         * @Important: After login successfully, the SimplifiedUserAuthentication info should be revised.
         *             Which means, this function can only be called in SimplifiedUserAuthentication.java
         *             The skeleton has been built in SimplifiedUserAuthentication.java
         *             TODO: Handle cases where login failed in SimplifiedUserAuthentication.java
         * @para: username, password
         * @return: boolean
         *
         * try {
         *     boolean loginSucceeded = new AuthenticationWithRemote().execute("test", "test").get();
         * } catch (Exception e) {
         *     e.printStackTrace();
         * }
         *
         ******************************************************************/


        /******************************************************************
         *
         * Usage: register user to remote. A toast will be shown.
         * @para: User
         * @return: none or string (depends on how to call it. Examples are given below.)
         *
         * The return string will be one of the following:
         * 1. Failed
         * 2. This username has been registered.
         * 3. Register Succeeded
         *
         * User user = new User("test2", "test@test.com", "123", "test", "male");
         * try {
         *     String registerUserInfo = new RegisterUserToRemote().execute(user).get();
         *     System.out.println(registerInfo);
         * } catch (Exception e) {
         *     e.printStackTrace();
         * }
         *
         * or new RegisterUserToRemote().execute(user);
         *
         ******************************************************************/


        /******************************************************************
         *
         * Usage: new RegisterEventToRemote().execute(event_id, username);
         * @para: event_id, username
         * @return: none or string (depends on how to call it. Examples are given below.)
         *
         * The return string will be one of the following:
         * 1. You have already registered.
         * 2. OK
         *
         * try {
         *     String registerEventInfo = new RegisterEventToRemote().execute("1", "test").get();
         * } catch (Exception e) {
         *     e.printStackTrace();
         * }
         *
         * or  new RegisterEventToRemote().execute("2", "test");
         *
         ******************************************************************/


        /******************************************************************
         *
         * Usage: new UnregisterEventToRemote().execute(event_id, username);
         * @para: event_id, username
         * @return: none or string (depends on how to call it. Examples are given below.)
         *
         * The return string will be one of the following:
         * 1. You have not registered.
         * 2. OK
         *
         * try {
         *     String unregisterEventInfo = new UnregisterEventToRemote().execute("2", "test").get();
         * } catch (Exception e) {
         *    e.printStackTrace();
         * }
         *
         * or new UnregisterEventToRemote().execute("1", "test");
         *
         ******************************************************************/


        /******************************************************************
         *
         * Usage: Update event to remote
         * @para: none
         * @return: none
         *
         * EventWithID eventWithID = new EventWithID();
         * new UpdateEventToRemote().execute(eventWithID);
         *
         ******************************************************************/
        EventWithID eventWithID = new EventWithID();
        eventWithID.setID("3");
        eventWithID.setCOLUMN_NAME_DESCRIPTION("DESCRIPTION TEST!!!!").setCOLUMN_NAME_DATEANDTIME("1449878400")
                    .setCOLUMN_NAME_DRESS_CODE("DRESS CODE!!!").setCOLUMN_NAME_EVENT_NAME("KTV!!")
                    .setCOLUMN_NAME_LAUNCHER_ID("test!!!!").setCOLUMN_NAME_MAX_PEOPLE("7!!")
                    .setCOLUMN_NAME_VENUE("huoguo!!!").setCOLUMN_NAME_TARGET("humans!!!")
                    .setCOLUMN_NAME_POSTER("/storage/emulated/0/DCIM/Camera/burger_king_icon.png");
        new UpdateEventToRemote().execute(eventWithID);
    }

}
