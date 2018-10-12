package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.R;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EquipmentActivity extends Activity {

    private static final String TAG = "EquipmentActivity";

    private static final int ADD_BED_RESULT = 0;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.title_add)
    ImageView titleAdd;
    @BindView(R.id.tv_note_info)
    TextView tvNoteInfo;
    @BindView(R.id.list_view)
    ListView listView;

    private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case ADD_BED_RESULT:
                        processData(msg.obj.toString());
                        break;
                }
            }
        };

    private void processData(String result) {
        switch (result){
            case "0"://用户不存在
                Toast.makeText(EquipmentActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                break;
            case "1"://该床位不存在
                Toast.makeText(EquipmentActivity.this, "该床位不存在", Toast.LENGTH_SHORT).show();
                break;
            case "2"://该床位已被使用
                Toast.makeText(EquipmentActivity.this, "该床位已被使用", Toast.LENGTH_SHORT).show();
                break;
            case "3"://添加床位号成功
                Toast.makeText(EquipmentActivity.this, "添加床位号成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleTitleName.setText("床位管理");
        titleAdd.setImageResource(R.drawable.iv_add_family);
    }

    @OnClick({R.id.title_back, R.id.title_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_add:
                addBed();
                break;
        }
    }

    private void addBed() {
        //显示AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.dialog_add_bedinfo,null);
        final EditText et_phone = view.findViewById(R.id.et_phone);
        final EditText et_bed_info = view.findViewById(R.id.et_bed_info);
        Button btn_add = view.findViewById(R.id.btn_add);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString().trim();
                String bedInfo = et_bed_info.getText().toString().trim();
                if (!Utils.isMobileNO(phone)){
                    Toast.makeText(EquipmentActivity.this, "手机号码不正确！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bedInfo.equals("") ){
                    Toast.makeText(EquipmentActivity.this, "床位信息不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //联网请求数据
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Message message;
                        String url = Constance.ADD_BED_INFO_URL+"username="+et_phone.getText().toString().trim()+"&number="+et_bed_info.getText().toString().trim();
                        try {
                            String result = getDataFromNet(url);
                            message = Message.obtain();
                            message.what = ADD_BED_RESULT;
                            message.obj = result;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                dialog.dismiss();
            }
        });

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
}
