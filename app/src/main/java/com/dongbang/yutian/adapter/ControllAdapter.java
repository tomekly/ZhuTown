package com.dongbang.yutian.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.ValueEntity;

import java.util.LinkedList;

/**
 * Created by qzp on 2016/12/6.
 */

public class ControllAdapter extends RecyclerView.Adapter<ControllAdapter.MyViewHolder> {
    private String TAG = "ControllAdapter";
    private Context context;
    //    private LinkedList<ArticleBean> lists;
    private LinkedList<ValueEntity> valueEntities;

    public ControllAdapter(Context context, LinkedList<ValueEntity> list) {
        this.context = context;
        this.valueEntities = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_control_center, null);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.itemView.setTag(position);
//        if (valueEntities.get(position).set() == 0) {
//            holder.btn.setImageResource(R.drawable.switch_on);
//        }


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    //选中
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    dlg.setTitle("提示");
                    dlg.setMessage("确定要开启吗");
                    SpannableStringBuilder builder1 = new SpannableStringBuilder("返回");
                    builder1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_000000)), 0, 2, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannableStringBuilder builder = new SpannableStringBuilder("确定");
                    builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_000000)), 0, 2, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    dlg.setPositiveButton(builder, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.checkBox.setBackground(context.getResources().getDrawable(R.drawable.switch_on));
                            holder.checkBox.setChecked(true);
                        }
                    }).setNegativeButton(builder1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.checkBox.setChecked(false);
                            dialog.dismiss();
                        }
                    });
                    dlg.show();
                } else {
                    //未选中
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    dlg.setTitle("提示");
                    dlg.setMessage("确定要关闭吗");

                    SpannableStringBuilder builder1 = new SpannableStringBuilder("返回");
                    builder1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_000000)), 0, 2, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

                    SpannableStringBuilder builder = new SpannableStringBuilder("确定");
                    builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_000000)), 0, 2, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    dlg.setPositiveButton(builder, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.checkBox.setBackground(context.getResources().getDrawable(R.drawable.switch_off));
                            holder.checkBox.setChecked(false);
                        }
                    }).setNegativeButton(builder1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.checkBox.setChecked(true);
                            dialog.dismiss();
                        }
                    });
                    dlg.show();


                }
            }
        });// 添加监听事件

    }

    @Override
    public int getItemCount() {
        return valueEntities.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text_name;
        private CheckBox checkBox;
        private ImageButton btn;
        public MyViewHolder(View view) {
            super(view);
            text_name = (TextView) itemView.findViewById(R.id.item_control_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_control_btn);
            btn= (ImageButton) itemView.findViewById(R.id.img_btn);
        }

        public OnItemDeviceClick onItemDeviceClick;

        interface OnItemDeviceClick {
            void onClick(View v, int pos);
        }

        @Override
        public void onClick(View v) {
            try {
                onItemDeviceClick.onClick(v, (Integer) itemView.getTag());
            } catch (Exception e) {
//                Toast.makeText("",TAG);
            }

        }
    }
}
