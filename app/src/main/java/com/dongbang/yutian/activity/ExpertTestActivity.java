package com.dongbang.yutian.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.dongbang.yutian.R;
import com.dongbang.yutian.common.AppManager;
import com.dongbang.yutian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 芮勤 on 2016/5/5.
 */
public class ExpertTestActivity extends  BaseActivity {
    private Toolbar mToolbar;
    private Spinner spi_parent;
    private Spinner spi_sub;
    private Context mContext;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_expert_test);
        initView();
        initToolbar(mToolbar,"专家诊断",R.color.colorPrimary);
    }

    private void initView()
    {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        ListView listView01= (ListView) findViewById(R.id.expert_test_listview01);


        SimpleAdapter adapter=new SimpleAdapter(this,getDate(),R.layout.expert_datebase_item,
                new String[]{"image","post","territory","resume"},
                new int[]{R.id.expert_datebase_item_img01,R.id.expert_datebase_item_txt01,R.id.expert_datebase_item_txt02,R.id.expert_datebase_item_txt03});

        listView01.setAdapter(adapter);
        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(ExpertTestActivity.this,"详情功能正在完善中！");
            }
        });
    }

    private List getDate(){
        List list=new ArrayList<Map<String,String>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("image",R.mipmap.expert);
        map.put("post","姓名：崔启志");
        map.put("territory","性别：男");
        map.put("resume","专业：果树专家");

        list.add(map);
        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("image",R.mipmap.expert2);
        map2.put("post","姓名：李立颖");
        map2.put("territory","性别：女 ");
        map2.put("resume","专业：油菜等农作物栽培");

        list.add(map2);
        Map<String,Object> map3=new HashMap<String,Object>();
        map3.put("image",R.mipmap.expert3);
        map3.put("post","姓名：李长增");
        map3.put("territory","性别：男");
        map3.put("resume","专业：园艺");

        list.add(map3);
        Map<String,Object> map4=new HashMap<String,Object>();
        map4.put("image",R.mipmap.expert4);
        map4.put("post","姓名：张海元");
        map4.put("territory","性别：男");
        map4.put("resume","专业：肥料专家");

        list.add(map4);
        Map<String,Object> map5=new HashMap<String,Object>();
        map5.put("image",R.mipmap.expert5);
        map5.put("post","姓名：张运涛");
        map5.put("territory","性别：男");
        map5.put("resume","专业：小麦种植");

        list.add(map5);
        return list;
    }


}
