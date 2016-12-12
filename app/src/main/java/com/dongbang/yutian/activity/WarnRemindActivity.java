package com.dongbang.yutian.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.dongbang.yutian.R;

/**
 * Created by DongBang on 2016/5/8.
 */
public class WarnRemindActivity extends BaseActivity
{
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_remind);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar,"预警提醒",R.color.colorPrimary);
    }
}
