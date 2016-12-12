package com.dongbang.yutian.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 诊断记录
 * Created by DongBang on 2016/9/23.
 */

public class ResponseDiagnosticLogOutEntity implements Serializable{


    /**
     * solved : [{"id":"1","title":"测试？？","time":"2016-09-26","content":"大范甘迪","address":"XXX","category":"畜牧","solved":"1"}]
     * unsolved : [{"id":"2","title":"222","time":"2016-09-26","content":"222","address":"XXX","category":"畜牧","solved":"0"},{"id":"6","title":"测试数据","time":"2016-09-30","content":"测试数据测试数据测试数据","address":"南京东邦","category":"畜牧","solved":"0"},{"id":"7","title":"10.9","time":"2016-10-09","content":"","address":"","category":"畜牧","solved":"0"},{"id":"13","title":"11","time":"2016-10-14","content":"qw","address":"qq","category":"畜牧","solved":"0"},{"id":"15","title":"是","time":"2016-12-01","content":"ssss","address":"ss","category":"畜牧","solved":"0"},{"id":"17","title":"wf","time":"2016-12-01","content":"wef","address":"wef","category":"畜牧","solved":"0"},{"id":"18","title":"","time":"2016-12-02","content":"fwewfwefwfw wfw wfe","address":"","category":"畜牧","solved":"0"},{"id":"19","title":"wefwfe","time":"2016-12-02","content":"wefwfewefwefwqqwfqwqqwq","address":"","category":"畜牧","solved":"0"},{"id":"22","title":"fw","time":"2016-12-03","content":"ww","address":"we","category":"畜牧","solved":"0"},{"id":"23","title":"11","time":"2016-12-05","content":"2","address":"","category":"畜牧","solved":"0"},{"id":"26","title":"违反","time":"2016-12-09","content":"让我","address":"问","category":"畜牧","solved":"0"},{"id":"33","title":"wwfe 王菲","time":"2016-12-12","content":"问","address":"e wew额","category":"畜牧","solved":"0"},{"id":"36","title":"测试添加数据","time":"2016-12-12","content":"大棚1","address":"测试添加数据","category":"畜牧","solved":"0"},{"id":"38","title":"CCC","time":"2016-12-12","content":"CCCCC","address":"CCCC","category":"畜牧","solved":"0"},{"id":"39","title":"CCC","time":"2016-12-12","content":"CCCCC","address":"CCCC","category":"畜牧","solved":"0"},{"id":"40","title":"收费","time":"2016-12-12","content":"是的发送到发送到爽肤水","address":"是的发送到","category":"畜牧","solved":"0"},{"id":"41","title":"爽肤水的发生的","time":"2016-12-12","content":"是打发斯蒂芬","address":"是的发送到","category":"畜牧","solved":"0"}]
     * zhuanjia : {"id":"167","catid":"28","thumb":"http://120.27.24.137:8088/yutian/yingyong/uploadfile/2016/0822/20160822030737865.jpg","keywords":"张三","description":"我是张三","inputtime":"2016-09-26","content":"我是张三","name":"张三","email":"34255@qq.com","address":"石鼓路X号石鼓湾美食休闲街区X号X楼","birthday":"1978-05-18","zj_category":"6","catname":"畜牧","qaqNum":"18"}
     */

    private ZhuanjiaBean zhuanjia;
    private List<UnsolvedBean> solved;
    private List<UnsolvedBean> unsolved;

    public ZhuanjiaBean getZhuanjia() {
        return zhuanjia;
    }

    public void setZhuanjia(ZhuanjiaBean zhuanjia) {
        this.zhuanjia = zhuanjia;
    }

    public List<UnsolvedBean> getSolved() {
        return solved;
    }

    public void setSolved(List<UnsolvedBean> solved) {
        this.solved = solved;
    }

    public List<UnsolvedBean> getUnsolved() {
        return unsolved;
    }

    public void setUnsolved(List<UnsolvedBean> unsolved) {
        this.unsolved = unsolved;
    }

    public static class ZhuanjiaBean {
        /**
         * id : 167
         * catid : 28
         * thumb : http://120.27.24.137:8088/yutian/yingyong/uploadfile/2016/0822/20160822030737865.jpg
         * keywords : 张三
         * description : 我是张三
         * inputtime : 2016-09-26
         * content : 我是张三
         * name : 张三
         * email : 34255@qq.com
         * address : 石鼓路X号石鼓湾美食休闲街区X号X楼
         * birthday : 1978-05-18
         * zj_category : 6
         * catname : 畜牧
         * qaqNum : 18
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
        private String zj_category;
        private String catname;
        private String qaqNum;

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

        public String getZj_category() {
            return zj_category;
        }

        public void setZj_category(String zj_category) {
            this.zj_category = zj_category;
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
    }



    public static class UnsolvedBean {
        /**
         * id : 2
         * title : 222
         * time : 2016-09-26
         * content : 222
         * address : XXX
         * category : 畜牧
         * solved : 0
         */

        private String id;
        private String title;
        private String time;
        private String content;
        private String address;
        private String category;
        private String solved;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getSolved() {
            return solved;
        }

        public void setSolved(String solved) {
            this.solved = solved;
        }
    }
}
