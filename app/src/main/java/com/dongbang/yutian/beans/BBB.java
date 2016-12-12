package com.dongbang.yutian.beans;

import java.util.List;

/**
 * Created by root on 16-11-17.
 */

public class BBB {


    /**
     * alarmList : {"a113":[{"id":3,"alarmContent":"超过阈值","areaName":"A1棚","processStatus":0,"equipCode":"SDJ004","happenTime":1472434493000,"currentValue":"222","alarmType":2,"areaId":113}]}
     */

    private AlarmListBean alarmList;

    public AlarmListBean getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(AlarmListBean alarmList) {
        this.alarmList = alarmList;
    }

    public static class AlarmListBean {
        private List<A113Bean> a113;

        public List<A113Bean> getA113() {
            return a113;
        }

        public void setA113(List<A113Bean> a113) {
            this.a113 = a113;
        }

        public static class A113Bean {
            /**
             * id : 3
             * alarmContent : 超过阈值
             * areaName : A1棚
             * processStatus : 0
             * equipCode : SDJ004
             * happenTime : 1472434493000
             * currentValue : 222
             * alarmType : 2
             * areaId : 113
             */

            private int id;
            private String alarmContent;
            private String areaName;
            private int processStatus;
            private String equipCode;
            private long happenTime;
            private String currentValue;
            private int alarmType;
            private int areaId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAlarmContent() {
                return alarmContent;
            }

            public void setAlarmContent(String alarmContent) {
                this.alarmContent = alarmContent;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public int getProcessStatus() {
                return processStatus;
            }

            public void setProcessStatus(int processStatus) {
                this.processStatus = processStatus;
            }

            public String getEquipCode() {
                return equipCode;
            }

            public void setEquipCode(String equipCode) {
                this.equipCode = equipCode;
            }

            public long getHappenTime() {
                return happenTime;
            }

            public void setHappenTime(long happenTime) {
                this.happenTime = happenTime;
            }

            public String getCurrentValue() {
                return currentValue;
            }

            public void setCurrentValue(String currentValue) {
                this.currentValue = currentValue;
            }

            public int getAlarmType() {
                return alarmType;
            }

            public void setAlarmType(int alarmType) {
                this.alarmType = alarmType;
            }

            public int getAreaId() {
                return areaId;
            }

            public void setAreaId(int areaId) {
                this.areaId = areaId;
            }
        }
    }
}
