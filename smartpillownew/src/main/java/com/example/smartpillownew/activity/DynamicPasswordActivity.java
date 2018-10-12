package com.example.smartpillownew.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.MainActivity;
import com.example.smartpillownew.R;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class DynamicPasswordActivity extends Activity {

    private static final int SMS_VERIFY = 0;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_getVerift)
    Button btnGetVerift;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.et_verify)
    EditText etVerify;

    private String phone;
    private String verify;
    private TimeCount timer;

    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SMS_VERIFY:
                        processSMS(msg);
                        break;
                }
            }
        };

    private void processSMS(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;

        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
            //验证成功，开始注册
            //登陆
            if (result == SMSSDK.RESULT_ERROR){
                Toast.makeText(this, "验证码错误，请仔细检查或者重新获取！", Toast.LENGTH_SHORT).show();

            }else if (result == SMSSDK.RESULT_COMPLETE){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //存入sharepreference
                String tmp = ":"+phone+":true";
                CachUtils.putCachData(this,Constance.LOGININFO_KEY,tmp);
            }

        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
            Toast.makeText(this, "验证短信已发出", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_password);
        ButterKnife.bind(this);
        titleTitleName.setText("获取动态密码");
        timer = new TimeCount(60000,1000);

        SMSSDK.registerEventHandler(eventHandler);
    }

    @OnClick({R.id.title_back, R.id.btn_getVerift, R.id.btn_add})
    public void onViewClicked(View view) {
        phone = etPhone.getText().toString().trim();
        verify = etVerify.getText().toString().trim();
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_getVerift:
                dynamicLogin();
                break;
            case R.id.btn_add:
                //开始动态登陆
                verify();
                break;
        }
    }

    private void dynamicLogin() {
        if (!Utils.isMobileNO(phone)){
            Toast.makeText(DynamicPasswordActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
        }else {

            SMSSDK.getVerificationCode("86",phone);
            timer.start();
        }
    }

    /**
     * 请求验证码
     */
    private void verify() {
        if (!Utils.isMobileNO(phone)){
            Toast.makeText(DynamicPasswordActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
        }else {
            SMSSDK.submitVerificationCode("86",phone,verify);
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            btnGetVerift.setClickable(false);
            btnGetVerift.setText(l/1000+"s");
        }

        @Override
        public void onFinish() {
            btnGetVerift.setClickable(true);
            btnGetVerift.setText("重新获取验证码");
        }
    }

    EventHandler eventHandler = new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            super.afterEvent(event, result, data);
            Message message = Message.obtain();
            message.arg1 = event;
            message.arg2 = result;
            message.obj = data;
            message.what = SMS_VERIFY;
            handler.sendMessage(message);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
