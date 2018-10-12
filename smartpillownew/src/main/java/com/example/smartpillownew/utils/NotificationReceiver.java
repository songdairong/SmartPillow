package com.example.smartpillownew.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by a450J on 2018/8/14.
 */

public class NotificationReceiver extends BroadcastReceiver{
    private static final String TAG = "NotificationReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: ");
    }
}
