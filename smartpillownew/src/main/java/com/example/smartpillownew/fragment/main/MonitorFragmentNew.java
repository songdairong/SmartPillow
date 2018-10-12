package com.example.smartpillownew.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.activity.SleepDetailInfoActivity;
import com.example.smartpillownew.activity.SleepHeartDetailInfoActivity;
import com.example.smartpillownew.bean.RealTimeBreathDataBean;
import com.example.smartpillownew.bean.RealTimeHeartDataBean;
import com.example.smartpillownew.bean.RealTimeTurnDataBean;
import com.example.smartpillownew.fragment.BaseFragment;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.ImageFlickerAnimation;
import com.example.smartpillownew.utils.ServiceUtils;
import com.example.smartpillownew.view.PathView;
import com.example.smartpillownew.view.PathView2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Random;

/**
 * Created by a450J on 2018/8/13.
 */

public class MonitorFragmentNew extends BaseFragment{

    private static final String TAG = "MonitorFragmentNew";
    private static final int HEART_DATA = 0;
    private static final int BREATH_DATA = 1;
    private static final int TURNOVER_DATA = 2;
    private static final int MONITOR_ACTION = 3;
    private static final int START_SERVICE = 4;
    private static final int STOP_SERVICE = 5;

    private PathView heart_view;
    private PathView2 breath_view;
    private int[] heartdataList = new int[6];
    private int[] breathdataList = new int[6];
    private int[] heartrangeList = new int[6];
    private int[] breathrangeList = new int[6];
    private boolean flag = true;
    private MyReceiver receiver;
    private MyMonitorReceiver monitorReceiver;
    private MyThresHoldeReceiver thresHoldeReceiver;

    private TextView tv_current_point_breath;
    private TextView tv_current_point_heart;
    private TextView tv_current_point_turn;
    private TextView tv_monitor_breath;
    private TextView tv_monitor_heart;

    private TextView tv_breath_no_data;
    private TextView tv_heart_no_data;

    private ImageView iv_breath;
    private  ImageView iv_heart;
    private ImageView iv_turn;

    private LinearLayout ll_breath;
    private LinearLayout ll_heart;

    private String monitorAction;

    private  ImageFlickerAnimation animation ;

