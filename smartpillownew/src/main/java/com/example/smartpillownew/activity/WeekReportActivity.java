package com.example.smartpillownew.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.WeekDataBean;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.view.LineChartBreathView;
import com.example.smartpillownew.view.LineChartRangeView;
import com.example.smartpillownew.view.LineChartRateView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.LineData;
import org.xclcharts.renderer.XEnum;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 一周睡眠的具体数据记录，如呼吸心率
 */
public class WeekReportActivity extends Activity {

    private static final String TAG = "WeekReportActivity";
    private static final int BREATH_DATA = 0;
    private static final int HEART_DATA = 1;
    private static final int TURN_DATA = 2;

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.line_view_breath)
    LineChartBreathView lineViewBreath;
    @BindView(R.id.line_view_heart_rate)
    LineChartRateView lineViewHeartRate;
    @BindView(R.id.line_view_heart_range)
    LineChartRangeView lineViewHeartRange;

//    private MyLoginReciever reciever;

    private boolean haveGet = false;
    private MyApplication app;

    private String data;
    //呼吸
    LinkedList<Double> dataSeries1 = new LinkedList<Double>();
    private LinkedList<LineData> chartData1 = new LinkedList<LineData>();
    private List<CustomLineData> mCustomLineDataset1 = new LinkedList<CustomLineData>();
    //心率
    LinkedList<Double> dataSeries2 = new LinkedList<Double>();
    private LinkedList<LineData> chartData2 = new LinkedList<LineData>();
    private List<CustomLineData> mCustomLineDataset2 = new LinkedList<CustomLineData>();
    //翻身
    LinkedList<Double> dataSeries3 = new LinkedList<Double>();
    private LinkedList<LineData> chartData3 = new LinkedList<LineData>();
    private List<CustomLineData> mCustomLineDataset3 = new LinkedList<CustomLineData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_report);
        ButterKnife.bind(this);

        titleTitleName.setText("一周记录");

        app = (MyApplication) getApplication();


        //注册广播接收器
