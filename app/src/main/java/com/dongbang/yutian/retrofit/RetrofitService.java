package com.dongbang.yutian.retrofit;

import com.dongbang.yutian.beans.ExpertEntity;
import com.dongbang.yutian.beans.MessageEntity;
import com.dongbang.yutian.beans.ProductInfoEntity;
import com.dongbang.yutian.beans.ResponseDiagnosticLogOutEntity;
import com.dongbang.yutian.beans.VersionBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DongBang on 2016/9/22.
 */

public interface RetrofitService {
    @GET("index.php/")
    Call<List<ExpertEntity>> getExpertList(@Query("m") String m,@Query("c") String index,@Query("a") String flag);

    @FormUrlEncoded
    @POST("index.php?m=yutian&c=index&a=addQaq")
    Call<MessageEntity> diagnoseAdd(@Field("id") String id,@Field("title") String title,@Field("content") String content,@Field("address") String address ,@Field("category") String category);

    @GET("index.php?m=yutian&c=index&a=getQaqById")
    Call<ResponseDiagnosticLogOutEntity> getDiagnosticLogOut(@Query("id")String id);




    @GET("index.php?m=yutian&c=index&a=getInfoByCode")
    Call<ProductInfoEntity> getAsCendContent(@Query("code") String code);
    //登录接口
    @FormUrlEncoded
    @POST("index.php?m=yutian&c=index&a=ytLogin")
    Call<MessageEntity> requestLogin(@Field("username") String username,@Field("password") String  password);

    @GET("index.php?m=yutian&c=index&a=getVersion")
    Call<VersionBean> getNewVersion();


}
