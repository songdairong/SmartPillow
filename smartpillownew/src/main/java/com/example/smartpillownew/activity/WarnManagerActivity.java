package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartpillownew.R;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置报警项目
 *
 */
public class WarnManagerActivity extends Activity {

    private static final String TAG = "WarnManagerActivity";

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.iv_switch_breath)
    ImageView ivSwitchBreath;
    @BindView(R.id.iv_switch_heart_rate)
    ImageView ivSwitchHeartRate;
    @BindView(R.id.iv_switch_range)
    ImageView ivSwitchRange;
    @BindView(R.id.iv_switch_bihan)
    ImageView ivSwitchBihan;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private boolean switch_breath ;
    private boolean switch_heart_rate ;
    private boolean switch_range ;
    private boolean switch_bihan ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_manager);
        ButterKnife.bind(this);

        initData();

        initViews();
    }

    /**
     * 初始化按钮的图片
     */
    private void initViews() {

        titleTitleName.setText("报警项目设置");

        initSelected(switch_breath, ivSwitchBreath);
        initSelected(switch_heart_rate, ivSwitchHeartRate);
        initSelected(switch_range, ivSwitchRange);
        initSelected(switch_bihan, ivSwitchBihan);
    }

    /**在创建activity的时候初始化按钮的selected
     * @param b
     * @param view
     */
    private void initSelected(boolean b, ImageView view) {
        if (b) {
            Glide.with(WarnManagerActivity.this).load(R.drawable.btn_light_switch_select).centerCrop().into(view);
        } else {
            Glide.with(WarnManagerActivity.this).load(R.drawable.btn_light_switch_normal).centerCrop().into(view);
        }
    }

    private void initData() {
        String isSelectedResult = CachUtils.getCachData(WarnManagerActivity.this, Constance.WARNSELECTED_KEY);
        Log.i(TAG, "initData: tydch string "+isSelectedResult);
        if (!isSelectedResult.equals("")){
            String[] result = isSelectedResult.split(":");
            switch_breath = Boolean.parseBoolean(result[0]);
            switch_heart_rate = Boolean.parseBoolean(result[1]);
            switch_range = Boolean.parseBoolean(result[2]);
            switch_bihan = Boolean.parseBoolean(result[3]);
        }
    }

    @OnClick({R.id.title_back, R.id.iv_switch_breath, R.id.iv_switch_heart_rate, R.id.iv_switch_range, R.id.iv_switch_bihan, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_switch_breath:
                switch_breath = switchSelected(switch_breath, ivSwitchBreath);
                break;
            case R.id.iv_switch_heart_rate:
                switch_heart_rate = switchSelected(switch_heart_rate, ivSwitchHeartRate);
                break;
            case R.id.iv_switch_range:
                switch_range = switchSelected(switch_range, ivSwitchRange);
                break;
            case R.id.iv_switch_bihan:
                switch_bihan = switchSelected(switch_bihan, ivSwitchBihan);
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_confirm:
                storage();
                break;
        }
    }

    /**
     * 存储数据
     */
    private void storage() {
        String storageResult = switch_breath+":"+ switch_heart_rate+":"+ switch_range+":"+ switch_bihan;
        Log.i(TAG, "storage: tydch "+storageResult);
        CachUtils.putCachData(WarnManagerActivity.this, Constance.WARNSELECTED_KEY,storageResult);
        Toast.makeText(WarnManagerActivity.this, "成功保存", Toast.LENGTH_LONG).show();
    }

    /**
     * 根据状态选择按钮图形
     *
     * @param b   是否选择
     * @param img 要注入的imageview
     */
    private boolean switchSelected(boolean b, ImageView img) {
        if (!b) {
            Glide.with(WarnManagerActivity.this).load(R.drawable.btn_light_switch_select).centerCrop().into(img);
            b = true;
        } else {
            Glide.with(WarnManagerActivity.this).load(R.drawable.btn_light_switch_normal).centerCrop().into(img);
            b = false;
        }
        return b;
    }
}
