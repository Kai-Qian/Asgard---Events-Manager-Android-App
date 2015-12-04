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
import com.brynhildr.asgard.local.DownloadImageFromRemote;
import com.brynhildr.asgard.local.EventWithID;
import com.brynhildr.asgard.userInterface.activities.EventDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Dean Guo on 10/20/14.
 */
public class ViewEventAdapter
    extends RecyclerView.Adapter<ViewEventAdapter.ViewHolderForView>
{

    private List<EventWithID> events;
    private Resources res;
    private Context mContext;

    private ArrayList<ViewHolderForView> mViewHolderForView = new ArrayList<ViewHolderForView>();


    public ViewEventAdapter(Context context, List<EventWithID> events)
    {
        this.mContext = context;
        this.events = events;
        this.res = context.getResources();
    }

    @Override
    public ViewHolderForView onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ViewHolderForView tmp = new ViewHolderForView(v);
        mViewHolderForView.add(tmp);
        return tmp;
    }

    @Override
    public void onBindViewHolder( final ViewHolderForView viewHolderForView, int i )
    {

        // 给ViewHolder设置元素
        if (events.size() == 0) {
            System.out.println("events.size() == 0--->" + (events.size() == 0));
            return;
        } else {
            final EventWithID p = events.get(i);
            System.out.println("onBindViewHolder--->" + p.getCOLUMN_NAME_DATEANDTIME());
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
                        viewHolderForView.mCardView.setCardBackgroundColor(color3);
                        viewHolderForView.mTextView1.setTextColor(color2);
                        viewHolderForView.mTextView2.setTextColor(color2);
                        viewHolderForView.mTextView3.setTextColor(color2);
                    }
                }
            });
            System.out.println("this.COLUMN_NAME_POSTER---->view");
            try {
                Bitmap posterBitmap = new DownloadImageFromRemote().execute(p.getCOLUMN_NAME_POSTER()).get();
//                System.out.println("Bitmap got!");
//                System.out.println("posterBitmap.getHeight()---->" + posterBitmap.getHeight());
//                System.out.println("posterBitmap.getHeight()---->" + posterBitmap.getWidth());
                viewHolderForView.mImageView.setImageBitmap(posterBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            viewHolderForView.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));
        }
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return events == null ? 0 : events.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void sortNewToOld() {
        Collections.sort(events, new NewToOldComparator());
        for (EventWithID i : events) {
            System.out.println("DateAndTimeTimeStamp----->" + i.getDateAndTimeTimeStamp());
        }
        notifyDataSetChanged();
        System.out.println("sortNewToOld----->");
//        notifyItemInserted(0);
    }
    public void sortOldToNew() {
        Collections.sort(events, new OldToNewComparator());

//        notifyItemRemoved(0);
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
//            return -Long.compare(s1.getDateAndTimeTimeStamp(), s2.getDateAndTimeTimeStamp());
        }
    }
    private static class OldToNewComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return Long.valueOf(s1.getDateAndTimeTimeStamp())
                    .compareTo(Long.valueOf(s2.getDateAndTimeTimeStamp()));
//            return Long.compare(s1.getDateAndTimeTimeStamp(), s2.getDateAndTimeTimeStamp());
        }
    }
    private static class ModifiedComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return -Long.valueOf(s1.getModifiedTimeStamp())
                    .compareTo(Long.valueOf(s2.getModifiedTimeStamp()));
//            return -Long.compare(s1.getModifiedTimeStamp(), s2.getModifiedTimeStamp());
        }
    }


    public ArrayList<ViewHolderForView> getmViewHolderForView() {
        return mViewHolderForView;
    }

    // 重写的自定义ViewHolder
    public static class ViewHolderForView
        extends RecyclerView.ViewHolder
    {
        public TextView mTextView1;

        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mImageView;
        public CardView mCardView;

        public ViewHolderForView(View v)
        {
            super(v);
            mTextView1 = (TextView) v.findViewById(R.id.cardtime);
            mTextView2 = (TextView) v.findViewById(R.id.cardname);
            mTextView3 = (TextView) v.findViewById(R.id.cardvenue);
            mImageView = (ImageView) v.findViewById(R.id.pic);
            mCardView = (CardView) v.findViewById(R.id.card_view);
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
