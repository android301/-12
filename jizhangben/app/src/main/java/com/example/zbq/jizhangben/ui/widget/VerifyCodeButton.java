package com.example.zbq.jizhangben.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.zbq.jizhangben.R;


/**
 * Created by zbq on 18-2-6.
 */

@SuppressLint("AppCompatCustomView")
public class VerifyCodeButton extends Button {

    private final Context mContext;

    private int mClickAfterBackground;
    private String mAfterCountDownText;
    private int mCountdownTime = 60;
    private TimeCount mTimeCount;
    private int mBackground;

    public VerifyCodeButton(Context context) {
        this(context, null);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        //加载自定义属性集合VerifyCodeButton
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.VerifyCodeButton);
        //解析集合中的属性
        mBackground = a.getResourceId(R.styleable.VerifyCodeButton_android_background, mBackground);
        mClickAfterBackground = a.getResourceId(R.styleable.VerifyCodeButton_vcb_clickAfterBackground, mClickAfterBackground);
        mCountdownTime = a.getInteger(R.styleable.VerifyCodeButton_vcb_countdownTime, mCountdownTime);
        mAfterCountDownText = a.getString(R.styleable.VerifyCodeButton_vcb_afterCountdownText);
        a.recycle();//释放资源
    }

    private void init() {
        setBackgroundResource(mBackground);
        mTimeCount = new TimeCount(mCountdownTime * 1000, 1000);
    }


    /**
     * 开始倒计时
     */
    public void start(){
        mTimeCount.start();
    }


    class TimeCount extends CountDownTimer {

        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setClickable(false);
            setText(String.valueOf(millisUntilFinished / 1000 + "s"));
            setBackgroundResource(mClickAfterBackground);
        }

        @Override
        public void onFinish() {
            setClickable(true);
            setText(mAfterCountDownText != null ? mAfterCountDownText : "");
            setBackgroundResource(mBackground);
        }
    }
}
