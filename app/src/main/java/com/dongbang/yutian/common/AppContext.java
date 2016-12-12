package com.dongbang.yutian.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by DongBang on 2016/10/10.
 */

public class AppContext extends Application {

    private static  AppContext mInstance=null;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;

    }

    public static  AppContext getInstance(){

        return  mInstance;
    }

    public Context getContext(){
        return  mInstance.getApplicationContext();
    }

}
