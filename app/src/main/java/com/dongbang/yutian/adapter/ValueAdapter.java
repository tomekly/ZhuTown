package com.dongbang.yutian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.ValueEntity;

import org.w3c.dom.Text;

import java.util.LinkedList;

/**
 * Created by DongBang on 2016/6/16.
 */
public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder>
{
    private Context context;
    private LinkedList<ValueEntity> valueEntities;

    public ValueAdapter(Context context, LinkedList<ValueEntity> valueEntities)
    {
        this.valueEntities=valueEntities;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_aggregation_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.value.setText(valueEntities.get(position).getValue() + valueEntities.get(position).getSymbol());
        holder.date.setText(valueEntities.get(position).getDate());
        if (position%2==0){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_f7f7f7));
        }else
        {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount()
    {
        return valueEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView date;
        private TextView value;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.item_data_agg_date);
            value=(TextView)itemView.findViewById(R.id.item_data_agg_value);
        }
    }
}
