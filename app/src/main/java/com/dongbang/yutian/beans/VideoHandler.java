package com.dongbang.yutian.beans;

import android.util.Log;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/26.
 */
public class VideoHandler extends DefaultHandler{

    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private ArrayList<Name> video = new ArrayList<>();
    private int k=0;
    private int j=0;
    private List<ChildName> childNameList = new ArrayList<>();
    private List<ChildName> childNameList2 = new ArrayList<>();
    private List<ChildName> childNameList3 = new ArrayList<>();
    private VideoListener mVideoListListener;

    public VideoHandler(VideoListener videoListListener) {
        super();
        mVideoListListener = videoListListener;
    }

    @Override
    public void startDocument() throws SAXException{
        id = new StringBuilder();
        name = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             org.xml.sax.Attributes attributes) throws SAXException{

        nodeName = localName;
        //记录当前节点名
        if ("Department".equals(localName)&&attributes.getValue(0).length()==6){
            Name nameOne = new Name();
            nameOne.setName(attributes.getValue(1));
            video.add(nameOne);
            Log.d("VideoHandler", "Name is " + nameOne.getName());
        }

        if ("Channel".equals(localName)&&attributes.getLength()==1&&video.size()==k+1){
//            video.get(k).setChildName(childNameList);
            k++;
//            Log.d("VideoHandler", "localName is " +video.get(k-1).getChildName().size());
//            childNameList.clear();
        }

        if ("Device".equals(localName)&&attributes.getLength()==37&&j<6){
            ChildName childName = new ChildName();
            childName.setId(attributes.getValue(0)+"$1$0$0");
            childName.setName(attributes.getValue(2));
            childNameList.add(childName);
            Log.d("VideoHandler", "localName is " + childName.getName()+":"+childName.getId());
            j++;
        }else if ("Device".equals(localName)&&attributes.getLength()==37&&j>=6&&j<12){
            ChildName childName = new ChildName();
            childName.setId(attributes.getValue(0)+"$1$0$0");
            childName.setName(attributes.getValue(2));
            childNameList2.add(childName);
            Log.d("VideoHandler", "localName is " + childName.getName()+":"+childName.getId());
            j++;
        }else if ("Device".equals(localName)&&attributes.getLength()==37&&j>=12){
            ChildName childName = new ChildName();
            childName.setId(attributes.getValue(0)+"$1$0$0");
            childName.setName(attributes.getValue(2));
            childNameList3.add(childName);
            Log.d("VideoHandler", "localName is " + childName.getName()+":"+childName.getId());
            j++;
        }

        if (j==18){
            video.get(0).setChildName(childNameList);
            video.get(1).setChildName(childNameList2);
            video.get(2).setChildName(childNameList3);
            mVideoListListener.setVideoList(video);
        }

        /*if (j==6){
            video.get(0).setChildName(childNameList);
//            Log.d("VideoHandler", "localName  " + childNameList.get(0).getName());
//            childNameList = new ArrayList<>() ;
        }else if (j==12){
            video.get(1).setChildName(childNameList2);
//            Log.d("VideoHandler", "localName  " + childNameList.get(1).getName());
//            childNameList = new ArrayList<>();
//            mVideoListListener.setVideoList(video);
        }*/


    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException{
        //根据当前的结点名判断将内容添加到哪一个StringBuilder对象中
        /*if ("id".equals(nodeName)){
            id.append(ch, start ,length);
        }else if ("name".equals(nodeName)){
            name.append(ch, start, length);
        }*/
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        /*if ("app".equals(localName)){
            Log.d("VideoHandler", "id is "+ id.toString().trim());
            Log.d("VideoHandler", "name is " + name.toString().trim());
            //最后要将StringBuilder清空
            id.setLength(0);
            name.setLength(0);
        }*/
    }

    @Override
    public void endDocument() throws SAXException{
        /*for (int i=0; i<video.get(0).getChildName().size();i++){
            Log.d("VideoHandler", "localName is " + video.get(0).getChildName().get(i).getId());
            Log.d("VideoHandler", "localName is " + video.get(1).getChildName().get(i).getId());
        }*/
    }
}
