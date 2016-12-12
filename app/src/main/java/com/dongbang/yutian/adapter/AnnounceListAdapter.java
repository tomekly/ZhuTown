package com.dongbang.yutian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.ArticleBean;
import com.dongbang.yutian.utils.BaseUtils;
import com.dongbang.yutian.utils.DateUtils;

import java.util.LinkedList;

/**
 * Created by DongBang on 2016/5/8.
 */
public class AnnounceListAdapter extends RecyclerView.Adapter<AnnounceListAdapter.MyViewHolder> {

    private Context context;
    private LinkedList<ArticleBean> articleBeen;

    public AnnounceListAdapter(Context context, LinkedList<ArticleBean> articleBeens) {
        this.context = context;
        this.articleBeen = articleBeens;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_repository_recyclervrew, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder != null) {
            holder.repository_item_time_tv.setText(DateUtils.msToDate(articleBeen.get(position).getNoticeTime()));
            holder.repository_item_title_tv.setText(articleBeen.get(position).getNoticeContent());
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return articleBeen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView repository_item_title_tv;
        private TextView repository_item_time_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            repository_item_title_tv = (TextView) itemView.findViewById(R.id.repository_item_title_tv);
            repository_item_time_tv = (TextView) itemView.findViewById(R.id.repository_item_time_tv);
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
