package com.dongbang.yutian.utils;

import com.dongbang.yutian.beans.StationHistory;
import com.dongbang.yutian.beans.ValueEntity;
import com.dongbang.yutian.beans.WeatherHistory;

import java.util.LinkedList;

/**
 * Created by DongBang on 2016/6/16.
 */
public class AppUtils
{
    public static LinkedList<ValueEntity> getStationValueByPoistion(int poistion , LinkedList<StationHistory> stationInfos)
    {
        LinkedList<ValueEntity> valueEntities = new LinkedList<>();
        for (int i = 0; i < stationInfos.size(); i++)
        {
            ValueEntity valueEntity=new ValueEntity();
            valueEntity.setDate(stationInfos.get(i).getCollectTime());
            if (poistion==0)
            {
               valueEntity.setValue(String.valueOf(stationInfos.get(i).getValue1()));
                valueEntity.setSymbol("°C");
            }

            if (poistion==1){
                valueEntity.setValue(String.valueOf(stationInfos.get(i).getValue2()));
                valueEntity.setSymbol("%");
            }
            if (poistion==2)
            {
                valueEntity.setValue(String.valueOf(stationInfos.get(i).getValue3()));
                valueEntity.setSymbol("h");
            }
            if (poistion==3)
            {
                valueEntity.setValue(String.valueOf(stationInfos.get(i).getValue4()));
                valueEntity.setSymbol("PPM");
            }
            if (poistion==4)
            {
                valueEntity.setValue(String.valueOf(stationInfos.get(i).getValue5()));
                valueEntity.setSymbol("PPM");
            }
            if (poistion==5)
            {
                valueEntity.setValue(String.valueOf(stationInfos.get(i).getValue6()));
                valueEntity.setSymbol("%");
            }
            valueEntities.add(valueEntity);
        }
        return valueEntities;

    }
    public static LinkedList<ValueEntity> getWeatherValueByPoistion(int poistion , LinkedList<WeatherHistory> weatherDataBeens)
    {
        LinkedList<ValueEntity> valueEntities=new LinkedList<>();
        for (int i=0;i<weatherDataBeens.size();i++)
        {
            ValueEntity valueEntity=new ValueEntity();
            valueEntity.setDate(weatherDataBeens.get(i).getCollectTime());
            if (poistion==0)
            {//空气温度
                valueEntity.setValue(weatherDataBeens.get(i).getAirTemperature());
                valueEntity.setSymbol("°C");
            }
            if (poistion==1)
            {//空气湿度
                valueEntity.setValue(weatherDataBeens.get(i).getAirHumidity());
                valueEntity.setSymbol("%");
            }
            if (poistion==2)
            {
                valueEntity.setValue(weatherDataBeens.get(i).getPressure());
                valueEntity.setSymbol("kpa");
            }
            if (poistion==3)
            {
                valueEntity.setValue(weatherDataBeens.get(i).getWindSpeed());
                valueEntity.setSymbol("km/h");
            }

            if (poistion==4)
            {
                valueEntity.setValue(BaseUtils.isEmpty(weatherDataBeens.get(i).getWindDirection()));
                valueEntity.setSymbol("");
            }
            if (poistion==5)
            {
                valueEntity.setValue(weatherDataBeens.get(i).getRainfall());
                valueEntity.setSymbol("mm");
            }
            if (poistion==6)
            {
            valueEntity.setValue(weatherDataBeens.get(i).getEvaporation());
                valueEntity.setSymbol("kpa");
            }
            if (poistion==7)
            {
                valueEntity.setValue(weatherDataBeens.get(i).getSunshineTime());
                valueEntity.setSymbol("h");
            }
            if (poistion==8)
            {
                valueEntity.setValue(weatherDataBeens.get(i).getPhotosynthetic());
                valueEntity.setSymbol("||");
            }

            if (poistion==9)
            {
                valueEntity.setValue(weatherDataBeens.get(i).getSoilTemp1());
                valueEntity.setSymbol("°C");
            }
            if (poistion==10)
            {
                valueEntity.setValue(weatherDataBeens.get(i).getSoilHumidity1());
                valueEntity.setSymbol("%");
            }
            valueEntities.add(valueEntity);
        }

    return  valueEntities;
    }

}
