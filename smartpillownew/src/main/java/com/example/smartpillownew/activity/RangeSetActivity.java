package com.example.smartpillownew.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.MyApplication;
import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.GetRangeBean;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RangeSetActivity extends Activity {

    private static final String TAG = "RangeSetActivity";

    private static final int BREATH_DATA = 0;
    private static final int HEART_DATA = 1;
    private static final int TURN_DATA = 2;
    private static final int SEND_DATA = 3;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.et_breath_low)
    EditText etBreathLow;
    @BindView(R.id.et_breath_high)
    EditText etBreathHigh;
    @BindView(R.id.iv_question_breath)
    ImageView ivQuestionBreath;
    @BindView(R.id.et_heart_rate_low)
    EditText etHeartRateLow;
    @BindView(R.id.et_heart_rate_high)
    EditText etHeartRateHigh;
    @BindView(R.id.iv_question_heart_rate)
    ImageView ivQuestionHeartRate;
    @BindView(R.id.et_range_low)
    EditText etRangeLow;
    @BindView(R.id.et_range_high)
    EditText etRangeHigh;
    @BindView(R.id.iv_question_range)
    ImageView ivQuestionRange;
    @BindView(R.id.et_bihan_low)
    EditText etBihanLow;
    @BindView(R.id.et_bihan_high)
    EditText etBihanHigh;
    @BindView(R.id.iv_question_bihan)
    ImageView ivQuestionBihan;
    @BindView(R.id.btn_save)
    Button btnSave;

    private String breath_title;
    private String heart_title;
    private String turn_title;
    private MyApplication app;



    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case BREATH_DATA:
