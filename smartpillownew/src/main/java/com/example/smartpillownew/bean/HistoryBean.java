package com.example.smartpillownew.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a450J on 2018/8/14.
 */

public class HistoryBean {

    private List<ValueBean> value;

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * 0 : 433
         * 1 : qianqian
         * 2 : 20
         * 3 : 70
         * 4 : 0
         * 5 : 100
         * 6 : 2018-08-14
         * 7 : 2018-08-14 20:21:31
         * id : 433
         * userid : qianqian
         * breath : 20
         * heart : 70
         * turnover : 0
         * status : 100
         * date : 2018-08-14
         * datetime : 2018-08-14 20:21:31
         */

        @SerializedName("0")
        private String _$0;
        @SerializedName("1")
        private String _$1;
        @SerializedName("2")
        private String _$2;
        @SerializedName("3")
        private String _$3;
        @SerializedName("4")
        private String _$4;
        @SerializedName("5")
        private String _$5;
        @SerializedName("6")
        private String _$6;
        @SerializedName("7")
        private String _$7;
        private String id;
        private String userid;
        private String breath;
        private String heart;
        private String turnover;
        private String status;
        private String date;
        private String datetime;

        public String get_$0() {
            return _$0;
        }

        public void set_$0(String _$0) {
            this._$0 = _$0;
        }

        public String get_$1() {
            return _$1;
        }

        public void set_$1(String _$1) {
            this._$1 = _$1;
        }

        public String get_$2() {
            return _$2;
        }

        public void set_$2(String _$2) {
            this._$2 = _$2;
        }

        public String get_$3() {
            return _$3;
        }

        public void set_$3(String _$3) {
            this._$3 = _$3;
        }

        public String get_$4() {
            return _$4;
        }

        public void set_$4(String _$4) {
            this._$4 = _$4;
        }

        public String get_$5() {
            return _$5;
        }

        public void set_$5(String _$5) {
            this._$5 = _$5;
        }

        public String get_$6() {
            return _$6;
        }

        public void set_$6(String _$6) {
            this._$6 = _$6;
        }

        public String get_$7() {
            return _$7;
        }

        public void set_$7(String _$7) {
            this._$7 = _$7;
        }

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
    }
}
