package com.dongbang.yutian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dongbang.yutian.activity.MainActivity;

/**
 * Created by 黄海全 on 2016/5/4.
 * 描述： Toast封装工具类
 */
public class ToastUtils {
    public static void showShort(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void showCustomDuration(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }


    public static class UIHelper {
        public static void showHome(Activity activity) {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }

        public static void showActivity(Activity activity, Class<?> cls) {
            Intent intent = new Intent(activity, cls);
            activity.startActivity(intent);
        }

        public static void showActivity(Activity activity, Class<?> cls, Bundle bundle) {
            Intent intent = new Intent(activity, cls);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }


        public static void jumpActivityBackResult(Activity activity,Class<?> cla,int requestCode){
            Intent intent=new Intent(activity,cla);
            activity.startActivityForResult(intent,requestCode);
        }

    }

}
