package com.example.smartpillownew.bean;

/**
 * Created by a450J on 2018/8/10.
 */

public class GetRangeBean {

    /**
     * status : 1
     * data : {"min":"1","max":"20"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * min : 1
         * max : 20
         */

        private String min;
        private String max;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "min='" + min + '\'' +
                    ", max='" + max + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetRangeBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
