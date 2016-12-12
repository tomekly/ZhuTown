package com.dongbang.yutian.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dongbang.yutian.R;
import com.dongbang.yutian.adapter.AnnounceListAdapter;
import com.dongbang.yutian.beans.ArticleBean;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.soap.SoapClient;
import com.dongbang.yutian.soap.SoapParams;
import com.dongbang.yutian.soap.SoapParseUtils;
import com.dongbang.yutian.soap.SoapUtil;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedList;

public class RepositoryActivity extends BaseActivity implements AnnounceListAdapter.OnClickArticleListener,SwipeRefreshLayout.OnRefreshListener
{
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private SwipeRefreshLayout layout_swipe_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        TAG=this.getClass().getSimpleName();
        initView();
        initToolbar(mToolbar,"知识库",R.color.colorPrimary);

        layout_swipe_refresh.post(new Runnable() {
            @Override
            public void run()
            {
                layout_swipe_refresh.setRefreshing(true);
                onRefresh();
            }
        });


    }

    private void initView()
    {   mContext=this;
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView= (RecyclerView) findViewById(R.id.repository_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        layout_swipe_refresh= (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        layout_swipe_refresh.setOnRefreshListener(this);
    }


    private void getAnnouncementByType()
    {
        SoapUtil soapUtil=SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams soapParams=new SoapParams();
        soapParams.put("articleType",6);
        soapParams.put("announcementId",0);
        soapUtil.call(AppConfig.SERVICE_URL_SYSTEM, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_ANNOUNCEMENT_BY_TYPE, soapParams, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception
            {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        SoapObject bodyIn= (SoapObject) envelope.bodyIn;
                        LogUtils.i(TAG,"onSuccess:"+bodyIn.toString());
                        LinkedList<ArticleBean> announceList= SoapParseUtils.getAnnounceList(bodyIn.toString());
                        updateUI(announceList);
                    }
                });
            }
            @Override
            public void onFailure(Exception e)
            {
                LogUtils.e(TAG,"onFailure:"+e.toString());
            }
        });
    }

    private LinkedList<ArticleBean> announceList;
    public void updateUI(LinkedList<ArticleBean> announceList)
    {
        this.announceList=announceList;
        AnnounceListAdapter adapter=new AnnounceListAdapter(mContext,announceList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickArticleListener(this);
    }

    @Override
    public void onClickItem(View view)
    {
        int position= (int) view.getTag();
        ArticleBean articleBean=announceList.get(position);
        Bundle bundle=new Bundle();
        bundle.putSerializable("articleBean",articleBean);
        ToastUtils.UIHelper.showActivity(RepositoryActivity.this,AnnouncementInfo.class,bundle);
    }

    @Override
    public void onRefresh()
    {
        layout_swipe_refresh.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                layout_swipe_refresh.setRefreshing(false);
                getAnnouncementByType();
            }
        }, 2000);
    }
}
