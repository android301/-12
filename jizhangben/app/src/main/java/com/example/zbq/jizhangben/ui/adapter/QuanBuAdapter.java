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
 * Created by zbq on 18-3-14.
 */

public class QuanBuAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    private int[] images = {
            R.drawable.kechengbiao, R.drawable.bianlitie,
            R.drawable.rili, R.drawable.jinianri
    };

    private String[] texts = {
            "課程表", "便利贴",
            "日历", "紀念日"
    };

    public QuanBuAdapter(Context context) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_quanbu, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageIcon.setImageResource(images[position]);
        holder.textTitle.setText(texts[position]);
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.image_icon)
        ImageView imageIcon;
        @BindView(R.id.text_title)
        TextView textTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
