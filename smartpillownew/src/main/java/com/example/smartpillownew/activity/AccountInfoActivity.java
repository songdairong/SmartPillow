package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountInfoActivity extends Activity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        ButterKnife.bind(this);
        titleTitleName.setText("账号信息");
        initData();
    }

    private void initData() {
        String tmp = CachUtils.getCachData(this, Constance.LOGININFO_KEY);
        if (!tmp.equals("")){
            String[] tmps = tmp.split(":");
            String name = "当前账号："+tmps[0];
            tvUserName.setText(name);
        }
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
