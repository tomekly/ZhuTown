package com.dongbang.yutian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.dongbang.yutian.R;


/**
 * 未登录提示框
 * Created by DongBang on 2016/7/11.
 */
public class DialogUtils {

    public static void tipsDialog(final Context context, String message, final Class<?> cla) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,context.getResources().getString(R.string.toolkit_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.toolkit_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ToastUtils.UIHelper.showActivity((Activity) context, cla);
            }
        });

        alertDialog.show();
    }

    public void domDialog(Context context, String tips, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(tips);
        dialog.setMessage(msg);
        /**
         * 确定
         */
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.toolkit_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogListener.onPositive(dialog, which);
            }
        });
        /**
         * 取消
         */
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getResources().getString(R.string.toolkit_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogListener.onNegative(dialog, which);
            }
        });
        dialog.show();
    }


    public void domConfirmDialog(Context context, String tips, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(tips);
        dialog.setMessage(msg);
        /**
         * 确定
         */
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.toolkit_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onConfirmDialogListener.onPositive(dialog, which);
            }
        });
        dialog.show();
    }


    private OnDialogListener onDialogListener;

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
    public interface OnDialogListener {
        void onNegative(DialogInterface dialog, int which);
        void onPositive(DialogInterface dialog, int which);
    }
    private OnConfirmDialogListener onConfirmDialogListener;
    public void setOnConfirmDialogListener(OnConfirmDialogListener onConfirmDialogListener){
        this.onConfirmDialogListener=onConfirmDialogListener;
    }

    public interface OnConfirmDialogListener {

        void onPositive(DialogInterface dialog, int which);
    }



}
