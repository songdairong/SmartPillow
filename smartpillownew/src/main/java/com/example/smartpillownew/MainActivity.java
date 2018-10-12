package com.example.smartpillownew;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.activity.IrregularReportActivity;
import com.example.smartpillownew.activity.WarnUserActivity;
import com.example.smartpillownew.adapter.MyFragmentPagerAdapter;
import com.example.smartpillownew.bean.AbnormalReportBean;
import com.example.smartpillownew.bean.RealTimeBreathDataBean;
import com.example.smartpillownew.fragment.BaseFragment;
import com.example.smartpillownew.service.HTTPService;
import com.example.smartpillownew.utils.CachUtils;
import com.example.smartpillownew.utils.Constance;
import com.example.smartpillownew.utils.ImageFlickerAnimation;
import com.example.smartpillownew.utils.MyTimeUtils;
import com.example.smartpillownew.utils.ServiceUtils;
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

/**
 *
 */
public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private static final int BREATH_DATA = 0;
    private static final int HEART_DATA = 1;
    private static final int TURNOVER_DATA = 2;
    private static final int LATEST_ABNORMAL = 3;
    private static final String SHARE_APP_TAG = "first load";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_breath_warn)
    ImageView ivBreathWarn;
    @BindView(R.id.iv_heart_warn)
    ImageView ivHeartWarn;

    @BindView(R.id.tv_start_monitor)
    TextView tvStartMonitor;
    @BindView(R.id.tv_stop_monitor)
    TextView tvStopMonitor;
    @BindView(R.id.tv_abnormal_info)
    TextView tvAbnormalInfo;
    @BindView(R.id.tv_abnormal_data)
    TextView tvAbnormalData;
    @BindView(R.id.tv_abnormal_date)
    TextView tvAbnormalDate;
    @BindView(R.id.ll_warn)
    LinearLayout llWarn;

    private List<BaseFragment> fragmentList;
    private List<String> titleList;
    private MyFragmentPagerAdapter adapter;
    private Intent intent;
    private Intent monitorIntent;


    private MyReceiver receiver;
    private ExitReceiver exitReceiver;
    private MyThresHoldeReceiver thresHoldeReceiver;

    private String breath_data;
    private String heart_data;
    private String turn_data;

    private boolean switch_breath;
    private boolean switch_heart_rate;
    private boolean switch_range;


    private MyApplication app;

    private Gson gson;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BREATH_DATA:
                    processData(breath_data, 0);
                    break;
                case HEART_DATA:
                    processData(heart_data, 1);
                    break;
                case TURNOVER_DATA:
                    processData(turn_data, 2);
                    break;
                case LATEST_ABNORMAL:
                    processAbnormalData(msg.obj.toString());
                    break;
            }
        }
    };


    private void processAbnormalData(String json) {
        try {
            gson = new Gson();
            AbnormalReportBean bean = gson.fromJson(json, new TypeToken<AbnormalReportBean>() {
            }.getType());
            AbnormalReportBean.ValueBean valueBean = bean.getValue().get(0);
            String date = valueBean.getDate();
            MyTimeUtils utils = new MyTimeUtils();
            int flag = utils.DateDiffer(date);
            Log.i(TAG, "processAbnormalData: " + flag);
            if (flag < 20) {
                // TODO: 几天之内的算最新的
                switch (valueBean.getInfo()) {
                    case "异常:呼吸":
                        setTitleImage(ivBreathWarn, valueBean.getInfo(), valueBean.getDate(), valueBean.getBreath() + "(次/分钟)");
                        break;
                    case "异常:心跳":
                        setTitleImage(ivHeartWarn, valueBean.getInfo(), valueBean.getDate(), valueBean.getHeart() + "(bpm)");
                        break;
                }
            }
        }catch (Exception e){
            Log.i(TAG, "processAbnormalData: exception "+e.getMessage());
        }

    }

    /**
     * 所需要申请的权限
     */
    private static final String[] permissionArray = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE
    };

    /**
     * 还需要申请的权限
     */
    private List<String> permissionList = new ArrayList<>();
    private int[] dataList;


    /**
     * @param data
     * @param status 0-breath ; 1-heart ; 2-turnover
     */
    private void processData(String data, int status) {
        initData();
        gson = new Gson();
        RealTimeBreathDataBean bean = gson.fromJson(data, new TypeToken<RealTimeBreathDataBean>() {
        }.getType());
//        int haveMan = bean.getStatus();
        int haveMan = 1;
        switch (status) {
            case 0://breath
                if (switch_breath && get(haveMan, 0) == 1) {

                    Log.i(TAG, "processData: breath");
                    makeNotification(bean.getData(), dataList[0], dataList[1], R.drawable.iv_breath_trouble, "呼吸警告", ivBreathWarn, "一级警报", 0);
                }
                break;
            case 1://heart
                if (switch_heart_rate && get(haveMan, 0) == 1) {

                    Log.i(TAG, "processData: heart");
                    makeNotification(bean.getData(), dataList[2], dataList[3], R.drawable.iv_heart_rate_trouble, "心率警告", ivHeartWarn, "一级警报", 1);
                }
                break;
            case 2://turnover
                if (switch_range && get(haveMan, 0) == 1) {

                    Log.i(TAG, "processData: turnover");
//                    makeNotification(bean.getData(), 10, 20, R.drawable.iv_range_trouble, "翻身次数警告", ivRangeWarn, "二级警报", 2);
                }
                break;
        }
    }

    private void setTitleImage(ImageView imageView, String info, String date, String data) {
        if (imageView.getVisibility() != View.VISIBLE && llWarn.getVisibility() != View.VISIBLE) {
            //图片
            imageView.setVisibility(View.VISIBLE);
            ImageFlickerAnimation animation = new ImageFlickerAnimation();
            animation.ImageFlickerAnimation(imageView);
            //LinearLayout
            llWarn.setVisibility(View.VISIBLE);
            tvAbnormalInfo.setText(info);
            tvAbnormalData.setText(data);
            tvAbnormalDate.setText(date);
        } else {
            imageView.setVisibility(View.GONE);
            llWarn.setVisibility(View.GONE);

            setTitleImage(imageView, info, date, data);
        }
    }

    /**
     * 做notification的提示
     *
     * @param data
     * @param min
     * @param max
     * @param src
     * @param content
     * @param view
     * @param warnContent
     * @param id
     */
    private void makeNotification(String data, int min, int max, int src, String content, ImageView view, String warnContent, int id) {
        if (data != null) {
            int realTimeData = Integer.parseInt(data);
            if (realTimeData < min || realTimeData > max) {
                //标题栏显示图标
//            view.setVisibility(View.VISIBLE);

                Log.i(TAG, "makeNotification: " + content);
                Intent intent = new Intent(this, WarnUserActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder builder = new Notification.Builder(this);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("警告")
                        .setContentText(content)
                        .setContentIntent(pendingIntent)
                        .setSubText(warnContent)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{0, 300, 500, 700})
                        .setFullScreenIntent(pendingIntent, true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), src));
                Notification notification = builder.build();
//                notification.flags = Notification.FLAG_INSISTENT;
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
//                notification.ledARGB = Color.GREEN;
//                notification.ledOnMS = 1000;
//                notification.ledOffMS = 1000;
//                notification.flags |=Notification.FLAG_SHOW_LIGHTS;
                notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.warn);
                NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(id, notification);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        app = (MyApplication) getApplication();

        //申请权限
        requestPermission(MainActivity.this);

        initFragment();

        initData();

        //初始化报警设置以及报警阈值
        initWarnManage();

