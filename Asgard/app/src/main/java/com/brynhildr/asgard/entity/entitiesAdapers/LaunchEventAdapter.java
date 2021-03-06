package com.brynhildr.asgard.entity.entitiesAdapers;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.entity.entities.Event;
import com.brynhildr.asgard.entity.entities.EventTitle;
import com.brynhildr.asgard.global.SimplifiedUserAuthentication;
import com.brynhildr.asgard.connection.CreateEventToRemote;
import com.brynhildr.asgard.connection.GetEventsFromRemote;
import com.brynhildr.asgard.connection.GetRelationsFromRemote;
import com.brynhildr.asgard.userInterface.activities.MainActivity;
import com.brynhildr.asgard.userInterface.fragments.LaunchEventFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by willQian on 2015/11/15.
 */
public class LaunchEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static String path;
    private static final int NORMAL_ITEM = 0;
    private static final int BUTTON_ITEM = 1;

    private ArrayList<EventTitle> eventTitles;
    private ViewHolderForLaunch mmViewHolderForLaunch = null;
    private Context mContext;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private StringBuilder str = new StringBuilder("");

    private ArrayList<ViewHolderForLaunch> mViewHolderForLaunch = new ArrayList<ViewHolderForLaunch>();

    public static String getPath() {
        return path;
    }

    public static  void setPath(String mPath) {
        LaunchEventAdapter.path = mPath;
    }

    public LaunchEventAdapter(Context context, ArrayList<EventTitle> eventTitles)
    {
        this.mContext = context;
        this.eventTitles = eventTitles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        if (i == NORMAL_ITEM) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_launch, viewGroup, false);
            ViewHolderForLaunch tmp = new ViewHolderForLaunch(v);
            mViewHolderForLaunch.add(tmp);
            return tmp;
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_launchbtn, viewGroup, false);
            ViewHolderForLaunchBtn tmp = new ViewHolderForLaunchBtn(v);
            return tmp;
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int i )
    {
        if (0 <= i && i <= 6) {
            ViewHolderForLaunch viewHolderForLaunch = (ViewHolderForLaunch) holder;
            EventTitle p = eventTitles.get(i);
            viewHolderForLaunch.mTextView.setText(p.getTitle());
            if (i == 1) {
                mmViewHolderForLaunch = viewHolderForLaunch;
                Calendar mCalendar=Calendar.getInstance(Locale.US);
                Date mydate=new Date();
                mCalendar.setTime(mydate);
                year=mCalendar.get(Calendar.YEAR);
                month=mCalendar.get(Calendar.MONTH);
                day=mCalendar.get(Calendar.DAY_OF_MONTH);
                hour = mCalendar.get(Calendar.HOUR);
                minute = mCalendar.get(Calendar.MINUTE);
                viewHolderForLaunch.mEditText.setInputType(InputType.TYPE_NULL);
                viewHolderForLaunch.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            DatePickerDialog dpd = new DatePickerDialog(mContext, mDatePickerDialogListener, year, month, day);
                            dpd.setTitle("Please choose the date");
                            dpd.show();
                        }
                    }
                });
                viewHolderForLaunch.mEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(mContext, mDatePickerDialogListener, year, month, day);
                        dpd.setTitle("Please choose the date");
                        dpd.show();
                    }
                });
            }
        } else if(i == 7) {
            final ViewHolderForLaunchBtn viewHolderForLaunchBtn = (ViewHolderForLaunchBtn) holder;
            viewHolderForLaunchBtn.mButton1.setText("Launch");
            viewHolderForLaunchBtn.mButton1.setBackgroundColor(Color.GREEN);
            viewHolderForLaunchBtn.mButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog();
                }
            });
            viewHolderForLaunchBtn.mButton2.setText("Clear");
            viewHolderForLaunchBtn.mButton2.setBackgroundColor(Color.GRAY);
            viewHolderForLaunchBtn.mButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = ((MainActivity) mContext).getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    LaunchEventFragment mLaunchEvent = ((MainActivity) mContext).getmLaunchEvent();
                    transaction.detach(mLaunchEvent);
                    transaction.attach(mLaunchEvent);
                    transaction.commit();
                }
            });
        }
    }

    private DatePickerDialog.OnDateSetListener mDatePickerDialogListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int mYear, int monthOfYear, int dayOfMonth) {
            year=mYear;
            month=monthOfYear;
            day=dayOfMonth;
            str.delete(0, str.length());
            str.append((month + 1) + "/" + day + "/" + year + " ");
            TimePickerDialog timeDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                    hour = hourOfDay;
                    minute = minuteOfHour;
                    str.append(hour + ":" + minute);
                    mmViewHolderForLaunch.mEditText.setText("");
                    mmViewHolderForLaunch.mEditText.setText(str);
                }
            }, hour, minute, false);
            timeDialog.setTitle("Please choose the time");
            timeDialog.show();
        }
    };

    @Override
    public int getItemCount()
    {
        if (eventTitles == null) {
            return 0;
        } else {
            return eventTitles.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 7 || position == 8) {
            return BUTTON_ITEM;
        } else {
            return NORMAL_ITEM;
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public ArrayList<ViewHolderForLaunch> getmViewHolderForLaunch() {
        return mViewHolderForLaunch;
    }

    public static class ViewHolderForLaunch extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public EditText mEditText;

        public ViewHolderForLaunch(View v)
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.launchcardtitle);
            mEditText = (EditText) v.findViewById(R.id.launchcardedittxt);
        }

        public EditText getmEditText() {
            return mEditText;
        }

        public void setmEditText(EditText mEditText) {
            this.mEditText = mEditText;
        }

        public TextView getmTextView() {
            return mTextView;
        }

        public void setmTextView(TextView mTextView) {
            this.mTextView = mTextView;
        }
    }

    public static class ViewHolderForLaunchBtn extends RecyclerView.ViewHolder
    {
        public Button mButton1;
        public Button mButton2;

        public ViewHolderForLaunchBtn(View v)
        {
            super(v);
            mButton1 = (Button) v.findViewById(R.id.launchbtn1);
            mButton2 = (Button) v.findViewById(R.id.launchbtn2);
        }

        public Button getmButton1() {
            return mButton1;
        }

        public void setmButton1(Button mButton1) {
            this.mButton1 = mButton1;
        }

        public Button getmButton2() {
            return mButton2;
        }

        public void setmButton2(Button mButton2) {
            this.mButton2 = mButton2;
        }
    }
    private long calculateTimeStamp(int year, int month, int day, int hour, int minute) {
        Calendar calendar = new GregorianCalendar(year, month, day, hour, minute);
        return calendar.getTimeInMillis() / 1000;
    }


    private void dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle("Confirmation");
        dialogBuilder.setMessage("Are you sure you want to launch this event？");
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Event event = new Event();
                        ArrayList<LaunchEventAdapter.ViewHolderForLaunch> tmp = getmViewHolderForLaunch();
                        event.setCOLUMN_NAME_EVENT_NAME(tmp.get(0).getmEditText().getText().toString());
                        event.setCOLUMN_NAME_DATEANDTIME(calculateTimeStamp(year, month, day, hour, minute) + "");
                        System.out.println("event.getCOLUMN_NAME_DATEANDTIME()--->" + event.getCOLUMN_NAME_DATEANDTIME());
                        event.setCOLUMN_NAME_VENUE(tmp.get(2).getmEditText().getText().toString());
                        event.setCOLUMN_NAME_DRESS_CODE(tmp.get(3).getmEditText().getText().toString());
                        event.setCOLUMN_NAME_TARGET(tmp.get(4).getmEditText().getText().toString());
                        event.setCOLUMN_NAME_MAX_PEOPLE(tmp.get(5).getmEditText().getText().toString());
                        event.setCOLUMN_NAME_DESCRIPTION(tmp.get(6).getmEditText().getText().toString());
                        event.setCOLUMN_NAME_POSTER(getPath());
                        System.out.println("getPath()--->" + getPath());
                        event.setCOLUMN_NAME_LAUNCHER_ID(SimplifiedUserAuthentication.getUsername());
                        CreateEventToRemote mCreateEventToRemote = new CreateEventToRemote();
                        mCreateEventToRemote.execute(event);
                        new GetEventsFromRemote().execute();
                        new GetRelationsFromRemote().execute();
                        FragmentManager fm = ((MainActivity) mContext).getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        LaunchEventFragment mLaunchEvent = ((MainActivity) mContext).getmLaunchEvent();
                        transaction.detach(mLaunchEvent);
                        transaction.attach(mLaunchEvent);
                        transaction.commit();

                    }
                }, 500);
            }
        });
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.create().show();
    }
}
