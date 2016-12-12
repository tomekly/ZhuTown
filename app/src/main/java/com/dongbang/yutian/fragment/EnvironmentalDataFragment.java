package com.dongbang.yutian.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dongbang.yutian.R;
import com.dongbang.yutian.widget.OnTabSelectListener;
import com.dongbang.yutian.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 智能控制和信息采集
 * A simple {@link Fragment} subclass.
 */
public class EnvironmentalDataFragment extends BaseFragment implements OnTabSelectListener {
    private String[] mTitles;
    private List<Fragment> mFragments;
    private MyPagerAdapter adapter;
    private View rootView;
    private ImageButton imgbtn;

    @Override
    protected void lazyload() {

    }

    public EnvironmentalDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_environmental_data, container, false);
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            mTitles = getResources().getStringArray(R.array.environmental_data_title);
        }
        addFragment();
        initView();
    }

    private void initView() {
        ViewPager viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(1);
        SlidingTabLayout tablayout = (SlidingTabLayout) rootView.findViewById(R.id.layout_sliding_tab);
        tablayout.setViewPager(viewpager);
        tablayout.setOnTabSelectListener(this);
        viewpager.setCurrentItem(0);
    }

    /**
     * 设置数据采集和智能控制
     */
    private void addFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(DataCollectionFragment.getInstance(mTitles[0]));
        mFragments.add(DataAnalysisFragment.getInstance(mTitles[1]));
//        mFragments.add(DataAggregationFragment.getInstance(mTitles[2]));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onTabSelect(int position) {  // Tab选择触发该方法
    }

    @Override
    public void onTabReselect(int position) {
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }
}
