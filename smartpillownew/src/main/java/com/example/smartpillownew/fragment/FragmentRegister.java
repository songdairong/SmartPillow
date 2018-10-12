package com.example.smartpillownew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.MainActivity;
import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a450J on 2018/8/6.
 */

public class FragmentRegister extends BaseFragment {

    private static final String TAG = "FragmentRegister";
    private static final int COUNT_DOWN = 0;
    private static final int SMS_VERIFY = 1;
    private static final int REGISTER_VERIFY = 2;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_password)
    EditText etUserPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.tv_get_verification)
    TextView tvGetVerification;
    @BindView(R.id.et_user_verification)
    EditText etUserVerification;

    private String phone;
    private String password;
    private String verifycode;
    private String name;
    private MyApplication app;

    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case COUNT_DOWN:
                        processCount(msg.arg1);
                        break;
                    case SMS_VERIFY:

                            processSms(msg);

                        break;
                    case REGISTER_VERIFY:
                        processRegister(msg.obj.toString().trim());
                        break;
                }
            }
        };



    private void processRegister(String result) {
        String info;
        switch (result){
            case "1"://成功
                info="注册成功";

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                //发出已经登录的广播
                Intent loginIntent = new Intent();
                loginIntent.setAction(Constance.LOGIN);
                context.sendBroadcast(loginIntent);
                //存入sharepreference中
                String tmp = name+":"+password+":"+"true";
                CachUtils.putCachData(context,Constance.LOGININFO_KEY,tmp);

                // TODO: 2018/9/11 放入全局变量
//            app.setUser_id(phone);
                break;
            case "2"://用户名被占用
                info="用户名被占用";
                break;
            case "3"://该号码已被注册
                info="该号码已被注册";
                break;
            case "4"://该号码已注册，请直接登陆
                info="该用户已注册，请直接登陆";
                break;
            default:
                info="未知错误";
                break;
        }
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }


    /**处理短信注册事件
     * @param msg
     */
    private void processSms(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        Log.i(TAG, "processSms: event = "+event);
        Log.i(TAG, "processSms: result = "+result);

        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
            //验证成功，开始注册
            //登陆
            if (result == SMSSDK.RESULT_ERROR){
                Toast.makeText(context, "验证码错误，请仔细检查或者重新获取！", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "processSms: data = "+data.toString());
            }else if (result == SMSSDK.RESULT_COMPLETE){

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            StringBuilder builder = new StringBuilder();
                            Log.i(TAG, "run: "+name);
                            builder.append("userid=")
                                    .append(URLEncoder.encode(name,"utf-8"))
                                    .append("&password=")
                                    .append(password)
                                    .append("&telphone=")
                                    .append(phone);
                            Log.i(TAG, "run: "+builder);
                            String data1 = getDataFromNet(Constance.REGISTER_URL+builder);
                            Message message = Message.obtain();
                            message.what = REGISTER_VERIFY;
                            message.obj = data1;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
            Toast.makeText(context, "验证短信已发出", Toast.LENGTH_SHORT).show();
        }
    }

    private void processCount(int count) {
        if (tvGetVerification != null){
            if (count<=0){
                tvGetVerification.setText("再次获取验证码");
                tvGetVerification.setClickable(true);
            }else {
                tvGetVerification.setClickable(false);
                String str = "剩余"+String.valueOf(count)+"s";
                tvGetVerification.setText(str);
            }
        }

    }

    /**
     * 判断是否已经过了60s
     */
    private boolean flag;

    /**
     * 判断context是否存活
     */
    private boolean isAlive;

    @Override
    public void onPause() {
        super.onPause();
        SMSSDK.unregisterAllEventHandler();
        isAlive = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isAlive = true;
    }

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_register, null);
        return view;
    }

    @Override
    public void initDat() {
        super.initDat();
        isAlive = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        SMSSDK.registerEventHandler(eventHandler);
        app = (MyApplication) context.getApplicationContext();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_get_verification, R.id.btn_login})
    public void onViewClicked(View view) {
        phone = getTxt(etUserPhone);
        password = getTxt(etUserPassword);
        verifycode = getTxt(etUserVerification);
        name = getTxt(etUserName);
        switch (view.getId()) {
            case R.id.tv_get_verification:
                verifyPhone();
                break;
            case R.id.btn_login:
                verify();
                break;
        }
    }

    private void verify() {
        if (name.equals("") || phone.equals("") ||
                verifycode.equals("") || password.equals("")){
            Toast.makeText(context, "填写信息有误", Toast.LENGTH_SHORT).show();
        }else {
            SMSSDK.submitVerificationCode("86",phone,verifycode);
        }
    }

    private void verifyPhone() {
        if ( !Utils.isMobileNO(phone)){
            Toast.makeText(context, "手机号格式错误", Toast.LENGTH_SHORT).show();
        }else {

            SMSSDK.getVerificationCode("86",phone);

            //开始倒计时60s
            flag = false;
            new Thread(){
                @Override
                public void run() {
                    Log.i(TAG, "run: verify");
                    super.run();
                    Message message;
                    int a = 60;

                    while (true){
                        if (flag)break;

                        a-=1;
                        if (a<=0)flag = true;

                        message = Message.obtain();
                        message.what=COUNT_DOWN;
                        message.arg1=a;
                        handler.sendMessage(message);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    private String getTxt(EditText text) {
        return text.getText().toString().trim();
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
    public void onDestroy() {
        super.onDestroy();
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
