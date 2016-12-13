package com.dongbang.yutian.retrofit;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by DongBang on 2016/9/22.
 */

public class RetrofitFactory {
    public static final String BASE_URL="http://60.2.131.35:8088/yutian/yingyong/";
    public static final String BASE_URL_1="http://60.2.131.35:5802/Portal.aspx?id=";//产品质量追溯
    private static Retrofit getRetrofit(String url){
       Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static  RetrofitService getRetrofitService(String url){
        return  getRetrofit(url).create(RetrofitService.class);
    }
    private static Retrofit getRetrofit1(String url){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(ScalarsConverterFactory.create()).build();
        return retrofit;
    }
    public static  RetrofitService getRetrofitService1(String url){
        return  getRetrofit(url).create(RetrofitService.class);
    }
}
