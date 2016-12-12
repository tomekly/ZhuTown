package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * 版本更新实体类
 * Created by root on 16-10-17.
 */

public class VersionBean implements Serializable {


    /**
     * id : 7
     * vno : 2
     * name : 电饭锅
     * description : 的风格的风格的过
     * size : 1409413
     * download : http://120.27.24.137:8088/yutian/yingyong/uploadfile/APK/20161013/newname.apk
     * create_time : 2016-10-13 11:31:36
     */

    private String id;
    private int vno;
    private String name;
    private String description;
    private String size;
    private String download;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVno() {
        return vno;
    }

    public void setVno(int vno) {
        this.vno = vno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
