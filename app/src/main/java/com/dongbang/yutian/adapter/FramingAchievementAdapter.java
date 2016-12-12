package com.dongbang.yutian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.ImageUrl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DongBang on 2016/6/14.
 */
public class FramingAchievementAdapter extends RecyclerView.Adapter<FramingAchievementAdapter.MyViewHolder>
{
    private String TAG="FramingAchievementAdapter";
    private Context context;
    private List<Integer> mHeights;
    private LinkedList<ImageUrl> imageUrls;
    public FramingAchievementAdapter(Context context, LinkedList<ImageUrl> imageUrls)
    {
        this.context=context;
        this.imageUrls = imageUrls;
        mHeights = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            mHeights.add( (int) (100 + Math.random() * 300));
        }


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView= LayoutInflater.from(context).inflate(R.layout.item_hp_fruit_view,null);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
     ViewGroup. LayoutParams lp = holder.item_img.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.item_img.setLayoutParams(lp);
        if (position<imageUrls.size()){
            Glide.with(context).load(imageUrls.get(position).getUrl()).placeholder(R.mipmap.hp_img_achievement).crossFade().into(holder.item_img);
        }else {
            holder.item_img.setBackgroundResource(R.mipmap.hp_img_achievement);
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount()
    {
        return imageUrls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView item_img;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            item_img=(ImageView) itemView.findViewById(R.id.recycler_item_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (onClickAchievementListener!=null)
            {
                onClickAchievementListener.onClickAchievement(v);
            }
        }
    }
    public void setOnClickAchievementListener(OnClickAchievementListener onClickArticleListener)
    {
        this.onClickAchievementListener =onClickArticleListener;
    }

    public OnClickAchievementListener onClickAchievementListener;
    public  interface OnClickAchievementListener {
        void onClickAchievement(View view);
    }


}
