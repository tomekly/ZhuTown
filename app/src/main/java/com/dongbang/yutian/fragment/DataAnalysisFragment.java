package com.dongbang.yutian.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.adapter.ControllAdapter;
import com.dongbang.yutian.adapter.ValueAdapter;
import com.dongbang.yutian.beans.MonitorBean;
import com.dongbang.yutian.beans.StationInfo;
import com.dongbang.yutian.beans.ValueEntity;
import com.dongbang.yutian.beans.WeatherHourData;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.graphic.LineData;
import com.dongbang.yutian.soap.SoapClient;
import com.dongbang.yutian.soap.SoapParams;
import com.dongbang.yutian.soap.SoapParseUtils;
import com.dongbang.yutian.soap.SoapUtil;
import com.dongbang.yutian.utils.ACache;
import com.dongbang.yutian.utils.DateUtils;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.ToastUtils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 智能控制
 * Created by haiquan on 2016/6/15.
 */
public class DataAnalysisFragment extends BaseFragment {

    private final String TAG = this.getClass().getSimpleName();
    private Spinner area;
    private Spinner subArea;
    private Spinner chackOption;
    private String[] areaName;
    private int[] areaId;
    private ACache aCache;
    private int selectId = 0;
    private MonitorBean mb;
    private Context mContext;
    private ArrayList<LineData> mLineDatas = new ArrayList<>();
    private LineData mLineData = new LineData();
    private LinearLayout linearLayout;
    private int areaPosition = 0;
    private int subPosition = 0;
    private LineChartView chartView;
    private LinkedList<ValueEntity> valueEntities;
    private TextView text_name;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    private ImageButton imgbtn;
    private int stutas = 1;

    @Override
    protected void lazyload() {

    }

    public DataAnalysisFragment() {
    }

    private View rootView;

    public static DataAnalysisFragment getInstance(String title) {
        DataAnalysisFragment sf = new DataAnalysisFragment();
        return sf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_data_analysis, container, false);
        ButterKnife.bind(this, rootView);
        initData();
        initView(rootView);

        return rootView;

    }


    /**
     * 初始化Spinner数据
     */
    private void initData() {
        areaName = getResources().getStringArray(R.array.area_name);
        areaId = getResources().getIntArray(R.array.area_id);
        mb = AppConfig.monitorBean;
    }

    /**
     * 初始化控件
     */
    private void initView(View view) {
        mContext = getActivity();
        aCache = ACache.get(mContext);
        area = (Spinner) view.findViewById(R.id.area);
        subArea = (Spinner) view.findViewById(R.id.subArea);
//        imgbt = (ImageButton) view.findViewById(R.id.item_control_btn);
//        chackOption = (Spinner) view.findViewById(R.id.checkOption);
        linearLayout = (LinearLayout) view.findViewById(R.id.cubic);
        text_name = (TextView) view.findViewById(R.id.item_control_name);

//        chartView = (LineChartView) view.findViewById(R.id.chart);
//        chartView.setInteractive(true);
//        chartView.setZoomEnabled(true);
//        chartView.setOnValueTouchListener(new ChartOnValueListener());
        buildData();
        showValueData(valueEntities);
    }

    private void showValueData(LinkedList<ValueEntity> valueEntities) {
        ControllAdapter adapter = new ControllAdapter(mContext, valueEntities);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }


    private ImageButton imgbt;

    private LinkedList<ValueEntity> buildData() {
        valueEntities = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            ValueEntity v = new ValueEntity();
            v.setDate(i + "tv");
            v.setButton(imgbt);
            valueEntities.add(v);
        }
        return valueEntities;
    }
