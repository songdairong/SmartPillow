package com.example.smartpillownew.bean;

/**
 * Created by a450J on 2018/8/7.
 */

public class UserBean {
    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "item='" + item + '\'' +
                '}';
    }
}
