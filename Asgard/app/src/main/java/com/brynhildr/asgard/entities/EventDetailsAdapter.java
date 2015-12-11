package com.brynhildr.asgard.entities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.global.SimplifiedUserAuthentication;
import com.brynhildr.asgard.local.EventWithID;
import com.brynhildr.asgard.local.GetEventsFromRemote;
import com.brynhildr.asgard.local.GetRelationsFromRemote;
import com.brynhildr.asgard.local.UpdateEventToRemote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by willQian on 2015/11/22.
 */
public class EventDetailsAdapter extends RecyclerView.Adapter<EventDetailsAdapter.ViewHolderForDetails>
{

    private ArrayList<EventTitleAndDetail> event;

    private Context mContext;
    private static String path;
    private static String originalPath;
    private ViewHolderForDetails mmViewHolderForDetails = null;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private StringBuilder str = new StringBuilder("");

    private String ID;

    private ArrayList<ViewHolderForDetails> mViewHolderForDetails = new ArrayList<ViewHolderForDetails>();

    public EventDetailsAdapter(Context context, ArrayList<EventTitleAndDetail> event, String ID)
    {
        this.mContext = context;
        this.event = event;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    @Override
    public ViewHolderForDetails onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_details, viewGroup, false);
        ViewHolderForDetails tmp = new ViewHolderForDetails(v);
        mViewHolderForDetails.add(tmp);
        return tmp;
    }


    @Override
    public void onBindViewHolder( ViewHolderForDetails viewHolderForDetails, int i )
    {
        EventTitleAndDetail p = event.get(i);
        viewHolderForDetails.mTextView.setText(p.getTitle());
        viewHolderForDetails.mEditText.setText(p.getDetail());
        if (i == 1) {
            mmViewHolderForDetails = viewHolderForDetails;
            Calendar mCalendar=Calendar.getInstance(Locale.US);
            Date mydate=new Date();
            mCalendar.setTime(mydate);
            year=mCalendar.get(Calendar.YEAR);
            month=mCalendar.get(Calendar.MONTH);
            day=mCalendar.get(Calendar.DAY_OF_MONTH);
            hour = mCalendar.get(Calendar.HOUR);
            minute = mCalendar.get(Calendar.MINUTE);
            viewHolderForDetails.mEditText.setInputType(InputType.TYPE_NULL);
            viewHolderForDetails.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        DatePickerDialog dpd = new DatePickerDialog(mContext, mDatePickerDialogListener, year, month, day);
                        dpd.setTitle("Please choose the date");
                        dpd.show();
                    }
                }
            });
            viewHolderForDetails.mEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dpd = new DatePickerDialog(mContext, mDatePickerDialogListener, year, month, day);
                    dpd.setTitle("Please choose the date");
                    dpd.show();
                }
            });
        }
    }

    private DatePickerDialog.OnDateSetListener mDatePickerDialogListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int myear, int monthOfYear, int dayOfMonth) {
            year=myear;
            month=monthOfYear;
            day=dayOfMonth;
            str.delete(0, str.length());
            str.append((month + 1) + "/" + day + "/" + year + " ");
            TimePickerDialog timeDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker tp, int hourOfDay, int minuteOfHour) {
                    hour = hourOfDay;
                    minute = minuteOfHour;
                    str.append(hour + ":" + minute);
                    mmViewHolderForDetails.mEditText.setText("");
                    mmViewHolderForDetails.mEditText.setText(str);
                }
            }, hour, minute, false);
            timeDialog.setTitle("Please choose the time");
            timeDialog.show();
        }
    };
    @Override
    public int getItemCount()
    {
        if (event == null) {
            return 0;
        } else {
            return event.size();
        }
    }

    public static String getPath() {
        return path;
    }

    public static String getOriginalPath() {
        return originalPath;
    }

    public static void setOriginalPath(String originalPath) {
        EventDetailsAdapter.originalPath = originalPath;
    }

    public static void setPath(String path) {
        EventDetailsAdapter.path = path;
    }

    public ArrayList<ViewHolderForDetails> getmViewHolderForDetail() {
        return mViewHolderForDetails;
    }

    public static class ViewHolderForDetails extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public EditText mEditText;

        public ViewHolderForDetails(View v)
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.detailscardtitle);
            mEditText = (EditText) v.findViewById(R.id.detailscardedittxt);
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

    public void dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle("Confirmation");
        dialogBuilder.setMessage("Are you sure you want to update this event？");
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        EventWithID eventWithID = new EventWithID();
                        ArrayList<EventDetailsAdapter.ViewHolderForDetails> tmp = getmViewHolderForDetail();
                        eventWithID.setCOLUMN_NAME_EVENT_NAME(tmp.get(0).getmEditText().getText().toString());
                        eventWithID.setCOLUMN_NAME_DATEANDTIME(calculateTimeStamp(year, month, day, hour, minute) + "");
                        System.out.println("event.getCOLUMN_NAME_DATEANDTIME()--->" + eventWithID.getCOLUMN_NAME_DATEANDTIME());
                        eventWithID.setCOLUMN_NAME_VENUE(tmp.get(2).getmEditText().getText().toString());
                        eventWithID.setCOLUMN_NAME_DRESS_CODE(tmp.get(3).getmEditText().getText().toString());
                        eventWithID.setCOLUMN_NAME_TARGET(tmp.get(4).getmEditText().getText().toString());
                        eventWithID.setCOLUMN_NAME_MAX_PEOPLE(tmp.get(5).getmEditText().getText().toString());
                        eventWithID.setCOLUMN_NAME_DESCRIPTION(tmp.get(6).getmEditText().getText().toString());
                        if (!originalPath.equals(path)) {
                            eventWithID.setCOLUMN_NAME_POSTER(getPath());
                        }
                        System.out.println("getPath()--->" + getPath());
                        eventWithID.setCOLUMN_NAME_LAUNCHER_ID(SimplifiedUserAuthentication.getUsername());
                        eventWithID.setID(getID());
                        System.out.println("getID()--->" + getID());
                        new UpdateEventToRemote().execute(eventWithID);
                        new GetEventsFromRemote().execute();
                        new GetRelationsFromRemote().execute();
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

    private long calculateTimeStamp(int year, int month, int day, int hour, int minute) {
        Calendar calendar = new GregorianCalendar(year, month, day, hour, minute);
        return calendar.getTimeInMillis() / 1000;
    }
}
