package com.dongbang.yutian.beans;

import java.io.Serializable;

/**
 * 文章
 * Created by DongBang on 2016/5/8.
 */
public class ArticleBean implements Serializable
{

    private String author;
    private String description;
    private int id;
    private int noticeCode;
    private String noticeContent;
    private String noticeFile;
    private String noticeImg;
    private int noticeQy;
    private long noticeTime;
    private int noticeType;
    private int roleId;
    private  int stat;

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public String getAuthor() { return author;}

    public void setAuthor(String author) { this.author = author;}

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description;}

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public int getNoticeCode() { return noticeCode;}

    public void setNoticeCode(int noticeCode) { this.noticeCode = noticeCode;}

    public String getNoticeContent() { return noticeContent;}

    public void setNoticeContent(String noticeContent) { this.noticeContent = noticeContent;}

    public String getNoticeFile() { return noticeFile;}

    public void setNoticeFile(String noticeFile) { this.noticeFile = noticeFile;}

    public String getNoticeImg() { return noticeImg;}

    public void setNoticeImg(String noticeImg) { this.noticeImg = noticeImg;}

    public int getNoticeQy() { return noticeQy;}

    public void setNoticeQy(int noticeQy) { this.noticeQy = noticeQy;}

    public long getNoticeTime() { return noticeTime;}

    public void setNoticeTime(long noticeTime) { this.noticeTime = noticeTime;}

    public int getNoticeType() { return noticeType;}

    public void setNoticeType(int noticeType) { this.noticeType = noticeType;}

    public int getRoleId() { return roleId;}

    public void setRoleId(int roleId) { this.roleId = roleId;}
}
