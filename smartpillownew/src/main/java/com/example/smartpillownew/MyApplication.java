package com.example.smartpillownew;

import android.app.Application;

import com.mob.MobSDK;

import org.litepal.LitePal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by a450J on 2018/8/11.
 */

public class MyApplication extends Application{

    private String user_id;


    public String getUser_id() {
        String tmp;
        try {
            tmp = URLEncoder.encode(user_id,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            tmp="-1";
        }
        return tmp;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public void onCreate() {
        user_id = "qianqian";

        super.onCreate();

        LitePal.initialize(this);

        MobSDK.init(this);
    }
}
