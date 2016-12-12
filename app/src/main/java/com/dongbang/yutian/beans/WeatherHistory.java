package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by DongBang on 2016/6/16.
 */
public class WeatherHistory implements Serializable
{
    private String airHumidity;// 湿度
    private String airTemperature;//空气温度
    private String collectTime;//采集时间
    private String evaporation;//蒸发
    private int id;
    private String photosynthetic;//光合作用
    private String pressure;//气压
    private String rainfall;//雨量
    private String soilHumidity1;//土壤湿度1
    private String soilHumidity2;//土壤湿度2
    private String soilHumidity3;//突然湿度3
    private String soilTemp1;//土壤温度1
    private String soilTemp2;//突然温度2
    private String soilTemp3;//t土壤温度3
    private String station;//站点
    private String sunshineTime;//光照时间
    private String windDirection;//风向
    private String windSpeed;//风速

    public String getAirHumidity()
    {
        return airHumidity;
    }

    public void setAirHumidity(String airHumidity)
    {
        this.airHumidity = airHumidity;
    }

    public String getAirTemperature()
    {
        return airTemperature;
    }

    public void setAirTemperature(String airTemperature)
    {
        this.airTemperature = airTemperature;
    }

    public String getCollectTime()
    {
        return collectTime;
    }

    public void setCollectTime(String collectTime)
    {
        this.collectTime = collectTime;
    }

    public String getEvaporation()
    {
        return evaporation;
    }

    public void setEvaporation(String evaporation)
    {
        this.evaporation = evaporation;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPhotosynthetic()
    {
        return photosynthetic;
    }

    public void setPhotosynthetic(String photosynthetic)
    {
        this.photosynthetic = photosynthetic;
    }

    public String getPressure()
    {
        return pressure;
    }

    public void setPressure(String pressure)
    {
        this.pressure = pressure;
    }

    public String getRainfall()
    {
        return rainfall;
    }

    public void setRainfall(String rainfall)
    {
        this.rainfall = rainfall;
    }

    public String getSoilHumidity1()
    {
        return soilHumidity1;
    }

    public void setSoilHumidity1(String soilHumidity1)
    {
        this.soilHumidity1 = soilHumidity1;
    }

    public String getSoilHumidity2()
    {
        return soilHumidity2;
    }

    public void setSoilHumidity2(String soilHumidity2)
    {
        this.soilHumidity2 = soilHumidity2;
    }

    public String getSoilHumidity3()
    {
        return soilHumidity3;
    }

    public void setSoilHumidity3(String soilHumidity3)
    {
        this.soilHumidity3 = soilHumidity3;
    }

    public String getSoilTemp1()
    {
        return soilTemp1;
    }

    public void setSoilTemp1(String soilTemp1)
    {
        this.soilTemp1 = soilTemp1;
    }

    public String getSoilTemp2()
    {
        return soilTemp2;
    }

    public void setSoilTemp2(String soilTemp2)
    {
        this.soilTemp2 = soilTemp2;
    }

    public String getSoilTemp3()
    {
        return soilTemp3;
    }

    public void setSoilTemp3(String soilTemp3)
    {
        this.soilTemp3 = soilTemp3;
    }

    public String getStation()
    {
        return station;
    }

    public void setStation(String station)
    {
        this.station = station;
    }

    public String getSunshineTime()
    {
        return sunshineTime;
    }

    public void setSunshineTime(String sunshineTime)
    {
        this.sunshineTime = sunshineTime;
    }

    public String getWindDirection()
    {
        return windDirection;
    }

    public void setWindDirection(String windDirection)
    {
        this.windDirection = windDirection;
    }

    public String getWindSpeed()
    {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed)
    {
        this.windSpeed = windSpeed;
    }
}
