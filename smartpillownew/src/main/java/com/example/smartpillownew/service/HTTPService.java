package com.example.smartpillownew.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.utils.Constance;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a450J on 2018/8/10.
 */

public class HTTPService extends Service {

    private static final String TAG = "HTTPService";
    private boolean flag = true;
    private MyApplication app;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = (MyApplication) getApplication();
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {



        //开启线程，在程序未被Destroy之前获取数据
        new Thread(){
            @Override
            public void run() {
                super.run();
//                //周数据
//                String week_breath_Url = Constance.BASE_URL_WEEK_DATA+"breath";
//                String week_heart_rate_Url = Constance.BASE_URL_WEEK_DATA+"heart";
//                String week_turn_over_Url = Constance.BASE_URL_WEEK_DATA+"turnover";
                //实时数据
                String realtime_breath_Url = Constance.BASE_URL_REAL_TIME+"userid="+app.getUser_id()+"&type=breath";
                String realtime_heart_rate_Url = Constance.BASE_URL_REAL_TIME+"userid="+app.getUser_id()+"&type=heart";
                String realtime_turn_over_Url = Constance.BASE_URL_REAL_TIME+"userid="+app.getUser_id()+"&type=turnover";

                Log.i(TAG, "run: "+realtime_breath_Url);

                while (flag){

                    try {

//                        String week_breath = getDataFromNet(week_breath_Url);
//                        String week_heart_rate = getDataFromNet(week_heart_rate_Url);
//                        String week_turn_over = getDataFromNet(week_turn_over_Url);

                        String realtime_breath = getDataFromNet(realtime_breath_Url);
                        String realtime_heart_rate = getDataFromNet(realtime_heart_rate_Url);
                        String realtime_turn_over = getDataFromNet(realtime_turn_over_Url);

                        //周数据
//                        Intent intentweek = new Intent();
//                        intentweek.setAction(getResources().getString(R.string.week_data));
//                        intentweek.putExtra("breath",week_breath);
//                        intentweek.putExtra("heart",week_heart_rate);
//                        intentweek.putExtra("turnover",week_turn_over);
//                        sendBroadcast(intentweek);

                        //实时数据
                        Intent intentrealtime = new Intent();
                        intentrealtime.setAction(getResources().getString(R.string.realtime_data));
                        intentrealtime.putExtra("breath",realtime_breath);
                        intentrealtime.putExtra("heart",realtime_heart_rate);
                        intentrealtime.putExtra("turnover",realtime_turn_over);
                        sendBroadcast(intentrealtime);

                        Thread.sleep(3000);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
    }

    OkHttpClient client = new OkHttpClient();

    String getDataFromNet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
