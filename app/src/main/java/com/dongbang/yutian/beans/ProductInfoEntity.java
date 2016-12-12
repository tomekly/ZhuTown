package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * Created by DongBang on 2016/9/23.
 */

public class ProductInfoEntity implements Serializable {


    private String title;
    private String inputtime;
    private String content;
    private String zs_code;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getZs_code() {
        return zs_code;
    }

    public void setZs_code(String zs_code) {
        this.zs_code = zs_code;
    }
}
