package com.dongbang.yutian.beans;

import java.util.List;

/**
 * Created by ruiqin on 2016/5/6.
 */
public class ExpertReplyDate {

    /**
     * consultContent : xxxx
     * consultorTime : 1417155087000
     * currentStatus : 1
     * id : 1
     * specialistId : 1
     * title : xxxxx
     * userId : 3
     */

    private SpecialistConsultBean specialistConsult;
    /**
     * consultId : 1
     * id : 2
     * processTime : 1417155117000
     * processor : 0
     * replayContent : “老方，你怎么把房间里的空调开的那么冷啊？”

     正和方雅志说着话的曹弘志忽然感到一阵寒意传来，不由用手拉起身边的被子盖在了身上，没好气的说道：“医院的空调不要钱，也别这么用，冻死我了……”

     “咦，是有点冷呀……”

     听到曹弘志这么一说，方雅志也感觉到了一丝寒意，用手搓了下脸，说道：“我去护士值班室看一下吧，这的空调又不是在屋里控制的……”

     曹弘志所住的病房，是这个医院的高干病房，这也算是他最后一次享受那死鬼老爹身份带来的便利了，以后再住院，怕是就没这种待遇了。

     “妈垩的，怎么这么困啊？”

     在方雅志站起身的时候，曹弘志张嘴打了个哈欠，一股浓浓的倦意传入脑海之中，脑袋一歪就陷入到了沉睡之中。

     “哎呦，你是谁啊？”

     拉开房门的方雅志，冷不防看到门口站了一个人，吓得连忙往后退了几步，用手捂住了心口窝，出了一身的冷汗。

     “方老板，好久不见啊……”

     秦风笑眯眯的走进了病房，看了一眼昏睡不醒的曹弘志，开口说道：“听闻方老板身体有恙，作为老朋友，秦某怎么着也要来看望一下啊……”

     “你……你是秦风？”

     听到那熟悉的声音，又看清了秦风的面孔之后方雅志脸色顿时变了，张口喊道：“来人，来人啊，有人要杀人了......”

     虽然不知道秦风是怎么摸到这间病房里来的，但是方雅志的应变能力却是非常快，他心里清楚，这上门来的秦风，绝对是个恶客。

     按照方雅志的想法，只要自己大声喊出来，引得那些医生护士们到来众目睽睽之下，秦风是不敢将自己怎么样的。

     不过让方雅志想不到的是，尽管他张大了嘴巴，却是发现，自己嗓子发出的声音，连他自个儿都听不太清楚，更不用提传到外面走廊上去了。

     “方老板您这是怎么了？”秦风一脸戏谑的看着方雅志，说道：“老朋友前来看望你，也不请我坐下吗？”

     看着一脸憔悴的方雅志，秦风无语的摇了摇头，这人也算是玉石行里的前辈，曾经将《雅致斋》开遍了大江南北，但惟独有一个缺点，那就是赌性太重。

     当年方雅志赌石败家将《雅致斋》百年的产业几乎都给输出去了，不得已之下，才把潘家园的那家店盘给了秦风，解决了《雅致斋》的燃眉之急。

     按理说吃过这么一个大亏之后，方雅志应该长点记性才对，可他却是又赌了一把，将宝压在了曹弘志的身上想通过不正当的手段，将秦风的《真玉坊》给夺到手上。

     但是秦风的回归，又打破了方雅志的如意算盘，以前输的可能只是金钱，但是此刻方雅志心中生出一种明悟，或许自己这次输掉的东西

     要比金钱更加珍贵。
     * replayType : 2
     * specialistName :
     * title : 11
     */

    private List<SpeReplayListBean> speReplayList;

    public SpecialistConsultBean getSpecialistConsult() {
        return specialistConsult;
    }

    public void setSpecialistConsult(SpecialistConsultBean specialistConsult) {
        this.specialistConsult = specialistConsult;
    }

    public List<SpeReplayListBean> getSpeReplayList() {
        return speReplayList;
    }

    public void setSpeReplayList(List<SpeReplayListBean> speReplayList) {
        this.speReplayList = speReplayList;
    }

    public static class SpecialistConsultBean {
        private String consultContent;
        private long consultorTime;
        private int currentStatus;
        private int id;
        private int specialistId;
        private String title;
        private int userId;

        public String getConsultContent() {
            return consultContent;
        }

        public void setConsultContent(String consultContent) {
            this.consultContent = consultContent;
        }

        public long getConsultorTime() {
            return consultorTime;
        }

        public void setConsultorTime(long consultorTime) {
            this.consultorTime = consultorTime;
        }

        public int getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(int currentStatus) {
            this.currentStatus = currentStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSpecialistId() {
            return specialistId;
        }

        public void setSpecialistId(int specialistId) {
            this.specialistId = specialistId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public static class SpeReplayListBean {
        private int consultId;
        private int id;
        private long processTime;
        private int processor;
        private String replayContent;
        private int replayType;
        private String specialistName;
        private String title;

        public int getConsultId() {
            return consultId;
        }

        public void setConsultId(int consultId) {
            this.consultId = consultId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getProcessTime() {
            return processTime;
        }

        public void setProcessTime(long processTime) {
            this.processTime = processTime;
        }

        public int getProcessor() {
            return processor;
        }

        public void setProcessor(int processor) {
            this.processor = processor;
        }

        public String getReplayContent() {
            return replayContent;
        }

        public void setReplayContent(String replayContent) {
            this.replayContent = replayContent;
        }

        public int getReplayType() {
            return replayType;
        }

        public void setReplayType(int replayType) {
            this.replayType = replayType;
        }

        public String getSpecialistName() {
            return specialistName;
        }

        public void setSpecialistName(String specialistName) {
            this.specialistName = specialistName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
