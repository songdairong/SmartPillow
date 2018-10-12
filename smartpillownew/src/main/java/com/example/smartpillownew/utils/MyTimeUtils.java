package com.example.smartpillownew.utils;

import java.util.Calendar;

/**
 * Created by a450J on 2018/8/21.
 */

public class MyTimeUtils {
    private Calendar calendar;

    /**上午返回0，中午返回1，下午返回2，晚上返回3
     * @return
     */
    public int getAmorPm(){
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour<11){
            return 0;
        }else if (hour<13){
            return 1;
        }else if (hour<18){
            return 2;
        }else if (hour<24){
            return 3;
        }
        return -1;
    }

    /**
     * @param date 2018-08-23
     * @return 和当前日期的差值（大概）
     */
    public int DateDiffer(String date){
        String[] dates = date.split("-");

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH)-Integer.parseInt(dates[2]);
        int month = calendar.get(Calendar.MONTH)+1-Integer.parseInt(dates[1]);
        return month*30+day;
    }
}
