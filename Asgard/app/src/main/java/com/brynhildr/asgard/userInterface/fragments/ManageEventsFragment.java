package com.brynhildr.asgard.userInterface.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.userInterface.activities.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageEventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageEventsFragment extends Fragment {
    private ViewPager m_vp;
    private EventsGoingFragment mfragment1;
    private EventsHostingFragment mfragment2;
    private ArrayList<Fragment> fragmentList;
    ArrayList<String>   titleList    = new ArrayList<String>();
    private PagerTabStrip pagerTabStrip;

    private PagerTitleStrip pagerTitleStrip;
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
     * @return A new instance of fragment ManageEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageEventsFragment newInstance(String param1, String param2) {
        ManageEventsFragment fragment = new ManageEventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ManageEventsFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_events, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        m_vp = (ViewPager) getActivity().findViewById(R.id.viewpager);

        pagerTabStrip=(PagerTabStrip) getActivity().findViewById(R.id.pagertab);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.toolbar));
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            pagerTitleStrip=(PagerTitleStrip) findViewById(R.id.pagertab);
//            pagerTitleStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));

        mfragment1 = new EventsGoingFragment();
        mfragment2 = new EventsHostingFragment();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(mfragment1);
        fragmentList.add(mfragment2);

        titleList.add("Going");
        titleList.add("Hosting");

        m_vp.setAdapter(new MyViewPagerAdapter(getFragmentManager()));
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_manage);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Manage Events");
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(((MainActivity)getActivity()));
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return titleList.get(position);
        }
    }

}
