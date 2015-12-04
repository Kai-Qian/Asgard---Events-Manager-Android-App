package com.brynhildr.asgard.userInterface.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.entities.EventDetailsAdapter;
import com.brynhildr.asgard.entities.EventTitleAndDetail;
import com.brynhildr.asgard.local.DownloadImageFromRemote;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HostEventDetailsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private EventDetailsAdapter eventDetailsAdapter;

    private Event event;

    private Button launchbtn;
    private Button cancelbtn;
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 300;
    private static int output_Y = 200;

    private ImageView headImage = null;
//    private ImageView headImage2 = null;

    private EventDatabase edb;

    private String posterName = "poster";


    private ArrayList<EventTitleAndDetail> eventTitleAndDetail = new ArrayList<EventTitleAndDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event_details);
        Intent intent = getIntent();
        event = (Event) intent.getSerializableExtra("Event");
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
//        headImage.setImageResource(R.drawable.poster2);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(event.getCOLUMN_NAME_EVENT_NAME());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("LaunchEventonResume");
        edb = new EventDatabase(this);
        eventTitleAndDetail.clear();
//        edb = new EventDatabase(getActivity());
//        edb.deleteTable();
//        launchbtn = (Button)getActivity().findViewById(R.id.launchbtn);
//        cancelbtn = (Button)getActivity().findViewById(R.id.cancelbtn);
        eventTitleAndDetail.add(new EventTitleAndDetail("Event Name", event.getCOLUMN_NAME_EVENT_NAME()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Date & Time", event.getCOLUMN_NAME_DATEANDTIME()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Address", event.getCOLUMN_NAME_VENUE()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Dress Code", event.getCOLUMN_NAME_DRESS_CODE()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Target Participant", event.getCOLUMN_NAME_TARGET()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Maximum People", event.getCOLUMN_NAME_MAX_PEOPLE()));
        eventTitleAndDetail.add(new EventTitleAndDetail("Description", event.getCOLUMN_NAME_DESCRIPTION()));
        //getActivity().getActionBar().setTitle("View Events");
        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.hostEventlist);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
//        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        eventDetailsAdapter = new EventDetailsAdapter(this, eventTitleAndDetail);
        System.out.println("eventDetailsAdapter.getItemCount()" + eventDetailsAdapter.getItemCount());

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(eventDetailsAdapter);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setTitle("Edit Event");
        mCollapsingToolbarLayout.setBackground(null);
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        headImage = (ImageView) findViewById(R.id.backdrop_details);
//        headImage.setImageDrawable(null);
//        headImage2 = (ImageView) getActivity().findViewById(R.id.imageViewlaunch2);

        headImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });

//        Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
//        buttonCamera.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                choseHeadImageFromCameraCapture();
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> eventDetails = new ArrayList<String>();
                                ArrayList<EventDetailsAdapter.ViewHolderForDetails> tmp = eventDetailsAdapter.getmViewHolderForDetail();
                                for (EventDetailsAdapter.ViewHolderForDetails i : tmp) {
                                    System.out.println("Input" + i.getmEditText().getText().toString());
                                    eventDetails.add(i.getmEditText().getText().toString());
                                }
                                eventDetails.add(posterName + 3);
                                //edb.insertRow(eventDetails);
                                Snackbar snackbar = Snackbar.make(v, "ActionClick", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }).show();
            }});
    }

    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(this, "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

    }

    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 2);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边


        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
    private Bitmap decodeUriAsBitmap(Uri uri){

        Bitmap bitmap = null;

        try {

            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            return null;

        }
        return bitmap;

    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
//        headImage2.setImageDrawable(null);
        headImage.setBackground(null);
        Uri imguri = intent.getData();
        System.out.println("imguri----->" + getPath(imguri));
        EventDetailsAdapter.setPath(getPath(imguri));
        if(imguri != null){
            Bitmap bitmap = decodeUriAsBitmap(imguri);//decode bitmap
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            Matrix matrix = new Matrix(); //接收图片之后放大 1.5倍
            matrix.postScale(1.5f, 1.5f);
            Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            headImage.setImageBitmap(bit);

        } else {
            System.out.println("imguri == null");
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    public String getPath(Uri mUri) {
        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor cursor = managedQuery(mUri, proj, null, null, null);


        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);


        cursor.moveToFirst();

        String path = cursor.getString(column_index);
        return path;

    }

//    private void dialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to update this event？");
//        builder.setTitle("Confirmation");
//        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(final DialogInterface dialog, int which) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//                        Event event = new Event();
//                        ArrayList<EventDetailsAdapter.ViewHolderForLaunch> tmp = getmViewHolderForLaunch();
//                        event.setCOLUMN_NAME_EVENT_NAME(tmp.get(0).getmEditText().getText().toString());
//                        event.setCOLUMN_NAME_DATEANDTIME(calculateTimeStamp(year, month, day, hour, minute) + "");
//                        System.out.println("event.getCOLUMN_NAME_DATEANDTIME()--->" + event.getCOLUMN_NAME_DATEANDTIME());
//                        event.setCOLUMN_NAME_VENUE(tmp.get(2).getmEditText().getText().toString());
//                        event.setCOLUMN_NAME_DRESS_CODE(tmp.get(3).getmEditText().getText().toString());
//                        event.setCOLUMN_NAME_TARGET(tmp.get(4).getmEditText().getText().toString());
//                        event.setCOLUMN_NAME_MAX_PEOPLE(tmp.get(5).getmEditText().getText().toString());
//                        event.setCOLUMN_NAME_DESCRIPTION(tmp.get(6).getmEditText().getText().toString());
//                        event.setCOLUMN_NAME_POSTER(getPath());
//                        System.out.println("getPath()--->" + getPath());
////                        event.setCOLUMN_NAME_POSTER("/storage/emulated/0/DCIM/Camera/burger_king_icon.png");
//                        event.setCOLUMN_NAME_LAUNCHER_ID(SimplifiedUserAuthentication.getUsername());
//                        CreateEventToRemote mCreateEventToRemote = new CreateEventToRemote();
//                        mCreateEventToRemote.execute(event);
//                        /*
//                        while (mCreateEventToRemote.getResponseCode() != 200 && mCreateEventToRemote.getResponseCode() != 500) {
////                            System.out.println("mCreateEventToRemote.getResponse()-->" + mCreateEventToRemote.getResponseCode());
//                        }
//                        if (mCreateEventToRemote.getResponse()) {
//                            Toast.makeText(mContext, "The event is launched successfully", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(mContext, "The event can not be launched. Maybe there is an error in the input ", Toast.LENGTH_LONG).show();
//                        }
//                        */
//                        FragmentManager fm = ((MainActivity) mContext).getFragmentManager();
//                        FragmentTransaction transaction = fm.beginTransaction();
//                        LaunchEventFragment mLaunchEvent = ((MainActivity) mContext).getmLaunchEvent();
//                        transaction.detach(mLaunchEvent);
//                        transaction.attach(mLaunchEvent);
//
//                        transaction.commit();
//
//                    }
//                }, 500);
//            }
//        });
//        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//    }
}
