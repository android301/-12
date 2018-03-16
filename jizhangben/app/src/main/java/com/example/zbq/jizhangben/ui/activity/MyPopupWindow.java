package com.example.zbq.jizhangben.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.Dao.OutInMoneyDB;
import com.example.zbq.jizhangben.ui.utils.DateUtils;
import com.example.zbq.jizhangben.ui.utils.JiZhangManager;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-1-24.
 */

public class MyPopupWindow extends PopupWindow implements View.OnClickListener {
    @BindView(R.id.tb_note_money)
    TextView tbNoteMoney;
    @BindView(R.id.tb_note_clear)
    ImageView tbNoteClear;
    @BindView(R.id.tb_note_cash)
    TextView tbNoteCash;
    @BindView(R.id.tb_note_remark)
    ImageView tbNoteRemark;
    @BindView(R.id.select_layout)
    LinearLayout selectLayout;
    @BindView(R.id.tb_calc_num_1)
    TextView tbCalcNum1;
    @BindView(R.id.tb_calc_num_4)
    TextView tbCalcNum4;
    @BindView(R.id.tb_calc_num_7)
    TextView tbCalcNum7;
    @BindView(R.id.tb_calc_num_dot)
    TextView tbCalcNumDot;
    @BindView(R.id.tb_calc_num_2)
    TextView tbCalcNum2;
    @BindView(R.id.tb_calc_num_3)
    TextView tbCalcNum3;
    @BindView(R.id.tb_calc_num_5)
    TextView tbCalcNum5;
    @BindView(R.id.tb_calc_num_6)
    TextView tbCalcNum6;
    @BindView(R.id.tb_calc_num_8)
    TextView tbCalcNum8;
    @BindView(R.id.tb_calc_num_9)
    TextView tbCalcNum9;
    @BindView(R.id.tb_calc_num_0)
    TextView tbCalcNum0;
    @BindView(R.id.tb_calc_num_del)
    RelativeLayout tbCalcNumDel;
    @BindView(R.id.tb_calc_num_done)
    TextView tbCalcNumDone;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.shuru)
    LinearLayout shuru;
    @BindView(R.id.edt_note)
    EditText edtNote;
    @BindView(R.id.tb_img_date)
    ImageView tbImgDate;
    @BindView(R.id.tb_img_money)
    ImageView tbImgMoney;
    @BindView(R.id.tb_calc_num_plus)
    TextView tbCalcNumPlus;
    @BindView(R.id.tb_calc_num_sub)
    TextView tbCalcNumSub;
    @BindView(R.id.tb_money)
    RelativeLayout tbMoney;

    private Context context;
    private View popupWindow;
    private TimePickerView pvTime;

    private boolean restart = true;
    private JiZhangBean jiZhangBean;

    public MyPopupWindow(Context context) {
        super(context);
        this.context = context;
        popupWindow = LayoutInflater.from(context).inflate(R.layout.write_popup, null);
        ButterKnife.bind(this, popupWindow);
        tbNoteMoney.setOnClickListener(this);
        tbNoteClear.setOnClickListener(this);
        tbCalcNum0.setOnClickListener(this);
        tbCalcNum1.setOnClickListener(this);
        tbCalcNum2.setOnClickListener(this);
        tbCalcNum3.setOnClickListener(this);
        tbCalcNum4.setOnClickListener(this);
        tbCalcNum5.setOnClickListener(this);
        tbCalcNum6.setOnClickListener(this);
        tbCalcNum7.setOnClickListener(this);
        tbCalcNum9.setOnClickListener(this);
        tbCalcNum8.setOnClickListener(this);
        tbCalcNumDot.setOnClickListener(this);
        tbCalcNumDel.setOnClickListener(this);
        tbCalcNumDone.setOnClickListener(this);
        tbCalcNumPlus.setOnClickListener(this);
        tbCalcNumSub.setOnClickListener(this);
        tbMoney.setOnClickListener(this);
        tbImgDate.setOnClickListener(this);

        setContentView(popupWindow);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);


        //设置弹出窗体可点击
        setFocusable(true);
        //实例化一个ColorDrawable颜色不透明
        ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
        //设置弹出窗体背景
        setBackgroundDrawable(colorDrawable);
        setOutsideTouchable(true);

        jiZhangBean=JiZhangManager.getInstance();

        //若没有选择日期则默认为系统当前日期时间
        jiZhangBean.setDate(DateUtils.stampToDate(String.valueOf(System.currentTimeMillis())));
    }

    private String text;
    private char operator;//用来记录运算符

    @Override
    public void onClick(View v) {
        // 获取文本框内容
        text = (String) tbNoteMoney.getText();
        //float value = Float.parseFloat(text);

        switch (v.getId()) {
            case R.id.tb_note_clear:
                tbNoteMoney.setText("0");
                restart = true;
                break;

            case R.id.tb_calc_num_0:
                if (restart) {
                    tbNoteMoney.setText("0");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "0");
                }
                break;

            case R.id.tb_calc_num_1:
                if (restart) {
                    tbNoteMoney.setText("1");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "1");
                }
                break;

            case R.id.tb_calc_num_2:
                if (restart) {
                    tbNoteMoney.setText("2");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "2");
                }
                break;

            case R.id.tb_calc_num_3:
                if (restart) {
                    tbNoteMoney.setText("3");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "3");
                }
                break;

            case R.id.tb_calc_num_4:
                if (restart) {
                    tbNoteMoney.setText("4");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "4");
                }
                break;

            case R.id.tb_calc_num_5:
                if (restart) {
                    tbNoteMoney.setText("5");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "5");
                }
                break;

            case R.id.tb_calc_num_6:
                if (restart) {
                    tbNoteMoney.setText("6");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "6");
                }
                break;

            case R.id.tb_calc_num_7:
                if (restart) {
                    tbNoteMoney.setText("7");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "7");
                }
                break;

            case R.id.tb_calc_num_8:
                if (restart) {
                    tbNoteMoney.setText("8");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "8");
                }
                break;

            case R.id.tb_calc_num_9:
                if (restart) {
                    tbNoteMoney.setText("9");
                    restart = false;
                } else {
                    tbNoteMoney.setText(text + "9");
                }
                break;

            case R.id.tb_calc_num_dot:
                tbNoteMoney.setText(text + "9");
                restart = false;
                break;

            case R.id.tb_calc_num_del:
                text = text.substring(0, text.length() - 1);
                tbNoteMoney.setText(text);
                break;

            case R.id.tb_calc_num_plus:
                text = addNum('+');
                tbNoteMoney.setText(text);
                operator = '+';
                break;

            case R.id.tb_calc_num_sub:
                text = addNum('-');
                tbNoteMoney.setText(text);
                operator = '-';
                break;

            case R.id.tb_calc_num_done:

                if (text.indexOf(operator,1)!=-1)//如果有输入运算符
                {
                    text = String.valueOf(equals(text));
                    tbNoteMoney.setText(text);//显示结果
                    restart=true;

                }

                    String mtype=jiZhangBean.getType();
                    float m = Float.valueOf(tbNoteMoney.getText().toString());
                    if (mtype.equals("支出")) {
                        //获取输入金额
                        jiZhangBean.setMoney(Float.valueOf("-"+m));
                    }else {
                        jiZhangBean.setMoney(m);
                    }


                    //获取备注，若无备注则默认为选中的方式
                    if (edtNote.getText().toString().trim().isEmpty()){
                        jiZhangBean.setWrite(jiZhangBean.getWay());
                    }else {
                        jiZhangBean.setWrite(edtNote.getText().toString());
                    }

                    //获取收支方式
                    jiZhangBean.setStyle(tbNoteCash.getText().toString());

                    //插入数据库
                    String type=jiZhangBean.getType();
                    String way=jiZhangBean.getWay();
                    String style=jiZhangBean.getStyle();
                    Float money=jiZhangBean.getMoney();
                    String write=jiZhangBean.getWrite();
                    String date=jiZhangBean.getDate();
                    //byte[] pic=jiZhangBean.getPic();

                    OutInMoneyDB outInMoneyDB=new OutInMoneyDB(context);
                    outInMoneyDB.insert(type,way,style,money,write,date);
                    //Log.v("lll",type+way+style+money+write+date);

                    dismiss();

                if (text.endsWith(String.valueOf(operator)))
                //最后以运算符结尾而不是数字，肯定不合理 如输入 9** ，不进行计算
                {
                    //输入不合理弹出警告
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tb_money:
                MoneyPopupWindow moneyPopupWindow = new MoneyPopupWindow(context,tbImgMoney,tbNoteCash);
                moneyPopupWindow.showAtLocation(LayoutInflater.from(context).
                                inflate(R.layout.money_popup, null),
                        Gravity.BOTTOM, 0, 0);
                break;

            case R.id.tb_img_date:
                //时间选择器
                initLunarPicker();

                break;
            default:
                break;
        }

    }

    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2018, 11, 31);
        pvTime = new TimePickerView.Builder(getContentView().getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                //获取选中日期
                //选择日期早于now
                if (date.getTime() <= new Date().getTime()){
                    //时间戳格式
                    String dates=String.valueOf(date.getTime());
                    //时间戳转化为时间
                    String ss=DateUtils.stampToDate(dates);
                    jiZhangBean.setDate(ss);
                }
                else {
                    Toast.makeText(context, "日期不得迟于今天", Toast.LENGTH_SHORT);
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setDecorView((ViewGroup) getContentView())//可自由设置pickview的容器，即控件显示在哪个控件里面。
                .setTitleSize(20)//标题文字大小
                .setTitleText("日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日",null,null,null)//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)
                .build();
        pvTime.show();

    }




    public String addNum(char c) {
        text = text + String.valueOf(c);
        return text;
    }


    public double equals(String OperateSum) {
        //计算结果
        double sum = 0, num1 = 0, num2 = 0;
        int indexOfOperator = 0;
        indexOfOperator = OperateSum.indexOf(operator, 1);         //计算运算符在从输入的OperateSum字符串里的位置
        if (OperateSum.length() >= 3) {
            num1 = Double.parseDouble(OperateSum.substring(0, indexOfOperator));  //从输入的OperateSum字符串里得到第一个运算数
            num2 = Double.parseDouble(OperateSum.substring(indexOfOperator + 1, OperateSum.length()));    //从输入的OperateSum字符串里得到第二个运算数
        }
        switch (operator)       //根据运算符进行计算
        {
            case '+':           //加法运算
                sum = num1 + num2;
                break;

            case '-':           //减法运算
                sum = num1 - num2;
                break;
        }
        return sum;
    }
}
