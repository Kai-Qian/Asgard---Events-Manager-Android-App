package com.brynhildr.asgard.userInterface.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.brynhildr.asgard.databaseLayout.events.EventDatabase;
import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.ViewEventAdapter;
import com.brynhildr.asgard.local.EventWithID;
import com.brynhildr.asgard.local.GetEventsFromRemote;
import com.brynhildr.asgard.userInterface.activities.MainActivity;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ViewEventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;

    private ViewEventAdapter viewEventAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private ArrayList<EventWithID> eventTmp;

    private DrawerLayout drawer;

    private EventDatabase edb;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */

    // TODO: Rename and change types of parameters
    public static ViewEventsFragment newInstance(String param1, String param2) {
        ViewEventsFragment fragment = new ViewEventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ViewEventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edb = new EventDatabase(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("ViewEventsonCreateView");
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        System.out.println("ViewEventsonAttach");
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
        System.out.println("ViewEventsononActivityCreated");
        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.id_swiperefreshlayout_view);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.input, R.color.black_overlay);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_view);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((MainActivity)getActivity());
    }

    @Override
    public void onResume() {
        System.out.println("ViewEventsonResume");
        super.onResume();
        edb = new EventDatabase(getActivity());
        eventTmp = edb.readRowWithID();
        ArrayList<EventWithID> event = new ArrayList<EventWithID>(eventTmp.size());
        for (int i = eventTmp.size() - 1; i >= 0; i--) {
            event.add(eventTmp.get(i));
        }
        mRecyclerView = (RecyclerView) ((MainActivity)getActivity()).findViewById(R.id.viewEventlist_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        viewEventAdapter = new ViewEventAdapter(getActivity(), event);
        mRecyclerView.setAdapter(viewEventAdapter);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout_view);
        mCollapsingToolbarLayout.setTitle("View Events");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        ImageView headImage = (ImageView) getActivity().findViewById(R.id.backdrop_view);
        headImage.setImageResource(R.drawable.poster3);
    }

    @Override
    public void onDestroy() {
        System.out.println("ViewEventsonDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        System.out.println("ViewEventsonDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        System.out.println("ViewEventsonStop");
        super.onStop();
    }

    @Override
    public void onPause() {
        System.out.println("ViewEventsonPause");
        super.onPause();
    }

    @Override
    public void onStart() {
        System.out.println("ViewEventsonStart");
        super.onStart();
    }

    @Override
    public void onDetach() {
        System.out.println("ViewEventsonDetach");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new GetEventsFromRemote().execute();
                mSwipeRefreshLayout.setRefreshing(false);
                FragmentManager fm = ((MainActivity) getActivity()).getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                ViewEventsFragment mViewEvent = ((MainActivity) getActivity()).getmViewEvent();
                transaction.detach(mViewEvent);
                transaction.attach(mViewEvent);
                transaction.commit();
            }
        }, 1000);
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
        public void onFragmentInteraction(String id);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.view_event_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.newToOld) {
            viewEventAdapter.sortNewToOld();
            return true;
        } else if(id == R.id.oldToNew) {
            viewEventAdapter.sortOldToNew();
            return true;
        } else if(id == R.id.modified) {
            viewEventAdapter.sortModified();
            return true;
        } else if(id == R.id.list_view) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(viewEventAdapter);
            return true;
        } else if(id == R.id.grid_view) {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(viewEventAdapter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}