package com.example.smartpillownew.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.RealTimeBreathDataBean;
import com.example.smartpillownew.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by a450J on 2018/8/10.
 */

public class MonitorFragment extends BaseFragment {

    private static final String TAG = "MonitorFragment";
    private static final int BREATH_DATA = 0;
    private static final int HEART_DATA = 1;
    private static final int TURNOVER_DATA = 2;


    @BindView(R.id.iv_breath_bg)
    ImageView ivBreathBg;
    @BindView(R.id.tv_current_point_breath)
    TextView tvCurrentPointBreath;
    @BindView(R.id.tv_have_men_breath)
    TextView tvHaveMenBreath;
    @BindView(R.id.tv_is_normal_breath)
    TextView tvIsNormalBreath;
    @BindView(R.id.tv_point_range_breath)
    TextView tvPointRangeBreath;
    @BindView(R.id.tv_time_breath)
    TextView tvTimeBreath;
    @BindView(R.id.iv_heart_rate_bg)
    ImageView ivHeartRateBg;
    @BindView(R.id.tv_current_point_heart_rate)
    TextView tvCurrentPointHeartRate;
    @BindView(R.id.tv_have_men_heart_rate)
    TextView tvHaveMenHeartRate;
    @BindView(R.id.tv_is_normal_heart_rate)
    TextView tvIsNormalHeartRate;
    @BindView(R.id.tv_point_range_heart_rate)
    TextView tvPointRangeHeartRate;
    @BindView(R.id.tv_time_heart_rate)
    TextView tvTimeHeartRate;
    @BindView(R.id.iv_range_bg)
    ImageView ivRangeBg;
    @BindView(R.id.tv_current_point_bihan)
    TextView tvCurrentPointBihan;
    @BindView(R.id.tv_have_men_bihan)
    TextView tvHaveMenBihan;
    @BindView(R.id.tv_is_normal_bihan)
    TextView tvIsNormalBihan;
    @BindView(R.id.tv_point_range_bihan)
    TextView tvPointRangeBihan;
    @BindView(R.id.tv_time_bihan)
    TextView tvTimeBihan;
    Unbinder unbinder;
    private String data_breath;
    private String data_heart;
    private String data_turnover;
    private MyReceiver receiver;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_monitor, null);

        return view;
    }

    @Override
    public void initDat() {
        super.initDat();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getResources().getString(R.string.realtime_data));
        context.registerReceiver(receiver,filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        //解注册
        context.unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class MyReceiver extends BroadcastReceiver {
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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BREATH_DATA:
                    processData(data_breath,tvCurrentPointBreath,tvHaveMenBreath,tvIsNormalBreath,tvPointRangeBreath,tvTimeBreath,ivBreathBg);
                    break;
                case HEART_DATA:
                    processData(data_heart,tvCurrentPointHeartRate,tvHaveMenHeartRate,tvIsNormalHeartRate,tvPointRangeHeartRate,tvTimeHeartRate,ivHeartRateBg);
                    break;
                case TURNOVER_DATA:
                    processData(data_turnover,tvCurrentPointBihan,tvHaveMenBihan,tvIsNormalBihan,tvPointRangeBihan,tvTimeBihan,ivRangeBg);
                    break;
            }
        }
    };

    private void processData(String json, TextView now_data, TextView have_man, TextView is_normal, TextView range, TextView time, ImageView backgroud) {
        Gson gson = new Gson();
        RealTimeBreathDataBean bean = gson.fromJson(json,new TypeToken<RealTimeBreathDataBean>(){}.getType());
        now_data.setText(bean.getData());



//        if (bean.getEmpty() == 1){
//            have_man.setText("有人");
//            have_man.setTextColor(getResources().getColor(R.color.green2));
//        }else {
//            have_man.setText("无人");
//            have_man.setTextColor(getResources().getColor(R.color.red3));
//        }

        if (bean.getAbnormal() == 1){
            is_normal.setText("正常");
            is_normal.setTextColor(getResources().getColor(R.color.green2));
        }else {
            is_normal.setText("异常");
            is_normal.setTextColor(getResources().getColor(R.color.red3));
        }

        time.setText(bean.getDate());
        if (Integer.parseInt(bean.getData()) == 0){
            Glide.with(context).load(R.drawable.bg_sleep_report_no_data2).centerCrop().override(380,150).into(backgroud);
        }else {
            Glide.with(context).load(R.drawable.sleep_info_top_bg).centerCrop().override(380,350).into(backgroud);
        }
    }
}
