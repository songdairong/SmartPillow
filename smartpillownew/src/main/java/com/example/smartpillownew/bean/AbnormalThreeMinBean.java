package com.example.smartpillownew.bean;

import java.util.List;

/**
 * Created by a450J on 2018/9/5.
 */

public class AbnormalThreeMinBean {

    private List<ValueBean> value;

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * id : 877
         * userid : qianqian
         * breath : 0
         * heart : 0
         * turnover : 0
         * status : 100
         * date : 2018-08-21
         * datetime : 2018-08-21 14:35:24
         */

        private String id;
        private String userid;
        private String breath;
        private String heart;
        private String turnover;
        private String status;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
                    ", status='" + status + '\'' +
                    ", date='" + date + '\'' +
                    ", datetime='" + datetime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AbnormalThreeMinBean{" +
                "value=" + value +
                '}';
    }
}
