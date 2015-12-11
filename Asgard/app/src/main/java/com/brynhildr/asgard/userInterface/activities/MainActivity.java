package com.brynhildr.asgard.userInterface.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.local.GetEventsFromRemote;
import com.brynhildr.asgard.userInterface.fragments.AppIntroFragment;
import com.brynhildr.asgard.userInterface.fragments.EventsGoingFragment;
import com.brynhildr.asgard.userInterface.fragments.EventsHostingFragment;
import com.brynhildr.asgard.userInterface.fragments.LaunchEventFragment;
import com.brynhildr.asgard.userInterface.fragments.ManageEventsFragment;
import com.brynhildr.asgard.userInterface.fragments.ProfileFragment;
import com.brynhildr.asgard.userInterface.fragments.ViewEventsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        ViewEventsFragment.OnFragmentInteractionListener,
        LaunchEventFragment.OnFragmentInteractionListener,
        AppIntroFragment.OnFragmentInteractionListener,
        EventsGoingFragment.OnFragmentInteractionListener,
        EventsHostingFragment.OnFragmentInteractionListener,
        ManageEventsFragment.OnFragmentInteractionListener {

    private FragmentManager fm;
    private FragmentTransaction transaction;
    private ViewEventsFragment mViewEvent = new ViewEventsFragment();
    private ProfileFragment mProfile = new ProfileFragment();
    private LaunchEventFragment mLaunchEvent = new LaunchEventFragment();
    private AppIntroFragment mIntro = new AppIntroFragment();
    private ManageEventsFragment mManageEvent = new ManageEventsFragment();
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.id_content, mViewEvent);
        transaction.add(R.id.id_content, mProfile);
        transaction.add(R.id.id_content, mLaunchEvent);
        transaction.add(R.id.id_content, mIntro);
        transaction.add(R.id.id_content, mManageEvent);
        transaction.show(mViewEvent);
        transaction.hide(mProfile);
        transaction.hide(mLaunchEvent);
        transaction.hide(mIntro);
        transaction.hide(mManageEvent);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
//        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction = fm.beginTransaction();
        int id = item.getItemId();
        if (id == R.id.nav_view_profile) {
            transaction.show(mProfile);
            transaction.hide(mViewEvent);
            transaction.hide(mLaunchEvent);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_view_events) {
            new GetEventsFromRemote().execute();
            transaction.show(mViewEvent);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_launch_events) {
            transaction.show(mLaunchEvent);
            transaction.hide(mProfile);
            transaction.hide(mViewEvent);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_manage_events) {
            new GetEventsFromRemote().execute();
            transaction.show(mManageEvent);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mViewEvent);
            transaction.hide(mIntro);
        } else if (id == R.id.nav_intro) {
            transaction.show(mIntro);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mViewEvent);
            transaction.hide(mManageEvent);
        } else if (id == R.id.logout) {
            finish();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        transaction.commit();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        //super.onSaveInstanceState(outState);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    public ViewEventsFragment getmViewEvent() {
        return mViewEvent;
    }

    public LaunchEventFragment getmLaunchEvent() {
        return mLaunchEvent;
    }

    public ManageEventsFragment getmManageEvent() {
        return mManageEvent;
    }
}
