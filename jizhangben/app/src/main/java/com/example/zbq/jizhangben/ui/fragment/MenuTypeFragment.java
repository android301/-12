package com.example.zbq.jizhangben.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.Bean.TypeRankBean;
import com.example.zbq.jizhangben.ui.Dao.OutInMoneyDB;
import com.example.zbq.jizhangben.ui.activity.MainActivity;
import com.example.zbq.jizhangben.ui.activity.SearchActivity;
import com.example.zbq.jizhangben.ui.adapter.TypeRankAdapter;
import com.example.zbq.jizhangben.ui.utils.DateUtils;
import com.example.zbq.jizhangben.ui.utils.FormatUtils;
import com.example.zbq.jizhangben.ui.utils.PieChartUtils;
import com.example.zbq.jizhangben.ui.view.CircleImageView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by zbq on 18-1-14.
 */

public class MenuTypeFragment extends BaseFragment
        implements OnChartValueSelectedListener ,View.OnClickListener{

    @BindView(R.id.data_year)
    TextView dataYear;
    @BindView(R.id.data_month)
    TextView dataMonth;
    @BindView(R.id.layout_data)
    LinearLayout layoutData;
    @BindView(R.id.cash_out)
    TextView cashOut;
    @BindView(R.id.cash_in)
    TextView cashIn;
    @BindView(R.id.cash_surplus)
    TextView cashSurplus;
    @BindView(R.id.chart)
    PieChart mChart;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.center_money)
    TextView centerMoney;
    @BindView(R.id.center_img)
    ImageView centerImg;
    @BindView(R.id.layout_center)
    LinearLayout layoutCenter;
    @BindView(R.id.circle_bg)
    CircleImageView circleBg;
    @BindView(R.id.circle_img)
    ImageView circleImg;
    @BindView(R.id.layout_circle)
    RelativeLayout layoutCircle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.item_type)
    RelativeLayout itemType;
    @BindView(R.id.rank_title)
    TextView rankTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.layout_typedata)
    LinearLayout layoutTypedata;
    @BindView(R.id.right_btn)
    ImageView rightBtn;

    private boolean TYPE = true;//默认总支出true
    private int report_type = 0;//饼状图与之相对应的类型
    private String type_name;
    private String back_color;
    private OutInMoneyDB outInMoneyDB;
    private float month_in;
    private float month_out;
    private String detail;
    private TypeRankAdapter adapter;
    private ArrayList<JiZhangBean> arrayList;
    private ArrayList<TypeRankBean.TMoneyBean> lists;
    private TypeRankBean typeRankBean;
    private String[] texts_out = new String[]{
            "办公","餐饮","偿还",
            "宠物","通讯","学习",
            "服饰","育儿","长辈",
            "交通","水果","烟酒",
    };
    private String[] texts_in = new String[]{
            "工资", "基金", "其他",
            "兼职", "利息", "礼金",
            "股票"
    };
    private String[] out_color=new String[]{
            "#FFA500","#FFD700","#FF8C00",
            "#FFA500","#FFD700","#FF8C00",
            "#FFA500","#FFD700","#FF8C00",
            "#FFA500","#FFD700","#FF8C00"
    };
    private String[] in_color=new String[]{
            "#6495ED","#B0C4DE","#6495ED","#B0C4DE",
            "#1E90FF","#6495ED","#1E90FF",
            "#6495ED","#B0C4DE","#6495ED","#B0C4DE",
            "#1E90FF","#0000FF","#1E90FF"
    };
    @Override
    protected int getLayoutId() {
        return R.layout.frgment_menu_type;
    }

    @Override
    protected void initEventAndData() {
        rightBtn.setOnClickListener(this);
        PieChartUtils.initPieChart(mChart);
        mChart.setOnChartValueSelectedListener(this);

        //改变加载显示的颜色
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));
        //设置向下拉多少出现刷新
        swipe.setDistanceToTriggerSync(200);
        //设置刷新出现的位置
        swipe.setProgressViewEndTarget(false, 200);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                PieChartUtils.setAnimate(mChart);
            }
        });

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayList = new ArrayList<>();
        adapter = new TypeRankAdapter(getActivity(), arrayList);
        rvList.setAdapter(adapter);

        setReportData();

    }

    private void setReportData() {
        typeRankBean=new TypeRankBean();
        lists=new ArrayList<>();
        detail=dataYear.getText()+"-"+dataMonth.getText();//"2018-01"格式
        outInMoneyDB = new OutInMoneyDB(getActivity());

        //查询该月总支出并显示
        month_out=outInMoneyDB.getMonthOut(detail);
        cashOut.setText(String.valueOf(month_out));

        //查询该月总收入并显示
        month_in=outInMoneyDB.getMonthIn(detail);
        cashIn.setText(String.valueOf(month_in));

        //结余
        cashSurplus.setText(String.valueOf(month_in+month_out));

        if (TYPE){
            centerTitle.setText("总支出");
            centerImg.setImageResource(R.drawable.tallybook_output);
            centerMoney.setText(String.valueOf(month_out));
            arrayList=outInMoneyDB.dbQueryAsc(detail,"支出");
            typeRankBean.setTotal(month_out);
            typeRankBean.setT_money(getsData("支出",texts_out));

            rankTitle.setText("支出排行榜");
            adapter.setmDatas(arrayList);
            adapter.notifyDataSetChanged();

        } else {
            centerTitle.setText("总收入");
            centerImg.setImageResource(R.drawable.tallybook_input);
            centerMoney.setText(String.valueOf(month_in));
            arrayList=outInMoneyDB.dbQueryDesc(detail,"收入");
            typeRankBean.setTotal(month_in);
            typeRankBean.setT_money(getsData("收入",texts_in));

            rankTitle.setText("收入排行榜");
            adapter.setmDatas(arrayList);
            adapter.notifyDataSetChanged();

        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (lists != null && lists.size() > 0) {
            layoutTypedata.setVisibility(View.VISIBLE);
            for (int i = 0; i < lists.size(); i++) {
                float scale = lists.get(i).getMoney() / typeRankBean.getTotal();
                float value = (scale < 0.1f) ? 0.1f : scale;
                entries.add(new PieEntry(value, PieChartUtils.getTypeDrawable(lists.get(i).getType())));
                if (centerTitle.getText().equals("总收入")) {
                    colors.add(Color.parseColor(in_color[i]));
                }else {
                    colors.add(Color.parseColor(out_color[i]));
                }
            }
            setNoteData(0);
        } else {
            //无数据时的显示
            layoutTypedata.setVisibility(View.GONE);
            entries.add(new PieEntry(1f));
            colors.add(0xffAAAAAA);
        }

        PieChartUtils.setPieChartData(mChart, entries, colors);
    }


    private List<TypeRankBean.TMoneyBean> getsData(String type,String[] texts) {
        float allmoney;
        for (int i=0;i<texts.length;i++){
            TypeRankBean.TMoneyBean bean=new TypeRankBean.TMoneyBean();
            bean.setType(texts[i]);
            //查询该月该类型支出/收入总金额
            allmoney=outInMoneyDB.dbQueryAll(detail,type,texts[i]);
            bean.setMoney(allmoney);
            lists.add(bean);
        }
        return lists;

    }


    /**
     * 点击饼状图上区域后相应的数据设置
     * @param index
     */
    private void setNoteData(int index){
        type_name = lists.get(index).getType();
        if (TYPE) {
            money.setText("-"+ getMoneyStr(lists.get(index).getMoney()));
            circleImg.setBackgroundColor(Color.parseColor(out_color[index]));
        }else {
            money.setText("+"+ getMoneyStr(lists.get(index).getMoney()));
            circleImg.setBackgroundColor(Color.parseColor(in_color[index]));
        }
        title.setText(type_name);

        circleImg.setImageDrawable(PieChartUtils.getTypeDrawables(type_name));


    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        int entryIndex = (int) h.getX();
        PieChartUtils.setRotationAngle(mChart, entryIndex);
        setNoteData(entryIndex);
    }

    @Override
    public void onNothingSelected() {

    }


    @OnClick({R.id.layout_center, R.id.layout_data, R.id.item_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_center:

                if(TYPE){
                    TYPE = false;
                }else{
                    TYPE = true;
                }
                setReportData();

                break;
            case R.id.layout_data:
                //时间选择器
                new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        dataYear.setText(DateUtils.date2Str(date, "yyyy"));
                        dataMonth.setText(DateUtils.date2Str(date, "MM"));

                        PieChartUtils.setAnimate(mChart);
                    }
                })
                        .setRangDate(null, Calendar.getInstance())
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .build()
                        .show();
                break;
            case R.id.item_type:
                break;
        }
    }

    private String getMoneyStr(double money) {
        return FormatUtils.MyDecimalFormat("##,###,###.##", money);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_btn:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
