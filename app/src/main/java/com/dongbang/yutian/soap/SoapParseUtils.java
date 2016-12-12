package com.dongbang.yutian.soap;


import android.util.Log;

import com.dongbang.yutian.beans.ArticleBean;
import com.dongbang.yutian.beans.ExpertReplyDate;
import com.dongbang.yutian.beans.MonitorBean;
import com.dongbang.yutian.beans.StationEntity;
import com.dongbang.yutian.beans.StationHistory;
import com.dongbang.yutian.beans.StationInfo;
import com.dongbang.yutian.beans.WeatherData;
import com.dongbang.yutian.beans.WeatherHistory;
import com.dongbang.yutian.beans.WeatherHourData;
import com.dongbang.yutian.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 类描述：
 * Created by Harvin on 2016/2/16.
 * Modefy by Harvin on 2016/2/16.
 * 修改备注：
 */
public class SoapParseUtils {
    private final static String TAG = "SoapParseUtils";

    /**
     * 去字符串头
     *
     * @param result
     * @return
     * @throws Exception
     */
    public static String removeHeader(String result) throws Exception {
        // 去头
        int index_sta = result.indexOf("json=");
        int index_end = result.indexOf("; result=success");
        String json = result.substring(index_sta, index_end);
        String newJson = json.replace("json=", "");
        return newJson;
    }


    public static LinkedList<MonitorBean> getMonitorBeans(String bodyIn) {
        LinkedList<MonitorBean> monitorBeens = new LinkedList<>();
        try {
            if (bodyIn == null) return monitorBeens;
            Gson gson = new Gson();
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray area = jsonObject.getJSONArray("areas");
            LogUtils.i("getMonitorBeans", area.toString());
            Type type = new TypeToken<LinkedList<MonitorBean>>() {
            }.getType();
            monitorBeens = gson.fromJson(area.toString(), type);
        } catch (Exception e) {
            LogUtils.e(TAG, "getMonitorBeans", e);
        }
        return monitorBeens;
    }

    public static ExpertReplyDate getExpertReplyDates(String bodyIn) {
        LinkedList<ExpertReplyDate> expertReplyDates = null;
        ExpertReplyDate expertReplyDate = new ExpertReplyDate();
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            Log.i("qin:expertReply", "去头后:" + jsonBoyIn.toString());

            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            if (jsonObject.get("specialistConsult") == null) {
                //     Log.i("qin:expertReply","这是空的");
                return null;
            } else {
                //     Log.i("qin:expertReply","这不空的");
            }


            //    JSONArray area=jsonObject.getJSONArray("specialistConsult");
            // 判读输出对象的类型
            JSONObject ConsultObj = jsonObject.getJSONObject("specialistConsult");
            //   Log.i("qin:expertReply","ConsultObj:"+ConsultObj.toString());
            JSONArray area0 = jsonObject.getJSONArray("speReplayList");
            //    Log.i("qin:expertReply","speReplayList:"+area0.toString());

            Gson gsonConsult = new Gson();
            ExpertReplyDate.SpecialistConsultBean specialistConsultBean = gsonConsult.fromJson
                    (ConsultObj.toString(), ExpertReplyDate.SpecialistConsultBean.class);
            expertReplyDate.setSpecialistConsult(specialistConsultBean);
            //      Log.i("qin:expertReply","specialistConsultBean:"+specialistConsultBean.getTitle());
            //     Log.i("qin:expertReply",area1.toString());
            Type type0 = new TypeToken<LinkedList<ExpertReplyDate.SpeReplayListBean>>() {
            }.getType();
            Gson gson = new Gson();
//           ExpertReplyDate.SpecialistConsultBean specialistConsultBean=gson.fromJson(ConsultObj.toString(), type0);
//            Log.i("qin:expertReply","SpecialistConsultBean:"+specialistConsultBean.getTitle());
//            expertReplyDate.setSpecialistConsult(specialistConsultBean);
//            expertReplyDate.setSpecialistConsult( gson.fromJson(area0.toString(), type));

            List<ExpertReplyDate.SpeReplayListBean> speReplayListBean = gson.fromJson(area0.toString(), type0);
            expertReplyDate.setSpeReplayList(speReplayListBean);
            //     Log.i("qin:expertReply","speReplayListBean:"+speReplayListBean.get(1).getProcessTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return monitorBeens;

        return expertReplyDate;
    }

