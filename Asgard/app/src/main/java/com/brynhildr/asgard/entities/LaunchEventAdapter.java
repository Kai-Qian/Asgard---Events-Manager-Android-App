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
 * Created by willQian on 2015/11/15.
 */
public class LaunchEventAdapter extends RecyclerView.Adapter<LaunchEventAdapter.ViewHolderForLaunch>
{

    private ArrayList<EventTitle> eventTitles;

    private Context mContext;

    private ArrayList<ViewHolderForLaunch> mViewHolderForLaunch = new ArrayList<ViewHolderForLaunch>();

    public LaunchEventAdapter(Context context, ArrayList<EventTitle> eventTitles)
    {
        this.mContext = context;
        this.eventTitles = eventTitles;
    }

    @Override
    public ViewHolderForLaunch onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_launch, viewGroup, false);
        ViewHolderForLaunch tmp = new ViewHolderForLaunch(v);
        mViewHolderForLaunch.add(tmp);
        return tmp;
    }


    @Override
    public void onBindViewHolder( ViewHolderForLaunch viewHolderForLaunch, int i )
    {
        // 给ViewHolder设置元素
        EventTitle p = eventTitles.get(i);
        viewHolderForLaunch.mTextView.setText(p.getTitle());
//        viewHolderForLaunch.mEditText.setText(p.getCOLUMN_NAME_EVENT_NAME());
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return eventTitles == null ? 0 : eventTitles.size();
    }

    public ArrayList<ViewHolderForLaunch> getmViewHolderForLaunch() {
        return mViewHolderForLaunch;
    }

    // 重写的自定义ViewHolder
    public static class ViewHolderForLaunch
            extends RecyclerView.ViewHolder
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
}