//
//    private LinkedList<ValueEntity> showDate(){
//
//        return  valueEntities;
//    }


    @Override

    public void onStart() {
        super.onStart();
        displayAreaData();
    }

    /**
     * 显示一级列表数据
     */
    private void displayAreaData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, areaName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(adapter);
        area.setSelection(areaPosition);//当前选择的Item
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectId = areaId[position];
                getSupSpinerData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 操作项
     */

    private void displayOptionName() {
        if (selectId == 4 && subPosition != 4 || selectId == 3) {
            optionName = getResources().getStringArray(R.array.stationOptionName);
        } else {
            if (this.getView() != null) {
                optionName = getResources().getStringArray(R.array.optionName);
            }
        }
        if (optionName == null) {
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, optionName);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        chackOption.setAdapter(adapter);
//        chackOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (selectId == 4 && position != 4 || selectId == 3 || selectId == 2 && position > 2) {
//                    displayStationChartData(position);
//                } else {
//
//                    displayWeatherChartData(position);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
    }

    /**
     * 显示站点图标数据
     *
     * @param position
     */
    public void displayStationChartData(int position) {
        List<PointValue> humidity = new LinkedList<>();//湿度
        List<PointValue> airTem = new LinkedList<>();//空气温度
        List<PointValue> sh = new LinkedList<>(); //硫化氢
        List<PointValue> illuminance = new LinkedList<>();//光照
        List<PointValue> carbonDioxide = new LinkedList<>();//二氧化碳
        List<PointValue> ammonia = new LinkedList<>();//氨气

        for (int i = 0; i < stationInfos.size(); i++) {
            try {
                StationInfo stationInfo = stationInfos.get(i);
                if (stationInfo.getPort() == 1) {
                    airTem.add(new PointValue(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(stationInfo.getCollectTime()).getHours())), (float) stationInfo.getCollectValue()));
                }
                if (stationInfo.getPort() == 2) {
                    humidity.add(new PointValue(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(stationInfo.getCollectTime()).getHours())), (float) stationInfo.getCollectValue()));
                }
                if (stationInfo.getPort() == 3) {
                    illuminance.add(new PointValue(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(stationInfo.getCollectTime()).getHours())), (float) stationInfo.getCollectValue()));
                }
                if (stationInfo.getPort() == 4) {
                    carbonDioxide.add(new PointValue(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(stationInfo.getCollectTime()).getHours())), (float) stationInfo.getCollectValue()));
                }
                if (stationInfo.getPort() == 5) {
                    ammonia.add(new PointValue(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(stationInfo.getCollectTime()).getHours())), (float) stationInfo.getCollectValue()));
                }
                if (stationInfo.getPort() == 6) {
                    sh.add(new PointValue(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(stationInfo.getCollectTime()).getHours())), (float) stationInfo.getCollectValue()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        switch (position) {
            case 0:
                listToArrays(airTem, "", "空气温度 ℃");
                break;
            case 1:
                listToArrays(humidity, "", "湿度 %");
                break;
            case 2:
                listToArrays(illuminance, "", "光照度 lux");
                break;
            case 3:
                listToArrays(carbonDioxide, "", "二氧化碳 ppm");
                break;
            case 4:
                listToArrays(ammonia, "", "氨气 ppm");
                break;
            case 5:
                listToArrays(sh, "", "硫化氢 ppm");
                break;
        }
    }

    public void listToArrays(List<PointValue> data, String axisX, String axisY) {
        displayChart(data, axisX, axisY);
    }

    /**
     * 显示天气图标数据
     *
     * @param position
     */

    public void displayWeatherChartData(int position) {
        List<PointValue> values = new ArrayList<>();
        switch (position) {
            case 0:
                //空气湿度

                for (int i = 0; i < whds.size(); i++) {
                    PointValue value = new PointValue();
                    try {
                        value.set(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(whds.get(i).getCollectTime()).getHours())), Float.parseFloat(whds.get(i).getAirHumidity()));
                        values.add(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                displayChart(values, "", "空气湿度 %");
                break;
            case 1:
                //空气温度

                for (int i = 0; i < whds.size(); i++) {
                    PointValue value = new PointValue();
                    try {
                        value.set(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(whds.get(i).getCollectTime()).getHours())), Float.parseFloat(whds.get(i).getAirTemperature()));
                        values.add(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                displayChart(values, "", "温度 ℃");
                break;
            case 2:
                //土壤湿度
                for (int i = 0; i < whds.size(); i++) {
                    PointValue value = new PointValue();
                    try {
                        value.set(Float.parseFloat(String.valueOf(DateUtils.ConverToDate(whds.get(i).getCollectTime()).getHours())), Float.parseFloat(whds.get(i).getSoilTemp1()));
                        values.add(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                displayChart(values, "", "湿度 %");
                break;
        }
    }


    /**
     * 获取子列表的数据
     */
    private void getSupSpinerData() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams params = new SoapParams();
        params.put("areaId", selectId);//区域ID
        params.put("userId", 3);//用户id
        soapUtil.call(AppConfig.SERVICE_URL, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_AREA_CHILDREN, params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                        final LinkedList<MonitorBean> monitorBeens = SoapParseUtils.getMonitorBeans(bodyIn.toString());
                        if (monitorBeens == null || monitorBeens.size() == 0) {
                            ToastUtils.showShort(mContext, getResources().getString(R.string.error_nothing_sub_menu));
                            return;
                        }
                        LinkedList<String> subAreaName = new LinkedList<String>();
                        for (int i = 0; i < monitorBeens.size(); i++) {
                            subAreaName.add(monitorBeens.get(i).getAreaName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, subAreaName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subArea.setAdapter(adapter);
                        subArea.setSelection(subPosition);
                        subArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                MonitorBean monitorBean = monitorBeens.get(position);
                                mb = monitorBean;
                                if (selectId == 4 && position != 4 || selectId == 3 || selectId == 2 && position > 2) {
                                    getStationHourData();
                                } else {
                                    getWeatherHourData();
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
                LogUtils.e(TAG, getResources().getString(R.string.error_get_sub_menu_failure), e);
            }
        });
    }

    public String optionName[];
    private LinkedList<WeatherHourData> whds;

    /**
     * 获取天气数据
     */
    public void getWeatherHourData() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams params = new SoapParams();
        params.put("beginTime", DateUtils.getCurrentTime());
        params.put("stationId", mb.getLocation());
        soapUtil.call(AppConfig.SERVICE_URL_DATA, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_WEATHER_HOUR_DATA, params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                getActivity().runOnUiThread(new Runnable() {
                    String resoult = null;

                    @Override
                    public void run() {
                        try {
                            SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                            resoult = bodyIn.toString();
                            LogUtils.d(TAG, "bodyIn" + resoult);
                        } catch (ClassCastException e) {
                            resoult = aCache.getAsString(AppConfig.KEY_STRING_GET_WEATHER_DATA);
                            LogUtils.e(TAG, "onSuccess : 类转换异常" + e.toString());
                        } finally {
                            aCache.put(AppConfig.KEY_STRING_GET_WEATHER_DATA, resoult);
                            whds = SoapParseUtils.getWeatherHourDate(resoult);
                            displayOptionName();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "onFailure:" + e.toString());
            }
        });
    }

    private LinkedList<StationInfo> stationInfos;

    public void getStationHourData() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams params = new SoapParams();
        params.put("beginTime", DateUtils.getSubDate(DateUtils.getCurrentTime()));
        params.put("stationId", com.dongbang.yutian.utils.StringUtils.isEmpty(mb.getLocation()));
        soapUtil.call(AppConfig.SERVICE_URL_DATA, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_GW_HOUR_DATA, params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                        stationInfos = SoapParseUtils.getStationInfos(bodyIn.toString());
                        LogUtils.d(TAG, "getStationHourData     " + bodyIn.toString());
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

    private void displayChart(List<PointValue> value, String axisX, String axisY) {
        generateData(value, axisX, axisY);
    }

    public int numberOfLines = 1;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isCubic = true;
    private boolean isFilled = false;
    private boolean hasLabels = true;
    private boolean hasLabelForSelected = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private LineChartData data;

    /**
     * 构建数据
     *
     * @param values
     * @param axisXName
     * @param axisYName
     */
    public void generateData(List<PointValue> values, String axisXName, String axisYName) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < numberOfLines; ++i) {
            Line line = new Line(values).setColor(getResources().getColor(R.color.colorPrimary));
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }
        data = new LineChartData();
        data.setLines(lines);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName(axisXName);
        axisY.setName(axisYName);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setBaseValue(Float.NEGATIVE_INFINITY);
//        chartView.setLineChartData(data);
    }

    public class ChartOnValueListener implements LineChartOnValueSelectListener {
        @Override
        public void onValueSelected(int i, int i1, PointValue pointValue) {
            ToastUtils.showShort(mContext, pointValue + "");
        }

        @Override
        public void onValueDeselected() {
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
