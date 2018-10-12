package com.example.smartpillownew.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * Created by a450J on 2018/8/11.
 */

public class UserWarnMemberBean extends LitePalSupport{

    @Column(unique = true , defaultValue = "unknown")
    private String name;

    @Column(nullable = false)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserWarnMemberBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
