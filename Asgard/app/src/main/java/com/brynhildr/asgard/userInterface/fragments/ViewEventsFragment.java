package com.brynhildr.asgard.userInterface.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brynhildr.asgard.DBLayout.events.EventDatabase;
import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entities.Event;
import com.brynhildr.asgard.entities.ViewEventAdapter;
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
public class ViewEventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener { //implements AbsListView.OnItemClickListener {

    private RecyclerView mRecyclerView;

    private ViewEventAdapter viewEventAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean flag = false;
    private ImageButton fab;

    private ImageButton fabAction1;
    private View fabAction2;

    private TextView textView3;
    private TextView textView4;

    private float offset1;
    private float offset2;
    private float offset3;
    private float offset4;
    private float offset5;
    private float offset6;
//    private List<Event> events = new ArrayList<Event>();

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
//    private ListAdapter mAdapter;

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
        System.out.println("ViewEventsonCreate");
        edb = new EventDatabase(getActivity());
//        edb.deleteTable();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("ViewEventsonCreateView");
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
//        mListView = (AbsListView) view.findViewById(android.R.id.list);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
//        mListView.setOnItemClickListener(this);

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
        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.input, R.color.black_overlay);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_view);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((MainActivity)getActivity());
    }
    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }
    @Override
    public void onResume() {
        System.out.println("ViewEventsonResume");
        super.onResume();
        edb = new EventDatabase(getActivity());
//        edb = new EventDatabase(getActivity());
        ArrayList<Event> event = edb.readRow();
//        events.add(new Event("HO HO HO ALL NIGHT LONG XMAS PARTY", "5000 Forbes AVE, Pittsburgh, PA",
//                "THU, DEC 24, 10:00 PM", "0", "Casual", "poster1", "21+", "30"));
//        events.add(new Event("The Dark Knight Rises", "5000 Forbes AVE, Pittsburgh, PA",
//                "TUE, NOV 24, 08:00 PM", "0", "Casual", "poster2", "Anyone", "30"));
//        events.add(new Event("IRON MAN", "5000 Forbes AVE, Pittsburgh, PA",
//                "TUE, NOV 17, 06:00 PM", "0", "Casual", "poster3", "Anyone", "30"));
        //getActivity().getActionBar().setTitle("View Events");
        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) ((MainActivity)getActivity()).findViewById(R.id.viewEventlist_view);
        System.out.println("" + (mRecyclerView == null));
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
//        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        viewEventAdapter = new ViewEventAdapter(getActivity(), event);
        System.out.println("viewEventAdapter.getItemCount()" + viewEventAdapter.getItemCount());
                // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(viewEventAdapter);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout_view);
        mCollapsingToolbarLayout.setTitle("View Events");
        mCollapsingToolbarLayout.setBackground(null);
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        ImageView headImage = (ImageView) getActivity().findViewById(R.id.backdrop_view);
        headImage.setImageResource(R.drawable.poster3);
        textView3 = (TextView)getActivity().findViewById(R.id.textView3);
        textView4 = (TextView)getActivity().findViewById(R.id.textView4);
        final ViewGroup fabContainer = (ViewGroup) getActivity().findViewById(R.id.fab_container);
        fab = (ImageButton) getActivity().findViewById(R.id.fab);
        fabAction1 = (ImageButton)getActivity().findViewById(R.id.fab_action_1);
        fabAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((MainActivity) getActivity()).getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                ViewEventsFragment mViewEvent = new ViewEventsFragment();
                LaunchEventFragment mManageEvent = new LaunchEventFragment();
//                transaction.detach(mViewEvent);
                transaction.attach(mManageEvent);
                transaction.commit();
            }
        });
        fabAction2 = getActivity().findViewById(R.id.fab_action_2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false) {
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.clockwise_rotate);
                    fab.setImageResource(R.drawable.ic_plus);
                    fab.startAnimation(animation);
                    fab.setImageResource(R.drawable.ic_x);
                    expandFab();
                    flag = true;
                } else {
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anticlockwise_rotate);
                    fab.setImageResource(R.drawable.ic_x);
                    fab.startAnimation(animation);
                    fab.setImageResource(R.drawable.ic_plus);
                    collapseFab();
                    flag = false;
                }
            }
        });
        fabContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fabContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                offset1 = fab.getY() - fabAction1.getY();
                fabAction1.setTranslationY(offset1);
                offset2 = fab.getY() - fabAction2.getY();
                fabAction2.setTranslationY(offset2);
                offset3 = fab.getY() - textView3.getY() + 50;
                System.out.println("offset3--->" + offset3);
                textView3.setTranslationY(offset3);
                offset4 = fab.getY() - textView4.getY() + 50;
                textView4.setTranslationY(offset4);
                offset5 = fab.getX() - textView3.getX() + 100;
                System.out.println("offset5--->" + offset5);
                textView3.setTranslationX(offset5);
                offset6 = fab.getX() - textView4.getX() + 100;
                textView4.setTranslationX(offset6);
                collapseFab();
                return true;
            }
        });
//        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.viewfab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                FragmentManager fm = ((MainActivity) getActivity()).getFragmentManager();
//                                FragmentTransaction transaction = fm.beginTransaction();
//                                ViewEventsFragment mViewEvent = new ViewEventsFragment();
//                                transaction.detach(mViewEvent);
//                                transaction.attach(mViewEvent);
//                                transaction.commit();
//                                Snackbar snackbar = Snackbar.make(v, "ActionClick", Snackbar.LENGTH_LONG);
//                                setSnackbarMessageTextColor(snackbar, Color.parseColor("#FF0000"));
//                                snackbar.show();
//                            }
//                        }).show();
//            }});

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
        collapseFab();
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
                mSwipeRefreshLayout.setRefreshing(false);
                FragmentManager fm = ((MainActivity) getActivity()).getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                ViewEventsFragment mViewEvent = new ViewEventsFragment();
                transaction.detach(mViewEvent);
                transaction.attach(mViewEvent);
                transaction.commit();
            }
        }, 1000);
    }
    private void collapseFab() {
//        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createCollapseAnimator(fabAction1, offset1),
                createCollapseAnimator(fabAction2, offset2),
                createCollapseAnimator(textView3, offset3),
                createCollapseAnimator(textView4, offset4),
                createCollapseAnimatorText(textView3, offset5),
                createCollapseAnimatorText(textView4, offset6));

        animatorSet.start();
//        animateFab();
    }

    private void expandFab() {
//        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createExpandAnimator(fabAction1, offset1),
                createExpandAnimator(fabAction2, offset2),
                createExpandAnimator(textView3, offset3),
                createExpandAnimator(textView4, offset4),
                createExpandAnimatorText(textView3, offset5),
                createExpandAnimatorText(textView4, offset6));
        animatorSet.start();
//        animateFab();
    }

    private static final String TRANSLATION_Y = "translationY";
    private static final String TRANSLATION_X = "translationX";

    private Animator createCollapseAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createCollapseAnimatorText(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimatorText(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
//    }


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
}