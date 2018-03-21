package com.example.zbq.jizhangben.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.fragment.FaXianFragment;
import com.example.zbq.jizhangben.ui.view.CircleProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-3-21.
 */

public class BianJiYuSuanPopup extends PopupWindow implements View.OnClickListener {

    @BindView(R.id.pop_computer)
    TextView popComputer;
    @BindView(R.id.pop_financial)
    TextView popFinancial;
    @BindView(R.id.pop_manage)
    TextView popManage;
    private Context context;
    private View popupWindow;
    private Button btyusuan;
    private LinearLayout llyusuan;
    String yusuan;
    float out;
    private TextView tvyusuan,tvshengyu,tvzhichu;
    private CircleProgressBar progressBar;

    public BianJiYuSuanPopup(Context context, Button btyusuan,LinearLayout llyusuan,float out,
                             TextView yusuan,TextView shengyu,TextView zhichu,CircleProgressBar progressBar) {
        this.context = context;
        popupWindow = LayoutInflater.from(context).inflate(R.layout.popup_bianjiyusuan, null);
        ButterKnife.bind(this, popupWindow);
        popComputer.setOnClickListener(this);
        popFinancial.setOnClickListener(this);
        popManage.setOnClickListener(this);

        this.btyusuan=btyusuan;
        this.llyusuan=llyusuan;
        this.out=out;
        this.tvshengyu=shengyu;
        this.tvzhichu=zhichu;
        this.tvyusuan=yusuan;
        this.progressBar=progressBar;

        setContentView(popupWindow);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);


        //设置弹出窗体可点击
        setFocusable(true);
        //实例化一个ColorDrawable颜色不透明
        ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
        //设置弹出窗体背景
        setBackgroundDrawable(colorDrawable);
        setOutsideTouchable(true);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.pop_computer:
                showdialog();
                llyusuan.setVisibility(View.VISIBLE);
                btyusuan.setVisibility(View.GONE);
                dismiss();
                break;

            case R.id.pop_financial:
                btyusuan.setVisibility(View.VISIBLE);
                llyusuan.setVisibility(View.GONE);
                dismiss();
                break;

            case R.id.pop_manage:
                dismiss();
                break;
        }

    }

    public void showdialog() {
        final EditText editText = new EditText(context);

        new AlertDialog.Builder(context)
                .setTitle("设置预算")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获得输入框的内容


                        yusuan = editText.getText().toString();
                        tvyusuan.setText(yusuan);

                        tvzhichu.setText(String.valueOf(out - 2 * out));//除去符号
                        tvshengyu.setText(String.valueOf(Float.parseFloat(yusuan) + out));
                        //设置进度最大值
                        progressBar.setMax(Integer.parseInt(yusuan));
                        //设置进度
                        progressBar.setProgress((int) (out - 2 * out));
                    }
                }).setNegativeButton("取消", null).create()
                .show();
    }

}
