package com.example.smartpillownew.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeekSleepActivity extends Activity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.ll_sleep_analysis)
    LinearLayout llSleepAnalysis;
    @BindView(R.id.ll_sleep_report)
    LinearLayout llSleepReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_sleep);
        ButterKnife.bind(this);

        titleTitleName.setText("每周睡眠");
    }

    @OnClick({R.id.title_back, R.id.ll_sleep_analysis, R.id.ll_sleep_report})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.ll_sleep_analysis:
                showDialog();
                break;
            case R.id.ll_sleep_report:
//                intent.setClass(this,SleepAnalysisActivity.class);
//                startActivity(intent);
                Toast.makeText(WeekSleepActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.item_choose_sleep_info,null);
        builder.setView(view);
        TextView tv_breath = view.findViewById(R.id.tv_breath);
        TextView tv_rate = view.findViewById(R.id.tv_rate);
        TextView tv_range = view.findViewById(R.id.tv_range);
        final AlertDialog dialog = builder.create();

        tv_breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSleepActivity.this,WeekReportActivityNew.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        tv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSleepActivity.this,WeekReportRateActivityNew.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        tv_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSleepActivity.this,WeekReportRangeActivityNew.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

//    private void showPop(View view) {
//        PopupMenu menu = new PopupMenu(this,view);
//        menu.inflate(R.menu.popup_week_report);
//        menu.show();
//
//        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent intent = new Intent();
//                switch (menuItem.getItemId()){
//                    case R.id.breath:
//                        intent.setClass(WeekSleepActivity.this,WeekReportActivityNew.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.rate:
//                        intent.setClass(WeekSleepActivity.this,WeekReportRateActivityNew.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.range:
//                        break;
//                }
//                return true;
//            }
//        });
//    }
}
