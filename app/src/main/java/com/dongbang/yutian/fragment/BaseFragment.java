package com.dongbang.yutian.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongbang.yutian.R;
import com.dongbang.yutian.utils.statusbar.StatusBarUtil;
import com.dongbang.yutian.view.LoadingDialog;

/**
 * Created by DongBang on 2016/6/13.
 */
public abstract class BaseFragment extends Fragment {
    public static String TAG;
    public Context mContext;
    private LoadingDialog dialog = null;
    protected boolean isVisible = false;

    protected abstract void lazyload();

    protected void onVisible() {
        lazyload();
    }

    protected void onInvisible() {
    }

    public BaseFragment() {
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TAG = BaseFragment.this.getClass().getSimpleName();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void showDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog(getActivity());
        }
        dialog.show();
    }

    public void dissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
