package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by DongBang on 2016/9/22.
 */

public class    ExpertEntity implements Serializable {


    /**
     * id : 2
     * catid : 28
     * thumb : http://192.168.1.99:8088/nongjiyun/yingyong/uploadfile/2016/0713/20160713045632430.jpg
     * keywords : 刘鑫
     * description : 这是简介
     * inputtime : 2016-07-13
     * content : 这是介绍
     * name : 刘鑫
     * email : 353@qq.com
     * address : XXX
     * birthday : 1989-05-18
     * catname : 专家库
     * qaqNum : 2
     */

    private String id;
    private String catid;
    private String thumb;
    private String keywords;
    private String description;
    private String inputtime;
    private String content;
    private String name;
    private String email;
    private String address;
    private String birthday;
    private String catname;
    private String qaqNum;
    /**
     * zj_category : 6
     */

    private String zj_category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getQaqNum() {
        return qaqNum;
    }

    public void setQaqNum(String qaqNum) {
        this.qaqNum = qaqNum;
    }

    public String getZj_category() {
        return zj_category;
    }

    public void setZj_category(String zj_category) {
        this.zj_category = zj_category;
    }
}
