package com.dongbang.yutian.utils;

import android.util.Log;

/**
 * Created by 黄海全 on 2016/5/4.
 * 描述： Log工具包
 */
public class LogUtils
{
    public static boolean isLog = true;

    public static void i(String tag, String msg)
    {
        if (isLog)
        {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag,String msg,Throwable tr){
        if (isLog)
        {
            Log.i(tag,msg,tr);
        }
    }

    public static void d(String tag, String msg)
    {
        if (isLog)
        {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag,String msg,Throwable tr){
        if (isLog)
        {
            Log.d(tag,msg,tr);
        }
    }
    public static void e(String tag, String msg)
    {
        if (isLog)
        {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag,String msg,Throwable tr){
        if (isLog)
        {
            Log.e(tag,msg,tr);
        }
    }
    public static void v(String tag, String msg)
    {
        if (isLog)
        {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag,String msg,Throwable tr){
        if (isLog)
        {
            Log.v(tag,msg,tr);
        }
    }


}
