package com.example.smartpillownew.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends Activity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.btn_call_phone)
    Button btnCallPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        titleTitleName.setText("帮助");
    }

    @OnClick({R.id.title_back, R.id.btn_call_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_call_phone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:17353239979");
                intent.setData(uri);
                startActivity(intent);
                break;
        }
    }
}
