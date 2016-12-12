package com.dongbang.yutian.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dongbang.yutian.R;
import com.dongbang.yutian.adapter.FramingAchievementAdapter;
import com.dongbang.yutian.beans.ArticleBean;
import com.dongbang.yutian.beans.ImageUrl;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.soap.SoapClient;
import com.dongbang.yutian.soap.SoapParams;
import com.dongbang.yutian.soap.SoapParseUtils;
import com.dongbang.yutian.soap.SoapUtil;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.widget.CustomeRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThearActivity extends BaseActivity {

    @Bind(R.id.recyclerViewThear)
    CustomeRecyclerView recyclerviewThear;
    private LinkedList<ImageUrl> imageUrls = new LinkedList<>();
    private FramingAchievementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thear);
        ButterKnife.bind(this);
        initView();
        setListener();
    }

    private void initView() {
        initToolBar(R.id.toolbar,R.id.tv_title,R.mipmap.back);
        setToolbarTitle(getResources().getString(R.string.framing_achievement));
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager( 3,StaggeredGridLayoutManager.VERTICAL);
        recyclerviewThear.setLayoutManager(manager);
        adapter =new FramingAchievementAdapter(context,imageUrls);
        recyclerviewThear.setAdapter(adapter);
    }


    private void setListener() {

    }


    @Override
    protected void onStart() {
        super.onStart();
        getImageUrlByType();
    }

    /**
     * 获取风采展示的图片
     */
    private void getImageUrlByType() {
        showDialog();
        SoapUtil soapUtil = SoapUtil.getInstance(context);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams soapParams = new SoapParams();
        soapParams.put("articleType", 10);
        soapParams.put("announcementId", 0);
        soapUtil.call(AppConfig.SERVICE_URL_SYSTEM, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_ANNOUNCEMENT_BY_TYPE, soapParams, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dissDialog();
                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                        LogUtils.i(TAG, "onSuccess:" + bodyIn.toString());
                        LinkedList<ArticleBean> announceList = SoapParseUtils.getAnnounceList(bodyIn.toString());
                        imageUrls.clear();
                        for (String url : announceList.get(0).getNoticeImg().split(";")) {
                            getImageUrl(url);
                            LogUtils.d("TAG2", url);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                dissDialog();
                LogUtils.e("TAG2", "onFailure:" + e.toString());
            }
        });
    }

    private void getImageUrl(String url) {
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setUrl(AppConfig.SERVICE_IMG_URL + url);
        imageUrls.add(imageUrl);
        LogUtils.d("TAG2", imageUrl.getUrl());
    }
}
