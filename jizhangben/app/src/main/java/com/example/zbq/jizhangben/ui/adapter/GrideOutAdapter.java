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
 * Created by zbq on 18-2-3.
 */

public class GrideOutAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int[] images = {
            R.drawable.sort_bangong, R.drawable.sort_canyin, R.drawable.sort_huankuan,
            R.drawable.sort_chongwu,R.drawable.sort_tongxun, R.drawable.sort_xuexi,
            R.drawable.sort_fanxian, R.drawable.sort_haizi,R.drawable.sort_zhangbei,
            R.drawable.sort_jiaotong,R.drawable.sort_shuiguo,R.drawable.sort_jiushui,
            R.drawable.sort_juanzeng,R.drawable.sort_jujia,R.drawable.sort_lijin,
            R.drawable.sort_liwu,R.drawable.sort_lingshi,R.drawable.sort_yundong,
            R.drawable.sort_lvxing,R.drawable.sort_meirong,R.drawable.sort_gouwu,
            R.drawable.sort_shuma,R.drawable.sort_yule,R.drawable.sort_zhufang,
            R.drawable.sort_weixiu,R.drawable.sort_tianjiade,R.drawable.sort_yiliao
    };

    private String[] texts = {
            "办公","餐饮","偿还",
            "宠物","通讯","学习",
            "服饰","育儿","长辈",
            "交通","水果","烟酒",
            "捐赠","居家","礼金",
            "礼物","零食","健身",
            "旅行","美容","商场",
            "数码","娱乐","住房",
            "维修","杂项","医疗"
    };

    public GrideOutAdapter(Context context) {
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
        GridinAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
            viewHolder=new GridinAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(GridinAdapter.ViewHolder)convertView.getTag();
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
