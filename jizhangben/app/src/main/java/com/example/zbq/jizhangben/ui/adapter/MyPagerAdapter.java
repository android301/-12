package com.example.zbq.jizhangben.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zbq on 18-1-23.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View> list;

    public MyPagerAdapter(List<View> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//初始化页卡
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {//判断view是否来自object
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//销毁页卡
        container.removeView(list.get(position));
    }
}
