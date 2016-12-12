package com.dongbang.yutian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.ExpertActivity;
import com.dongbang.yutian.activity.ExpertAddQuestionActivity;
import com.dongbang.yutian.beans.ExpertEntity;
import com.dongbang.yutian.utils.ToastUtils;
import com.dongbang.yutian.view.CircleImageView;

import java.util.List;

/**专家列表页面
 * Created by DongBang on 2016/9/22.
 */

public class ExpertListAdapter extends BaseAdapter {
    private Context context;
    private List<ExpertEntity> data;
    public static ExpertEntity entity;

    public ExpertListAdapter(Context context, List<ExpertEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 点击咨询跳转添加页面
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.expert_item_layout, parent, false);
            holder.expert_name = (TextView) convertView.findViewById(R.id.expert_name);
            holder.expert_style = (TextView) convertView.findViewById(R.id.expert_style);
            holder.headerImg = (CircleImageView) convertView.findViewById(R.id.expert_icon);
            holder.question_number = (TextView) convertView.findViewById(R.id.question_number);
            holder.sendConsult = (ImageView) convertView.findViewById(R.id.sendConsult);


            holder.sendConsult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entity = data.get(position);
                    Intent intent = new Intent(context, ExpertAddQuestionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("expert", entity);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.expert_name.setText(data.get(position).getName());
        holder.expert_style.setText(data.get(position).getCatname());
        Glide.with(context).load(data.get(position).getThumb()).into(holder.headerImg);
        holder.question_number.setText(data.get(position).getQaqNum());
        return convertView;
    }

    public static class ViewHolder {
        CircleImageView headerImg;
        TextView expert_name;
        TextView expert_style;
        TextView question_number;
        ImageView sendConsult;


    }

    private LayoutInflater bsman = null;

    public ExpertListAdapter(Context context) {
        this.context = context;
        bsman = LayoutInflater.from(context);

    }


    public void addAll(List<ExpertEntity> subData) {
        data.addAll(subData);
        notifyDataSetChanged();
    }

    public void add(ExpertEntity entity) {
        data.add(entity);
        notifyDataSetChanged();
    }

    public ExpertEntity getItemByIndex(int position) {
        return data.get(position);
    }


    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }


}
