package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by DongBang on 2016/7/6.
 */
public class StationHistory implements Serializable
{

    /**
     * collectTime : 2016-05-30
     * driverId : 1200201603180150
     * value5 : 0.7
     * value6 : 0.4
     * value3 : 7.46
     * value4 : 493.94
     * value1 : 24.66
     * value2 : 49.4
     */

    private String collectTime;
    private String driverId;
    private double value5;
    private double value6;
    private double value3;
    private double value4;
    private double value1;
    private double value2;

    public String getCollectTime() { return collectTime;}

    public void setCollectTime(String collectTime) { this.collectTime = collectTime;}

    public String getDriverId() { return driverId;}

    public void setDriverId(String driverId) { this.driverId = driverId;}

    public double getValue5() { return value5;}

    public void setValue5(double value5) { this.value5 = value5;}

    public double getValue6() { return value6;}

    public void setValue6(double value6) { this.value6 = value6;}

    public double getValue3() { return value3;}

    public void setValue3(double value3) { this.value3 = value3;}

    public double getValue4() { return value4;}

    public void setValue4(double value4) { this.value4 = value4;}

    public double getValue1() { return value1;}

    public void setValue1(double value1) { this.value1 = value1;}

    public double getValue2() { return value2;}

    public void setValue2(double value2) { this.value2 = value2;}
}
