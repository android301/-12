package com.example.zbq.jizhangben.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zbq on 18-2-27.
 */

public class SearchOutListView extends ListView {
    public SearchOutListView(Context context) {
        super(context);
    }

    public SearchOutListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchOutListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int customSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, customSpec);
    }
}
