package com.dongbang.yutian.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dongbang.yutian.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruiqin on 2016/5/5

 */
public class ExpertDatebaseFragemnt extends BaseFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("qin","oncreat");
    }

    @Override
    protected void lazyload() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("qin","view");
        View view = inflater.inflate(R.layout.expert_datebase,container);
        ListView listView01= (ListView) view.findViewById(R.id.expert_datebase_listview01);
        List list=new ArrayList<Map<String,String>>();
        for(int i=0;i<100;i++){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("image",R.drawable.test);
            map.put("post","专家职称：院士");
            map.put("territory","擅长领域：农情");
            map.put("resume","个人简历：xxxxxxxxxxxx");


            list.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),list,R.layout.expert_datebase_item,
                new String[]{"image","post","territory","resume"},
                new int[]{R.id.expert_datebase_item_img01,R.id.expert_datebase_item_txt01,R.id.expert_datebase_item_txt02,R.id.expert_datebase_item_txt03});

        listView01.setAdapter(adapter);
        Log.i("qin","adpter");
        return view;
    }

}
