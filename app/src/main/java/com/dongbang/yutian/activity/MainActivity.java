package com.dongbang.yutian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.common.AppManager;
import com.dongbang.yutian.fragment.EnvironmentalDataFragment;
import com.dongbang.yutian.fragment.ExpertFragment;
import com.dongbang.yutian.fragment.HomePageFragment;
import com.dongbang.yutian.fragment.ProductTrackingFragment;
import com.dongbang.yutian.fragment.VideoControlFragment;
import com.dongbang.yutian.utils.statusbar.StatusBarUtil;
import com.dongbang.yutian.widget.BottomNavigationBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.nav_bar)
    BottomNavigationBar navBar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.layout_coordinator)
    CoordinatorLayout layoutCoordinator;
    @Bind(R.id.layout_frame)
    FrameLayout layoutFrame;
    @Bind(R.id.layout_relative)
    RelativeLayout layoutRelative;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomePageFragment homePageFragment;
    private EnvironmentalDataFragment environmentalDataFragment;
    private VideoControlFragment videoControlFramgnet;
    private ExpertFragment expertFragment;
    private ProductTrackingFragment productTrackingFragment;
    private boolean isTransparent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar(R.id.toolbar, R.id.tv_title, 0);
        setToolbarTitle(getResources().getString(R.string.title_msg_farming_iot_system));
        initView();
        setNavigationBar();
        checkNewVersion();
    }

    /**
     * View初始化
     */
    private void initView() {
        TAG = this.getClass().getSimpleName();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance("首页", String.valueOf(0));
        }
        ft.replace(R.id.layout_frame, homePageFragment);
        ft.commit();
        layoutCoordinator.setVisibility(View.GONE);



    }

    @Override
    protected void onStart() {
        super.onStart();
        setStatusBar();
    }

    private void setNavigationBar() {
        navBar.addItemView(getResources().getString(R.string.nav_msg_homepage), R.mipmap.nav_img_homepage_select, R.mipmap.nav_img_homepage_normal);
        navBar.addItemView(getResources().getString(R.string.nav_msg_environmental_data), R.mipmap.nav_img_environmental_data_select, R.mipmap.nav_img_environmental_data_normal);
        navBar.addItemView(getResources().getString(R.string.nav_msg_video_control), R.mipmap.nav_img_video_control_select, R.mipmap.nav_img_video_control_normal);
        navBar.addItemView(getResources().getString(R.string.nav_msg_expert), R.mipmap.nav_img_expert_select, R.mipmap.nav_img_expert_normal);
        navBar.addItemView(getResources().getString(R.string.nav_msg_product_tracking), R.mipmap.nav_img_product_tracking_select, R.mipmap.nav_img_product_tracking_normal);
        navBar.setBackgroundColor(getResources().getColor(android.R.color.white));
        navBar.setOnItemViewSelectedListener(new BottomNavigationBar.OnItemViewSelectedListener() {
            @Override
            public void onItemClcik(View v, int index) {

                ft = fm.beginTransaction();
                layoutCoordinator.setVisibility(0 == index
                        ? View.GONE
                        : View.VISIBLE);
                isTransparent = (0 == index);
                setStatusBar();
                if (0 == index) {
                    layoutRelative.setBackgroundResource(R.mipmap.hp_framing_header);
                    if (homePageFragment == null) {
                        homePageFragment = HomePageFragment.newInstance("首页", String.valueOf(index));
                    }
                    if (videoControlFramgnet != null) {
                        videoControlFramgnet.videoStop();
                    }
                    ft.replace(R.id.layout_frame, homePageFragment);
                }
                if (1 == index)  //环境数据
                {
                    if (environmentalDataFragment == null) {
                        environmentalDataFragment = new EnvironmentalDataFragment();
                    }
                    if (videoControlFramgnet != null) {
                        videoControlFramgnet.videoStop();
                    }
                    ft.replace(R.id.layout_frame, environmentalDataFragment);
                }
                if (2 == index) {
                    if (videoControlFramgnet == null) {
                        videoControlFramgnet = VideoControlFragment.newInstance("视频控制", String.valueOf(index));
                    }

                    ft.replace(R.id.layout_frame, videoControlFramgnet);
                }
                if (3 == index) {
                    if (expertFragment == null) {
                        expertFragment = ExpertFragment.newInstance("专家诊断", String.valueOf(index));
                    }
                    if (videoControlFramgnet != null) {
                        videoControlFramgnet.videoStop();
                    }
                    ft.replace(R.id.layout_frame, expertFragment);
                }
                if (4 == index) {
                    if (productTrackingFragment == null) {
                        productTrackingFragment = ProductTrackingFragment.newInstance("我的", String.valueOf(index));
                    }
                    if (videoControlFramgnet!=null){
                        videoControlFramgnet.videoStop();
                    }
                    ft.replace(R.id.layout_frame, productTrackingFragment);
                }
                ft.commit();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        videoControlFramgnet.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        if (isTransparent) {

            StatusBarUtil.setTransparent(this);
        } else {
            layoutRelative.setBackgroundColor(getResources().getColor(R.color.color_458F14));
            StatusBarUtil.setColor(this, getResources().getColor(R.color.color_458F14));
            StatusBarUtil.setTranslucent(this);
        }

    }

}
