package com.dongbang.yutian.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.ArticleBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DongBang on 2016/5/8.
 */
public class AnnouncementInfo extends BaseActivity {

    @Bind(R.id.content_title)
    TextView contentTitle;
    @Bind(R.id.content_con)
    TextView contentCon;
    private ArticleBean articleBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        ButterKnife.bind(this);
        initData();

        initToolBar(R.id.toolbar, R.id.tv_title, R.mipmap.back);
        setToolbarTitle("文章详情");
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        articleBean = (ArticleBean) bundle.getSerializable("articleBean");
    }



    @Override
    protected void onStart() {
        super.onStart();
        contentTitle.setText(articleBean.getNoticeContent());
        contentCon.setText("        "+articleBean.getDescription());
    }
}
