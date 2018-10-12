package com.example.smartpillownew.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by a450J on 2018/8/8.
 */

public class CachUtils {

    public static String getCachData(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("tydch",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

    public static void putCachData(Context context, String key, String result) {
        SharedPreferences sp = context.getSharedPreferences("tydch",Context.MODE_PRIVATE);
        sp.edit().putString(key,result).commit();
    }
}
