package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by DongBang on 2016/5/9.
 */
public class StationInfo implements Serializable

{

    /**
     * collectTime : 2016-05-09 00:00:00
     * collectValue : 20.23
     * driverId : 1200201603180150
     * id : 2602
     * port : 1
     */

    private String collectTime;
    private double collectValue;
    private String driverId;
    private int id;
    private int port;

    public String getCollectTime() { return collectTime;}

    public void setCollectTime(String collectTime) { this.collectTime = collectTime;}

    public double getCollectValue() { return collectValue;}

    public void setCollectValue(double collectValue) { this.collectValue = collectValue;}

    public String getDriverId() { return driverId;}

    public void setDriverId(String driverId) { this.driverId = driverId;}

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public int getPort() { return port;}

    public void setPort(int port) { this.port = port;}
}
