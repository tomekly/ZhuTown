package com.dongbang.yutian.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.dhvideo.beans.TreeNode;
import com.dongbang.yutian.utils.LogUtils;

import java.util.List;

/**
 * Created by DongBang on 2016/9/30.
 */

public class BaseAddrAdapter implements SpinnerAdapter {
    private  Context context;
    private List<TreeNode> datas;
    public BaseAddrAdapter(Context context , List<TreeNode> datas){
    this.context =context;
        this.datas=datas;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        SpinnerViewHolder holder ;
        if (convertView==null){
            holder=new SpinnerViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_base_addr_spinner_view,parent,false);
            holder.itemName = (TextView) convertView.findViewById(R.id.item_video_sp);
            convertView.setTag(holder);
        }else {
            holder = (SpinnerViewHolder) convertView.getTag();
        }
        holder.itemName.setText(datas.get(position).getChannelInfo().getSzName());

        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpinnerViewHolder holder ;
        if (convertView==null){
            holder=new SpinnerViewHolder();
            LogUtils.d("BaseAddrAdapter","BaseAddrAdapter   "+ context);
            convertView= LayoutInflater.from(context).inflate(R.layout.item_base_addr_spinner_view,parent,false);
            holder.itemName = (TextView) convertView.findViewById(R.id.item_video_sp);
            convertView.setTag(holder);
        }else {
            holder = (SpinnerViewHolder) convertView.getTag();
        }
        holder.itemName.setText(datas.get(position).getChannelInfo().getSzName());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public static class  SpinnerViewHolder {

        private TextView itemName;


    }



}
