package com.dongbang.yutian.beans;

import android.widget.ImageButton;

/**
 * Created by DongBang on 2016/6/16.
 */
public class ValueEntity
{
    private String date;
    private String value;
    private String symbol;
    private ImageButton button;
    private int sta;

    public ImageButton getButton() {
        return button;
    }

    public void setButton(ImageButton button) {
        this.button = button;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getValue()
    {
        return value;
    }

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }


    public void setButton(int i) {

    }
}
