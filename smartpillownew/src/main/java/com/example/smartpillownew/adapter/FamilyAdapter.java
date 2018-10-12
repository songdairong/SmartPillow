package com.example.smartpillownew.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartpillownew.R;
import com.example.smartpillownew.bean.UserWarnMemberBean;

import java.util.List;

/**
 * Created by a450J on 2018/8/11.
 */

public class FamilyAdapter extends BaseAdapter{

    private final Context context;
    private final List<UserWarnMemberBean> list;

    public FamilyAdapter(Context context, List<UserWarnMemberBean> list) {
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
            view = View.inflate(context, R.layout.item_warn_member,null);
            viewHolder.tv_member_name = view.findViewById(R.id.tv_member_name);
            viewHolder.tv_member_phone = view.findViewById(R.id.tv_member_phone);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        UserWarnMemberBean bean = list.get(i);
        viewHolder.tv_member_name.setText(bean.getName());
        viewHolder.tv_member_phone.setText(bean.getPhone());
        if (i%2==0){
            view.setBackgroundColor(context.getResources().getColor(R.color.gray2));
        }else {
            view.setBackgroundColor(context.getResources().getColor(R.color.gray1));
        }

        return view;
    }

    class ViewHolder{
        TextView tv_member_name;
        TextView tv_member_phone;
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }
}
