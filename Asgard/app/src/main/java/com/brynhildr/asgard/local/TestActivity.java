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

        /**
         *
         * Usage: get events from remote
         *
         * new GetEventsFromRemote().execute();
         */

        /**
         *
         * Usage: Launch event to remote
         *
         * Event event = new Event();
         * event.setCOLUMN_NAME_DESCRIPTION("DESCRIPTION TEST").setCOLUMN_NAME_DATEANDTIME("1449878400")
         *       .setCOLUMN_NAME_DRESS_CODE("DRESS CODE").setCOLUMN_NAME_EVENT_NAME("KTV")
         *       .setCOLUMN_NAME_LAUNCHER_ID("test").setCOLUMN_NAME_MAX_PEOPLE("7")
         *       .setCOLUMN_NAME_VENUE("huoguo").setCOLUMN_NAME_TARGET("humans")
         *       .setCOLUMN_NAME_POSTER("/storage/emulated/0/DCIM/Camera/burger_king_icon.png");
         * new CreateEventToRemote().execute(event);
         */


        /**
         * Usage: get relations from remote
         *
         * new GetRelationsFromRemote().execute();
         */

        /**
         * Usage: download and display image from remote
         *
         * try {
         *    Bitmap posterBitmap = new DownloadImageFromRemote().execute("media/poster1.jpg").get();
         *    System.out.println("Bitmap got!");
         *    imageView.setImageBitmap(posterBitmap);
         * } catch (Exception e) {
         *    e.printStackTrace();
         * }
         */

        /**
         * Usage: Login to remote.
         * Important: After login successfully, the SimplifiedUserAuthentication info should be revised.
         *
         * boolean loginSucceeded =  new AuthenticationWithRemote().execute("test", "test").get();
         */

    }

}