//        reciever = new MyLoginReciever();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(getResources().getString(R.string.week_data));
//        registerReceiver(reciever, filter);

        initData();


    }

    private void drawViews() {
        //呼吸
        LineData lineData1 = new LineData("每日呼吸均值", dataSeries1, Color.rgb(234, 83, 71));
        lineData1.setDotStyle(XEnum.DotStyle.DOT);
        chartData1.add(lineData1);
        lineViewBreath.chart.setDataSource(chartData1);
        lineViewBreath.chart.setCustomLines(mCustomLineDataset1);
        lineViewBreath.chart.getPlotTitle().getTitlePaint().setTextSize(55);

        lineViewBreath.refreshChart();

        //心率
        LineData lineData2 = new LineData("每日心率均值", dataSeries2, Color.rgb(234, 83, 71));
        lineData2.setDotStyle(XEnum.DotStyle.DOT);
        chartData2.add(lineData2);
        lineViewHeartRate.chart.setDataSource(chartData2);
        lineViewHeartRate.chart.setCustomLines(mCustomLineDataset2);
        lineViewHeartRate.chart.getPlotTitle().getTitlePaint().setTextSize(55);
        lineViewHeartRate.refreshChart();

        //翻身
        LineData lineData3 = new LineData("每日翻身次数均值", dataSeries3, Color.rgb(234, 83, 71));
        lineData3.setDotStyle(XEnum.DotStyle.DOT);
        chartData3.add(lineData3);
        lineViewHeartRange.chart.setDataSource(chartData3);
        lineViewHeartRange.chart.setCustomLines(mCustomLineDataset3);
        lineViewHeartRange.chart.getPlotTitle().getTitlePaint().setTextSize(55);
        lineViewHeartRange.refreshChart();
    }

    private void initData() {
//        dataSeries1.add(18d);
//        dataSeries1.add(25d);
//        dataSeries1.add(20d);
//        dataSeries1.add(32d);
//        dataSeries1.add(28d);
//        dataSeries1.add(27d);
//        dataSeries1.add(30d);
//
//        dataSeries2.add(100d);
//        dataSeries2.add(95d);
//        dataSeries2.add(110d);
//        dataSeries2.add(125d);
//        dataSeries2.add(130d);
//        dataSeries2.add(115d);
//        dataSeries2.add(84d);
//
//        dataSeries3.add(10d);
//        dataSeries3.add(15d);
//        dataSeries3.add(13d);
//        dataSeries3.add(12d);
//        dataSeries3.add(16d);
//        dataSeries3.add(18d);
//        dataSeries3.add(12d);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Message message;
                try {
                    String data = getDataFromNet(Constance.BASE_URL_WEEK_DATA+"userid="+app.getUser_id()+"&type=breath");
                    Log.i(TAG, "run: "+data);
                    message = Message.obtain();
                    message.what = BREATH_DATA;
                    message.obj = data;
                    handler.sendMessage(message);

                    data = getDataFromNet(Constance.BASE_URL_WEEK_DATA+"userid="+app.getUser_id()+"&type=heart");
                    Log.i(TAG, "run: "+data);
                    message = Message.obtain();
                    message.what = HEART_DATA;
                    message.obj = data;
                    handler.sendMessage(message);

                    data = getDataFromNet(Constance.BASE_URL_WEEK_DATA+"userid="+app.getUser_id()+"&type=turnover");
                    Log.i(TAG, "run: "+data);
                    message = Message.obtain();
                    message.what = TURN_DATA;
                    message.obj = data;
                    handler.sendMessage(message);


                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        }.start();


        chartDesireLines(mCustomLineDataset1, 15d, 25d, 40d);
        chartDesireLines(mCustomLineDataset2, 60d, 120d, 160d);
        chartDesireLines(mCustomLineDataset3, 5d, 10d, 18d);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    /**
     * 期望线/分界线
     */
    private void chartDesireLines(List<CustomLineData> mCustomLineDataset, double low, double normal, double high) {
        mCustomLineDataset.add(new CustomLineData("较低", low, Color.rgb(35, 172, 57), 5));
        mCustomLineDataset.add(new CustomLineData("正常", normal, Color.rgb(69, 181, 248), 5));
//        mCustomLineDataset.add(new CustomLineData("[个人均线]",calcAvg(),Color.rgb(251, 79, 128),6));
        mCustomLineDataset.add(new CustomLineData("较高", high, Color.rgb(255, 0, 0), 5));
    }

//    class MyLoginReciever extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            data = intent.getStringExtra("breath");
//            Log.i(TAG, "onReceive: "+data);
//            if (!data.equals("")) {
//                handler.sendEmptyMessage(BREATH_DATA);
//            }
//        }
//    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BREATH_DATA:
                    Log.i(TAG, "handleMessage: " + data);
                    if (!haveGet) {
                        haveGet = true;
                        processData(msg.obj.toString() , dataSeries1);
                    }
                    break;
                case HEART_DATA:
                    processData(msg.obj.toString() , dataSeries2);
                    break;
                case TURN_DATA:
                    processData(msg.obj.toString() , dataSeries3);
                    break;
            }
        }
    };

    private void processData(String data , LinkedList<Double> list) {
        Gson gson = new Gson();
        WeekDataBean bean = gson.fromJson(data, new TypeToken<WeekDataBean>() {
        }.getType());
        if (bean.getStatus() == 1) {
            String[] weekData = new String[7];
            weekData[0] = String.valueOf(bean.getData().get(0).getOne());
            weekData[1] = String.valueOf(bean.getData().get(1).getTwo());
            weekData[2] = String.valueOf(bean.getData().get(2).getThree());
            weekData[3] = String.valueOf(bean.getData().get(3).getFour());
            weekData[4] = String.valueOf(bean.getData().get(4).getFive());
            weekData[5] = String.valueOf(bean.getData().get(5).getSix());
            weekData[6] = String.valueOf(bean.getData().get(6).getSeven());
            for (int i = 0; i < weekData.length; i++) {
                if (weekData[i].equals("null")) {
                    list.add(0.0);
                } else {
                    list.add(Double.parseDouble(weekData[i]));
                    Log.i(TAG, "processData: " + weekData[i]);
                }
            }

            drawViews();

        } else {
            Toast.makeText(WeekReportActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (reciever != null) {
//            unregisterReceiver(reciever);
//            reciever = null;
//        }
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
