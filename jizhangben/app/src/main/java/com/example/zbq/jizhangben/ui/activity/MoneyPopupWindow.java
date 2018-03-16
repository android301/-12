package com.example.zbq.jizhangben.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.utils.JiZhangManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-1-27.
 */

public class MoneyPopupWindow extends PopupWindow implements View.OnClickListener {
    @BindView(R.id.img_dismiss)
    ImageView imgDismiss;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.item_xianjin_img)
    ImageView itemXianjinImg;
    @BindView(R.id.item_xianjin_title)
    TextView itemXianjinTitle;
    @BindView(R.id.item_xianjin_money)
    TextView itemXianjinMoney;
    @BindView(R.id.item_xianjin_layout)
    RelativeLayout itemXianjinLayout;
    @BindView(R.id.item_weixin_img)
    ImageView itemWeixinImg;
    @BindView(R.id.item_weixin_title)
    TextView itemWeixinTitle;
    @BindView(R.id.item_weixin_money)
    TextView itemWeixinMoney;
    @BindView(R.id.item_weixin_layout)
    RelativeLayout itemWeixinLayout;
    @BindView(R.id.item_zhifubao_img)
    ImageView itemZhifubaoImg;
    @BindView(R.id.item_zhifubao_title)
    TextView itemZhifubaoTitle;
    @BindView(R.id.item_zhifubao_money)
    TextView itemZhifubaoMoney;
    @BindView(R.id.item_zhifubao_layout)
    RelativeLayout itemZhifubaoLayout;
    @BindView(R.id.item_chuxuka_img)
    ImageView itemChuxukaImg;
    @BindView(R.id.item_chuxuka_title)
    TextView itemChuxukaTitle;
    @BindView(R.id.item_chuxuka_money)
    TextView itemChuxukaMoney;
    @BindView(R.id.item_chuxuka_layout)
    RelativeLayout itemChuxukaLayout;
    @BindView(R.id.item_xinyongka_img)
    ImageView itemXinyongkaImg;
    @BindView(R.id.item_xinyongka_title)
    TextView itemXinyongkaTitle;
    @BindView(R.id.item_xinyongka_money)
    TextView itemXinyongkaMoney;
    @BindView(R.id.item_xinyongka_layout)
    RelativeLayout itemXinyongkaLayout;
    private Context context;
    private View popupWindow;

    private ImageView tbimagmoney;
    private TextView tbnotecash;

    private JiZhangBean jiZhangBean;
    public MoneyPopupWindow(Context context,ImageView imageView,TextView textView) {
        this.context = context;
        tbimagmoney=imageView;
        tbnotecash=textView;
        popupWindow = LayoutInflater.from(context).inflate(R.layout.money_popup, null);
        ButterKnife.bind(this,popupWindow);
        setContentView(popupWindow);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置弹出窗体可点击
        setFocusable(true);
        //实例化一个ColorDrawable颜色不透明
        ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
        //设置弹出窗体背景
        setBackgroundDrawable(colorDrawable);

        imgDismiss.setOnClickListener(this);
        itemXianjinLayout.setOnClickListener(this);
        itemWeixinLayout.setOnClickListener(this);
        itemZhifubaoLayout.setOnClickListener(this);
        itemChuxukaLayout.setOnClickListener(this);
        itemXinyongkaLayout.setOnClickListener(this);

        jiZhangBean= JiZhangManager.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_dismiss:
                dismiss();
                break;

            case R.id.item_xianjin_layout:
                dismiss();
                tbimagmoney.setImageResource(R.drawable.xianjin2);
                tbnotecash.setText("现金");
                break;

            case R.id.item_weixin_layout:
                dismiss();
                tbimagmoney.setImageResource(R.drawable.weixin2);
                tbnotecash.setText("微信");
                break;

            case R.id.item_zhifubao_layout:
                dismiss();
                tbimagmoney.setImageResource(R.drawable.zhifubao2);
                tbnotecash.setText("支付宝");
                break;

            case R.id.item_chuxuka_layout:
                dismiss();
                tbimagmoney.setImageResource(R.drawable.chuxuka2);
                tbnotecash.setText("储蓄卡");
                break;

            case R.id.item_xinyongka_layout:
                dismiss();
                tbimagmoney.setImageResource(R.drawable.xinyongka2);
                tbnotecash.setText("信用卡");
                break;

                default:
                    break;

        }
    }
}
