package com.example.smartpillownew.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.adapter.IrregularInfoAdapter;
import com.example.smartpillownew.bean.AbnormalReportBean;
import com.example.smartpillownew.bean.IrregularInforBean;
import com.example.smartpillownew.utils.Constance;
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

public class IrregularReportActivity extends Activity {

    private static final String TAG = "IrregularReportActivity";

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.spinner)
    Spinner spinner;

    private IrregularInfoAdapter adapter;
    private List<AbnormalReportBean.ValueBean> dataList;//全部的数据
    private List<AbnormalReportBean.ValueBean> dataArray;//需要显示的数据
    private String date;//异常数据的时间
    private MyApplication app;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            processData(msg.obj.toString());
        }
    };



    private void processData(String result) {
        Gson gson = new Gson();
        AbnormalReportBean bean = gson.fromJson(result, new TypeToken<AbnormalReportBean>() {
        }.getType());
        dataList = new ArrayList<>();
        dataList = bean.getValue();
        adapter = new IrregularInfoAdapter(IrregularReportActivity.this,dataList);
        listView.setAdapter(adapter);
        //设置下拉菜单点击
        initSpinner();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irregular_report);
        ButterKnife.bind(this);

        app = (MyApplication) getApplication();

        initData();
    }

    private void initSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dataArray = new ArrayList<>();
                switch (i){
                    case 0://全部信息
                        getArrayData("");
                        break;
                    case 1://呼吸
                        getArrayData("异常:呼吸");
                        break;
                    case 2://心率
                        getArrayData("异常:心跳");
                        break;
                    case 3://翻身
                        getArrayData("翻转频繁");
                        break;
                }
                Log.i(TAG, "onItemSelected: "+dataArray.toString());
                adapter = new IrregularInfoAdapter(IrregularReportActivity.this,dataArray);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**从所有异常数据中，根据info添加需要显示的数据
     * @param info 异常数据类型，为空则全部显示
     */
    private void getArrayData(String info) {
        if (info.equals("")){
            dataArray.addAll(dataList);
        }else {
            for(int i=0 ; i<dataList.size() ; i++){
                AbnormalReportBean.ValueBean bean = dataList.get(i);
                if (bean.getInfo().equals(info)){
                    dataArray.add(bean);
                }
            }
        }
    }

    private void initData() {

        titleTitleName.setText("异常记录");

//        dataList.add(new IrregularInforBean(R.drawable.iv_breath_trouble,"呼吸异常","12:34:23","2018-07-12"));
//        dataList.add(new IrregularInforBean(R.drawable.iv_heart_rate_trouble,"心率异常","12:11:23","2018-07-11"));
//        dataList.add(new IrregularInforBean(R.drawable.iv_range_trouble,"翻身次数异常","12:34:23","2018-07-10"));
//        dataList.add(new IrregularInforBean(R.drawable.iv_heart_rate_trouble,"心率异常","12:34:23","2018-07-1"));
//        dataList.add(new IrregularInforBean(R.drawable.iv_breath_trouble,"呼吸异常","12:34:23","2018-06-22"));

        //联网请求数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = getDataFromNet(Constance.BASE_URL_REPORT+"userid="+app.getUser_id());
                    Message message = Message.obtain();
                    message.obj = data;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv_info = view.findViewById(R.id.tv_info);
                TextView tv_date = view.findViewById(R.id.tv_date);
                date = tv_date.getText().toString();

                if (tv_info.getText().toString().trim().equals("异常:心跳")) {
                    showpopupwindows(view);
                } else {
                    Toast.makeText(IrregularReportActivity.this, "只支持查看心率异常的详细数据", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showpopupwindows(View view) {
        PopupMenu pop = new PopupMenu(this, view);

        pop.inflate(R.menu.popup_abnormal_detail);
        pop.show();

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.pre_min:
                        intent = new Intent();
                        intent.setClass(IrregularReportActivity.this, AbnormalInfoDetailActivity.class);
                        intent.putExtra("title", menuItem.getTitle());
                        intent.putExtra("datatime", date);
                        startActivity(intent);
                        break;
                    case R.id.now_min:
                        intent = new Intent();
                        intent.setClass(IrregularReportActivity.this, AbnormalInfoDetailActivity.class);
                        intent.putExtra("title", menuItem.getTitle());
                        intent.putExtra("datatime", date);
                        startActivity(intent);
                        break;
                    case R.id.next_min:
                        intent = new Intent();
                        intent.setClass(IrregularReportActivity.this, AbnormalInfoDetailActivity.class);
                        intent.putExtra("title", menuItem.getTitle());
                        intent.putExtra("datatime", date);
                        startActivity(intent);
                        break;
                    case R.id.new_ecg:
                        intent = new Intent();
                        intent.setClass(IrregularReportActivity.this, AbnormalInfoLineChartActivity.class);
                        intent.putExtra("datatime", date);
                        startActivity(intent);
                        break;
                }
                return true;
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
