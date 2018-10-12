package com.example.smartpillownew.bean;

import java.util.List;

/**
 * Created by a450J on 2018/8/15.
 */

public class WeekDataBean {

    /**
     * status : 1
     * data : [{"day":"2018-08-11","one":"null"},{"day":"2018-08-12","two":"null"},{"day":"2018-08-13","three":"null"},{"day":"2018-08-14","four":17.218309859155},{"day":"2018-08-15","five":8.5606060606061},{"day":"2018-08-16","six":"null"},{"day":"2018-08-17","seven":"null"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WeekDataBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * day : 2018-08-11
         * one : null
         * two : null
         * three : null
         * four : 17.218309859155
         * five : 8.5606060606061
         * six : null
         * seven : null
         */

        private String day;
        private String one;
        private String two;
        private String three;
        private String four;
        private String five;
        private String six;
        private String seven;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getOne() {
            return one;
        }

        public void setOne(String one) {
            this.one = one;
        }

        public String getTwo() {
            return two;
        }

        public void setTwo(String two) {
            this.two = two;
        }

        public String getThree() {
            return three;
        }

        public void setThree(String three) {
            this.three = three;
        }

        public String getFour() {
            return four;
        }

        public void setFour(String four) {
            this.four = four;
        }

        public String getFive() {
            return five;
        }

        public void setFive(String five) {
            this.five = five;
        }

        public String getSix() {
            return six;
        }

        public void setSix(String six) {
            this.six = six;
        }

        public String getSeven() {
            return seven;
        }

        public void setSeven(String seven) {
            this.seven = seven;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "day='" + day + '\'' +
                    ", one='" + one + '\'' +
                    ", two='" + two + '\'' +
                    ", three='" + three + '\'' +
                    ", four=" + four +
                    ", five=" + five +
                    ", six='" + six + '\'' +
                    ", seven='" + seven + '\'' +
                    '}';
        }
    }
}
