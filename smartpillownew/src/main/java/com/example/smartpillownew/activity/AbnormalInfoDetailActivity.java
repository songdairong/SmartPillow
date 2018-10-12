package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.AbnormalThreeMinBean;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.view.AbnormalECGVIew;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AbnormalInfoDetailActivity extends Activity {

    private static final String TAG = "AbnormalInfoDetail";
    private static final int GET_DATA = 0;

    @BindView(R.id.ecg_view)
    AbnormalECGVIew ecgView;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;

    private int[] datas = new int[12];
    private String url ;
    private MyApplication app;


    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case GET_DATA:
                        processData(msg.obj.toString());
                        break;
                }
            }
        };

    private void processData(String json) {
        try{
            AbnormalThreeMinBean bean = new Gson().fromJson(json,new TypeToken<AbnormalThreeMinBean>(){}.getType());
            List<AbnormalThreeMinBean.ValueBean> valueBeans = bean.getValue();
        }catch (Exception e){
            Log.i(TAG, "processData: exception "+e.getMessage());
            titleTitleName.setText("数据格式错误！");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormal_info_detail);
        ButterKnife.bind(this);

        app = new MyApplication();

        initData();

        initRandomData();
    }

    private void initRandomData() {
        for(int i=0 ; i<12 ; i++){
            datas[i] = (new Random().nextInt(6) + 6) * 10;
        }
        ecgView.getData(datas);
    }

    private void initData() {
        titleTitleName.setText(getIntent().getStringExtra("title"));
        //url="http://111.231.223.127/sleep-monitoring/abnormal-record.php?userid=qianqian&datetime=2018-08-21 14:52:35"
        url = Constance.ABNORMAL_URL+"userid="+app.getUser_id()+"&datetime="+getIntent().getStringExtra("datatime");
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
