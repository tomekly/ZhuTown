package com.dongbang.yutian.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DongBang on 2016/5/11.
 */
public class DateUtils
{
    //把日期转为字符串
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return df.format(date);
    }
    //把字符串转为日期
    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(strDate);
    }



    /**
     * 毫秒转换成String 日期
     * 格式 yyyy-MM-dd hh :mm:ss
     * @param ms
     * @return
     */
    public static String msToDate(long  ms)
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        return  formatter.format(calendar.getTime());
    }

    public static String msToSimpleDate(long  ms)
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        return  formatter.format(calendar.getTime());
    }

    /**
     *获取当前时间
     * 格式：yyyy-MM-dd
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


    /**
     * 获取当月第一天
     * @return
     */
    public static Date getFirstdayofThisMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }


    public static String getSubDate(String date) {
        return date.substring(0, 10);

    }

    public static String getSubDate(String date, int beginIndex, int endIndex) {
        return date.substring(beginIndex, endIndex);
    }
}