//        //注册广播
        intent = new Intent(this, HTTPService.class);
//        startService(intent);

        //注册报警的广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getResources().getString(R.string.realtime_data));
        registerReceiver(receiver, filter);

        exitReceiver = new ExitReceiver();
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(Constance.EXIT);
        registerReceiver(exitReceiver, filter1);

        thresHoldeReceiver = new MyThresHoldeReceiver();
        IntentFilter thresholdeFilter = new IntentFilter();
        thresholdeFilter.addAction(Constance.NOTIFY_SETTINGS_CHANGE);
        registerReceiver(thresHoldeReceiver, thresholdeFilter);

        monitorIntent = new Intent();
        monitorIntent.setAction(getResources().getString(R.string.monitor_intent));

        //设置报警阈值
        initRange();

        //获取最新的异常信息
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String data = getDataFromNet(Constance.BASE_URL_ABNORMAL+"userid="+app.getUser_id());
                    if (data != null) {
                        Message message = Message.obtain();
                        message.what = LATEST_ABNORMAL;
                        message.obj = data;
                        Log.i(TAG, "run: " + data);
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initRange() {
        String data = CachUtils.getCachData(this, Constance.RANGESET_KEY);
        Log.i(TAG, "initRange: data = " + data);
        if (data != null) {
            String[] datas = data.split(":");
            dataList = new int[6];
            for (int i = 0; i < datas.length; i++) {
                Log.i(TAG, "initRange: " + datas[i]);
                dataList[i] = (datas[i] == "" ? 0 : Integer.parseInt(datas[i]));
            }
        }

    }

    private void requestPermission(Activity activity) {
        for (String permission : permissionArray) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList != null && permissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int i = 0; i < permissionList.size(); i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "为了更好地使用该产品，请同意权限： " + permissions[i], Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }

    private void initWarnManage() {

        SharedPreferences settings = getSharedPreferences(SHARE_APP_TAG, 0);
        boolean first_load = settings.getBoolean("FIRST", true);
        if (first_load) {
            String storageResult = true + ":" + true + ":" + true + ":" + true;
            Log.i(TAG, "storage: tydch " + storageResult);
            CachUtils.putCachData(this, Constance.WARNSELECTED_KEY, storageResult);

            String storageData = "10:25:60:100:20:26";
            CachUtils.putCachData(this, Constance.RANGESET_KEY, storageData);

            settings.edit().putBoolean("FIRST", false).commit();
        }


    }

    private void initData() {
        String isSelectedResult = CachUtils.getCachData(this, Constance.WARNSELECTED_KEY);
        Log.i(TAG, "initData: tydch string " + isSelectedResult);
        if (!isSelectedResult.equals("")) {
            String[] result = isSelectedResult.split(":");
            switch_breath = Boolean.parseBoolean(result[0]);
            switch_heart_rate = Boolean.parseBoolean(result[1]);
            switch_range = Boolean.parseBoolean(result[2]);

        }
    }

    private void initFragment() {
//        fragmentList = new ArrayList<>();
//        fragmentList.add(new MainFragment());
//        fragmentList.add(new UserFragment());
//
        titleList = new ArrayList<>();
        titleList.add("睡眠报告");
        titleList.add("实时监测");
        titleList.add("用户");

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(titleList.get(position));
//                Log.i(TAG, "onPageSelected: view="+viewPager.getCurrentItem());
                Log.i(TAG, "onPageSelected: " + position);
                if (position != 1) {
                    tvStartMonitor.setVisibility(View.GONE);
                    tvStopMonitor.setVisibility(View.GONE);
                } else {
                    if (ServiceUtils.isServiceRunning(MainActivity.this, "com.example.smartpillownew.service.HTTPService")) {
                        tvStopMonitor.setVisibility(View.VISIBLE);
                        tvStartMonitor.setVisibility(View.GONE);
                    } else {
                        tvStopMonitor.setVisibility(View.GONE);
                        tvStartMonitor.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (intent != null) {
            stopService(intent);
            intent = null;
        }
        //解注册接收器
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        if (exitReceiver != null) {
            unregisterReceiver(exitReceiver);
            exitReceiver = null;
        }
        if (thresHoldeReceiver != null) {
            unregisterReceiver(thresHoldeReceiver);
            thresHoldeReceiver = null;
        }
    }

    @OnClick({R.id.tv_start_monitor, R.id.tv_stop_monitor, R.id.ll_warn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_monitor:
                //开始service
                if (intent != null) {
                    startService(intent);
                    tvStartMonitor.setVisibility(View.GONE);
                    tvStopMonitor.setVisibility(View.VISIBLE);
                    //发出广播
                    monitorIntent.putExtra("action", "start");
                    sendBroadcast(monitorIntent);
                }
                break;
            case R.id.tv_stop_monitor:
                if (intent != null) {
                    stopService(intent);
                    tvStartMonitor.setVisibility(View.VISIBLE);
                    tvStopMonitor.setVisibility(View.GONE);
                    //发出广播
                    monitorIntent.putExtra("action", "stop");
                    sendBroadcast(monitorIntent);
                }
                break;
            case R.id.ll_warn:
                llWarn.setVisibility(View.GONE);
                ivBreathWarn.setVisibility(View.GONE);
                ivHeartWarn.setVisibility(View.GONE);

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, IrregularReportActivity.class);
                startActivity(intent);
                break;

        }
    }

    /**
     * 接受数据的receiver
     */
    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            breath_data = intent.getStringExtra("breath");
            heart_data = intent.getStringExtra("heart");
            turn_data = intent.getStringExtra("turnover");

            if (!breath_data.equals("")) {
                handler.sendEmptyMessage(BREATH_DATA);
            }
            if (!heart_data.equals("")) {
                handler.sendEmptyMessage(HEART_DATA);
            }
            if (!turn_data.equals("")) {
                handler.sendEmptyMessage(TURNOVER_DATA);
            }
        }
    }

    /**
     * 接受退出登录广播的receiver
     */
    class ExitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    /**
     * 刷新阈值参数的广播
     */
    class MyThresHoldeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initRange();
            Log.i(TAG, "onReceive: MyThresHoldeReceiver ");
        }
    }

    /**
     * 根据数位返回是否是1
     *
     * @param num
     * @param index
     * @return
     */
    public static int get(int num, int index) {
        return (num & (0x1 << index)) >> index;
    }

    OkHttpClient client = new OkHttpClient();

    String getDataFromNet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(MainActivity.this, "再次点击退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
