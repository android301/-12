package com.example.zbq.jizhangben.ui.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.MyApplication;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

/**
 * Created by zbq on 18-2-4.
 */

public class PieChartUtils {
    /**
     * 初始化饼状图
     *
     * @param mChart
     */
    public static void initPieChart(PieChart mChart) {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(3, 2, 3, 2);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);
        // add a selection listener
        //mChart.spin(2000, 0, 360, Easing.EasingOption.valueOf());
        Legend l = mChart.getLegend();
        l.setEnabled(false);
    }

    /**
     * 设置饼状图数据
     *
     * @param mChart
     * @param entries
     * @param colors
     */
    public static void setPieChartData(PieChart mChart, ArrayList<PieEntry> entries, ArrayList<Integer> colors) {

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(true);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 1));
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.TRANSPARENT);
        // data.setValueTypeface(mTfLight);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);


        setStartAngle(mChart);
        setAnimate(mChart);
    }

    /**
     * 设置初始角度
     *
     * @param mChart
     */
    public static void setStartAngle(PieChart mChart) {
        //设置初始角度
        float[] mDrawAngles = mChart.getDrawAngles();
        float offset = mDrawAngles[0] / 2;
        mChart.setRotationAngle(90 - offset);
    }


    /**
     * 设置初始旋转动画
     *
     * @param mChart
     */
    public static void setAnimate(PieChart mChart) {
        //设置动画
        mChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);
        mChart.invalidate();
    }

    private int[] images = {
            R.drawable.sort_lingshi, R.drawable.sort_yundong,
            R.drawable.sort_lvxing, R.drawable.sort_meirong, R.drawable.sort_gouwu,
            R.drawable.sort_shuma, R.drawable.sort_yule, R.drawable.sort_zhufang,
            R.drawable.sort_weixiu, R.drawable.sort_tianjiade, R.drawable.sort_yiliao,
            R.drawable.sort_tianjia};

    public static Drawable getTypeDrawable(String type) {
        Drawable drawable = null;
        switch (type) {
            case "办公":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_bangong);
                break;

            case "餐饮":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_canyin);
                break;

            case "偿还":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_changhuanfeiyong);
                break;

            case "宠物":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_chongwu);
                break;

            case "通讯":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_tongxun);
                break;

            case "学习":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_xuexi);
                break;

            case "服饰":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_fuzhuang);
                break;

            case "育儿":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_haizi);
                break;

            case "长辈":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_zhangbei);
                break;

            case "交通":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_jiaotong);
                break;

            case "水果":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_shuiguo);
                break;

            case "烟酒":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_jiushuiyinliao);
                break;

            case "捐赠":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_juanzeng);
                break;

            case "居家":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_jujia);
                break;

            case "礼金":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_lijin);
                break;

            case "礼物":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_liwu);
                break;

            case "零食":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_lingshi);
                break;

            case "健身":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_yundong);
                break;

            case "旅行":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_lvxing);
                break;

            case "美容":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_meirong);
                break;

            case "商场":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_gouwu);
                break;

            case "数码":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_shuma);
                break;

            case "娱乐":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_yule);
                break;

            case "住房":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_zhufang);
                break;

            case "维修":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_weixiu);
                break;

            case "杂项":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_tianjiade);
                break;

            case "医疗":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_yiliao);
                break;

            case "工资":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_jiaxi);
                break;

            case "基金":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_juanzeng);
                break;

            case "其他":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_fanxian);
                break;

            case "兼职":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_jianzhi);
                break;

            case "利息":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_lixi);
                break;

            case "股票":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.type_shouxufei);
                break;
        }
        return drawable;
    }

    public static Drawable getTypeDrawables(String type) {
        Drawable drawable = null;
        switch (type) {
            case "办公":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_bangong);
                break;

            case "餐饮":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_canyin);
                break;

            case "偿还":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_huankuan);
                break;

            case "宠物":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_chongwu);
                break;

            case "通讯":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_tongxun);
                break;

            case "学习":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_xuexi);
                break;

            case "服饰":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_fanxian);
                break;

            case "育儿":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_haizi);
                break;

            case "长辈":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_zhangbei);
                break;

            case "交通":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_jiaotong);
                break;

            case "水果":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_shuiguo);
                break;

            case "烟酒":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_jiushui);
                break;

            case "捐赠":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_juanzeng);
                break;

            case "居家":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_jujia);
                break;

            case "礼金":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_lijin);
                break;

            case "礼物":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_liwu);
                break;

            case "零食":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_lingshi);
                break;

            case "健身":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_yundong);
                break;

            case "旅行":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_lvxing);
                break;

            case "美容":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_meirong);
                break;

            case "商场":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_gouwu);
                break;

            case "数码":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_shuma);
                break;

            case "娱乐":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_yule);
                break;

            case "住房":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_zhufang);
                break;

            case "维修":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_weixiu);
                break;

            case "杂项":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_tianjiade);
                break;

            case "医疗":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_yiliao);
                break;

            case "工资":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_jiangjin);
                break;

            case "基金":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_tianjiade);
                break;

            case "其他":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_yiban);
                break;

            case "兼职":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_jianzhi);
                break;

            case "利息":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_lixi);
                break;

            case "股票":
                drawable = MyApplication.application.getResources().getDrawable(R.drawable.sort_shouxufei);
                break;
        }
        return drawable;
    }


    public static void setRotationAngle(PieChart mChart, int entryIndex) {

        float[] mDrawAngles = mChart.getDrawAngles();

        //初始角度
        float inAngle = 90 - mDrawAngles[0] / 2;
        switch (entryIndex) {
            case 0:
                mChart.setRotationAngle(inAngle);
                break;
            case 1:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] / 2));
                break;
            case 2:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] / 2));
                break;
            case 3:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] / 2));
                break;
            case 4:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] / 2));
                break;
            case 5:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] / 2));
                break;
            case 6:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] / 2));
                break;
            case 7:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] / 2));
                break;
            case 8:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] / 2));
                break;
            case 9:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] / 2));
                break;
            case 10:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] / 2));
                break;
            case 11:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] / 2));
                break;
            case 12:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] / 2));
                break;
            case 13:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] + mDrawAngles[13] / 2));
                break;
            case 14:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] + mDrawAngles[13] + mDrawAngles[14] / 2));
                break;
            case 15:
                mChart.setRotationAngle(inAngle - (mDrawAngles[0] / 2 + mDrawAngles[1] + mDrawAngles[2] + mDrawAngles[3] + mDrawAngles[4] + mDrawAngles[5] + mDrawAngles[6] + mDrawAngles[7] + mDrawAngles[8] + mDrawAngles[9] + mDrawAngles[10] + mDrawAngles[11] + mDrawAngles[12] + mDrawAngles[13] + mDrawAngles[14] + mDrawAngles[15] / 2));
                break;
            default:
                break;
        }
        mChart.invalidate();
    }
}

