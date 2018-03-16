package com.example.zbq.jizhangben.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-3-2.
 */

public class SelectRemindCyclePopup extends PopupWindow implements View.OnClickListener {

    @BindView(R.id.tv_drugcycle_once)
    TextView tvDrugcycleOnce;
    @BindView(R.id.tv_drugcycle_0)
    TextView tvDrugcycle0;
    @BindView(R.id.tv_drugcycle_1)
    TextView tvDrugcycle1;
    @BindView(R.id.tv_drugcycle_2)
    TextView tvDrugcycle2;
    @BindView(R.id.tv_drugcycle_3)
    TextView tvDrugcycle3;
    @BindView(R.id.tv_drugcycle_4)
    TextView tvDrugcycle4;
    @BindView(R.id.tv_drugcycle_5)
    TextView tvDrugcycle5;
    @BindView(R.id.tv_drugcycle_6)
    TextView tvDrugcycle6;
    @BindView(R.id.tv_drugcycle_7)
    TextView tvDrugcycle7;
    @BindView(R.id.tv_drugcycle_sure)
    TextView tvDrugcycleSure;

    private SelectRemindCyclePopupOnClickListener selectRemindCyclePopupListener;

    private View view;

    private Context mContext;

    @SuppressWarnings("deprecation")
    public SelectRemindCyclePopup(Context context) {
        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.selectremindway_pop_window, null);
        ButterKnife.bind(this,view);
        setContentView(view);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置弹出窗体可点击
        setFocusable(true);
        //实例化一个ColorDrawable颜色不透明
        ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
        //设置弹出窗体背景
        setBackgroundDrawable(colorDrawable);


        tvDrugcycleOnce.setOnClickListener(this);
        tvDrugcycle0.setOnClickListener(this);
        tvDrugcycle1.setOnClickListener(this);
        tvDrugcycle2.setOnClickListener(this);
        tvDrugcycle3.setOnClickListener(this);
        tvDrugcycle4.setOnClickListener(this);
        tvDrugcycle5.setOnClickListener(this);
        tvDrugcycle6.setOnClickListener(this);
        tvDrugcycle7.setOnClickListener(this);
        tvDrugcycleSure.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Drawable nav_right = mContext.getResources().getDrawable(R.drawable.cycle_check);
        nav_right.setBounds(0, 0, nav_right.getMinimumWidth(), nav_right.getMinimumHeight());
        switch (v.getId()) {
            case R.id.tv_drugcycle_once:
                selectRemindCyclePopupListener.obtainMessage(9, "");
                break;
            case R.id.tv_drugcycle_0:
                selectRemindCyclePopupListener.obtainMessage(8, "");
                break;
            case R.id.tv_drugcycle_1:
                if (tvDrugcycle1.getCompoundDrawables()[2] == null)
                    tvDrugcycle1.setCompoundDrawables(null, null, nav_right, null);
                else tvDrugcycle1.setCompoundDrawables(null, null, null, null);
                selectRemindCyclePopupListener.obtainMessage(0, "");
                break;
            case R.id.tv_drugcycle_2:
                if (tvDrugcycle2.getCompoundDrawables()[2] == null)
                    tvDrugcycle2.setCompoundDrawables(null, null, nav_right, null);
                else tvDrugcycle2.setCompoundDrawables(null, null, null, null);
                selectRemindCyclePopupListener.obtainMessage(1, "");
                break;
            case R.id.tv_drugcycle_3:
                if (tvDrugcycle3.getCompoundDrawables()[2] == null)
                    tvDrugcycle3.setCompoundDrawables(null, null, nav_right, null);
                else tvDrugcycle3.setCompoundDrawables(null, null, null, null);
                selectRemindCyclePopupListener.obtainMessage(2, "");
                break;
            case R.id.tv_drugcycle_4:
                if (tvDrugcycle4.getCompoundDrawables()[2] == null)
                    tvDrugcycle4.setCompoundDrawables(null, null, nav_right, null);
                else tvDrugcycle4.setCompoundDrawables(null, null, null, null);
                selectRemindCyclePopupListener.obtainMessage(3, "");
                break;
            case R.id.tv_drugcycle_5:
                if (tvDrugcycle5.getCompoundDrawables()[2] == null)
                    tvDrugcycle5.setCompoundDrawables(null, null, nav_right, null);
                else tvDrugcycle5.setCompoundDrawables(null, null, null, null);
                selectRemindCyclePopupListener.obtainMessage(4, "");
                break;
            case R.id.tv_drugcycle_6:
                if (tvDrugcycle6.getCompoundDrawables()[2] == null)
                    tvDrugcycle6.setCompoundDrawables(null, null, nav_right, null);
                else tvDrugcycle6.setCompoundDrawables(null, null, null, null);
                selectRemindCyclePopupListener.obtainMessage(5, "");
                break;
            case R.id.tv_drugcycle_7:
                if (tvDrugcycle7.getCompoundDrawables()[2] == null)
                    tvDrugcycle7.setCompoundDrawables(null, null, nav_right, null);
                else tvDrugcycle7.setCompoundDrawables(null, null, null, null);
                selectRemindCyclePopupListener.obtainMessage(6, "");
                break;
            case R.id.tv_drugcycle_sure:
                int remind = ((tvDrugcycle1.getCompoundDrawables()[2] == null) ? 0 : 1) * 1 // 周一
                        + ((tvDrugcycle2.getCompoundDrawables()[2] == null) ? 0 : 1) * 2 // 周二
                        + ((tvDrugcycle3.getCompoundDrawables()[2] == null) ? 0 : 1) * 4 // 周三
                        + ((tvDrugcycle4.getCompoundDrawables()[2] == null) ? 0 : 1) * 8 // 周四
                        + ((tvDrugcycle5.getCompoundDrawables()[2] == null) ? 0 : 1) * 16 // 周五
                        + ((tvDrugcycle6.getCompoundDrawables()[2] == null) ? 0 : 1) * 32 // 周六
                        + ((tvDrugcycle7.getCompoundDrawables()[2] == null) ? 0 : 1) * 64; // 周日
                selectRemindCyclePopupListener.obtainMessage(7, String.valueOf(remind));
                dismiss();
                break;
            default:
                break;
        }

    }

    public interface SelectRemindCyclePopupOnClickListener {
        void obtainMessage(int flag, String ret);
    }

    public void setOnSelectRemindCyclePopupListener(SelectRemindCyclePopupOnClickListener l) {
        this.selectRemindCyclePopupListener = l;
    }

}