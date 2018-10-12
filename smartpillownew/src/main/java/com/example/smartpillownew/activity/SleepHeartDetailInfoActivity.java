package com.example.smartpillownew.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.RealTimeBreathDataBean;
import com.example.smartpillownew.view.PathView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepHeartDetailInfoActivity extends Activity {

    private static final String TAG = "SleepHeartDetailInfoActivity";

    private static final int RECEIVE_DATA = 0;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
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
    @BindView(R.id.breath_view)
    PathView breathView;
    private String data;
    private int[] heartdataList = new int[]{0, 0, 0, 0, 0, 0};
    private int[] heartrangeList = new int[]{0, 0, 0, 0, 0, 0};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RECEIVE_DATA:
                    processData(data);
                    break;
            }
        }
    };
    private MyReceiver receiver;
    private Random random;

    private void processData(String data) {
        Gson gson = new Gson();
        RealTimeBreathDataBean bean = gson.fromJson(data, new TypeToken<RealTimeBreathDataBean>() {
        }.getType());

        if (bean.getData() != null) {


            tvHaveMenBreath.setText("有人");
            int result = Integer.parseInt(bean.getData());
            if (result == 0) {
                result += 1;
            }

            tvCurrentPointBreath.setText(bean.getData() + "次/分钟");
//        tvHaveMenBreath.setText(bean.getEmpty() == );
            if (bean.getAbnormal() == 0) {
                tvIsNormalBreath.setText("正常");
                tvIsNormalBreath.setTextColor(getResources().getColor(R.color.green2));
            } else {
                tvIsNormalBreath.setText("异常");
                tvIsNormalBreath.setTextColor(Color.RED);
            }

            tvTimeBreath.setText(bean.getDate());
            if (result < 60) {
                tvPointRangeBreath.setText("0~60");
                tvCurrentPointBreath.setTextColor(Color.RED);
            } else if (result > 120) {
                tvPointRangeBreath.setText(">120");
                tvCurrentPointBreath.setTextColor(Color.RED);
            } else {
                tvPointRangeBreath.setText("60~120");
                tvCurrentPointBreath.setTextColor(getResources().getColor(R.color.green2));
            }

            random = new Random();
            int range = 100 * (random.nextInt(3) + 2);

            addData(result, heartdataList);
            addData(range, heartrangeList);
            breathView.setData(heartdataList, heartrangeList);
            breathView.invalidate();
        } else {
            tvHaveMenBreath.setText("无人");
            tvCurrentPointBreath.setText("无数据");
            tvPointRangeBreath.setText("无数据");
            tvIsNormalBreath.setText("无数据");
            tvTimeBreath.setText("无数据");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_heart_detail_info);
        ButterKnife.bind(this);

        initTitle();

        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getResources().getString(R.string.realtime_data));
        registerReceiver(receiver, filter);
    }

    private void initTitle() {
        titleTitleName.setText("睡眠：心率详细信息");
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            data = intent.getStringExtra("heart");
            if (data != null) {
                handler.sendEmptyMessage(RECEIVE_DATA);
            }
        }
    }

    private void addData(int data, int[] target) {
        for (int i = target.length - 1; i > 0; i--) {
            target[i] = target[i - 1];
        }
        target[0] = data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    /**
     * 根据数位返回是否是1
     *
     * @param num
     * @param index
     * @return
     */
    public static int get(int num, int index) {
        return (num & (0x1 << index)) >> index;
    }

}
