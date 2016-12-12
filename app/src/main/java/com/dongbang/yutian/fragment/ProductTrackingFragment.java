package com.dongbang.yutian.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.activity.CommonScanActivity;
import com.dongbang.yutian.activity.ProductInfoActivity;
import com.dongbang.yutian.beans.Name;
import com.dongbang.yutian.beans.ScanResult;
import com.dongbang.yutian.beans.StationInfo;
import com.dongbang.yutian.common.UIhelper;
import com.dongbang.yutian.retrofit.RetrofitFactory;
import com.dongbang.yutian.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 产品追溯
 */

public class ProductTrackingFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    @Bind(R.id.product_edt)
//    AppCompatEditText productEdt;
//    @Bind(R.id.product_scan)
//    Button productScan;
//    @Bind(R.id.product_confirm)
//    Button productConfirm;
    @Bind(R.id.text_name)
    TextView text_name;
    private String mParam1;
    private String mParam2;

    private View rootView;

    @Override
    protected void lazyload() {

    }

    public ProductTrackingFragment() {
    }


    public static ProductTrackingFragment newInstance(String param1, String param2) {
        ProductTrackingFragment fragment = new ProductTrackingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_prodouct_tracking, container, false);
        }
        ButterKnife.bind(this, rootView);
        initview();
        return rootView;
    }

    private void initview() {
        text_name.setText("yy258");
//        text_name.setText(RetrofitFactory.class.getName().toString());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
//
//    @OnClick({R.id.product_scan, R.id.product_confirm})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.product_scan:
//                UIhelper.jumpActivity(getActivity(), CommonScanActivity.class);
//                break;
//            case R.id.product_confirm:
//                String code = productEdt.getText().toString();
//                if (code.length() < 1) {
//                    ToastUtils.showShort(getActivity(), "请输入或扫描追溯码");
//                    return;
//                }
//                ScanResult.setResult(code);
//                UIhelper.jumpActivity(getActivity(), ProductInfoActivity.class);
//                break;
//        }
//    }
}
