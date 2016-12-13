package com.dongbang.yutian.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.ExpertEntity;
import com.dongbang.yutian.fragment.ExpertAlreadDiagnoseFragemnt;
import com.dongbang.yutian.fragment.ExpertWaitDiagnoseFragemnt;
import com.dongbang.yutian.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 专家诊断 记录
 */
public class ExpertActivity extends BaseActivity {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @Bind(R.id.layout_coordinator)
    CoordinatorLayout layoutCoordinator;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.activity_main_viewpage)
    ViewPager mViewPager;
    //可供切换的VIEW数组
    private int primaryColor;
    public static ExpertEntity entity;
    private  String titles[]={"已诊断信息","未诊断信息"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);
        ButterKnife.bind(this);
        primaryColor = getResources().getColor(R.color.colorPrimary);
        initToolbar(toolbar, "", primaryColor);
        tvTitle.setText("专家诊断");
        initData();
        setStatusBar();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        entity = (ExpertEntity) getIntent().getExtras().getSerializable("expert");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        ExpertInfoAdapter adapter = new ExpertInfoAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(mViewPager,true);
        mViewPager.setOffscreenPageLimit(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expert_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.expert_menu_add) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("expert", entity);
            ToastUtils.UIHelper.showActivity(this, ExpertAddQuestionActivity.class, bundle);
        }
        return super.onOptionsItemSelected(item);
    }


    public class ExpertInfoAdapter extends FragmentStatePagerAdapter {

        public ExpertInfoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return ExpertAlreadDiagnoseFragemnt.newInstance();
            }
            if (position == 1) {
                return ExpertWaitDiagnoseFragemnt.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
