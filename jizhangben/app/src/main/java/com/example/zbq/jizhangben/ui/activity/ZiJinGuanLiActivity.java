package com.example.zbq.jizhangben.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Dao.OutInMoneyDB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zbq on 18-3-13.
 */

public class ZiJinGuanLiActivity extends AppCompatActivity {
    @BindView(R.id.tv_jingzichan)
    TextView tvJingzichan;
    @BindView(R.id.cash_out)
    TextView cashOut;
    @BindView(R.id.cash_in)
    TextView cashIn;
    @BindView(R.id.ll_pane)
    LinearLayout llPane;
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
    @BindView(R.id.img_viewor)
    ImageView imgViewor;
    @BindView(R.id.tv)
    TextView tv;

    private OutInMoneyDB outInMoneyDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zijinguanli);
        ButterKnife.bind(this);
        outInMoneyDB = new OutInMoneyDB(this);
        itemXianjinMoney.setText(String.valueOf(outInMoneyDB.getStyleTotal("现金")));
        itemZhifubaoMoney.setText(String.valueOf(outInMoneyDB.getStyleTotal("支付宝")));
        itemWeixinMoney.setText(String.valueOf(outInMoneyDB.getStyleTotal("微信")));
        itemChuxukaMoney.setText(String.valueOf(outInMoneyDB.getStyleTotal("储蓄卡")));
        itemXinyongkaMoney.setText(String.valueOf(outInMoneyDB.getStyleTotal("信用卡")));
        float out = 0, in = 0;
        out = outInMoneyDB.getOutTotal();
        in = outInMoneyDB.getInTotal();
        cashOut.setText(String.valueOf(out));
        cashIn.setText(String.valueOf(in));
        tvJingzichan.setText(String.valueOf(in + out));
    }

    @OnClick({R.id.item_xianjin_layout, R.id.item_weixin_layout,
            R.id.item_zhifubao_layout, R.id.item_chuxuka_layout,
            R.id.item_xinyongka_layout,R.id.img_viewor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_xianjin_layout:
                break;
            case R.id.item_weixin_layout:
                break;
            case R.id.item_zhifubao_layout:
                break;
            case R.id.item_chuxuka_layout:
                break;
            case R.id.item_xinyongka_layout:
                break;
            case R.id.img_viewor:
                setMoneyVisible();
                break;
        }
    }
    /**
     * 设置金额是否可见
     */
    private boolean isPwdVisible=true;//默认可见
    private void setMoneyVisible() {

                //修改密码是否可见的状态
                isPwdVisible = !isPwdVisible;
                //設置密碼是否可見
                if (isPwdVisible) {
                    //设置密码为明文，并更改眼睛图标
                    itemXinyongkaMoney.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    itemZhifubaoMoney.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    itemChuxukaMoney.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    itemWeixinMoney.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    itemXianjinMoney.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    tvJingzichan.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    cashIn.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    cashOut.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    imgViewor.setImageResource(R.drawable.yanjing);
                } else {
                    //设置密码为暗文，并更改眼睛图标
                    itemXinyongkaMoney.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    itemZhifubaoMoney.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    itemChuxukaMoney.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    itemWeixinMoney.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    itemXianjinMoney.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    tvJingzichan.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    cashIn.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    cashOut.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    imgViewor.setImageResource(R.drawable.biyanjing);
                }

    }

}
