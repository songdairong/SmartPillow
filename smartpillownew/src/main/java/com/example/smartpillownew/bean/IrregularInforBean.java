package com.example.smartpillownew.bean;

/**
 * Created by a450J on 2018/8/7.
 */

public class IrregularInforBean {
    private int imageSrc;
    private String info;
    private String time;
    private String date;

    public IrregularInforBean(int imageSrc, String info, String time, String date) {
        this.imageSrc = imageSrc;
        this.info = info;
        this.time = time;
        this.date = date;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "IrregularInforBean{" +
                "imageSrc=" + imageSrc +
                ", info='" + info + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
