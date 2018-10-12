package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.adapter.HistoryAdapter;
import com.example.smartpillownew.bean.HistoryBean;
import com.example.smartpillownew.utils.Constance;
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

public class HistoryActivity extends Activity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.tv_breath)
    TextView tvBreath;
    @BindView(R.id.tv_heart)
    TextView tvHeart;
    @BindView(R.id.tv_range)
    TextView tvRange;
    @BindView(R.id.list_view)
    ListView listView;

    private List<HistoryBean.ValueBean> list;

    private HistoryAdapter adapter;

    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                processData(msg.obj.toString());
            }
        };

    private void processData(String result) {
        Gson gson = new Gson();
        HistoryBean bean = gson.fromJson(result,new TypeToken<HistoryBean>(){}.getType());
        list = bean.getValue();
        adapter = new HistoryAdapter(this,list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        titleTitleName.setText("历史记录");

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String data = getDataFromNet(Constance.BASE_URL_HISTORY);
                    Message message = Message.obtain();
                    message.obj = data;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvBreath.setText(list.get(i).getBreath());
                tvHeart.setText(list.get(i).getHeart());
                tvRange.setText(list.get(i).getTurnover());
            }
        });
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
