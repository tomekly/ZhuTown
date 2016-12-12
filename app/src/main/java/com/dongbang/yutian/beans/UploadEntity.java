package com.dongbang.yutian.beans;

import java.io.Serializable;

public class UploadEntity implements Serializable {
    private String fileName;

    private String absolutePath;

    private String filePath;

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public String getFileName(){
        return this.fileName;
    }
    public void setAbsolutePath(String absolutePath){
        this.absolutePath = absolutePath;
    }
    public String getAbsolutePath(){
        return this.absolutePath;
    }
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    public String getFilePath(){
        return this.filePath;
    }

}
