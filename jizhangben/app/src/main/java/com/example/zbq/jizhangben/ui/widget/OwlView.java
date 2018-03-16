package com.example.zbq.jizhangben.ui.widget;

/**
 * Created by zbq on 18-2-6.
 */


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.example.zbq.jizhangben.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 猫头鹰输入密码的时候捂住眼睛
 * 布局文件中宽=175dp，height=107dp,也可以不写默认，只是为了预览
 */
public class OwlView extends View {

    private Context mContext;

    private Bitmap bm_owl;

    private Bitmap bm_owl_arm_left;

    private Bitmap bm_owl_arm_right;

    private int bm_height;
    private int moveHeight;

    private int alpha = 255;

    private int move_length = 0;

    private Paint handPaintBefore;
    private Paint handPaintAfter;

    public OwlView(Context context) {
        this(context,null);
    }

    public OwlView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OwlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){
        setBackgroundColor(Color.TRANSPARENT);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {//会在view布局完成时自动调用
                //OnGlobalLayoutListener()可能会多次触发,所以获取完宽高后要注销掉
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //获取控件的layoutParams
                ViewGroup.LayoutParams lp = getLayoutParams();
                if(lp==null)
                    return;
                lp.width = dip2px(175);
                lp.height = dip2px(107);
                //将修改好的layoutParams设置为该控件的layoutParams
                setLayoutParams(lp);

                setTranslationY(dip2px(9));
            }
        });


        bm_owl = BitmapFactory.decodeResource(getResources(), R.drawable.owl_login);
        bm_owl_arm_left = BitmapFactory.decodeResource(getResources(),R.drawable.owl_login_arm_left);
        bm_owl_arm_right = BitmapFactory.decodeResource(getResources(),R.drawable.owl_login_arm_right);

        bm_owl = compressBitmap(bm_owl,dip2px(115),dip2px(107),false);
        bm_owl_arm_left = compressBitmap(bm_owl_arm_left,dip2px(40),dip2px(65),true);
        bm_owl_arm_right = compressBitmap(bm_owl_arm_right,dip2px(40),dip2px(65),true);

        bm_height = bm_owl_arm_left.getHeight()/3*2-dip2px(10);

        handPaintBefore = new Paint();
        handPaintBefore.setColor(Color.parseColor("#472d20"));
        handPaintBefore.setAntiAlias(true);//无锯齿

        handPaintAfter = new Paint();
        handPaintAfter.setAntiAlias(true);
    }


    public void open(){

        ValueAnimator alphaVa = ValueAnimator.ofInt(0,255)
                .setDuration(300);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        alphaVa.start();

        ValueAnimator moveVa = ValueAnimator.ofInt(dip2px(45),0)
                .setDuration(200);
        moveVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move_length = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        moveVa.setStartDelay(200);
        moveVa.start();

        ValueAnimator va = ValueAnimator.ofInt(bm_height,0)
                .setDuration(300);
        va.setInterpolator(new LinearInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveHeight = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.start();
    }

    public void close(){

        final ValueAnimator alphtVa = ValueAnimator.ofInt(255,0)
                .setDuration(300);

        alphtVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {//值每改变一次该方法则调用一次
                alpha = (Integer) animation.getAnimatedValue();//获得改变后的值
                Log.v("ssssssssss",String.valueOf(alpha));
                requestLayout();//请求重新draw()，但只会绘制调用者本身
            }
        });
        alphtVa.start();

        ValueAnimator moveVa = ValueAnimator.ofInt(0,dip2px(45))
                .setDuration(200);
        moveVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move_length = (Integer) animation.getAnimatedValue();
                requestLayout();
            }
        });
        moveVa.start();

        ValueAnimator va = ValueAnimator.ofInt(0,bm_height)
                .setDuration(300);
        va.setInterpolator(new LinearInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveHeight = (Integer) animation.getAnimatedValue();
                requestLayout();
            }
        });
        va.setStartDelay(100);
        va.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);// 通过重写View.onDraw（）创建Canvas对象,在该方法里可以获得这个View对应的Canvas对象
        Log.v("sssss",String.valueOf(alpha));
        canvas.drawBitmap(bm_owl,
                new Rect(0,0,bm_owl.getWidth(),bm_owl.getHeight()),//绘制整张图片
                new Rect(dip2px(30), 0,dip2px(30)+bm_owl.getWidth(), bm_owl.getHeight()),//指定图片显示的位置
                handPaintAfter);

        handPaintBefore.setAlpha(alpha);
        canvas.drawOval(
                new RectF(
                        move_length,
                        getHeight()-dip2px(20),
                        move_length+dip2px(30),
                        getHeight()),
                handPaintBefore);

        canvas.drawOval(
                new RectF(
                        getWidth()-dip2px(30)-move_length,
                        getHeight()-dip2px(20),
                        getWidth()-move_length,
                        getHeight()),
                handPaintBefore);

        canvas.drawBitmap(bm_owl_arm_left,
                new Rect(0,0,bm_owl_arm_left.getWidth(), moveHeight),
                new Rect(dip2px(43),
                        getHeight()-moveHeight-dip2px(10),
                        dip2px(43)+bm_owl_arm_left.getWidth(),
                        getHeight()-dip2px(9)),
                null);

        canvas.drawBitmap(bm_owl_arm_right,
                new Rect(0,0,bm_owl_arm_right.getWidth(), moveHeight),
                new Rect(getWidth()-dip2px(40)-bm_owl_arm_right.getWidth(),
                        getHeight()-moveHeight-dip2px(10),
                        getWidth()-dip2px(40),
                        getHeight()-dip2px(9)),
                null);

    }


    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private Bitmap compressBitmap(Bitmap bitmap, float reqsW, float reqsH, boolean isAdjust) {
        if (bitmap == null || reqsW == 0 || reqsH == 0)
            return bitmap;

        if (bitmap.getWidth() > reqsW || bitmap.getHeight() > reqsH) {
            float scaleX = new BigDecimal(reqsW).divide(new BigDecimal(bitmap.getWidth()), 4, RoundingMode.DOWN).floatValue();
            float scaleY = new BigDecimal(reqsH).divide(new BigDecimal(bitmap.getHeight()), 4, RoundingMode.DOWN).floatValue();
            if (isAdjust) {
                scaleX = scaleX < scaleY ? scaleX : scaleY;
                scaleY = scaleX;
            }
            Matrix matrix = new Matrix();
            matrix.postScale(scaleX, scaleY);//缩放
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
}
