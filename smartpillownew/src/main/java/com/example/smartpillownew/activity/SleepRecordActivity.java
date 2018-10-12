package com.example.smartpillownew.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.view.DountChart01View;
import com.example.smartpillownew.view.GardientView;

import org.xclcharts.chart.PieData;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepRecordActivity extends Activity {

    private static final String TAG = "SleepRecordActivity";
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.gardient_view)
    GardientView gardientView;
    @BindView(R.id.dount_view)
    DountChart01View dountView;

    private int[] gardientData;
    private LinkedList<PieData> dountData = new LinkedList<PieData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_record);
        ButterKnife.bind(this);

        titleTitleName.setText("睡眠记录");

        initData();
    }

    private void initData() {
        //gardient
        //一个15个等级，0-4清醒，5-8浅度睡眠，9-12深度睡眠
        gardientData = new int[]{2, 2, 6, 12, 10, 9, 11, 10, 12, 8, 10, 6, 7, 4, 2};
        gardientView.getData(gardientData);
        gardientView.postInvalidate();
        //dountchart

        dountData.add(new PieData("清醒","25%",25, Color.RED));
        dountData.add(new PieData("深度睡眠","25%",25,Color.YELLOW));
        dountData.add(new PieData("浅度睡眠","50%",50,Color.BLUE));
        dountView.getData(dountData);
        dountView.postInvalidate();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
