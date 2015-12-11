package com.brynhildr.asgard.userInterface.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.brynhildr.asgard.dblayout.events.EventDatabase;
import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entity.entitiesAdapers.ManageEventAdapter;
import com.brynhildr.asgard.local.EventWithID;
import com.brynhildr.asgard.local.GetRegisteredEvents;
import com.brynhildr.asgard.userInterface.activities.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsGoingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsGoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsGoingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;

    private ManageEventAdapter manageEventAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private EventDatabase edb;

    private ArrayList<EventWithID> eventTmp;

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
     * @return A new instance of fragment EventsGoingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsGoingFragment newInstance(String param1, String param2) {
        EventsGoingFragment fragment = new EventsGoingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventsGoingFragment() {
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
        return inflater.inflate(R.layout.fragment_events_going, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.id_swiperefreshlayout_going);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.input, R.color.black_overlay);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                FragmentManager fm = ((MainActivity) getActivity()).getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                EventsGoingFragment mEventsGoing = ((MainActivity) getActivity()).getmManageEvent().getMfragment1();
                transaction.detach(mEventsGoing);
                transaction.attach(mEventsGoing);
                transaction.commit();
            }
        }, 1000);
    }


    @Override
    public void onResume() {
        super.onResume();
        eventTmp = new GetRegisteredEvents().getRegisteredEvents();
        ArrayList<EventWithID> event = new ArrayList<EventWithID>(eventTmp.size());
        for (int i = eventTmp.size() - 1; i >= 0; i--) {
            event.add(eventTmp.get(i));
        }
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.viewEventlist_going);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        manageEventAdapter = new ManageEventAdapter(getActivity(), event);
        mRecyclerView.setAdapter(manageEventAdapter);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.manage_event_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.newToOld_manage) {
            manageEventAdapter.sortNewToOld();
            return true;
        } else if(id == R.id.oldToNew_manage) {
            manageEventAdapter.sortOldToNew();
            return true;
        } else if(id == R.id.modified_manage) {
            manageEventAdapter.sortModified();
            return true;
        } else if(id == R.id.list_manage) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(manageEventAdapter);
            return true;
        } else if(id == R.id.grid_manage) {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(manageEventAdapter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
