package com.brynhildr.asgard.entity.entitiesAdapers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.connection.DownloadImageFromRemote;
import com.brynhildr.asgard.local.EventWithID;
import com.brynhildr.asgard.userInterface.activities.EventDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewEventAdapter extends RecyclerView.Adapter<ViewEventAdapter.ViewHolderForView>
{

    private List<EventWithID> events;
    private Context mContext;

    private ArrayList<ViewHolderForView> mViewHolderForView = new ArrayList<ViewHolderForView>();


    public ViewEventAdapter(Context context, List<EventWithID> events)
    {
        this.mContext = context;
        this.events = events;
    }

    @Override
    public ViewHolderForView onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ViewHolderForView tmp = new ViewHolderForView(v);
        mViewHolderForView.add(tmp);
        return tmp;
    }

    @Override
    public void onBindViewHolder( final ViewHolderForView viewHolderForView, int i )
    {
        if (events.size() == 0) {
            return;
        } else {
            final EventWithID p = events.get(i);
            viewHolderForView.mTextView1.setText(p.getCOLUMN_NAME_DATEANDTIME());
            viewHolderForView.mTextView2.setText(p.getCOLUMN_NAME_EVENT_NAME());
            viewHolderForView.mTextView3.setText(p.getCOLUMN_NAME_VENUE());
            viewHolderForView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("Event", p);
                    intent.setClass(mContext, EventDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
            try {
                Bitmap posterBitmap = new DownloadImageFromRemote().execute(p.getCOLUMN_NAME_POSTER()).get();
                viewHolderForView.mImageView.setImageBitmap(posterBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount()
    {
        if (events == null) {
            return 0;
        } else {
            return events.size();
        }
    }

    public void sortNewToOld() {
        Collections.sort(events, new NewToOldComparator());
        notifyDataSetChanged();
    }
    public void sortOldToNew() {
        Collections.sort(events, new OldToNewComparator());
        notifyDataSetChanged();
    }
    public void sortModified() {
        Collections.sort(events, new ModifiedComparator());
        notifyDataSetChanged();
    }
    private static class NewToOldComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return -Long.valueOf(s1.getDateAndTimeTimeStamp())
                    .compareTo(Long.valueOf(s2.getDateAndTimeTimeStamp()));
        }
    }
    private static class OldToNewComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return Long.valueOf(s1.getDateAndTimeTimeStamp())
                    .compareTo(Long.valueOf(s2.getDateAndTimeTimeStamp()));
        }
    }
    private static class ModifiedComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return -Long.valueOf(s1.getModifiedTimeStamp())
                    .compareTo(Long.valueOf(s2.getModifiedTimeStamp()));
        }
    }


    public ArrayList<ViewHolderForView> getmViewHolderForView() {
        return mViewHolderForView;
    }

    public static class ViewHolderForView extends RecyclerView.ViewHolder
    {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mImageView;

        public ViewHolderForView(View v)
        {
            super(v);
            mTextView1 = (TextView) v.findViewById(R.id.cardtime);
            mTextView2 = (TextView) v.findViewById(R.id.cardname);
            mTextView3 = (TextView) v.findViewById(R.id.cardvenue);
            mImageView = (ImageView) v.findViewById(R.id.pic);
        }

        public TextView getmTextView1() {
            return mTextView1;
        }

        public void setmTextView1(TextView mTextView1) {
            this.mTextView1 = mTextView1;
        }

        public TextView getmTextView2() {
            return mTextView2;
        }

        public void setmTextView2(TextView mTextView2) {
            this.mTextView2 = mTextView2;
        }

        public TextView getmTextView3() {
            return mTextView3;
        }

        public void setmTextView3(TextView mTextView3) {
            this.mTextView3 = mTextView3;
        }

        public ImageView getmImageView() {
            return mImageView;
        }

        public void setmImageView(ImageView mImageView) {
            this.mImageView = mImageView;
        }
    }
}
