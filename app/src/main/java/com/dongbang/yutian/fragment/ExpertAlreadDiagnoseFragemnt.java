package com.dongbang.yutian.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.ExpertActivity;
import com.dongbang.yutian.adapter.DiagnosisRecordsAdapter;
import com.dongbang.yutian.beans.DiagnosticLogOutEntity;
import com.dongbang.yutian.beans.ExpertReplyDate;
import com.dongbang.yutian.beans.ResponseDiagnosticLogOutEntity;
import com.dongbang.yutian.retrofit.RetrofitFactory;
import com.dongbang.yutian.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruiqin on 2016/5/5
 * 已经诊断的界面.
 */
public class ExpertAlreadDiagnoseFragemnt extends BaseFragment {
    //所有问题集合
    private List<ExpertReplyDate> expertReplyDates;
    private List list;
    //获取到的数据是否为空
    private boolean isReplyDateNULL = false;
    private int consultId;
    private DiagnosisRecordsAdapter adapter ;
    private List<ResponseDiagnosticLogOutEntity.UnsolvedBean> entities=new ArrayList<>();
    private boolean isPrepared=false;
    @Override
    protected void lazyload() {
        if (isPrepared&&isVisible){
            requestDiagnosticLogOut();
        }
    }

    public ExpertAlreadDiagnoseFragemnt(){}
    public static Fragment newInstance(){
        ExpertAlreadDiagnoseFragemnt fragemnt=new ExpertAlreadDiagnoseFragemnt();
        return fragemnt;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expert_alread_diagnose, container,false);
        consultId = 1;
        expertReplyDates = new ArrayList<>();
        isPrepared=true;
        initListView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestDiagnosticLogOut();
    }

    private void initListView(View view) {
        ListView listView01 = (ListView) view.findViewById(R.id.expert_alread_listview01);
        adapter =new DiagnosisRecordsAdapter(getContext(),entities);
        listView01.setAdapter(adapter);
    }

    Call<ResponseDiagnosticLogOutEntity> call;
    private  void requestDiagnosticLogOut(){
        call = RetrofitFactory.getRetrofitService(RetrofitFactory.BASE_URL).getDiagnosticLogOut(ExpertActivity.entity.getId());
        call.enqueue(new Callback<ResponseDiagnosticLogOutEntity>() {
            @Override
            public void onResponse(Call<ResponseDiagnosticLogOutEntity> call, Response<ResponseDiagnosticLogOutEntity> response) {
                ResponseDiagnosticLogOutEntity rdloe=response.body();
                adapter.addAll(rdloe.getSolved());
            }
            @Override
            public void onFailure(Call<ResponseDiagnosticLogOutEntity> call, Throwable t) {
                LogUtils.d(TAG,t.getMessage(),t);
            }
        });
    }


}
