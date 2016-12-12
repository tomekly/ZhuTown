package com.dongbang.yutian.common;

import com.dongbang.yutian.beans.MonitorBean;

/**
 * Created by DongBang on 2016/5/4.
 */
public class AppConfig
{  //120.27.24.137.8687
    public static final int TIME_OUT=2000;
    public static final String SERVICE_URL="http://60.2.131.35:8687/farm/service/basic?wsdl";
    public static final String SERVICE_URL_DATA="http://60.2.131.35:8687/farm/service/data?wsdl";
    public static final String SERVICE_URL_SYSTEM="http://60.2.131.35:8687/farm/service/system?wsdl";
    public static final String EXPERT_SERVICE_URL="http://60.2.131.35:8687/farm/service/harm?wsdl"; //专家
    public static final String SERVICE_NAME_SPACE="http://definition.webservice.farm.njdobest.com/";
    public static final String  SERVICE_IMG_URL="http://60.2.131.35:8687/farm";
    public static final String MN_GET_WARNING_BY_DIRVER="GetWarningByDirver";//获取警告列表
    public static final String MN_GET_AREA_CHILDREN="GetAreaChildren";//获取区域子监控点
    public static final String MN_GET_GW_HOUR_DATA="GetGwHourData";
    public static final String MN_GET_WEATHERE_DATA="GetWeatherData";//获取气象数据
    public static final String MN_GET_STATION_DATA="GetStationData";//获取站点数据
    public static final String MN_GET_WEATHER_HOUR_DATA="GetWeatherHourData";//获取每小时的天气数据
    public static final String MN_GET_ANNOUNCEMENT_BY_TYPE="GetAnnouncementByType";//知识库列表
    public static final String MN_GET_WEATHER_HISTORY="GetWeatherHistory";//获取气象的历史数据
    public static final  String MN_GET_GW_HISTORY="GetGwHistory";//获取当前站点的历史数据


    /**
     * 缓存KEY
     */
    public static final String KEY_STRING_MONITOR_BEANS="key_string_monitor_beans";
    public static final String KEY_STRING_GET_WEATHER_DATA="weather_data";
    public static MonitorBean monitorBean;

    public static  final  int REQUEST_CODE_SIGN_IN=1000;
    public static final int RESULT_CODE=0;

    public static int  RETURN_VALUE=0;

    public static String DOWNLOAD_URL="";
    public static final int PRESMISSIONS_CAMERA_CODE=40001;

}
