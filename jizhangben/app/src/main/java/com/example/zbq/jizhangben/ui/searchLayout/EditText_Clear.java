package com.example.zbq.jizhangben.ui.searchLayout;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.zbq.jizhangben.R;

/**
 * Created by zbq on 18-2-23.
 */

public class EditText_Clear extends AppCompatEditText {

    /**
     * 1.定义左侧搜索图标,删除图标
     * @param context
     */
    private Drawable clearDrawable,searchDrawable;

    public EditText_Clear(Context context) {
        super(context);
        init();
        //初始化该组件时顺便对EditText_Clear进行初始化
    }

    public EditText_Clear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_Clear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化图标资源
     */
    private void init() {
        clearDrawable = getResources().getDrawable(R.drawable.clear);
        searchDrawable = getResources().getDrawable(R.drawable.search);
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null,
                null, null);
        // setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom)介绍
        // 作用：在EditText上、下、左、右设置图标（相当于android:drawableLeft=""  android:drawableRight=""）
        // 注1：setCompoundDrawablesWithIntrinsicBounds（）传入的Drawable的宽高=固有宽高（自动通过getIntrinsicWidth（）& getIntrinsicHeight（）获取）
        // 注2：若不想在某个地方显示，则设置为null
        // 此处设置了左侧搜索图标

        // 另外一个相似的方法：setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)介绍
        // 与setCompoundDrawablesWithIntrinsicBounds（）的区别：可设置图标大小
        // 传入的Drawable对象必须已经setBounds(x,y,width,height)，即必须设置过初始位置、宽和高等信息
        // x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点 width:组件的长度 height:组件的高度

    }

    /**
     *  3：通过监听复写EditText本身的方法来确定是否显示删除图标
     * 监听方法：onTextChanged（） & onFocusChanged（）
     * 调用时刻：当输入框内容变化时 & 焦点发生变化时
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
        // hasFocus()返回是否获得EditTEXT的焦点，即是否选中
        // setClearIconVisible（） = 根据传入的是否选中 & 是否有输入来判断是否显示删除图标

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
        // focused = 是否获得焦点
        // 同样根据setClearIconVisible（）判断是否要显示删除图标
    }


    /**
     * 作用：判断是否显示删除图标
     */
    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null,
                visible ? clearDrawable : null, null);
    }

    /**
     *  4：对删除图标区域设置点击事件，即"点击 = 清空搜索框内容"
     * 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            // 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
            case MotionEvent.ACTION_UP:
                if (clearDrawable!=null&&event.getX()<=getWidth()-getPaddingRight()&&
                    event.getX()>=getWidth()-getPaddingRight()-clearDrawable.getBounds().width()){
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
