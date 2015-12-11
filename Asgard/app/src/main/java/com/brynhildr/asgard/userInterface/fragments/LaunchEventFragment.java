package com.brynhildr.asgard.userInterface.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.EventTitle;
import com.brynhildr.asgard.entities.LaunchEventAdapter;
import com.brynhildr.asgard.userInterface.activities.MainActivity;

import java.io.IOException;
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

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private static int outputX = 300;
    private static int outputY = 200;

    private ImageView headImage = null;


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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launch_event, container, false);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
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
                LaunchEventAdapter.setPath(getPath(data.getData()));
                try {
                    //Reference: http://stackoverflow.com/questions/9638455/uri-returned-after-action-get-content-from-gallery-is-not-working-in-setimageuri
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

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
        eventTitles.clear();
        eventTitles.add(new EventTitle("Event Name"));
        eventTitles.add(new EventTitle("Date & Time"));
        eventTitles.add(new EventTitle("Address"));
        eventTitles.add(new EventTitle("Dress Code"));
        eventTitles.add(new EventTitle("Target Participant"));
        eventTitles.add(new EventTitle("Maximum People"));
        eventTitles.add(new EventTitle("Description"));
        eventTitles.add(new EventTitle("Register"));
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.launchEventlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        launchEventAdapter = new LaunchEventAdapter(getActivity(), eventTitles);
        mRecyclerView.setAdapter(launchEventAdapter);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout_launch);
        mCollapsingToolbarLayout.setTitle("Launch Event");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        headImage = (ImageView) getActivity().findViewById(R.id.backdrop_launch);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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


    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.launch_event_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    //Reference: http://stackoverflow.com/questions/20856601/how-to-get-path-of-a-captured-image-in-android
    public String getPath(Uri mUri) {
        Cursor cursor = getActivity().managedQuery(mUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column);
        return path;
    }
}
