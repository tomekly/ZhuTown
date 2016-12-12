package com.dongbang.yutian.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dongbang.yutian.R;
import com.dongbang.yutian.beans.MonitorBean;
import com.dongbang.yutian.beans.StationEntity;
import com.dongbang.yutian.beans.WeatherData;
import com.dongbang.yutian.common.AppConfig;
import com.dongbang.yutian.soap.SoapClient;
import com.dongbang.yutian.soap.SoapParams;
import com.dongbang.yutian.soap.SoapParseUtils;
import com.dongbang.yutian.soap.SoapUtil;
import com.dongbang.yutian.utils.ACache;
import com.dongbang.yutian.utils.DateUtils;
import com.dongbang.yutian.utils.LogUtils;
import com.dongbang.yutian.utils.StringUtils;
import com.dongbang.yutian.utils.ToastUtils;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.LinkedList;


/**
 * 数据采集
 * A simple {@link Fragment} subclass.
 */
public class DataCollectionFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Spinner spi_parent;
    private Spinner spi_sub;
    private Context mContext;
    private ACache aCache;
    private TextView airHumidity;// 湿度
    private TextView airTemperature;//空气温
    private TextView evaporation;//蒸发
    private TextView photosynthetic;//光合作
    private TextView pressure;//气压
    private TextView rainfall;//雨量
    private TextView soilHumidity1;//土壤湿度
    private TextView soilTemp1;//土壤温度1
    private TextView sunshineTime;//光照时间
    private TextView windDirection;//风向
    private TextView windSpeed;//风速
    private TextView edt1;
    private TextView edt2;
    private TextView edt3;
    private TextView edt4;
    private TextView edt5;
    private TextView edt6;
    private TextView tv_update_time;
    private SwipeRefreshLayout srlayout;
    private LinearLayout layout_weather_data;
    private LinearLayout layout_station_data;

    private View rootView;


    @Override
    protected void lazyload() {

    }

    public DataCollectionFragment() {
    }

    public static DataCollectionFragment getInstance(String title) {
        DataCollectionFragment sf = new DataCollectionFragment();

        return sf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_data_collection, container, false);
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) container.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }

        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mContext = getActivity();
        TAG = DataCollectionFragment.this.getClass().getSimpleName();
        aCache = ACache.get(mContext);
        spi_parent = (Spinner) view.findViewById(R.id.spi_area);
        spi_sub = (Spinner) view.findViewById(R.id.spi_area_monitor);
        layout_station_data = (LinearLayout) view.findViewById(R.id.layout_station_data);
        layout_weather_data = (LinearLayout) view.findViewById(R.id.layout_weather_data);
        airHumidity = (TextView) view.findViewById(R.id.airHumidity);
        assert airHumidity != null;
        airHumidity.setOnClickListener(this);
        airTemperature = (TextView) view.findViewById(R.id.airTemperature);
        airTemperature.setOnClickListener(this);
        evaporation = (TextView) view.findViewById(R.id.evaporation);
        evaporation.setOnClickListener(this);
        photosynthetic = (TextView) view.findViewById(R.id.photosynthetic);
        photosynthetic.setOnClickListener(this);
        pressure = (TextView) view.findViewById(R.id.pressure);
        pressure.setOnClickListener(this);
        rainfall = (TextView) view.findViewById(R.id.rainfall);
        rainfall.setOnClickListener(this);
        soilHumidity1 = (TextView) view.findViewById(R.id.soilHumidity1);
        soilHumidity1.setOnClickListener(this);
        soilTemp1 = (TextView) view.findViewById(R.id.soilTemp1);
        soilTemp1.setOnClickListener(this);
        sunshineTime = (TextView) view.findViewById(R.id.sunshineTime);
        sunshineTime.setOnClickListener(this);
        windDirection = (TextView) view.findViewById(R.id.windDirection);
        windDirection.setOnClickListener(this);
        windSpeed = (TextView) view.findViewById(R.id.windSpeed);
        windSpeed.setOnClickListener(this);
        edt1 = (TextView) view.findViewById(R.id.edt1);
        edt1.setOnClickListener(this);
        edt2 = (TextView) view.findViewById(R.id.edt2);
        edt2.setOnClickListener(this);
        edt3 = (TextView) view.findViewById(R.id.edt3);
        edt3.setOnClickListener(this);
        edt4 = (TextView) view.findViewById(R.id.edt4);
        edt4.setOnClickListener(this);
        edt5 = (TextView) view.findViewById(R.id.edt5);
        edt5.setOnClickListener(this);
        edt6 = (TextView) view.findViewById(R.id.edt6);
        edt6.setOnClickListener(this);
        tv_update_time = (TextView) view.findViewById(R.id.update_time);
        srlayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        srlayout.setOnRefreshListener(this);

        srlayout.post(new Runnable() {
            @Override
            public void run() {
                srlayout.setRefreshing(true);
                onRefresh();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        displayParentSpinerData();
    }

    private int select_id;
    private String select_name;
    private String select_subArea;
    private MonitorBean select_monitorBean;
    private String[] area_name;
    private int[] area_id;
    private int areaPosition = 0;
    private int subAreaPosition = 0;

    private void displayParentSpinerData() {
        area_name = getResources().getStringArray(R.array.area_name);
        area_id = getResources().getIntArray(R.array.area_id);
        select_id = area_id[0];
        select_name = area_name[0];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, area_name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_parent.setAdapter(adapter);
        spi_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaPosition = position;
                select_id = area_id[position];
                select_name = area_name[position];
                getSupSpinerData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 获取子列表的数据
     */
    private void getSupSpinerData() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams params = new SoapParams();
        params.put("areaId", select_id);
        params.put("userId", 3);
        soapUtil.call(AppConfig.SERVICE_URL, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_AREA_CHILDREN, params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {

                ((Activity) mContext).runOnUiThread(new Runnable() {
                    SoapObject bodyIn = null;
                    String resoult = null;
                    @Override
                    public void run() {
                        try {
                            bodyIn = (SoapObject) envelope.bodyIn;
                            resoult = bodyIn.toString();
                        } catch (ClassCastException e) {
                            LogUtils.e(TAG, "onSuccess:" + "数据获取转换异常，加载缓存");
                            resoult = aCache.getAsString(AppConfig.KEY_STRING_MONITOR_BEANS);
                        } finally {
                            aCache.put(AppConfig.KEY_STRING_MONITOR_BEANS, resoult);
                            final LinkedList<MonitorBean> monitorBeens = SoapParseUtils.getMonitorBeans(resoult);
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
                            spi_sub.setAdapter(adapter);
                            spi_sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    MonitorBean monitorBean = monitorBeens.get(position);
                                    subAreaPosition = position;//当前选择的位置
                                    AppConfig.monitorBean = select_monitorBean = monitorBean;
                                    if (select_id == 4 && position != 4 || select_id == 3 || select_id == 2 && position >= 2) {
                                        layout_station_data.setVisibility(View.VISIBLE);
                                        layout_weather_data.setVisibility(View.GONE);
                                        getStationData(monitorBean.getLocation());
                                    } else {
                                        layout_weather_data.setVisibility(View.VISIBLE);
                                        layout_station_data.setVisibility(View.GONE);
                                        getWeatherData(monitorBean.getLocation());
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {   }
                            });
                            LogUtils.i(TAG, "MonitorBean:" + monitorBeens.get(0).getAreaName() + monitorBeens.toString());
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "获取子列表出错了", e);
            }
        });

    }

    /**
     * 获取气象数据
     *
     * @param stationId
     */
    public void getWeatherData(String stationId) {
        showDialog();
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams soapParams = new SoapParams();
        soapParams.put("stationId", stationId);
        soapUtil.call(AppConfig.SERVICE_URL_DATA, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_WEATHERE_DATA, soapParams, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(SoapSerializationEnvelope envelope) throws Exception {
                SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                final LinkedList<WeatherData> weatherDataBeens = SoapParseUtils.getWeatherDataBeans(bodyIn.toString());
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dissDialog();
                        if (weatherDataBeens == null || weatherDataBeens.size() == 0) {
                            ToastUtils.showShort(mContext, "没有数据");
                            airTemperature.setText("" + "°C");
                            airHumidity.setText("" + "%");
                            evaporation.setText("" + "kpa");
                            photosynthetic.setText("" + "ll");
                            pressure.setText("" + "kpa");
                            rainfall.setText("" + "mm");
                            soilHumidity1.setText("" + "%");
                            soilTemp1.setText("" + "°C");
                            sunshineTime.setText("" + "h");
                            windDirection.setText(" ");
                            windSpeed.setText(" " + "km/h");
                            return;
                        }
                        airTemperature.setText(weatherDataBeens.get(0).getAirtemperature() + "°C");
                        airHumidity.setText(weatherDataBeens.get(0).getAirhumidity() + "%");
                        evaporation.setText(weatherDataBeens.get(0).getAAP() + "kpa");
                        photosynthetic.setText(weatherDataBeens.get(0).getPhotosynthetic() + "ll");
                        pressure.setText(weatherDataBeens.get(0).getAAP() + "kpa");
                        rainfall.setText(weatherDataBeens.get(0).getAPT() + "mm");
                        soilHumidity1.setText(weatherDataBeens.get(0).getSTC40() + "%");
                        soilTemp1.setText(weatherDataBeens.get(0).getSTC20() + "°C");
                        sunshineTime.setText(weatherDataBeens.get(0).getSunshineTime() + "h");
                        windDirection.setText(weatherDataBeens.get(0).getAWD());
                        windSpeed.setText(weatherDataBeens.get(0).getAWS() + "km/h");
                        if (StringUtils.isBooEmpty(weatherDataBeens.get(0).getCollecttime())) {
                            tv_update_time.setText("");
                        } else {
                            tv_update_time.setText(DateUtils.getSubDate(weatherDataBeens.get(0).getCollecttime(), 0, 19));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                dissDialog();
                LogUtils.e(TAG, "onFailure : " + e.toString());
            }
        });
    }

    /**
     * 获取站点数据
     *
     * @param stationId
     */
    public void getStationData(String stationId) {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(AppConfig.TIME_OUT);
        SoapParams soapParams = new SoapParams();
        soapParams.put("stationId", stationId);
        soapUtil.call(AppConfig.SERVICE_URL_DATA, AppConfig.SERVICE_NAME_SPACE, AppConfig.MN_GET_STATION_DATA, soapParams, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(SoapSerializationEnvelope envelope) throws Exception {
                final SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                final LinkedList<StationEntity> stationEntities = SoapParseUtils.getStationEntity(bodyIn.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayStationData(stationEntities);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "onFailure : " + e.toString());
            }
        });
    }

    private void displayStationData(LinkedList<StationEntity> stationEntities) {
        if (stationEntities.size() == 0) {
            ToastUtils.showShort(mContext, "没有该站点的数据");
            edt1.setText("" + "℃");
            edt2.setText(""+ "%");
            edt3.setText("" + "lux");
            edt4.setText("" + "ppm");
            edt5.setText("" + "ppm");
            edt6.setText("" + "ppm");
            return;
        }
        if (stationEntities.size() == 1) {
            edt1.setText(stationEntities.get(0).getData() + "℃");
        }
        if (stationEntities.size() == 2) {
            edt1.setText(stationEntities.get(0).getData() + "℃");
            edt2.setText(stationEntities.get(1).getData() + "%");
        }
        if (stationEntities.size() == 3) {
            edt1.setText(stationEntities.get(0).getData() + "℃");
            edt2.setText(stationEntities.get(1).getData() + "%");
            edt3.setText(+stationEntities.get(2).getData() + "lux");
        }
        if (stationEntities.size() == 4) {
            edt1.setText(stationEntities.get(0).getData() + "℃");
            edt2.setText(stationEntities.get(1).getData() + "%");
            edt3.setText(stationEntities.get(2).getData() + "lux");
            edt4.setText(stationEntities.get(3).getData() + "ppm");
        }

        if (stationEntities.size() == 5) {
            edt1.setText(stationEntities.get(0).getData() + "℃");
            edt2.setText(stationEntities.get(1).getData() + "%");
            edt3.setText(stationEntities.get(2).getData() + "lux");
            edt4.setText(stationEntities.get(3).getData() + "ppm");
            edt5.setText(stationEntities.get(4).getData() + "ppm");
        }
        if (stationEntities.size() == 6) {
            edt1.setText(stationEntities.get(0).getData() + "℃");
            edt2.setText(stationEntities.get(1).getData() + "%");
            edt3.setText(stationEntities.get(2).getData() + "lux");
            edt4.setText(stationEntities.get(3).getData() + "ppm");
            edt5.setText(stationEntities.get(4).getData() + "ppm");
            edt6.setText(stationEntities.get(5).getData() + "ppm");
        }

        tv_update_time.setText("更新时间：" + DateUtils.msToDate(stationEntities.get(0).getTime()));
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("areaName", area_name);
        bundle.putIntArray("areaId", area_id);
        bundle.putString("select_name", select_name);
        bundle.putInt("select_id", select_id);
        bundle.putSerializable("sm", select_monitorBean);
        bundle.putInt("areaPosition", areaPosition);
        bundle.putInt("subAreaPosition", subAreaPosition);
        //        ToastUtils.UIHelper.showActivity(getActivity(),DataAnalysisActivity.class,bundle);
    }

    @Override
    public void onRefresh() {
        srlayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                srlayout.setRefreshing(false);
                getSupSpinerData();
            }
        }, 2000);
    }


}
