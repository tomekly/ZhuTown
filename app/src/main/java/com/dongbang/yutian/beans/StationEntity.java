package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by 黄海全 on 2016/5/8.
 */
public class StationEntity implements Serializable
{

    /** describe 站点实体
     * data : 68190.91
     * dirverId : 1200201603180148
     * port : 3
     * time : 1462668740000
     */

    private double data;
    private String dirverId;
    private int port;
    private long time;

    public double getData() { return data;}

    public void setData(double data) { this.data = data;}

    public String getDirverId() { return dirverId;}

    public void setDirverId(String dirverId) { this.dirverId = dirverId;}

    public int getPort() { return port;}

    public void setPort(int port) { this.port = port;}

    public long getTime() { return time;}

    public void setTime(long time) { this.time = time;}
}