    private Random random;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HEART_DATA:
//                    processData((int)msg.obj);
                    processheartHttpData(data_heart);
                    break;
                case BREATH_DATA:
//                    processBreathData((int)msg.obj);
                    processbreathHttpData(data_breath);
                    break;
                case TURNOVER_DATA:
                    break;
                case MONITOR_ACTION:
                    switch (monitorAction){
                        case "start":
                            Log.i(TAG, "handleMessage: animation start");
                            startAnimation();
                            break;
                        case "stop":
                            Log.i(TAG, "handleMessage: animation stop");
                            stopAnimation();
                            break;
                    }
                    break;
            }
        }
    };
    private int[] dataList;

    private void stopAnimation() {
        iv_breath.clearAnimation();
        iv_heart.clearAnimation();

        tv_monitor_breath.setVisibility(View.GONE);
        tv_monitor_heart.setVisibility(View.GONE);
    }

    private void startAnimation() {
        animation.ImageFlickerAnimation(iv_heart);
        animation.ImageFlickerAnimation(iv_breath);

        tv_monitor_breath.setVisibility(View.VISIBLE);
        tv_monitor_heart.setVisibility(View.VISIBLE);

    }

    /**处理呼吸网络数据
     * @param data
     */
    private void processbreathHttpData(String data) {
        random = new Random();
        int range = 100*(random.nextInt(3)+2);
        Gson gson = new Gson();
        RealTimeBreathDataBean bean = gson.fromJson(data,new TypeToken<RealTimeBreathDataBean>(){}.getType());
        if (bean.getData() != null){
            tv_breath_no_data.setVisibility(View.GONE);
            int point = Integer.parseInt(bean.getData());
            if (point == 0){
                point+=1;
            }
            setTextData(bean.getData(),tv_current_point_breath,iv_breath,dataList[0],dataList[1],0);
            addData(point,breathdataList);
            addData(range,breathrangeList);
            breath_view.getData(breathdataList,breathrangeList);
            breath_view.invalidate();
        }else {
            tv_breath_no_data.setVisibility(View.VISIBLE);
        }

//        if (get(bean.getStatus(),0) == 1){



//        }else {
//            tv_current_point_breath.setText("无数据");
//        }

    }

    /**处理心率网络数据
     * @param data
     */
    private void processheartHttpData(String data) {
        random = new Random();
        int range = 100*(random.nextInt(3)+2);
        Gson gson = new Gson();
        RealTimeHeartDataBean bean = gson.fromJson(data,new TypeToken<RealTimeHeartDataBean>(){}.getType());
        if (bean.getData()!=null){
            tv_heart_no_data.setVisibility(View.GONE);
            int point = Integer.parseInt(bean.getData());
            if (point == 0){
                point+=1;
            }
            setTextData(bean.getData(), tv_current_point_heart, iv_heart, dataList[2],dataList[3], 1);
            addData(point, heartdataList);
            addData(range, heartrangeList);
            heart_view.setData(heartdataList,heartrangeList);
            heart_view.invalidate();
        }else {
            tv_heart_no_data.setVisibility(View.VISIBLE);
        }

//        if (get(bean.getStatus(),0) == 1) {

//        }else {
//            tv_current_point_heart.setText("无数据");
//        }
    }

    private String data_breath;
    private String data_heart;
    private String data_turnover;

    @Override
    protected View initView() {
        Log.i(TAG, "initView: ");
        View view = View.inflate(context, R.layout.fragment_monitor_new,null);
        heart_view = view.findViewById(R.id.heart_view);
        breath_view = view.findViewById(R.id.breath_view);
        tv_current_point_heart = view.findViewById(R.id.tv_current_point_heart);
        tv_current_point_breath = view.findViewById(R.id.tv_current_point_breath);
        tv_breath_no_data = view.findViewById(R.id.tv_breath_no_data);
        tv_heart_no_data = view.findViewById(R.id.tv_heart_no_data);

        ll_breath = view.findViewById(R.id.ll_breath);
        ll_breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SleepDetailInfoActivity.class);
                startActivity(intent);
            }
        });

        ll_heart = view.findViewById(R.id.ll_heart);
        ll_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SleepHeartDetailInfoActivity.class);
                startActivity(intent);
            }
        });

        iv_breath = view.findViewById(R.id.iv_breath);
        iv_heart = view.findViewById(R.id.iv_heart);


        tv_monitor_breath = view.findViewById(R.id.tv_monitor_breath);
        tv_monitor_heart = view.findViewById(R.id.tv_monitor_heart);


        animation = new ImageFlickerAnimation();

        return view;
    }

    @Override
    public void initDat() {
        Log.i(TAG, "initDat: ");
        super.initDat();

        getThresHolde();

        flag = true;
//        new Thread(){
//            Message message;
//            @Override
//            public void run() {
//                super.run();
//                while (true){
//
//                    if (!flag){
//                        break;
//                    }
//
////                    message = Message.obtain();
////                    Random random = new Random();
////                    int i = 10*random.nextInt(15);
////                    message.what = HEART_DATA;
////                    message.obj = i;
////                    handler.sendMessage(message);
////
////                    message = Message.obtain();
////                    int j = random.nextInt(10)+10;
////                    message.what = BREATH_DATA;
////                    message.obj = j;
////                    handler.sendMessage(message);
//
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    if (!flag)
//                        break;
//                    if (ServiceUtils.isServiceRunning(context,"com.example.smartpillownew.service.HTTPService") &&
//                            !isServiceRunning){
//                        //如果在运行，则开始闪烁和显示监测
//                        isServiceRunning = true;
//                        handler.sendEmptyMessage(START_SERVICE);
//                        Log.i(TAG, "run: start animation");
//                    }else if (!ServiceUtils.isServiceRunning(context,"com.example.smartpillownew.service.HTTPService") &&
//                            isServiceRunning){
//                        isServiceRunning = false;
//                        handler.sendEmptyMessage(STOP_SERVICE);
//                        Log.i(TAG, "run: stop animation");
//                    }
//                }
//            }
//        }).start();
    }

    private void getThresHolde() {
        String data = CachUtils.getCachData(context, Constance.RANGESET_KEY);
        Log.i(TAG, "initRange: data = "+data);
        if (data!=null){
            String[] datas = data.split(":");
            dataList = new int[6];
            for (int i = 0; i < datas.length; i++) {
                Log.i(TAG, "initRange: "+datas[i]);
                dataList[i] = (datas[i] == ""?0:Integer.parseInt(datas[i]));
            }
        }
    }

    private void addData(int data , int[] target){
        for (int i=target.length-1;i>0;i--){
            target[i] = target[i-1];
        }
        target[0] = data;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        flag = false;
        //动态解注册
        if (receiver != null){
            context.unregisterReceiver(receiver);
            receiver = null;
        }
        if (monitorReceiver != null){
            context.unregisterReceiver(monitorReceiver);
            monitorReceiver = null;
        }
        if (thresHoldeReceiver != null){
            context.unregisterReceiver(thresHoldeReceiver);
            thresHoldeReceiver = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        //动态注册广播接收器
        if (receiver == null){
            receiver = new MyReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(getResources().getString(R.string.realtime_data));
            context.registerReceiver(receiver,filter);
        }

        if (monitorReceiver == null){
            monitorReceiver = new MyMonitorReceiver();
            IntentFilter monitorfileter = new IntentFilter();
            monitorfileter.addAction(getResources().getString(R.string.monitor_intent));
            context.registerReceiver(monitorReceiver,monitorfileter);
        }

        if (thresHoldeReceiver == null){
            thresHoldeReceiver = new MyThresHoldeReceiver();
            IntentFilter thresholdeFilter = new IntentFilter();
            thresholdeFilter.addAction(Constance.NOTIFY_SETTINGS_CHANGE);
            context.registerReceiver(thresHoldeReceiver,thresholdeFilter);
        }

    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            data_breath = intent.getStringExtra("breath");
            data_heart = intent.getStringExtra("heart");
            data_turnover = intent.getStringExtra("turnover");

            if (!data_breath.equals("")){
                handler.sendEmptyMessage(BREATH_DATA);
            }
            if (!data_heart.equals("")){
                handler.sendEmptyMessage(HEART_DATA);
            }
            if (!data_turnover.equals("")){
                handler.sendEmptyMessage(TURNOVER_DATA);
            }
        }
    }



    /**根据数据选择控件颜色
     * @param data
     * @param textView
     * @param view
     * @param min 推荐最小值
     * @param max 推荐最大值
     * @param status 0-breath,1-heart,2-turnover
     */
    private void setTextData(String data, TextView textView, ImageView view, int min, int max, int status) {
        if (Integer.parseInt(data)<10){
            textView.setText("0"+data);
        }else {
            textView.setText(data);
        }
        int i = Integer.parseInt(data);
        if (i>min && i<max){
            textView.setTextColor(getResources().getColor(R.color.green2));
            switch (status){
                case 0:
                    view.setImageResource(R.drawable.iv_breath);
                    break;
                case 1:
                    view.setImageResource(R.drawable.iv_heart_rate);
                    break;
                case 2:
                    view.setImageResource(R.drawable.iv_range);
                    break;
            }
        }else {
            textView.setTextColor(getResources().getColor(R.color.red3));
            switch (status){
                case 0:
                    view.setImageResource(R.drawable.iv_breath_trouble);
                    break;
                case 1:
                    view.setImageResource(R.drawable.iv_heart_rate_trouble);
                    break;
                case 2:
                    view.setImageResource(R.drawable.iv_range_trouble);
                    break;
            }
        }
    }

    /**根据数位返回是否是1
     * @param num
     * @param index
     * @return
     */
    public static int get(int num, int index)
    {
        return (num & (0x1 << index)) >> index;
    }

    class MyMonitorReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            monitorAction = intent.getStringExtra("action");
            if (monitorAction != null){
                handler.sendEmptyMessage(MONITOR_ACTION);
            }
        }
    }

    class MyThresHoldeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            getThresHolde();
            Log.i(TAG, "onReceive: MyThresHoldeReceiver ");
        }
    }
}
