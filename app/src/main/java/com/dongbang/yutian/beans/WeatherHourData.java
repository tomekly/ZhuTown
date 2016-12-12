package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by DongBang on 2016/5/6.
 */
public class WeatherHourData implements Serializable {

    /**
     * airHumidity : 100.000
     * airTemperature : 12.914
     * collectTime : 2016-05-06 00:00:00
     * id : 681
     * soilTemp1 : 15.767
     * soilTemp2 : 16.154
     * soilTemp3 : 15.279
     * station : tingm002
     */

    private String airHumidity;
    private String airTemperature;
    private String collectTime;
    private int id;
    private String soilTemp1;
    private String soilTemp2;
    private String soilTemp3;
    private String station;

    public String getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(String airHumidity) {
        this.airHumidity = airHumidity;
    }

    public String getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(String airTemperature) {
        this.airTemperature = airTemperature;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoilTemp1() {
        return soilTemp1;
    }

    public void setSoilTemp1(String soilTemp1) {
        this.soilTemp1 = soilTemp1;
    }

    public String getSoilTemp2() {
        return soilTemp2;
    }

    public void setSoilTemp2(String soilTemp2) {
        this.soilTemp2 = soilTemp2;
    }

    public String getSoilTemp3() {
        return soilTemp3;
    }

    public void setSoilTemp3(String soilTemp3) {
        this.soilTemp3 = soilTemp3;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
