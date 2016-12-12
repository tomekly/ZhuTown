package com.dongbang.yutian.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dongbang.yutian.R;


/**
 * Created by root on 16-10-18.
 */

public class UIhelper {


    public static void jumpActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public static void jumpActivity(Activity activity, Class<?> cls, Bundle bundle){
        Intent intent =new Intent(activity,cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
    }



    public static void jumpActivityFinish(Activity activity,Class<?> cls){
        Intent intent=new Intent(activity,cls);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
    }
    public static void jumpActivityForResult(Activity activity,Class<?> cls,int requestCOde){
        Intent intent =new Intent(activity,cls);
        activity.startActivityForResult(intent,requestCOde);
        activity.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
    }

    public static void finishActivity(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_right_out,R.anim.slide_left_in);
    }

}
