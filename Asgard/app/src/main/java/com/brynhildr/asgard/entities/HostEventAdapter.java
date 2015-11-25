package com.brynhildr.asgard.entities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.userInterface.activities.HostEventDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willQian on 2015/11/22.
 */
public class HostEventAdapter extends RecyclerView.Adapter<HostEventAdapter.ViewHolderForHost>
{

    private List<Event> events;
    private Resources res;
    private Context mContext;

    private ArrayList<ViewHolderForHost> mViewHolderForHost = new ArrayList<ViewHolderForHost>();


    public HostEventAdapter(Context context, List<Event> events)
    {
        this.mContext = context;
        this.events = events;
        this.res = context.getResources();
    }

    @Override
    public ViewHolderForHost onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_host, viewGroup, false);
        ViewHolderForHost tmp = new ViewHolderForHost(v);
        mViewHolderForHost.add(tmp);
        return tmp;
    }


    @Override
    public void onBindViewHolder( final ViewHolderForHost viewHolderForHost, int i )
    {
        // 给ViewHolder设置元素
        if (events.size() == 0) {
            return;
        } else {
            final Event p = events.get(i);
            viewHolderForHost.mTextView1.setText(p.getCOLUMN_NAME_DATEANDTIME());
            viewHolderForHost.mTextView2.setText(p.getCOLUMN_NAME_EVENT_NAME());
            viewHolderForHost.mTextView3.setText(p.getCOLUMN_NAME_VENUE());
            viewHolderForHost.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));
            viewHolderForHost.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    intent.putExtra("Event", p);
                    intent.setClass(mContext, HostEventDetailsActivity.class);
                    mContext.startActivity(intent);
                }
            });
            Bitmap bitmap = BitmapFactory.decodeResource(res, p.getImageResourceId(mContext));
            //异步获得bitmap图片颜色值
            Palette.Builder from = Palette.from(bitmap);
            from.generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力
                    Palette.Swatch DarkVibrant = palette.getDarkVibrantSwatch();//有活力 暗色
                    Palette.Swatch LightVibrant = palette.getLightVibrantSwatch();//有活力 亮色
                    Palette.Swatch Muted = palette.getMutedSwatch();//柔和
                    Palette.Swatch DarkMuted = palette.getDarkMutedSwatch();//柔和 暗色
                    Palette.Swatch LightMuted = palette.getLightMutedSwatch();//柔和 亮色

                    if (vibrant != null) {
                        int color1 = vibrant.getBodyTextColor();//内容颜色
                        int color2 = vibrant.getTitleTextColor();//标题颜色
                        int color3 = vibrant.getRgb();//rgb颜色
                        viewHolderForHost.mCardView.setCardBackgroundColor(color3);
                        viewHolderForHost.mTextView1.setTextColor(color2);
                        viewHolderForHost.mTextView2.setTextColor(color2);
                        viewHolderForHost.mTextView3.setTextColor(color2);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return events == null ? 0 : events.size();
    }
    public ArrayList<ViewHolderForHost> getmViewHolderForView() {
        return mViewHolderForHost;
    }

    // 重写的自定义ViewHolder
    public static class ViewHolderForHost
            extends RecyclerView.ViewHolder
    {
        public TextView mTextView1;

        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mImageView;
        public CardView mCardView;

        public ViewHolderForHost(View v)
        {
            super(v);
            mTextView1 = (TextView) v.findViewById(R.id.cardtime);
            mTextView2 = (TextView) v.findViewById(R.id.cardname);
            mTextView3 = (TextView) v.findViewById(R.id.cardvenue);
            mImageView = (ImageView) v.findViewById(R.id.pic);
            mCardView = (CardView) v.findViewById(R.id.card_manage);
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
        public CardView getmCardView() {
            return mCardView;
        }

        public void setmCardView(CardView mCardView) {
            this.mCardView = mCardView;
        }

    }
}

