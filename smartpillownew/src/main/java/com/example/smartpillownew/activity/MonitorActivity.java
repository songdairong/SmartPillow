package com.example.smartpillownew.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MonitorActivity extends Activity {

    private static final int RECEIVE_DATA = 0;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
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

    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        ButterKnife.bind(this);

        titleTitleName.setText("实时数据");

        //注册广播接收器

    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            data = intent.getStringExtra("result");
            if (!data.equals("")){
                handler.sendEmptyMessage(RECEIVE_DATA);
            }
        }
    }

    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case RECEIVE_DATA:
                        processData(data);
                        break;
                }
            }
        };

    private void processData(String json) {

    }
}
