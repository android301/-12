package com.example.zbq.jizhangben.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-2-26.
 */

public class MyListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<JiZhangBean> list;

    public MyListViewAdapter(Context context, ArrayList<JiZhangBean> arrayList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = arrayList;
    }

    public void setDatas(ArrayList<JiZhangBean> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_alllist_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageDrawable(list.get(position).getPic());
        viewHolder.tv_write.setText(list.get(position).getWrite());
        viewHolder.tv_date.setText(list.get(position).getDate());
        viewHolder.tv_money.setText(String.valueOf(list.get(position).getMoney()));
        Log.v("aaaa",viewHolder.tv_money.getText().toString());
        return convertView;
    }

     class ViewHolder {
        @BindView(R.id.item_img)
        ImageView imageView;
        @BindView(R.id.item_title)
        TextView tv_write;
        @BindView(R.id.item_date)
        TextView tv_date;
        @BindView(R.id.item_money)
        TextView tv_money;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
