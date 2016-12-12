package com.dongbang.yutian.beans;


import java.io.Serializable;

public class WeatherData  implements Serializable
{
    /**
     * 序号
     */
    private Long id;
    
    /**
     * 采集时间
     */
    private String collecttime;
    
    /**
     * 站号
     */
    private String station;
    
    /**
     * 风速
     */
    private String AWS;
    
    /**
     * 风向
     */
    private String AWD;
    
    /**
     * 空气温度
     */
    private String airtemperature;
    
    /**
     * 空气湿度
     */
    private String airhumidity;
    
    /**
     * 大气压力
     */
    private String AAP;
    
    /**
     * 光合有效
     */
    private String photosynthetic;
    
    /**
     * 日照时数
     */
    private String sunshineTime;
    
    /**
     * 蒸发
     */
    private String AET;
    
    /**
     * 日降雨量
     */
    private String APT;
    
    /**
     * 土壤温度
     */
    private String STC20;
    
    /**
     * 土壤温度
     */
    private String STC40;
    
    /**
     * 土壤温度
     */
    private String STC60;
    
    /**
     * 土壤温度
     */
    private String STC80;
    
    /**
     * 土壤湿度
     */
    private String SWC20;
    
    /**
     * 土壤湿度
     */
    private String SWC40;
    
    /**
     * 土壤湿度
     */
    private String SWC60;
    
    /**
     * 土壤湿度
     */
    private String SWC80;
    
    /**
     * 小时ET
     */
    private String ET_XS;
    
    /**
     * 有效降雨
     */
    private String JY_YX;
    
    /**
     *  日累计有效降雨
     */
    private String JY_YX_R;
    
    /**
     * 最大风速 
     */
    private String AWS_MAX;
    
    /**
     * 最小风速 
     */
    private String AWS_MIN;
    
    public String getSTC80()
    {
        return STC80;
    }
    
    public void setSTC80(String sTC80)
    {
        STC80 = sTC80;
    }
    
    public String getSWC80()
    {
        return SWC80;
    }
    
    public void setSWC80(String sWC80)
    {
        SWC80 = sWC80;
    }
    
    public String getET_XS()
    {
        return ET_XS;
    }
    
    public void setET_XS(String eT_XS)
    {
        ET_XS = eT_XS;
    }
    
    public String getJY_YX()
    {
        return JY_YX;
    }
    
    public void setJY_YX(String jY_YX)
    {
        JY_YX = jY_YX;
    }
    
    public String getJY_YX_R()
    {
        return JY_YX_R;
    }
    
    public void setJY_YX_R(String jY_YX_R)
    {
        JY_YX_R = jY_YX_R;
    }
    
    public String getAWS_MAX()
    {
        return AWS_MAX;
    }
    
    public void setAWS_MAX(String aWS_MAX)
    {
        AWS_MAX = aWS_MAX;
    }
    
    public String getAWS_MIN()
    {
        return AWS_MIN;
    }
    
    public void setAWS_MIN(String aWS_MIN)
    {
        AWS_MIN = aWS_MIN;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getCollecttime()
    {
        return collecttime;
    }
    
    public void setCollecttime(String collecttime)
    {
        this.collecttime = collecttime;
    }
    
    public String getAWS()
    {
        return AWS;
    }
    
    public void setAWS(String aWS)
    {
        AWS = aWS;
    }
    
    public String getAWD()
    {
        return AWD;
    }
    
    public void setAWD(String aWD)
    {
        AWD = aWD;
    }
    
    public String getAirtemperature()
    {
        return airtemperature;
    }
    
    public void setAirtemperature(String airtemperature)
    {
        this.airtemperature = airtemperature;
    }
    
    public String getAirhumidity()
    {
        return airhumidity;
    }
    
    public void setAirhumidity(String airhumidity)
    {
        this.airhumidity = airhumidity;
    }
    
    public String getAAP()
    {
        return AAP;
    }
    
    public void setAAP(String aAP)
    {
        AAP = aAP;
    }
    
    public String getAET()
    {
        return AET;
    }
    
    public void setAET(String aET)
    {
        AET = aET;
    }
    
    public String getAPT()
    {
        return APT;
    }
    
    public void setAPT(String aPT)
    {
        APT = aPT;
    }
    
    public String getSTC20()
    {
        return STC20;
    }
    
    public void setSTC20(String sTC20)
    {
        STC20 = sTC20;
    }
    
    public String getSTC40()
    {
        return STC40;
    }
    
    public void setSTC40(String sTC40)
    {
        STC40 = sTC40;
    }
    
    public String getSTC60()
    {
        return STC60;
    }
    
    public void setSTC60(String sTC60)
    {
        STC60 = sTC60;
    }
    
    public String getSWC20()
    {
        return SWC20;
    }
    
    public void setSWC20(String sWC20)
    {
        SWC20 = sWC20;
    }
    
    public String getSWC40()
    {
        return SWC40;
    }
    
    public void setSWC40(String sWC40)
    {
        SWC40 = sWC40;
    }
    
    public String getSWC60()
    {
        return SWC60;
    }
    
    public void setSWC60(String sWC60)
    {
        SWC60 = sWC60;
    }
    
    public String getStation()
    {
        return station;
    }
    
    public void setStation(String station)
    {
        this.station = station;
    }
    
    public String getPhotosynthetic()
    {
        return photosynthetic;
    }
    
    public void setPhotosynthetic(String photosynthetic)
    {
        this.photosynthetic = photosynthetic;
    }
    
    public String getSunshineTime()
    {
        return sunshineTime;
    }
    
    public void setSunshineTime(String sunshineTime)
    {
        this.sunshineTime = sunshineTime;
    }
    
}
