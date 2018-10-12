package com.example.smartpillownew.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartpillownew.R;
import com.example.smartpillownew.fragment.BaseFragment;
import com.example.smartpillownew.fragment.FragmentLogin;
import com.example.smartpillownew.fragment.FragmentRegister;
import com.example.smartpillownew.utils.Constance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends FragmentActivity {

    @BindView(R.id.rg_login)
    RadioGroup rgLogin;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.iv_bottom_1)
    ImageView ivBottom1;
    @BindView(R.id.iv_bottom_2)
    ImageView ivBottom2;
    @BindView(R.id.iv_bottom_3)
    ImageView ivBottom3;
    @BindView(R.id.iv_bottom_4)
    ImageView ivBottom4;
    private List<BaseFragment> fragmentList;
    private MyLoginReciever reciever;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent=getIntent();
            String action=mainIntent.getAction();
            if(mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initFragment();

        switchFragment();

        //注册接受登录的广播
        reciever = new MyLoginReciever();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constance.LOGIN);
        registerReceiver(reciever, filter);
    }

    private void switchFragment() {
        rgLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_login:
                        position = 0;
                        break;
                    case R.id.rb_register:
                        position = 1;
                        break;
                    default:break;
                }
                Fragment to = getFragment();
                change(to);
            }
        });
        rgLogin.check(R.id.rb_login);
    }

    private void change(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fl_content,fragment);
        ft.commit();
    }

    private BaseFragment getFragment() {
        return fragmentList.get(position);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentLogin());
        fragmentList.add(new FragmentRegister());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        if (reciever != null) {
            unregisterReceiver(reciever);
            reciever = null;
        }
    }

    @OnClick({R.id.iv_bottom_1, R.id.iv_bottom_2, R.id.iv_bottom_3, R.id.iv_bottom_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bottom_1:

            case R.id.iv_bottom_2:

            case R.id.iv_bottom_3:

            case R.id.iv_bottom_4:
                Toast.makeText(LoginActivity.this, "正在审核", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class MyLoginReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }
}
