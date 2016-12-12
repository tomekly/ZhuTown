package com.dongbang.yutian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.AgriculturalKnowledge;
import com.dongbang.yutian.beans.ArticleBean;
import com.dongbang.yutian.utils.DateUtils;
import com.dongbang.yutian.utils.LogUtils;

import java.util.LinkedList;

/**
 * Created by DongBang on 2016/5/8.
 */
public class FramingKnowledgeAdapter extends RecyclerView.Adapter<FramingKnowledgeAdapter.MyViewHolder> {
    private String TAG = "FramingKnowledgeAdapter";
    private Context context;
    private LinkedList<ArticleBean> agriculturalKnowledges;

    public FramingKnowledgeAdapter(Context context, LinkedList<ArticleBean> agriculturalKnowledges) {
        this.context = context;
        this.agriculturalKnowledges = agriculturalKnowledges;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_hp_fk_recycler, null);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.framing_recycler_item_title.setText(agriculturalKnowledges.get(position).getNoticeContent());
        holder.framing_recycler_item_date.setText(DateUtils.msToDate(agriculturalKnowledges.get(position).getNoticeTime()));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return agriculturalKnowledges.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView framing_recycler_item_title;
        private TextView framing_recycler_item_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            framing_recycler_item_title = (TextView) itemView.findViewById(R.id.framing_recycler_item_title);
            framing_recycler_item_date = (TextView) itemView.findViewById(R.id.framing_recycler_item_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickArticleListener != null) {
                onClickArticleListener.onClickItem(v);
            }
        }
    }

    public void setOnClickArticleListener(OnClickArticleListener onClickArticleListener) {
        this.onClickArticleListener = onClickArticleListener;
    }

    public OnClickArticleListener onClickArticleListener;

    public interface OnClickArticleListener {
        void onClickItem(View view);
    }

}
