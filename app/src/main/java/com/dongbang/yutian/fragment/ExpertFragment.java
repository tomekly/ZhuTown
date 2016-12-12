package com.dongbang.yutian.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.ExpertActivity;
import com.dongbang.yutian.adapter.ExpertListAdapter;
import com.dongbang.yutian.beans.ExpertEntity;
import com.dongbang.yutian.retrofit.RetrofitFactory;
import com.dongbang.yutian.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 专家诊断
 */
public class ExpertFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ExpertListAdapter adapter;
    private View mView;
    private ListView mExpertList;
    private List<Map<String, Object>> list;
    public static ExpertEntity entity;

    @Override
    protected void lazyload() {

    }

    public ExpertFragment() {
    }


    public static ExpertFragment newInstance(String param1, String param2) {
        ExpertFragment fragment = new ExpertFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_expert, container, false);
        mExpertList = (ListView) mView.findViewById(R.id.expert_listview);
        list = new ArrayList();
        adapter = new ExpertListAdapter(getContext(), new ArrayList<ExpertEntity>());
        mExpertList.setAdapter(adapter);
        mExpertList.setOnItemClickListener(this);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestExpertData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ExpertActivity.class);
        ExpertEntity entity = adapter.getItemByIndex(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("expert", entity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 请求数据
     */
    private void requestExpertData() {
        Call<List<ExpertEntity>> call = RetrofitFactory.getRetrofitService(RetrofitFactory.BASE_URL).getExpertList("yutian", "index", "getZjList");
        call.enqueue(new Callback<List<ExpertEntity>>() {
            @Override
            public void onResponse(Call<List<ExpertEntity>> call, Response<List<ExpertEntity>> response) {
                adapter.clear();
                adapter.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<ExpertEntity>> call, Throwable t) {
                LogUtils.d(TAG, "onFailure" + t.getMessage());
            }
        });
    }


}
