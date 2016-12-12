package com.dongbang.yutian.view;

import android.app.Application;
import android.content.Context;


/**
 * Created by adu on 16/5/3.
 */
public class YqzjApplication extends Application {
    private static final String TAG = YqzjApplication.class.getSimpleName();
    private static YqzjApplication mInstance;
    /**
     * Android volley http的请求队列
     */
//    private static RequestQueue mRequestQueue;

    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        //自定义拦截程序所有异常
//        UnCeHandler catchExcep = new UnCeHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

        mContext = this;
        if (mInstance == null)
            mInstance = this;

//        CrashHandler.getInstance().init(this);

    }

    public static YqzjApplication getInstance() {
        return mInstance;
    }

    /**
     * 创建volley HTTPRequest的请求队列
     *
     *
     */
//    public static RequestQueue getRequestQueue(Context context) {
//        // lazy initialize the request queue, the queue instance will be
//        // created when it is accessed for the first time
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(context);
//        }
//        return mRequestQueue;
//    }

}
