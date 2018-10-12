package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.WeekDataBean;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.view.LinearChartRangeViewNew;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeekReportRangeActivityNew extends Activity {

    private static final int RECEIVER_DATA = 0;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.new_linear_chart_view)
    LinearChartRangeViewNew newLinearChartView;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_high)
    TextView tvHigh;

    private List<WeekDataBean.DataBean> dataBeans;
    private String[] time;
    private String[] data;

    private int lowDay;
    private int normalDay;
    private int highDay;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            processData(msg.obj.toString());
        }
    };

    private void processData(String json) {
        WeekDataBean bean = new Gson().fromJson(json,new TypeToken<WeekDataBean>(){}.getType());
        if (bean.getStatus() ==1){
            dataBeans = bean.getData();
            for(int i=0 ; i<dataBeans.size() ; i++){
                //解析数据放入数组中
                WeekDataBean.DataBean dataBean = dataBeans.get(i);
                String tmpTime = dataBean.getDay().substring(5);
                time[i+1] = tmpTime;


                switch (i){
                    case 0:
                        data[i] =getIntegerData(dataBean.getOne());
                        switchDate(dataBean.getOne());
                        break;
                    case 1:
                        data[i] =getIntegerData(dataBean.getTwo());
                        switchDate(dataBean.getTwo());
                        break;
                    case 2:
                        data[i] =getIntegerData(dataBean.getThree());
                        switchDate(dataBean.getThree());
                        break;
                    case 3:
                        data[i] =getIntegerData(dataBean.getFour());
                        switchDate(dataBean.getFour());
                        break;
                    case 4:
                        data[i] =getIntegerData(dataBean.getFive());
                        switchDate(dataBean.getFive());
                        break;
                    case 5:
                        data[i] =getIntegerData(dataBean.getSix());
                        switchDate(dataBean.getSix());
                        break;
                    case 6:
                        data[i] =getIntegerData(dataBean.getSeven());
                        switchDate(dataBean.getSeven());
                        break;
                }
            }
            //显示数据
            String[] data1 = {"10","20","15","25","15","23","10"};
            newLinearChartView.getData(data,time);
            tvLow.setText(lowDay+"");
            tvHigh.setText(highDay+"");
            tvNormal.setText(normalDay+"");
        }
    }

    private String getIntegerData(String data) {
        String tmpStr = data.equals("null")?"0":data;
        int idx = tmpStr.lastIndexOf(".");
        if (idx>0){
            return tmpStr.substring(0,idx);
        }
        return tmpStr;
    }

    private void switchDate(String number) {
        int num;
        if (!number.equals("null") && number != null){
            float tmp = Float.valueOf(number);
            num = (int) tmp;
        }else {
            num=0;
        }

        if (num<10){
            lowDay+=1;
        }else if (num>40){
            highDay+=1;
        }else {
            normalDay+=1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_report_range_new);
        ButterKnife.bind(this);

        titleTitleName.setText("翻身次数");

        initArray();

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String data = getDataFromNet(Constance.BASE_URL_WEEK_DATA+"turnover");
                    Message message = Message.obtain();
                    message.what = RECEIVER_DATA;
                    message.obj = data;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    private void initArray() {
        dataBeans = new ArrayList<>();
        time = new String[]{"","","","","","","",""};
        data = new String[7];
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
