package com.brynhildr.asgard.userInterface.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.EventTitle;
import com.brynhildr.asgard.entities.LaunchEventAdapter;
import com.brynhildr.asgard.userInterface.activities.MainActivity;
import com.brynhildr.asgard.userInterface.dummy.ChoosePic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LaunchEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction eventTitles.
 * Use the {@link LaunchEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchEventFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private LaunchEventAdapter launchEventAdapter;

    private ChoosePic choosePic;

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


    private ArrayList<EventTitle> eventTitles = new ArrayList<EventTitle>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LaunchEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaunchEventFragment newInstance(String param1, String param2) {
        LaunchEventFragment fragment = new LaunchEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LaunchEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        edb = new EventDatabase(getActivity());
//        edb.deleteTable();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launch_event, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_launch);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((MainActivity) getActivity());
    }
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == getActivity().RESULT_CANCELED) {
            Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "没有SDCard!", Toast.LENGTH_LONG)
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("LaunchEventonResume");
        edb = new EventDatabase(getActivity());
        eventTitles.clear();
//        edb = new EventDatabase(getActivity());
//        edb.deleteTable();
//        launchbtn = (Button)getActivity().findViewById(R.id.launchbtn);
//        cancelbtn = (Button)getActivity().findViewById(R.id.cancelbtn);
        eventTitles.add(new EventTitle("Event Name"));
        eventTitles.add(new EventTitle("Date & Time"));
        eventTitles.add(new EventTitle("Address"));
        eventTitles.add(new EventTitle("Dress Code"));
        eventTitles.add(new EventTitle("Target Participant"));
        eventTitles.add(new EventTitle("Maximum People"));
        eventTitles.add(new EventTitle("Description"));
        //getActivity().getActionBar().setTitle("View Events");
        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.launchEventlist);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
//        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        launchEventAdapter = new LaunchEventAdapter(getActivity(), eventTitles);
        System.out.println("launchEventAdapter.getItemCount()" + launchEventAdapter.getItemCount());

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(launchEventAdapter);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout_launch);
        mCollapsingToolbarLayout.setTitle("Launch Event");
        mCollapsingToolbarLayout.setBackground(null);
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        headImage = (ImageView) getActivity().findViewById(R.id.backdrop_launch);
        headImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //实例化SelectPicPopupWindow
                choosePic = new ChoosePic(getActivity(), itemsOnClick);
                //显示窗口
                choosePic.showAtLocation(getActivity().findViewById(R.id.launch), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.launchfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> eventDetails = new ArrayList<String>();
                                ArrayList<LaunchEventAdapter.ViewHolderForLaunch> tmp = launchEventAdapter.getmViewHolderForLaunch();
                                for (LaunchEventAdapter.ViewHolderForLaunch i : tmp) {
                                    System.out.println("Input" + i.getmEditText().getText().toString());
                                    eventDetails.add(i.getmEditText().getText().toString());
                                }
                                eventDetails.add(posterName + ((MainActivity)getActivity()).getNum());
                                edb.insertRow(eventDetails);
                                FragmentManager fm = ((MainActivity)getActivity()).getFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                LaunchEventFragment mLaunchEvent = new LaunchEventFragment();
                                transaction.detach(mLaunchEvent);
                                transaction.attach(mLaunchEvent);
                                transaction.commit();
                                Snackbar snackbar = Snackbar.make(v, "ActionClick", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }).show();
            }});
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            choosePic.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    choseHeadImageFromCameraCapture();
                    break;
                case R.id.btn_pick_photo:
                    choseHeadImageFromGallery();
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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

            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));

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
}
