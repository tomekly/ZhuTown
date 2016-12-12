package com.dongbang.yutian.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.AnnouncementInfo;
import com.dongbang.yutian.activity.CommonScanActivity;
import com.dongbang.yutian.activity.ProductInfoActivity;
import com.dongbang.yutian.activity.RepositoryActivity;
import com.dongbang.yutian.adapter.FramingAchievementAdapter;
import com.dongbang.yutian.adapter.FramingKnowledgeAdapter;
import com.dongbang.yutian.beans.ArticleBean;
import com.dongbang.yutian.beans.ScanResult;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.common.UIhelper;
import com.dongbang.yutian.soap.SoapClient;
import com.dongbang.yutian.soap.SoapParams;
import com.dongbang.yutian.soap.SoapParseUtils;
import com.dongbang.yutian.soap.SoapUtil;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;
import com.dongbang.yutian.widget.CustomeRecyclerView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页页面
 */

public class HomePageFragment extends BaseFragment implements FramingKnowledgeAdapter.OnClickArticleListener, FramingAchievementAdapter.OnClickAchievementListener {
    private final String TAG = this.getClass().getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.edt_search)
    EditText edtSearch;
    @Bind(R.id.homepage_more)
    TextView homepageMore;
    @Bind(R.id.recycler_framing_knowledge)
    CustomeRecyclerView recyclerFramingKnowledge;
    @Bind(R.id.homepage_achievement_more)
    TextView homepageAchievementMore;
    @Bind(R.id.layout_relative_framing_achievement)
    RelativeLayout layoutRelativeFramingAchievement;
    @Bind(R.id.recycler_framing_achievement)
    CustomeRecyclerView recyclerFramingAchievement;

    private View rootView;
    private String mParam1;
    private String mParam2;
    private FramingKnowledgeAdapter adapter;

    @Override
    protected void lazyload() {

    }

    public HomePageFragment() {
    }

    /**
     * 首页Fragment设置
     * @param param1
     * @param param2
     * @return
     */
    public static HomePageFragment newInstance(String param1, String param2) {
        //首页两个
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        }
        ButterKnife.bind(this, rootView);
        mContext = getActivity();
        getAnnouncementByType();

        return rootView;
    }

    private FramingAchievementAdapter achievementAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity());
        recyclerFramingKnowledge.setLayoutManager(manager);
        recyclerFramingAchievement.setLayoutManager(manager2);

        adapter = new FramingKnowledgeAdapter(getActivity(), agriculturalKnowledges);
        adapter.setOnClickArticleListener(this);
        recyclerFramingKnowledge.setAdapter(adapter);

        recyclerFramingAchievement.setAdapter(adapter);

//        achievementAdapter = new FramingAchievementAdapter(getActivity(), imageUrls);
//        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

//        recyclerFramingAchievement.setLayoutManager(manager);
//        recyclerFramingAchievement.setAdapter(adapter);



//        adapter = new FramingKnowledgeAdapter(getActivity(), agriculturalKnowledges);
//        adapter.setOnClickArticleListener(this);




//        recyclerFramingAchievement.setAdapter(achievementAdapter);
//        achievementAdapter.setOnClickAchievementListener(this);
//        recyclerFramingAchievement.setLayoutManager(staggeredGridLayoutManager);
//        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
//        recyclerFramingAchievement.addItemDecoration(decoration);
    }


    private LinkedList<ArticleBean> agriculturalKnowledges = new LinkedList<>();
//    private LinkedList<ImageUrl> imageUrls = new LinkedList<>();
//
//    private void getImageUrl(String url) {
//        ImageUrl imageUrl = new ImageUrl();
//        imageUrl.setUrl(AppConfig.SERVICE_IMG_URL + url);
//        imageUrls.add(imageUrl);
//        LogUtils.d("TAG2", imageUrl.getUrl());
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
//    R.id.homepage_more 农业知识 更多

    /**
     * 主页更多
     * @param view
     */
    @OnClick({R.id.img_search, R.id.homepage_more,R.id.homepage_achievement_more, R.id.img_erweima})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_search:
                String code = edtSearch.getText().toString();
                if (code.length() < 1) {
                    ToastUtils.showShort(getActivity(), "请输入或扫描追溯码");
                    return;
                }
                ScanResult.setResult(code);
                UIhelper.jumpActivity(getActivity(), ProductInfoActivity.class);
                break;
            case R.id.homepage_more:
                UIhelper.jumpActivity(getActivity(), RepositoryActivity.class);
                break;
            case R.id.homepage_achievement_more:
                //更多
//                ToastUtils.showShort(getActivity(), getResources().getString(R.string.tips_msg_developing));
//                UIhelper.jumpActivity(getActivity(), MoreFrement.class);
                UIhelper.jumpActivity(getActivity(), RepositoryActivity.class);
                break;

            case R.id.img_erweima:

                    UIhelper.jumpActivity(getActivity(), CommonScanActivity.class);

        }
    }

    @Override
    public void onClickItem(View view) {
        int position = (int) view.getTag();
        ArticleBean agrKnowLedge = agriculturalKnowledges.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("articleBean", agrKnowLedge);
        UIhelper.jumpActivity(getActivity(), AnnouncementInfo.class, bundle);
    }

    @Override
    public void onClickAchievement(View view) {
        int position = (int) view.getTag();
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
//        ImageView imgView = getImageView(position);
////        dialog.setView(imgView);
//        dialog.addContentView(imgView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000));
//
//        imgView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
    }

//    private ImageView getImageView(int position) {
//        ImageView imgView = new ImageView(getActivity());
//        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Glide.with(mContext).load(imageUrls.get(position).getUrl()).placeholder(R.mipmap.hp_img_achievement).crossFade().into(imgView);
//        return imgView;
//    }

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

//    /**
//     * 获取风采展示的图片
//     */
//    private void getImageUrlByType() {
//        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
//        soapUtil.setTimeout(AppConfig.TIME_OUT);
//        SoapParams soapParams = new SoapParams();
//        soapParams.put("articleType", 10);
//        soapParams.put("announcementId", 0);
//        soapUtil.call(AppConfig.SERVICE_URL_SYSTEM, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_ANNOUNCEMENT_BY_TYPE, soapParams, new SoapClient.ISoapUtilCallback() {
//            @Override
//            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
//                ((Activity) mContext).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
//                        LogUtils.i(TAG, "256 onSuccess:" + bodyIn.toString());
//                        LinkedList<ArticleBean> announceList = SoapParseUtils.getAnnounceList(bodyIn.toString());
//                        imageUrls.clear();
//                        for (String url : announceList.get(0).getNoticeImg().split(";")) {
//                            getImageUrl(url);
//                            LogUtils.d("TAG2", url);
//                        }
//                        achievementAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                LogUtils.e("TAG2", "onFailure:" + e.toString());
//            }
//        });
//    }


}
