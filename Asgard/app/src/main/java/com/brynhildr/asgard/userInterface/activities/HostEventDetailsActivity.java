package com.brynhildr.asgard.userInterface.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.EventDetailsAdapter;
import com.brynhildr.asgard.entities.EventTitleAndDetail;
import com.brynhildr.asgard.local.DownloadImageFromRemote;
import com.brynhildr.asgard.local.EventWithID;

import java.io.IOException;
import java.util.ArrayList;

public class HostEventDetailsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private EventDetailsAdapter eventDetailsAdapter;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private EventWithID event;

    private static int outputX = 300;
    private static int outputY = 200;

    private ImageView headImage = null;

    private String posterName = "poster";


    private ArrayList<EventTitleAndDetail> eventTitleAndDetail = new ArrayList<EventTitleAndDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event_details);
        Intent intent = getIntent();
        event = (EventWithID) intent.getSerializableExtra("Event");
        EventDetailsAdapter.setOriginalPath(event.getCOLUMN_NAME_POSTER());
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
        try {
            Bitmap posterBitmap = new DownloadImageFromRemote().execute(event.getCOLUMN_NAME_POSTER()).get();
            headImage.setImageBitmap(posterBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(event.getCOLUMN_NAME_EVENT_NAME());
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("LaunchEventonResume");
        eventTitleAndDetail.clear();
        eventTitleAndDetail.add(new EventTitleAndDetail("Event Name", event.getCOLUMN_NAME_EVENT_NAME()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Date & Time", event.getCOLUMN_NAME_DATEANDTIME()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Address", event.getCOLUMN_NAME_VENUE()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Dress Code", event.getCOLUMN_NAME_DRESS_CODE()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Target Participant", event.getCOLUMN_NAME_TARGET()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Maximum People", event.getCOLUMN_NAME_MAX_PEOPLE()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Description", event.getCOLUMN_NAME_DESCRIPTION()));
        mRecyclerView = (RecyclerView) findViewById(R.id.hostEventlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eventDetailsAdapter = new EventDetailsAdapter(this, eventTitleAndDetail, event.getEventID());
        mRecyclerView.setAdapter(eventDetailsAdapter);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setTitle("Edit Event");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        headImage = (ImageView) findViewById(R.id.backdrop_details);

        headImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventDetailsAdapter.dialog();
            }});
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case 1:
                //Reference: http://stackoverflow.com/questions/15807766/android-crop-image-size
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(data.getData(), "image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 3);
                intent.putExtra("aspectY", 2);
                intent.putExtra("outputX", outputX);
                intent.putExtra("outputY", outputY);
                intent.putExtra("return-data", false);
                intent.putExtra("scale", true);
                intent.putExtra("scaleUpIfNeeded", true);
                startActivityForResult(intent, 2);
                break;
            case 2:
                EventDetailsAdapter.setPath(getPath(data.getData()));
                try {
                    //Reference: http://stackoverflow.com/questions/9638455/uri-returned-after-action-get-content-from-gallery-is-not-working-in-setimageuri
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                    Matrix matrix = new Matrix();
                    matrix.postScale(1.5f, 1.5f);
                    Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    headImage.setImageBitmap(bit);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //Reference: http://stackoverflow.com/questions/20856601/how-to-get-path-of-a-captured-image-in-android
    public String getPath(Uri mUri) {
        Cursor cursor = this.managedQuery(mUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column);
        return path;
    }

}
