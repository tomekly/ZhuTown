package com.dongbang.yutian.beans;

import java.util.List;

/**
 * Created by DoBest on 2016/5/5.
 */
public class Name {
    private String name;
    private List<ChildName> childName;

    public String getName() {
        return name;
    }

    public List<ChildName> getChildName() {
        return childName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildName(List<ChildName> childName) {
        this.childName = childName;
    }
}