    public static LinkedList<WeatherData> getWeatherDataBeans(String bodyIn) {
        LinkedList<WeatherData> weatherDataBeens = null;
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray weatherList = jsonObject.getJSONArray("weatherList");
            Type type = new TypeToken<LinkedList<WeatherData>>() {
            }.getType();
            Gson gson = new Gson();
            weatherDataBeens = gson.fromJson(weatherList.toString(), type);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherDataBeens;
    }


    public static LinkedList<WeatherHistory> getWeatherDataOfMonth(String bodyIn) {
        LinkedList<WeatherHistory> weatherDataBeens = null;
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray weatherList = jsonObject.getJSONArray("historyList");
            Type type = new TypeToken<LinkedList<WeatherHistory>>() {
            }.getType();
            Gson gson = new Gson();
            weatherDataBeens = gson.fromJson(weatherList.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherDataBeens;
    }


    public static LinkedList<StationEntity> getStationEntity(String bodyIn) {
        LinkedList<StationEntity> statins = null;
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray weatherList = jsonObject.getJSONArray("stationList");
            Type type = new TypeToken<LinkedList<StationEntity>>() {
            }.getType();
            Gson gson = new Gson();
            statins = gson.fromJson(weatherList.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statins;
    }


    public static LinkedList<WeatherHourData> getWeatherHourDate(String bodyIn) {
        LinkedList<WeatherHourData> weatherDataBeens = null;
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray weatherList = jsonObject.getJSONArray("hourList");
            Type type = new TypeToken<LinkedList<WeatherHourData>>() {
            }.getType();
            Gson gson = new Gson();
            weatherDataBeens = gson.fromJson(weatherList.toString(), type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherDataBeens;
    }

    public static LinkedList<StationHistory> getStationHistoryInfos(String bodyIn) {
        LinkedList<StationHistory> stationInfos = null;
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray weatherList = jsonObject.getJSONArray("historyList");
            Type type = new TypeToken<LinkedList<StationHistory>>() {
            }.getType();
            Gson gson = new Gson();
            stationInfos = gson.fromJson(weatherList.toString(), type);

        } catch (Exception e) {
            stationInfos = new LinkedList<>();
            LogUtils.e(TAG, e.getMessage(), e);
        }
        return stationInfos;
    }

    public static LinkedList<StationInfo> getStationInfos(String bodyIn) {
        LinkedList<StationInfo> stationInfos = null;
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray weatherList = jsonObject.getJSONArray("gwList");
            Type type = new TypeToken<LinkedList<StationInfo>>() {
            }.getType();
            Gson gson = new Gson();
            stationInfos = gson.fromJson(weatherList.toString(), type);

        } catch (Exception e) {
            stationInfos = new LinkedList<>();
            LogUtils.e(TAG, e.getMessage(), e);
        }
        return stationInfos;
    }

    public static LinkedList<ArticleBean> getAnnounceList(String bodyIn) {
        LinkedList<ArticleBean> announceList = null;
        try {
            String jsonBoyIn = removeHeader(bodyIn);
            JSONObject jsonObject = new JSONObject(jsonBoyIn);
            JSONArray jsonArray = jsonObject.getJSONArray("announceList");
            Type type = new TypeToken<LinkedList<ArticleBean>>() {
            }.getType();
            Gson gson = new Gson();
            announceList = gson.fromJson(jsonArray.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
            announceList = new LinkedList<>();
            LogUtils.e(TAG, e.getMessage(), e);
        }
        return announceList;
    }

}
