package com.brynhildr.asgard.userInterface.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
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

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.entities.HostEventAdapter;
import com.brynhildr.asgard.local.GetEventsFromRemote;
import com.brynhildr.asgard.userInterface.activities.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsHostingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsHostingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsHostingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;

    private HostEventAdapter hostEventAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<Event> eventTmp;

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
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsHostingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsHostingFragment newInstance(String param1, String param2) {
        EventsHostingFragment fragment = new EventsHostingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventsHostingFragment() {
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
        return inflater.inflate(R.layout.fragment_events_hosting, container, false);
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
        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.id_swiperefreshlayout_hosting);
        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.input, R.color.black_overlay);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
        edb = new EventDatabase(getActivity());
        eventTmp = edb.readRow();
        ArrayList<Event> event = new ArrayList<Event>(eventTmp.size());
        for (int i = eventTmp.size() - 1; i >= 0; i--) {
            event.add(eventTmp.get(i));
        }
        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.viewEventlist_hosting);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
//        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        hostEventAdapter = new HostEventAdapter(getActivity(), event);
        System.out.println("HostEventAdapter.getItemCount()" + hostEventAdapter.getItemCount());

        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(hostEventAdapter);
    }

    @Override
    public void onDetach() {
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
                EventsHostingFragment mEventsHosting = ((MainActivity) getActivity()).getmManageEvent().getMfragment2();
                transaction.detach(mEventsHosting);
                transaction.attach(mEventsHosting);
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
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        System.out.println(" Hosting Menu cleared!!!!!!!!!!!!!!!!!!!!");
        inflater.inflate(R.menu.manage_event_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.newToOld_manage) {
            hostEventAdapter.sortNewToOld();
            return true;
        } else if(id == R.id.oldToNew_manage) {
            hostEventAdapter.sortOldToNew();
            return true;
        } else if(id == R.id.modified_manage) {
            hostEventAdapter.sortModified();
            return true;
        } else if(id == R.id.list_manage) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(hostEventAdapter);
            return true;
        } else if(id == R.id.grid_manage) {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(hostEventAdapter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
