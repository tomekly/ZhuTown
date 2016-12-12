package com.dongbang.yutian.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.AnnouncementInfo;
import com.dongbang.yutian.adapter.FramingAchievementAdapter;
import com.dongbang.yutian.adapter.FramingKnowledgeAdapter;
import com.dongbang.yutian.beans.ArticleBean;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.common.AppManager;
import com.dongbang.yutian.common.UIhelper;
import com.dongbang.yutian.fragment.BaseFragment;
import com.dongbang.yutian.soap.SoapClient;
import com.dongbang.yutian.soap.SoapParams;
import com.dongbang.yutian.soap.SoapParseUtils;
import com.dongbang.yutian.soap.SoapUtil;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.widget.CustomeRecyclerView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qzp on 2016/12/7.
 */

public class MoreFrement extends BaseFragment implements FramingKnowledgeAdapter.OnClickArticleListener, FramingAchievementAdapter.OnClickAchievementListener {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.home_more)
    CustomeRecyclerView homeMore;
    private View rootView;
    private FramingKnowledgeAdapter adapter;
    private LinkedList<ArticleBean> agriculturalKnowledges = new LinkedList<>();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);
        getAnnouncementByType();
    }

    private void setContentView(int activity_product_tracing) {
    }


    @Override
    protected void lazyload() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        homeMore.setLayoutManager(manager);

        adapter = new FramingKnowledgeAdapter(getActivity(), agriculturalKnowledges);
        adapter.setOnClickArticleListener(this);
        homeMore.setAdapter(adapter);

        homeMore.setAdapter(adapter);
    }

    @Override
    public void onClickAchievement(View view) {
        int position = (int) view.getTag();
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
    }

    @Override
    public void onClickItem(View view) {
        int position = (int) view.getTag();
        ArticleBean agrKnowLedge = agriculturalKnowledges.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("articleBean", agrKnowLedge);
        UIhelper.jumpActivity(getActivity(), AnnouncementInfo.class, bundle);
    }


    /**
     * 请求农业知识
     */
    private void getAnnouncementByType() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams soapParams = new SoapParams();
        soapParams.put("articleType", 6);
        soapParams.put("announcementId", 0);
        soapUtil.call(AppConfig.SERVICE_URL_SYSTEM, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_ANNOUNCEMENT_BY_TYPE, soapParams, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        getImageUrlByType();
                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                        LogUtils.i(TAG, "222 onSuccess:" + bodyIn.toString());
                        LinkedList<ArticleBean> announceList = SoapParseUtils.getAnnounceList(bodyIn.toString());
                        agriculturalKnowledges.clear();
                        agriculturalKnowledges.addAll(announceList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "onFailure:" + e.toString());
//                getImageUrlByType();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
