package com.example.zbq.jizhangben.ui.view;

/**
 * Created by zbq on 18-1-14.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 封装的底部导航栏。
 * n个tab按钮
 */

public class BaseBottomBar extends LinearLayout implements View.OnClickListener {

    private static final String TAG = BaseBottomBar.class.getSimpleName();

    private View currentView;

    private View[] views;

    private OnBottomBarListener onBottomBarListener;

    public BaseBottomBar(Context context) {
        super(context);
    }

    public BaseBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {//当View中所有的子控件均被映射成xml后触发
        super.onFinishInflate();
        views = new View[getChildCount()];
        for (int i = 0; i < views.length; i++) {
            views[i] = getChildAt(i);
            views[i].setOnClickListener(this);
            views[i].setTag(i);
        }
    }

    /**
     * 显示某一个tab
     *
     * @param index
     */
    public void showTab(int index) {
        if (index < 0)
            return;
        if (views != null && views.length > 0 && views.length > index) {
            for (int i = 0; i < views.length; i++) {
                if (i != index) {
                    onBottomBarListener.hideFragment(i, index);
                } else {
                    setCurrentView(views[index]);
                }
            }
        }

    }

    public void setCurrentView(View view) {
        if (view == null) {
            return;
        }

        if (onBottomBarListener == null) {
            return;
        }


        // 当前view = 点击view则调用刷新接口
        if (view == currentView) {
            onBottomBarListener.refreshView((Integer)currentView.getTag());
            return;
        }

        // 将之前的tab置灰 隐藏fragment
        if (currentView != null) {
            currentView.setSelected(false);
            onBottomBarListener.hideFragment((Integer)currentView.getTag(), (Integer)view.getTag());
        }

        // 刷新当前view引用 显示当前fragment
        currentView = view;
        currentView.setSelected(true);
        onBottomBarListener.showFragment((Integer)view.getTag());
    }

    @Override
    public void onClick(View view) {
        if (onBottomBarListener != null) {
            onBottomBarListener.onTabClick(view);
        }
    }

    public void setOnBottomBarListener(OnBottomBarListener listener) {
        this.onBottomBarListener = listener;
    }

    public int getCount() {
        return views.length;
    }


    public interface OnBottomBarListener {

        /**
         * 显示新tab页
         *
         * @param
         */
        void showFragment(int index);

        /**
         * 隐藏新tab页
         *
         * @param
         */
        void hideFragment(int lastIndex, int curIndex);

        /**
         * 刷新当前tab页
         */
        void refreshView(int index);

        /**
         * tab点击
         *
         * @param view
         */
        void onTabClick(View view);
    }
}
