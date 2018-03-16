package com.example.zbq.jizhangben.ui.searchLayout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zbq on 18-2-23.
 */

public class SearchListView extends ListView{
    public SearchListView(Context context) {
        super(context);
    }

    public SearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //通过重写其onMeasure（）方法，达到对ScrollView适配效果

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,expandSpec);
    }
}
