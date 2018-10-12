package com.example.smartpillownew.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.activity.AccountInfoActivity;
import com.example.smartpillownew.activity.BiologicDetectActivity;
import com.example.smartpillownew.activity.EquipmentActivity;
import com.example.smartpillownew.activity.HelpActivity;
import com.example.smartpillownew.activity.LoginActivity;
import com.example.smartpillownew.activity.ReconmmendActivity;
import com.example.smartpillownew.fragment.BaseFragment;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by a450J on 2018/8/9.
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_account_info)
    TextView userAccountInfo;
    @BindView(R.id.user_biologic_point)
    TextView userBiologicPoint;
    @BindView(R.id.user_help)
    TextView userHelp;
    @BindView(R.id.user_recommend)
    TextView userRecommend;
    @BindView(R.id.btn_exit)
    Button btnExit;
    Unbinder unbinder;
    @BindView(R.id.user_equipment_manage)
    TextView userEquipmentManage;

    private MyApplication app;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_user, null);
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
        initData();
        return rootView;
    }

    private void initData() {
        String tmp = CachUtils.getCachData(context,Constance.LOGININFO_KEY);
        if (!tmp.equals("")){
            String[] tmps = tmp.split(":");
            String name = "当前账号："+tmps[0];
            userName.setText(name);
        }
//        app = (MyApplication) context.getApplicationContext();
//        userName.setText(app.getUser_id());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_account_info, R.id.user_biologic_point, R.id.user_help,
            R.id.user_recommend, R.id.btn_exit, R.id.user_equipment_manage})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.user_account_info:
                intent.setClass(context, AccountInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.user_biologic_point:
                intent.setClass(context, BiologicDetectActivity.class);
                startActivity(intent);
                break;
            case R.id.user_equipment_manage:
                intent.setClass(context, EquipmentActivity.class);
                startActivity(intent);
                break;
            case R.id.user_help:
                intent.setClass(context, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.user_recommend:
                intent.setClass(context, ReconmmendActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                //发送关闭广播
                Intent exitIntent = new Intent(Constance.EXIT);
                context.sendBroadcast(exitIntent);
                break;
        }
    }
}
