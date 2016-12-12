package com.dongbang.yutian.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dongbang.yutian.R;
import com.dongbang.yutian.adapter.ValueAdapter;
import com.dongbang.yutian.beans.MonitorBean;
import com.dongbang.yutian.beans.StationHistory;
import com.dongbang.yutian.beans.StationInfo;
import com.dongbang.yutian.beans.ValueEntity;
import com.dongbang.yutian.beans.WeatherHistory;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.soap.SoapClient;
import com.dongbang.yutian.soap.SoapParams;
import com.dongbang.yutian.soap.SoapParseUtils;
import com.dongbang.yutian.soap.SoapUtil;
import com.dongbang.yutian.utils.ACache;
import com.dongbang.yutian.utils.AppUtils;
import com.dongbang.yutian.utils.DateUtils;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 数据汇总
 */
public class DataAggregationFragment extends BaseFragment {

    @Bind(R.id.data_agg_one_spinner)
    Spinner dataAggOneSpinner;
    @Bind(R.id.data_agg_two_spinner)
    Spinner dataAggTwoSpinner;
//    @Bind(R.id.data_agg_three_spinner)
//    Spinner dataAggThreeSpinner;

    @Bind(R.id.data_agg_recycler)
    RecyclerView recyclerView;

    private String[] areaName;
    private int[] areaId;
    private int areaPosition = 0;
    private int twoSpinnerPosition = 0;
    private int threeSelectPosition = 0;

    private String selectName;
    private int selectId;

    @Override
    protected void lazyload() {

    }

    public DataAggregationFragment() {
    }


    public static DataAggregationFragment getInstance(String title) {
        DataAggregationFragment fragment = new DataAggregationFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_aggregation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化一些数据
     */
    private void initView() {
        mContext = getActivity();
        areaName = getResources().getStringArray(R.array.area_name);
        areaId = getResources().getIntArray(R.array.area_id);
        selectId = areaId[0];
        selectName = areaName[0];
    }


    @Override
    public void onStart() {
        super.onStart();
        showOnSpinner();
    }

    /**
     * Spinner 数据填充
     * 一级下拉列表填充数据
     */
    private void showOnSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, areaName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAggOneSpinner.setAdapter(adapter);
        dataAggOneSpinner.setSelection(areaPosition);
        dataAggOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaPosition = position;
                selectId = areaId[position];
                selectName = areaName[position];
                getTwoSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Spanner下拉数据
     * 二级下拉列表填充数据
     */
    private MonitorBean monitorBean;

    private void getTwoSpinner() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        //下拉调用接口处
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams params = new SoapParams();
        params.put("areaId", selectId);
        params.put("userId", 3);
        soapUtil.call(AppConfig.SERVICE_URL, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_AREA_CHILDREN, params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                        final LinkedList<MonitorBean> monitorBeens = SoapParseUtils.getMonitorBeans(bodyIn.toString());
                        if (monitorBeens == null || monitorBeens.size() == 0) {
                            ToastUtils.showShort(mContext, "没有下级菜单");
                            return;
                        }
                        LinkedList<String> subAreaName = new LinkedList<>();
                        for (int i = 0; i < monitorBeens.size(); i++) {
                            subAreaName.add(monitorBeens.get(i).getAreaName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, subAreaName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAggTwoSpinner.setAdapter(adapter);
                        dataAggTwoSpinner.setSelection(twoSpinnerPosition);
                        dataAggTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                monitorBean = monitorBeens.get(position);
                                twoSpinnerPosition = position;//当前选择的位置
                                AppConfig.monitorBean = monitorBean;
                                if (selectId == 4 && position != 4 || selectId == 3 || selectId == 2 && position > 2) {

                                    getStationHistoryOfCurrentMonth(monitorBean);
                                } else {

                                    getWeatherHistoryOfCurrentMonth();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        LogUtils.i(TAG, "MonitorBean:" + monitorBeens.get(0).getAreaName() + monitorBeens.toString());
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "获取子列表出错了", e);
            }
        });

    }

    private LinkedList<StationHistory> stationInfos = new LinkedList<>();

    /**
     * 获取当前站点的历史数据
     *
     * @param monitorBean
     */
    public void getStationHistoryOfCurrentMonth(MonitorBean monitorBean) {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams params = new SoapParams();
//        params.put("beginTime", DateUtils.getSubDate(DateUtils.ConverToString(DateUtils.getFirstdayofThisMonth())));
//        params.put("endTime",DateUtils.getCurrentTime());
        params.put("beginTime", "2016-05-01");
        params.put("endTime", "2016-05-31");

        params.put("stationId", monitorBean.getLocation());
        soapUtil.call(AppConfig.SERVICE_URL_DATA, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_GW_HISTORY, params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                        stationInfos = SoapParseUtils.getStationHistoryInfos(bodyIn.toString());
                        displayOptionName();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "获取数据错误" + e.toString());
            }
        });
    }


    /**
     * 操作项
     */
    private float value[][];
    private String[] stationOptionName;

    /**
     * 显示数据
     */
    private void displayOptionName() {
        if (selectId == 4 && twoSpinnerPosition != 4 || selectId == 3) {
            stationOptionName = getResources().getStringArray(R.array.stationOptionName);
        } else {
            stationOptionName = getResources().getStringArray(R.array.weather_option_name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stationOptionName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dataAggThreeSpinner.setAdapter(adapter);
//        dataAggThreeSpinner.setSelection(0);
//        dataAggThreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                threeSelectPosition = position;
//                if (selectId == 4 && position != 4 || selectId == 3 || selectId == 2 && twoSpinnerPosition > 2)
//                {
//                    showValueData(AppUtils.getStationValueByPoistion(threeSelectPosition, stationInfos));
//                } else
//                {
//                    showValueData(AppUtils.getWeatherValueByPoistion(threeSelectPosition, weatherDataBeens));
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
    }

    private void showValueData(LinkedList<ValueEntity> valueEntities) {
        ValueAdapter adapter = new ValueAdapter(mContext, valueEntities);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


    /**
     * 得到当前月的数据汇总
     */
    private LinkedList<WeatherHistory> weatherDataBeens = new LinkedList<>();

    private void getWeatherHistoryOfCurrentMonth() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams params = new SoapParams();
        params.put("beginTime", DateUtils.getSubDate(DateUtils.ConverToString(DateUtils.getFirstdayofThisMonth())));
        params.put("endTime", DateUtils.getCurrentTime());

        params.put("stationId", monitorBean.getLocation());
        soapUtil.call(AppConfig.SERVICE_URL_DATA, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_WEATHER_HISTORY, params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                getActivity().runOnUiThread(new Runnable() {
                    SoapObject bodyIn;
                    String result;

                    @Override
                    public void run() {
                        try {
                            bodyIn = (SoapObject) envelope.bodyIn;
                            result = bodyIn.toString();
                        } catch (ClassCastException e) {
                            result = ACache.get(mContext).getAsString("DataAggregation");
                        } finally {
                            ACache.get(mContext).put("DataAggregation", result);
                            weatherDataBeens = SoapParseUtils.getWeatherDataOfMonth(result);
                            displayOptionName();
                        }

                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.i(TAG, "onFailure + " + e.toString());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
