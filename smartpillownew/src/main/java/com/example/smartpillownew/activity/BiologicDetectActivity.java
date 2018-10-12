package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.example.smartpillownew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BiologicDetectActivity extends Activity {

    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.tv_pressure_high)
    TextView tvPressureHigh;
    @BindView(R.id.tv_pressure_low)
    TextView tvPressureLow;
    @BindView(R.id.tv_pulse)
    TextView tvPulse;
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    @BindView(R.id.tv_shixin_rate)
    TextView tvShixinRate;
    @BindView(R.id.tv_shishang_rate)
    TextView tvShishangRate;
    @BindView(R.id.tv_heart_rate_12)
    TextView tvHeartRate12;
    @BindView(R.id.tv_shixin_rate_12)
    TextView tvShixinRate12;
    @BindView(R.id.tv_shishang_rate_12)
    TextView tvShishangRate12;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.fa_button)
    FloatingActionButton faButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biologic_detect);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleTitleName.setText("生理指标");
    }

    @OnClick({R.id.title_back, R.id.fa_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.fa_button:
                showPupMenu(view);
                break;
        }
    }

    private void showPupMenu(View view) {
        PopupMenu pop = new PopupMenu(BiologicDetectActivity.this,view);

        pop.inflate(R.menu.popup_menu);
        pop.show();

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.real_time_pressure:
                        Toast.makeText(BiologicDetectActivity.this, "实时血压", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dandao_xindian:
                        Toast.makeText(BiologicDetectActivity.this, "单道心电", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dao12_xindian:
                        Toast.makeText(BiologicDetectActivity.this, "12导心电", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

//        pop.setOnDismissListener(new PopupMenu.OnDismissListener() {
//            @Override
//            public void onDismiss(PopupMenu popupMenu) {
//                Toast.makeText(BiologicDetectActivity.this, "pop消失", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
