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
import com.dongbang.yutian.activity.ExpertActivity;
import com.dongbang.yutian.adapter.DiagnosisRecordsAdapter;
import com.dongbang.yutian.beans.DiagnosticLogOutEntity;
import com.dongbang.yutian.beans.ResponseDiagnosticLogOutEntity;
import com.dongbang.yutian.retrofit.RetrofitFactory;
import com.dongbang.yutian.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruiqin on 2016/5/5
 * 没有诊断的界面.
 */
public class ExpertWaitDiagnoseFragemnt extends BaseFragment {
    private List<ResponseDiagnosticLogOutEntity.UnsolvedBean> entities = new ArrayList<>();
    private DiagnosisRecordsAdapter adapter;
    Call<ResponseDiagnosticLogOutEntity> call;
    private boolean isPrepared = false;

    @Override
    protected void lazyload() {
        if (isPrepared && isVisible) {
            requestDsInfo();
        }

    }

    public ExpertWaitDiagnoseFragemnt() {
    }

    public static android.support.v4.app.Fragment newInstance() {
        ExpertWaitDiagnoseFragemnt fragemnt = new ExpertWaitDiagnoseFragemnt();
        return fragemnt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("qin", "oncreat");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("qin", "view");
        requestDsInfo();
        View view = inflater.inflate(R.layout.expert_wait_diagnose, container, false);
        ListView listView01 = (ListView) view.findViewById(R.id.expert_wait_listview01);
        adapter = new DiagnosisRecordsAdapter(getContext(), entities);
        listView01.setAdapter(adapter);
        Log.i("qin", "adpter");
        isPrepared = true;
        return view;
    }

    private void requestDsInfo() {
        call = RetrofitFactory.getRetrofitService(RetrofitFactory.BASE_URL).getDiagnosticLogOut(ExpertActivity.entity.getId());
        call.enqueue(new Callback<ResponseDiagnosticLogOutEntity>() {
            @Override
            public void onResponse(Call<ResponseDiagnosticLogOutEntity> call, Response<ResponseDiagnosticLogOutEntity> response) {
                ResponseDiagnosticLogOutEntity rdloe = response.body();
                if (rdloe.getUnsolved()!=null){
                    adapter.addAll(rdloe.getUnsolved());
                }
            }

            @Override
            public void onFailure(Call<ResponseDiagnosticLogOutEntity> call, Throwable t) {
                LogUtils.d(TAG, t.getMessage(), t);
            }
        });
    }
}
