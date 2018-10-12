package com.example.smartpillownew.bean;

/**
 * Created by a450J on 2018/8/10.
 */

public class RealTimeTurnDataBean {

    /**
     * empty : 100
     * abnormal : 0
     * data : 0
     * date : 2018-08-14 16:49:03
     * status : 100
     */

    private String empty;
    private int abnormal;
    private String data;
    private String date;
    private String status;

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public int getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(int abnormal) {
        this.abnormal = abnormal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
