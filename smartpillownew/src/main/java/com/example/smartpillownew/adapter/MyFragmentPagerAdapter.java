package com.example.smartpillownew.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.fragment.main.MainFragment;
import com.example.smartpillownew.fragment.main.MonitorFragment;
import com.example.smartpillownew.fragment.main.MonitorFragmentNew;
import com.example.smartpillownew.fragment.main.UserFragment;


/**
 * Created by a450J on 2018/8/7.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private Context context;
    private String[] titles = new String[]{"睡眠报告","实时监测","用户"};

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new MonitorFragmentNew();
            case 2:
                return new UserFragment();
            default:
                return new MainFragment();
        }
    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//
//        if (object instanceof FragmentLogin){
//            return POSITION_NONE;
//        }
//
//        return POSITION_UNCHANGED;
//    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private int[] imgSrc = {R.drawable.rb_bottom_info_selector,R.drawable.rb_bottom_monitor_selector,R.drawable.rb_bottom_user_selector};

    public View getTabView(int position){
        View view = View.inflate(context,R.layout.item_tab,null);
        ImageView iv_img = view.findViewById(R.id.iv_img);
        TextView tv_text = view.findViewById(R.id.tv_text);
        tv_text.setText(titles[position]);
//        Glide.with(context).load(imgSrc[position]).centerCrop().into(iv_img);
        iv_img.setImageResource(imgSrc[position]);
        return view;
    }
}
