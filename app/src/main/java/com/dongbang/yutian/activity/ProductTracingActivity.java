package com.dongbang.yutian.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.common.AppManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 芮勤 on 2016/5/5.
 */
public class ProductTracingActivity extends BaseActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private Context mContext;
    private Toolbar mToolbar;
    private TextInputEditText mEditText;
    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_product_tracing);
        ButterKnife.bind(this);
        initView();
        initToolbar(mToolbar, "我的", R.color.colorPrimary);
    }

    private void initView() {
//        tvTitle.setText(AppConfig.getUser.getUser.getName);
    }
}
