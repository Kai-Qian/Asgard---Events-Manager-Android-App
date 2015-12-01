package com.brynhildr.asgard.local;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.Event;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //new GetEventsFromRemote().execute();


        Event event = new Event();
        event.setCOLUMN_NAME_DESCRIPTION("DESCRIPTION TEST").setCOLUMN_NAME_DATEANDTIME("1449878400")
                .setCOLUMN_NAME_DRESS_CODE("DRESS CODE").setCOLUMN_NAME_EVENT_NAME("KTV")
                .setCOLUMN_NAME_LAUNCHER_ID("test").setCOLUMN_NAME_MAX_PEOPLE("7")
                .setCOLUMN_NAME_VENUE("huoguo").setCOLUMN_NAME_TARGET("humans");


        new CreateEventToRemoteV2().execute(event);

    }

}
