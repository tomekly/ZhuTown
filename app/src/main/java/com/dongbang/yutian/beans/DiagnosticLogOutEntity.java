package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by DongBang on 2016/9/23.
 */

public class DiagnosticLogOutEntity  implements Serializable{


    /**
     * id : 2
     * tid : 2
     * title : "测试,测试,测试"
     * content : 这是内容这是呢人这是内容
     * time : 2016-09-22
     */

    private String id;
    private String tid;
    private String title;
    private String content;
    private String time;


    private String address;
    private String category;
    private Integer  solved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSolved() {
        return solved;
    }

    public void setSolved(Integer solved) {
        this.solved = solved;
    }
}
