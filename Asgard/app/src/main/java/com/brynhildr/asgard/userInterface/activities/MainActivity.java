package com.brynhildr.asgard.userInterface.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.userInterface.fragments.AppIntroFragment;
import com.brynhildr.asgard.userInterface.fragments.EventsGoingFragment;
import com.brynhildr.asgard.userInterface.fragments.EventsHostingFragment;
import com.brynhildr.asgard.userInterface.fragments.GeneralPreferenceFragment;
import com.brynhildr.asgard.userInterface.fragments.InboxFragment;
import com.brynhildr.asgard.userInterface.fragments.LaunchEventFragment;
import com.brynhildr.asgard.userInterface.fragments.ManageEventsFragment;
import com.brynhildr.asgard.userInterface.fragments.ProfileFragment;
import com.brynhildr.asgard.userInterface.fragments.ViewEventsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener,
        ViewEventsFragment.OnFragmentInteractionListener,
        LaunchEventFragment.OnFragmentInteractionListener,
        InboxFragment.OnFragmentInteractionListener,
        AppIntroFragment.OnFragmentInteractionListener,
        GeneralPreferenceFragment.OnFragmentInteractionListener,
        EventsGoingFragment.OnFragmentInteractionListener,
        EventsHostingFragment.OnFragmentInteractionListener,
        ManageEventsFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private ViewEventsFragment mViewEvent = new ViewEventsFragment();
    private ProfileFragment mProfile = new ProfileFragment();
    private LaunchEventFragment mLaunchEvent = new LaunchEventFragment();
    private InboxFragment mInbox = new InboxFragment();
    private GeneralPreferenceFragment mPreference = new GeneralPreferenceFragment();
    private AppIntroFragment mIntro = new AppIntroFragment();
    private ManageEventsFragment mManageEvent = new ManageEventsFragment();

    private boolean previousIsManage = false;

    public int getNum() {

        num++;
        System.out.println("num--->" + num);
        return num;
    }

    private int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.id_content, mViewEvent);

        transaction.add(R.id.id_content, mProfile);
        transaction.add(R.id.id_content, mLaunchEvent);
        transaction.add(R.id.id_content, mInbox);
        transaction.add(R.id.id_content, mPreference);
        transaction.add(R.id.id_content, mIntro);
        transaction.add(R.id.id_content, mManageEvent);
//        transaction.add(R.id.id_content, mEventHosting);
        transaction.show(mViewEvent);
        transaction.hide(mProfile);
        transaction.hide(mLaunchEvent);
        transaction.hide(mInbox);
        transaction.hide(mPreference);
        transaction.hide(mIntro);
        transaction.hide(mManageEvent);
//        transaction.hide(mEventHosting);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            finish();
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, LoginActivity.class);
//            MainActivity.this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();


        if (id == R.id.nav_view_profile) {
            transaction.show(mProfile);
            transaction.hide(mViewEvent);
            transaction.hide(mLaunchEvent);
            transaction.hide(mInbox);
            transaction.hide(mPreference);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_view_events) {
            transaction.show(mViewEvent);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mInbox);
            transaction.hide(mPreference);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_launch_events) {
            transaction.show(mLaunchEvent);
            transaction.hide(mProfile);
            transaction.hide(mViewEvent);
            transaction.hide(mInbox);
            transaction.hide(mPreference);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_manage_events) {
            transaction.show(mManageEvent);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mInbox);
            transaction.hide(mViewEvent);
            transaction.hide(mIntro);
            transaction.hide(mPreference);
        } else if (id == R.id.nav_inbox) {
            transaction.show(mInbox);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mViewEvent);
            transaction.hide(mPreference);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_settings) {
            transaction.show(mPreference);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mInbox);
            transaction.hide(mViewEvent);
            transaction.hide(mIntro);
            transaction.hide(mManageEvent);
        } else if (id == R.id.nav_intro) {
            transaction.show(mIntro);
            transaction.hide(mProfile);
            transaction.hide(mLaunchEvent);
            transaction.hide(mInbox);
            transaction.hide(mPreference);
            transaction.hide(mViewEvent);
            transaction.hide(mManageEvent);
        } else if (id == R.id.logout) {
            finish();
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, LoginActivity.class);
//            MainActivity.this.startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        transaction.commit();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
//    public class MyViewPagerAdapter extends FragmentPagerAdapter {
//        public MyViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//            // TODO Auto-generated constructor stub
//        }
//
//        @Override
//        public Fragment getItem(int arg0) {
//            return fragmentList.get(arg0);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // TODO Auto-generated method stub
//            return titleList.get(position);
//        }
//    }
}
