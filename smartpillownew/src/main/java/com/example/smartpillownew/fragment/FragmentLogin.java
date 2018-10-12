package com.example.smartpillownew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.MainActivity;
import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.activity.DynamicPasswordActivity;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.Utils;

import java.io.IOException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by a450J on 2018/8/6.
 */

public class FragmentLogin extends BaseFragment {
    private static final String TAG = "FragmentLogin";
    private static final int LOGIN_RESULT = 0;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_password)
    EditText etUserPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;
    @BindView(R.id.checkbox)
    CheckBox checkbox;

    private String name;
    private String password;
    private MyApplication app;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_RESULT:
                    processData(msg.obj.toString());
                    break;
            }
        }
    };



    private void processData(String result) {
        switch (result) {
            case "0":
                Toast.makeText(context, "登陆失败！请检查登录名和密码", Toast.LENGTH_SHORT).show();
                break;
            case "1":

                //发出已经登录的广播
                Intent loginIntent = new Intent();
                loginIntent.setAction(Constance.LOGIN);
                context.sendBroadcast(loginIntent);

                Toast.makeText(context, "登陆成功！", Toast.LENGTH_SHORT).show();

                //放入sharepreference中
                String tmp = name + ":" + password + ":" + checkbox.isChecked();
                CachUtils.putCachData(context, Constance.LOGININFO_KEY, tmp);

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

                //放入全局变量
//                app.setUser_id(name);
        }
    }

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_login, null);

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

        initPassName();

        app = (MyApplication) context.getApplicationContext();

        return rootView;
    }

    //是否显示密码
    private void initPassName() {
        String tmp = CachUtils.getCachData(context, Constance.LOGININFO_KEY);
        if (!tmp.equals("")) {
            String[] tmps = tmp.split(":");
            etUserName.setText(tmps[0]);
            if (tmps[2].equals("true")) {
                etUserPassword.setText(tmps[1]);
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void login() {
        name = etUserName.getText().toString().trim();
        password = etUserPassword.getText().toString().trim();
        if (name.equals("")) {
            Toast.makeText(context, "登录名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                Message message;
                StringBuilder builder = new StringBuilder();
                try {
                    builder.append("userid=")
                            .append(URLEncoder.encode(name,"utf-8"))
                            .append("&password=")
                            .append(password);
                    String reslut = getDataFromNet(Constance.LOGIN_URL+builder.toString());
                    message = Message.obtain();
                    message.what = LOGIN_RESULT;
                    message.obj = reslut;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    OkHttpClient client = new OkHttpClient();

    String getDataFromNet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @OnClick({R.id.tv_forget_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                Intent intent = new Intent(context, DynamicPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();

                break;
        }
    }
}
