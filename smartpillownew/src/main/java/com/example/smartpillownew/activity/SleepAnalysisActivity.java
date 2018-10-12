package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.view.GardientView1;
import com.example.smartpillownew.view.GardientView2;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepAnalysisActivity extends Activity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.gardient_view1)
    GardientView1 gardientView1;
    @BindView(R.id.gardient_view2)
    GardientView2 gardientView2;

    private int[] data1 = {5, 4, 5, 6, 5, 5, 4, 4, 5, 6, 5, 4, 4, 5, 5};
    private int[] data2 = {13, 14, 15, 13, 14, 15, 14, 13, 14, 15, 15, 14, 15, 15, 14};

    private int[] data3 = new int[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_analysis);
        ButterKnife.bind(this);

        titleTitleName.setText("睡眠报告");

        initData();
    }

    private void initData() {

        gardientView1.getData(data1, data2);
        gardientView1.postInvalidate();

        Random random ;
        for(int i=0 ; i<8 ; i++){
            random = new Random();
            data3[i] = random.nextInt(5)+6;
        }
        gardientView2.getData(data3);
        gardientView2.postInvalidate();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }
}