//                        Log.i(TAG, "handleMessage: breath"+msg.obj.toString());
//                        bean = gson.fromJson(msg.obj.toString(),new TypeToken<GetRangeBean>(){}.getType());
//                        Log.i(TAG, "handleMessage: breath"+bean.toString());
//                        breath_title = bean.getData().getMin()+"~"+bean.getData().getMax();
                        try {
                            JSONObject object = new JSONObject(msg.obj.toString());
                            String breath_l = object.optString("breath_l");
                            String breath_h = object.getString("breath_h");
                            breath_title = String.valueOf(Utils.RetInteger(breath_l))+"~"+String.valueOf(Utils.RetInteger(breath_h));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case HEART_DATA:
//                        Log.i(TAG, "handleMessage: heart"+msg.obj.toString());
//                        bean = gson.fromJson(msg.obj.toString(),new TypeToken<GetRangeBean>(){}.getType());
//                        Log.i(TAG, "handleMessage: heart"+bean.toString());
//                        heart_title = bean.getData().getMin()+"~"+bean.getData().getMax();
                        try {
                            JSONObject object = new JSONObject(msg.obj.toString());
                            String heart_l = object.optString("heart_l");
                            String heart_h = object.getString("heart_h");
                            heart_title = String.valueOf(Utils.RetInteger(heart_l))+"~"+String.valueOf(Utils.RetInteger(heart_h));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case TURN_DATA:
//                        Log.i(TAG, "handleMessage: turn"+msg.obj.toString());
//                        bean = gson.fromJson(msg.obj.toString(),new TypeToken<GetRangeBean>(){}.getType());
//                        Log.i(TAG, "handleMessage: turn"+bean.toString());
                        turn_title = "10~20";
                        break;
                    case SEND_DATA:
                        if (Integer.parseInt(msg.obj.toString()) == 1){
                            Toast.makeText(RangeSetActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            //发送广播,通知监控界面参数已经变化
                            Intent intent = new Intent(Constance.NOTIFY_SETTINGS_CHANGE);
                            sendBroadcast(intent);
                        }else {
                            Toast.makeText(RangeSetActivity.this, "保存失败！请检查网络设置", Toast.LENGTH_SHORT).show();
                        }
                    default:break;
                }
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_set);
        ButterKnife.bind(this);

        titleTitleName.setText("睡眠参数设置");
        app = (MyApplication) getApplication();

//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                Message message;
//                try {
//
//                    //呼吸
//                    message = Message.obtain();
//                    String data_breath = getDataFromNet(Constance.BASE_URL_GET_RANGE+"breath");
//                    message.what = BREATH_DATA;
//                    message.obj = data_breath;
//                    Log.i(TAG, "run: data breath = "+data_breath);
//                    handler.sendMessage(message);
//
//                    //心率
//                    message = Message.obtain();
//                    String data_heart = getDataFromNet(Constance.BASE_URL_GET_RANGE+"heart");
//                    message.what = HEART_DATA;
//                    message.obj = data_heart;
//                    Log.i(TAG, "run: data heart = "+data_heart);
//                    handler.sendMessage(message);
//
//                    //翻身
//                    message = Message.obtain();
//                    String data_turn = getDataFromNet(Constance.BASE_URL_GET_RANGE+"turnover");
//                    message.what = TURN_DATA;
//                    message.obj = data_turn;
//                    Log.i(TAG, "run: data turn = "+data_turn);
//                    handler.sendMessage(message);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        initData();
    }

    private void initData() {
        new Thread(){
            Message message;
            @Override
            public void run() {
                super.run();
                try {

                    message = Message.obtain();
                    String breath_data = getDataFromNet(Constance.BASE_URL_GET_RANGE+"userid="+app.getUser_id()+"&type=breath");
                    message.what = BREATH_DATA;
                    message.obj = breath_data;
                    handler.sendMessage(message);

                    message = Message.obtain();
                    String heart_data = getDataFromNet(Constance.BASE_URL_GET_RANGE+"userid="+app.getUser_id()+"&type=heart");
                    message.what = HEART_DATA;
                    message.obj = heart_data;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @OnClick({R.id.title_back, R.id.iv_question_breath, R.id.iv_question_heart_rate, R.id.iv_question_range, R.id.iv_question_bihan, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_question_breath:
                showDialogFragment(breath_title,getResources().getString(R.string.breath_suggest));
                break;
            case R.id.iv_question_heart_rate:
                showDialogFragment(heart_title,getResources().getString(R.string.heart_rate_suggest));
                break;
            case R.id.iv_question_range:
                showDialogFragment("10~20",getResources().getString(R.string.range_suggest));
                break;
            case R.id.iv_question_bihan:
                showDialogFragment("10~25",getResources().getString(R.string.bihan_suggest));
                break;
            case R.id.btn_save:
                if (!getText(etBreathLow).equals("") && !getText(etBreathHigh).equals("") && !getText(etHeartRateLow).equals("")
                        && !getText(etHeartRateHigh).equals("") && !getText(etRangeLow).equals("") && !getText(etRangeHigh).equals("")){
                    saveData();
                }else {
                    Toast.makeText(RangeSetActivity.this, "数据不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void saveData() {
        //存到服务器
        new Thread(){
            @Override
            public void run() {
                super.run();
                String url = Constance.BASE_URL_SEND_RANGE+"userid="+app.getUser_id()+"&bmin="+getText(etBreathLow)+"&bmax="+getText(etBreathHigh)+
                                "&hmin="+getText(etHeartRateLow)+"&hmax="+getText(etHeartRateHigh)+
                                "&tmin="+getText(etRangeLow)+"&tmax="+getText(etRangeHigh);
                Log.i(TAG, "run: "+url);
                try {
                    String result = getDataFromNet(url);
                    Message message = Message.obtain();
                    message.what = SEND_DATA;
                    message.obj = result;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        //存到sharepreference
        CachUtils.putCachData(this,Constance.RANGESET_KEY,
                getText(etBreathLow)+":"+getText(etBreathHigh)+":"+getText(etHeartRateLow)+":"+getText(etHeartRateHigh)+":"+
                getText(etRangeLow)+":"+getText(etRangeHigh));

        finish();
    }

    private void showDialogFragment(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RangeSetActivity.this);
        View view = View.inflate(RangeSetActivity.this,R.layout.dialog_suggestion,null);
        builder.setView(view);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_content = view.findViewById(R.id.tv_dialog_content);
        tv_dialog_title.setText(title);
        tv_dialog_content.setText(content);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    OkHttpClient client = new OkHttpClient();

    String getDataFromNet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String getText(EditText et){
        return et.getText().toString().trim();
    }

}
