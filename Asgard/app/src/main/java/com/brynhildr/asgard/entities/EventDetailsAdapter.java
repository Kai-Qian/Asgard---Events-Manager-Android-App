package com.brynhildr.asgard.entities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.brynhildr.asgard.R;

import java.util.ArrayList;

/**
 * Created by willQian on 2015/11/22.
 */
public class EventDetailsAdapter extends RecyclerView.Adapter<EventDetailsAdapter.ViewHolderForDetails>
{

    private ArrayList<EventTitleAndDetail> event;

    private Context mContext;
    private static String path;

    private ArrayList<ViewHolderForDetails> mViewHolderForDetails = new ArrayList<ViewHolderForDetails>();

    public EventDetailsAdapter(Context context, ArrayList<EventTitleAndDetail> event)
    {
        this.mContext = context;
        this.event = event;
    }

    @Override
    public ViewHolderForDetails onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_details, viewGroup, false);
        ViewHolderForDetails tmp = new ViewHolderForDetails(v);
        mViewHolderForDetails.add(tmp);
        return tmp;
    }


    @Override
    public void onBindViewHolder( ViewHolderForDetails viewHolderForLaunch, int i )
    {
        // 给ViewHolder设置元素
        EventTitleAndDetail p = event.get(i);
        viewHolderForLaunch.mTextView.setText(p.getTitle());
        viewHolderForLaunch.mEditText.setText(p.getDetail());
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return event == null ? 0 : event.size();
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        EventDetailsAdapter.path = path;
    }

    public ArrayList<ViewHolderForDetails> getmViewHolderForDetail() {
        return mViewHolderForDetails;
    }

    // 重写的自定义ViewHolder
    public static class ViewHolderForDetails
            extends RecyclerView.ViewHolder
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
}
