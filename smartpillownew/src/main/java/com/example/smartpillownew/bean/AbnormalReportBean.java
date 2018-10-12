package com.example.smartpillownew.bean;

import java.util.List;

/**
 * Created by a450J on 2018/8/14.
 */

public class AbnormalReportBean {

    private List<ValueBean> value;

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * id : 1
         * userid : qianqian
         * breath : 29
         * heart : 120
         * turnover : 98
         * info : 异常:心跳
         * date : 2018-08-11
         * datetime : 2018-08-11 10:49:17
         */

        private String id;
        private String userid;
        private String breath;
        private String heart;
        private String turnover;
        private String info;
        private String date;
        private String datetime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getBreath() {
            return breath;
        }

        public void setBreath(String breath) {
            this.breath = breath;
        }

        public String getHeart() {
            return heart;
        }

        public void setHeart(String heart) {
            this.heart = heart;
        }

        public String getTurnover() {
            return turnover;
        }

        public void setTurnover(String turnover) {
            this.turnover = turnover;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        @Override
        public String toString() {
            return "ValueBean{" +
                    "id='" + id + '\'' +
                    ", userid='" + userid + '\'' +
                    ", breath='" + breath + '\'' +
                    ", heart='" + heart + '\'' +
                    ", turnover='" + turnover + '\'' +
                    ", info='" + info + '\'' +
                    ", date='" + date + '\'' +
                    ", datetime='" + datetime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AbnormalReportBean{" +
                "value=" + value +
                '}';
    }
}
