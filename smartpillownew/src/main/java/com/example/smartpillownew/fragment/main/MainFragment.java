package com.example.smartpillownew.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartpillownew.R;
import com.example.smartpillownew.activity.HistoryActivity;
import com.example.smartpillownew.activity.IrregularReportActivity;
import com.example.smartpillownew.activity.RangeSetActivity;
import com.example.smartpillownew.activity.SleepAnalysisActivity;
import com.example.smartpillownew.activity.SleepRecordActivity;
import com.example.smartpillownew.activity.WarnManagerActivity;
import com.example.smartpillownew.activity.WarnUserActivity;
import com.example.smartpillownew.activity.WeekSleepActivity;
import com.example.smartpillownew.fragment.BaseFragment;
import com.example.smartpillownew.utils.GlideImageLoader;
import com.example.smartpillownew.utils.MyTimeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by a450J on 2018/8/9.
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_main_welcome)
    TextView tvMainWelcome;
    @BindView(R.id.btn_meizhou_jilu)
    Button btnMeizhouJilu;
    @BindView(R.id.btn_yichang_jilu)
    Button btnYichangJilu;
    @BindView(R.id.btn_baojingshezhi)
    Button btnBaojingshezhi;
    @BindView(R.id.btn_canshu_shezhi)
    Button btnCanshuShezhi;
    @BindView(R.id.tv_shuimian_baogao)
    TextView tvShuimianBaogao;
    @BindView(R.id.tv_shuimian_defen)
    TextView tvShuimianDefen;
    @BindView(R.id.tv_fenshu)
    TextView tvFenshu;
    @BindView(R.id.btn_shuimian_biaoqian)
    Button btnShuimianBiaoqian;
    Unbinder unbinder;
    @BindView(R.id.btn_jiankang_jianyi)
    Button btnJiankangJianyi;
    @BindView(R.id.btn_baojing_renyuan)
    Button btnBaojingRenyuan;

    private List<Integer> imageList;
    private Intent intent;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_main, null);
        return view;
    }

    @Override
    public void initDat() {
        super.initDat();

        imageList = new ArrayList<>();
        imageList.add(R.drawable.pm_scroll_image1);
        imageList.add(R.drawable.pm_scroll_image2);
        imageList.add(R.drawable.pm_scroll_image3);
        imageList.add(R.drawable.pm_scroll_image4);

        //设置banner
        initBanner();

        //设置问候
        MyTimeUtils utils = new MyTimeUtils();
        if (utils.getAmorPm()==0){
            tvMainWelcome.setText("亲爱的用户，上午好！");
        }else if (utils.getAmorPm()==1){
            tvMainWelcome.setText("亲爱的用户，中午好！");
        }else if (utils.getAmorPm()==2){
            tvMainWelcome.setText("亲爱的用户，下午好！");
        }else if (utils.getAmorPm()==3){
            tvMainWelcome.setText("亲爱的用户，晚上好！");
        }
    }

    private void initBanner() {
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imageList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_meizhou_jilu, R.id.btn_yichang_jilu, R.id.btn_baojingshezhi, R.id.btn_canshu_shezhi,
            R.id.btn_shuimian_biaoqian, R.id.btn_jiankang_jianyi, R.id.btn_baojing_renyuan, R.id.tv_shuimian_defen})
    public void onViewClicked(View view) {
        intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_meizhou_jilu:
                intent.setClass(context, WeekSleepActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_yichang_jilu:
                intent.setClass(context, IrregularReportActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baojingshezhi:
                intent.setClass(context, WarnManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_canshu_shezhi:
                intent.setClass(context, RangeSetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_shuimian_biaoqian:
                showPopupWindows(view);
                break;
            case R.id.btn_jiankang_jianyi:
                intent.setClass(context, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baojing_renyuan:
                intent.setClass(context, WarnUserActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_shuimian_defen:
//                intent.setClass(context, SleepRecordActivity.class);
//                startActivity(intent);
                break;
        }
    }

    private void showPopupWindows(View view) {
        PopupMenu pop = new PopupMenu(context, view);

        pop.inflate(R.menu.popup_biaoqian);
        pop.show();

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.jiaocha:
                        Toast.makeText(context, "您的睡眠质量较差，可结合医生建议进行治疗", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.yiban:
                        Toast.makeText(context, "您的睡眠质量一般，可继续加强", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.lianghao:
                        Toast.makeText(context, "您的睡眠质量良好，请继续保持", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.youyi:
                        Toast.makeText(context, "您的睡眠质量优异！", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }


}
