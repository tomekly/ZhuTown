package com.dongbang.yutian.beans;

import java.util.List;

/**
 * Created by dobest on 2016/10/20.
 */

public class EquipBean {

    /**
     * areaId : 6
     * classifyId : 25
     * classifyName : 电磁阀
     * collectTime : 1476844251873
     * currentState : 0
     * currentStateOther : 0
     * equipCode : eq_00062
     * equipName : 电磁阀
     * id : 1
     * latitude : 32.01583333
     * longitude : 120.123190206117
     */

    private List<EquipConListBean> equipConList;

    public List<EquipConListBean> getEquipConList() {
        return equipConList;
    }

    public void setEquipConList(List<EquipConListBean> equipConList) {
        this.equipConList = equipConList;
    }

    public static class EquipConListBean {
        private int areaId;
        private int classifyId;
        private String classifyName;
        private long collectTime;
        private int currentState;
        private int currentStateOther;
        private String equipCode;
        private String equipName;
        private int id;
        private double latitude;
        private double longitude;

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(int classifyId) {
            this.classifyId = classifyId;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public long getCollectTime() {
            return collectTime;
        }

        public void setCollectTime(long collectTime) {
            this.collectTime = collectTime;
        }

        public int getCurrentState() {
            return currentState;
        }

        public void setCurrentState(int currentState) {
            this.currentState = currentState;
        }

        public int getCurrentStateOther() {
            return currentStateOther;
        }

        public void setCurrentStateOther(int currentStateOther) {
            this.currentStateOther = currentStateOther;
        }

        public String getEquipCode() {
            return equipCode;
        }

        public void setEquipCode(String equipCode) {
            this.equipCode = equipCode;
        }

        public String getEquipName() {
            return equipName;
        }

        public void setEquipName(String equipName) {
            this.equipName = equipName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
