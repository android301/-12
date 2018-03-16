package com.example.zbq.jizhangben.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
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

public class SelectRemindWayPopup extends PopupWindow implements View.OnClickListener {
    @BindView(R.id.tv_drugway_1)
    TextView tvDrugway1;
    @BindView(R.id.tv_drugway_2)
    TextView tvDrugway2;
    private SelectRemindWayPopupOnClickListener selectRemindWayPopupListener;


    private Context mContext;

    @SuppressWarnings("deprecation")
    public SelectRemindWayPopup(Context context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.selectremindcycle_pop_window, null);
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

        tvDrugway1 = view.findViewById(R.id.tv_drugway_1);
        tvDrugway2 = view.findViewById(R.id.tv_drugway_2);
        tvDrugway1.setOnClickListener(this);
        tvDrugway2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_drugway_1:
                selectRemindWayPopupListener.obtainMessage(0);
                break;
            case R.id.tv_drugway_2:
                selectRemindWayPopupListener.obtainMessage(1);
                break;
            default:
                break;
        }
        dismiss();

    }

    public interface SelectRemindWayPopupOnClickListener {
        void obtainMessage(int flag);
    }

    public void setOnSelectRemindWayPopupListener(SelectRemindWayPopupOnClickListener l) {
        this.selectRemindWayPopupListener = l;
    }

}
