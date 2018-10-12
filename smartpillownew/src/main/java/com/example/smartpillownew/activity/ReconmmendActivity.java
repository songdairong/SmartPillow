package com.example.smartpillownew.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReconmmendActivity extends Activity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.et_content)
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconmmend);
        ButterKnife.bind(this);

        titleTitleName.setText("意见反馈");
    }

    @OnClick({R.id.title_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_confirm:
                etContent.setText("");
                Toast.makeText(ReconmmendActivity.this, "感谢您的意见！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
