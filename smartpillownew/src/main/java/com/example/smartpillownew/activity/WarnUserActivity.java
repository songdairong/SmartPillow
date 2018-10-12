package com.example.smartpillownew.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartpillownew.R;
import com.example.smartpillownew.adapter.FamilyAdapter;
import com.example.smartpillownew.bean.UserWarnMemberBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置报警优先人员
 */
public class WarnUserActivity extends Activity {

    private static final String TAG = "WarnUserActivity";

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title_name)
    TextView titleTitleName;
    @BindView(R.id.title_add)
    ImageView titleAdd;
    @BindView(R.id.list_view)
    ListView listView;
    private List<UserWarnMemberBean> families;
    private FamilyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_user);
        ButterKnife.bind(this);

        initTitle();

        initData();

        setListener();
    }

    private void initTitle() {
        titleTitleName.setText("报警人员设置");
        Glide.with(this).load(R.drawable.iv_add_family).centerCrop().into(titleAdd);
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserWarnMemberBean bean = families.get(i);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:"+Long.parseLong(bean.getPhone()));
                intent.setData(uri);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showUserDialog(i);
                return true;
            }
        });
    }

    /**点击对应的人出现dialog
     * @param position
     */
    private void showUserDialog(final int position) {

        UserWarnMemberBean bean = families.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.dialog_set_family,null);
        builder.setView(view);
        TextView et_name = view.findViewById(R.id.et_name);
        final EditText et_phone = view.findViewById(R.id.et_phone);
        Button btn_confirm = view.findViewById(R.id.btn_confirm);
        Button btn_delete = view.findViewById(R.id.btn_delete);
        et_name.setText(bean.getName());
        et_phone.setText(bean.getPhone());

        final AlertDialog dialog = builder.create();
        dialog.show();
        //确认修改
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = getTxt(et_phone);
                UserWarnMemberBean tmpBean = new UserWarnMemberBean();
                tmpBean.setPhone(phone);
                tmpBean.update(position+1);
                Toast.makeText(WarnUserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                //刷新listview
                refreshListView();
            }
        });

        //删除
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.delete(UserWarnMemberBean.class,position+1);
                refreshListView();
                Toast.makeText(WarnUserActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    /**
     * 刷新listview
     */
    private void refreshListView() {
        if (adapter != null){
            adapter.clear();
        }
        initData();
    }

    private void initData() {
        families = new ArrayList<>();
        families = LitePal.findAll(UserWarnMemberBean.class);
        if (families != null && families.size() > 0) {
            adapter = new FamilyAdapter(this, families);
            listView.setAdapter(adapter);
        }
    }

    @OnClick({R.id.title_back, R.id.title_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_add:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.dialog_add_family,null);
        builder.setView(view);
        final EditText et_name = view.findViewById(R.id.et_name);
        final EditText et_phone = view.findViewById(R.id.et_phone);
        Button btn_add = view.findViewById(R.id.btn_add);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getTxt(et_name);
                String phone = getTxt(et_phone);

                if (isaBoolean(name) && isaBoolean(phone)){
                    //存入数据库
                    UserWarnMemberBean bean = new UserWarnMemberBean();
                    bean.setName(name);
                    bean.setPhone(phone);
                    bean.save();

                    Toast.makeText(WarnUserActivity.this, "存入成功", Toast.LENGTH_SHORT).show();
                    refreshListView();
                    dialog.dismiss();

                }else {
                    Toast.makeText(WarnUserActivity.this, "名字或号码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isaBoolean(String name) {
        return name!=null && !name.equals("");
    }

    private String getTxt(EditText text) {
        return text.getText().toString().trim();
    }


}
