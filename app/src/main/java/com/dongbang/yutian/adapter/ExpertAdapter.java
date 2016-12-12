package com.dongbang.yutian.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ruiqin on 2016/5/4.
 * 专家诊断viewpage切换适配器
 */
public class ExpertAdapter extends PagerAdapter {
    private List mList;
    private View mView;
    public ExpertAdapter(List p_list, View p_View){
        mList=p_list;
        mView=p_View;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView((View) mList.get(position));
        return mList.get(position);
    }
}
