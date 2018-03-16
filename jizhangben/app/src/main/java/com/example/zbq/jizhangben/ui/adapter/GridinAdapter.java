package com.example.zbq.jizhangben.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.zbq.jizhangben.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-1-23.
 */

public class GridinAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int[] images = {
            R.drawable.sort_jiangjin, R.drawable.sort_tianjiade, R.drawable.sort_yiban,
            R.drawable.sort_jianzhi, R.drawable.sort_lixi, R.drawable.sort_lijin,
            R.drawable.sort_shouxufei};

    private String[] texts = {
            "工资", "基金", "其他",
            "兼职", "利息", "礼金",
            "股票"
    };

    public GridinAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.imageIcon.setImageResource(images[position]);
        viewHolder.textTitle.setText(texts[position]);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.image_icon)
        ImageView imageIcon;
        @BindView(R.id.text_title)
        TextView textTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
