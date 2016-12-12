package com.dongbang.yutian.utils;

/**
 * Created by DongBang on 2016/7/14.
 */
public class StringUtils {
    public static  String isEmpty(String location){
        if (location==null||location.equals("")||location.length()==0){
            return "0";
        }else {
            return location;
        }
    }

    public static String isStrEmpty(String text){
        if (text==null||text.equals("")||text.length()==0){
            return " ";
        }else {
            return text;
        }
    }

    public static boolean isBooEmpty(String text){
        if (text==null||text.equals("")||text.length()==0){
            return true;
        }else {
            return false;
        }
    }

}
