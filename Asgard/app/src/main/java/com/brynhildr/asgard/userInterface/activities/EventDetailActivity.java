package com.brynhildr.asgard.userInterface.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.Event;

public class EventDetailActivity extends AppCompatActivity {

    private TextView dateAndTime;
    private TextView address;
    private TextView dressCode;
    private TextView targetParticipant;
    private TextView maximumPeople;
    private TextView description;
    private ImageButton mapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        final Event event = (Event) intent.getSerializableExtra("Event");
        dateAndTime = (TextView) findViewById(R.id.dateandtime);
        address = (TextView) findViewById(R.id.address);
        dressCode = (TextView) findViewById(R.id.dresscode);
        targetParticipant = (TextView) findViewById(R.id.targetParticipant);
        maximumPeople = (TextView) findViewById(R.id.maximumPeople);
        description = (TextView) findViewById(R.id.description);
        mapBtn = (ImageButton) findViewById(R.id.mapbutton);
        mapBtn.setImageResource(R.drawable.ic_map);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("Event", event);
                intent.setClass(EventDetailActivity.this, MapsActivity.class);
                EventDetailActivity.this.startActivity(intent);
            }
        });
        dateAndTime.setText(event.getCOLUMN_NAME_DATEANDTIME());
        address.setText(event.getCOLUMN_NAME_VENUE());
        dressCode.setText(event.getCOLUMN_NAME_DRESS_CODE());
        targetParticipant.setText(event.getCOLUMN_NAME_TARGET());
        maximumPeople.setText(event.getCOLUMN_NAME_MAX_PEOPLE());
        description.setText(event.getCOLUMN_NAME_DESCRIPTION());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView headImage = (ImageView) findViewById(R.id.backdrop_details);
        headImage.setImageResource(R.drawable.poster2);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(event.getCOLUMN_NAME_EVENT_NAME());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
    }
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailActivity.this);
        builder.setMessage("Are you sure you want to register this event？");
        builder.setTitle("Confirmation");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
