package com.example.smartpillownew.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartpillownew.R;

import java.util.List;

import com.example.smartpillownew.bean.AbnormalReportBean;
import com.example.smartpillownew.bean.IrregularInforBean;


/**
 * Created by a450J on 2018/8/7.
 */

public class IrregularInfoAdapter extends BaseAdapter{

    private final Context context;
    private final List<AbnormalReportBean.ValueBean> list;

    public IrregularInfoAdapter(Context context, List<AbnormalReportBean.ValueBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_list_view_info,null);
            viewHolder.iv_info = view.findViewById(R.id.iv_info);
            viewHolder.tv_info = view.findViewById(R.id.tv_info);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            viewHolder.tv_date = view.findViewById(R.id.tv_date);
            viewHolder.tv_range = view.findViewById(R.id.tv_range);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        AbnormalReportBean.ValueBean bean = list.get(i);
        viewHolder.tv_info.setText(bean.getInfo());
        viewHolder.tv_date.setText(bean.getDatetime());
        if (bean.getInfo().equals("异常:心跳")){
            viewHolder.iv_info.setImageResource(R.drawable.iv_heart_rate_trouble);
            viewHolder.tv_time.setText(bean.getHeart());
            viewHolder.tv_range.setText("60~120");
        }else if (bean.getInfo().equals("翻转频繁")){
            viewHolder.iv_info.setImageResource(R.drawable.iv_range_trouble);
            viewHolder.tv_time.setText(bean.getTurnover());
            viewHolder.tv_range.setText("15~25");
        }else if (bean.getInfo().equals("异常:呼吸")){
            viewHolder.iv_info.setImageResource(R.drawable.iv_breath_trouble);
            viewHolder.tv_time.setText(bean.getBreath());
            viewHolder.tv_range.setText("10~25");
        }

        return view;
    }

    class ViewHolder{
        ImageView iv_info;
        TextView tv_info;
        TextView tv_time;
        TextView tv_date;
        TextView tv_range;
    }
}
