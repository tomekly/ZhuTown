package com.dongbang.yutian.view;

/**
 * Created by DongBang on 2016/6/28.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.dongbang.yutian.R;


/**
 * @ Class Describe ：
 * @ Created by Gordo on 2016/3/24.
 * @ Modefy by Gordo on 2016/3/24
 */
public class LoadingDialog extends AlertDialog {
    private TextView tips_loading_msg;
    private String message = null;
    private String msg_load_ing = "加载中";

    public LoadingDialog(Context context) {
        super(context);
        message = msg_load_ing;
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
        this.setCancelable(false);

    }

    public LoadingDialog(Context context, int themeResId, String message) {
        super(context, themeResId);
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_loading_dialog);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    /**
     * 设置数据
     *
     * @param message
     */
    public void setText(CharSequence message) {
        this.message = (String) message;
        tips_loading_msg.setText(this.message);
    }

    /**
     * 设置数据
     *
     * @param resId
     */
    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

    public void setTextVisibility(int visibility) {

        tips_loading_msg.setVisibility(visibility);
    }
}