package com.example.zbq.jizhangben.ui.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.NewsBean;
import com.example.zbq.jizhangben.ui.activity.NewsDisplayActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-3-19.
 */

public class ReDianAdapter extends BaseAdapter  {

    private ArrayList<NewsBean> newsList;
    private Context context;
    private LayoutInflater inflater;


    public ReDianAdapter(Context context, ArrayList<NewsBean> newsList) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.newsList = newsList;

    }

    public void setNews(ArrayList<NewsBean> list){
        this.newsList=list;
    }


    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.list_item_redian, null);
            viewHolder=new MyViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder= (MyViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(newsList.get(position).getNewsTitle());
        viewHolder.tvContent.setText(newsList.get(position).getNewsContent());
        viewHolder.tvAuthor.setText(newsList.get(position).getNesAuthor());
        viewHolder.tvSource.setText(newsList.get(position).getNewsSource());
        viewHolder.tvTime.setText(newsList.get(position).getNewsTime());
        viewHolder.tvNum.setText(newsList.get(position).getNewsLookNum());

        return convertView;
    }




    static class MyViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_num)
        TextView tvNum;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
