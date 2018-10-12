package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.AbnormalThreeMinBean;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.Utils;
import com.example.smartpillownew.view.ECGLinearChartView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AbnormalInfoLineChartActivity extends Activity {

    private static final String TAG = "AbnormalInfoLineChar";
    private static final int GET_DATA = 0;

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.ecg_view)
    ECGLinearChartView ecgView;
    private String url;

    private String[] data;
    private String[] time;

    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                processData(msg.obj.toString());
            }
        };

    private void processData(String json) {
        try{
            AbnormalThreeMinBean bean = new Gson().fromJson(json,new TypeToken<AbnormalThreeMinBean>(){}.getType());
            List<AbnormalThreeMinBean.ValueBean> valueBeans = bean.getValue();
            int size = valueBeans.size();
            Log.i(TAG, "processData: "+size);
            size = Utils.EvenNumber(size);
            data = new String[size];
            time = new String[size];
            AbnormalThreeMinBean.ValueBean valueBean;
            for(int i=0 ; i<valueBeans.size() ; i++){
                if (i%2==0){
                    valueBean = valueBeans.get(i);
                    String heart = valueBean.getHeart();
                    String date = valueBean.getDatetime();
                    String[] times = date.split(" ");
                    data[i/2] = heart;
                    time[i/2] = times[1];
                }
            }
            ecgView.getData(data,time);
        }catch (Exception e){
            Log.i(TAG, "processData: exception "+e.getMessage());
            titleTitleName.setText("数据格式错误！");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormal_info_line_chart);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleTitleName.setText("详细异常数据");
        url = Constance.ABNORMAL_URL+"userid="+"qianqian"+"&datetime="+getIntent().getStringExtra("datatime");
        Log.i(TAG, "initData: "+url);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    String result = getDataFromNet(url);
                    Message message = Message.obtain();
                    message.what = GET_DATA;
                    message.obj = result;
                    handler.sendMessage(message);
                }catch (Exception e){
                    Log.i(TAG, "run: exception : "+e.getMessage());
                }
            }
        }.start();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
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
