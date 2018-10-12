package com.example.smartpillownew.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.HistoryBean;

import java.util.List;

/**
 * Created by a450J on 2018/8/14.
 */

public class HistoryAdapter extends BaseAdapter{

    private final Context context;
    private final List<HistoryBean.ValueBean> list;

    public HistoryAdapter(Context context, List<HistoryBean.ValueBean> list) {
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
            view = View.inflate(context,R.layout.item_list_history,null);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HistoryBean.ValueBean bean = list.get(i);
        viewHolder.tv_time.setText(bean.getDatetime());

        return view;
    }

    class ViewHolder{
        TextView tv_time;
    }
}
