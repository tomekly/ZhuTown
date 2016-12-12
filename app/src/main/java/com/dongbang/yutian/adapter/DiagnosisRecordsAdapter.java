package com.dongbang.yutian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.DiagnosticLogOutEntity;
import com.dongbang.yutian.beans.ResponseDiagnosticLogOutEntity;

import java.util.List;

/**
 * Created by DongBang on 2016/9/23.
 */

public class DiagnosisRecordsAdapter extends BaseAdapter {

    private Context context;
    private List<ResponseDiagnosticLogOutEntity.UnsolvedBean> data;

    public DiagnosisRecordsAdapter(Context context, List<ResponseDiagnosticLogOutEntity.UnsolvedBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return   data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.expert_alread_diagnose_item,parent,false);
            holder.expert_alread_item_tv01= (TextView) convertView.findViewById(R.id.expert_alread_item_tv01);
            holder.expert_alread_item_tv02= (TextView) convertView.findViewById(R.id.expert_alread_item_tv02);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.expert_alread_item_tv01.setText(data.get(position).getTitle());
        holder.expert_alread_item_tv02.setText(data.get(position).getTime());
        return convertView;
    }


    public static class  ViewHolder{
        TextView expert_alread_item_tv01;
        TextView expert_alread_item_tv02;
    }

    public  void addAll(List<ResponseDiagnosticLogOutEntity.UnsolvedBean> entities){
        data.clear();
        data.addAll(entities);
        notifyDataSetChanged();
    }
    public void add(ResponseDiagnosticLogOutEntity.UnsolvedBean entity){
        data.add(entity);
        notifyDataSetChanged();
    }


    public  void clear(){
        data.clear();
        notifyDataSetChanged();
    }
    public void  remove(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

}
