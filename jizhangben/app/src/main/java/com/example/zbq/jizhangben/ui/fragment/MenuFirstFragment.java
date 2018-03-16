package com.example.zbq.jizhangben.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.DayListBean;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.Dao.OutInMoneyDB;
import com.example.zbq.jizhangben.ui.activity.TiXingActivity;
import com.example.zbq.jizhangben.ui.adapter.MyStickyListHeadersAdapter;
import com.example.zbq.jizhangben.ui.stickyHeader.StickyHeaderGridLayoutManager;
import com.example.zbq.jizhangben.ui.utils.DateUtils;
import com.example.zbq.jizhangben.ui.utils.PieChartUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by zbq on 18-1-14.
 */

public class MenuFirstFragment extends BaseFragment implements View.OnClickListener{
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
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.right_btn)
    ImageView rightBtn;

    Unbinder unbinder;
    private OutInMoneyDB outInMoneyDB;
    private ArrayList<DayListBean.ListBean> arrayList;
    private ArrayList<DayListBean> list;
    private StickyHeaderGridLayoutManager mLayoutManager;
    private MyStickyListHeadersAdapter adapter;
    private float month_in;
    private float month_out;

    private final static int SPAN_SIZE=1;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu_first;
    }


    @Override
    protected void initEventAndData() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_menu_first, null);
        layoutData.setOnClickListener(this);//点击弹出日期选择框
        rightBtn.setOnClickListener(this);

        String detail=dataYear.getText()+"-"+dataMonth.getText();//"2018-01"格式
        outInMoneyDB = new OutInMoneyDB(getActivity());
        //从数据库中查询月记账数据
        arrayList = outInMoneyDB.dbQueryAll(detail);
        list=getDatas(arrayList);

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
                String detail=dataYear.getText()+"-"+dataMonth.getText();//"2018-01"格式
                //从数据库中查询月记账数据
                arrayList = outInMoneyDB.dbQueryAll(detail);
                list=getDatas(arrayList);
                adapter.setDatas(list);
                adapter.notifyAllSectionsDataSetChanged();
                //查询该月所有数据并显示
                arrayList=outInMoneyDB.dbQueryAll(detail);
                list=getDatas(arrayList);
                adapter.setDatas(list);
                adapter.notifyAllSectionsDataSetChanged();

                //查询该月总支出并显示
                month_out=outInMoneyDB.getMonthOut(detail);
                cashOut.setText(String.valueOf(month_out));

                //查询该月总收入并显示
                month_in=outInMoneyDB.getMonthIn(detail);
                cashIn.setText(String.valueOf(month_in));

                //结余
                cashSurplus.setText(String.valueOf(month_in+month_out));
            }
        });

        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(5);

        // Workaround RecyclerView limitation when playing remove animations. RecyclerView always
        // puts the removed item on the top of other views and it will be drawn above sticky header.
        // The only way to fix this, abandon remove animations :(
        rvList.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        rvList.setLayoutManager(mLayoutManager);
        adapter = new MyStickyListHeadersAdapter(mContext,list);
        rvList.setAdapter(adapter);
        String details=dataYear.getText()+"-"+dataMonth.getText();//"2018-01"格式
        //查询该月所有数据并显示
        arrayList=outInMoneyDB.dbQueryAll(details);
        list=getDatas(arrayList);

        adapter.setDatas(list);
        adapter.notifyAllSectionsDataSetChanged();

        //adapter的侧滑选项事件监听
        adapter.setOnStickyHeaderClickListener(new MyStickyListHeadersAdapter.OnStickyHeaderClickListener() {
            @Override
            public void OnDeleteClick(int id, int section, int offset) {
                Log.v("nnn",String.valueOf(id));
                //删除该条记录
                outInMoneyDB.delete(id);
                String detail=dataYear.getText()+"-"+dataMonth.getText();//"2018-01"格式
                outInMoneyDB = new OutInMoneyDB(getActivity());
                //从数据库中查询月记账数据
                arrayList = outInMoneyDB.dbQueryAll(detail);
                list=getDatas(arrayList);
                adapter.setDatas(list);
                adapter.notifyAllSectionsDataSetChanged();
                //查询该月总支出并显示
                month_out=outInMoneyDB.getMonthOut(detail);
                cashOut.setText(String.valueOf(month_out));

                //查询该月总收入并显示
                month_in=outInMoneyDB.getMonthIn(detail);
                cashIn.setText(String.valueOf(month_in));

                //结余
                cashSurplus.setText(String.valueOf(month_in+month_out));
            }

            @Override
            public void OnEditClick(JiZhangBean item, int section, int offset) {

            }
        });

        //查询该月总支出并显示
        month_out=outInMoneyDB.getMonthOut(details);
        cashOut.setText(String.valueOf(month_out));

        //查询该月总收入并显示
        month_in=outInMoneyDB.getMonthIn(details);
        cashIn.setText(String.valueOf(month_in));

        //结余
        cashSurplus.setText(String.valueOf(month_in+month_out));
    }

    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2015, 0,1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2018, 11,31);
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                //获取选中日期
                //选择日期早于now
                //时间戳格式
                String dates = String.valueOf(date.getTime());
                //时间戳转化为时间
                String ss = DateUtils.stampToMonth(dates);
                try {
                    String textYear=DateUtils.exchangeStringYear(ss);
                    String textMonth=DateUtils.exchangeStringMonth(ss);
                    dataYear.setText(textYear);
                    dataMonth.setText(textMonth);

                    //查询该月所有数据并显示
                    arrayList=outInMoneyDB.dbQueryAll(ss);
                    list=getDatas(arrayList);
                    adapter.setDatas(list);
                    adapter.notifyAllSectionsDataSetChanged();

                    //查询该月总支出并显示
                    month_out=outInMoneyDB.getMonthOut(ss);
                    cashOut.setText(String.valueOf(month_out));

                    //查询该月总收入并显示
                    month_in=outInMoneyDB.getMonthIn(ss);
                    cashIn.setText(String.valueOf(month_in));

                    //结余
                    cashSurplus.setText(String.valueOf(month_in+month_out));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月",null,null,null,null)//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label
                .build();
        pvTime.show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_data:
                //时间选择器
                initLunarPicker();
                break;
            case R.id.right_btn:
                Intent intent=new Intent(getActivity(), TiXingActivity.class);
                startActivity(intent);
                break;
        }
    }

    public ArrayList<DayListBean> getDatas(List<DayListBean.ListBean> details) {
        //最后我们要返回带有分组的list,初始化
        ArrayList<DayListBean> list = new ArrayList<>();
        //ListBean作为key是yyyy-MM-dd格式,List<WarnDetail>是对应的值是HH:mm:ss格式
        Map<DayListBean.ListBean, List<DayListBean.ListBean>> map = new HashMap<>();
        //List<DayListBean.ListBean> listBeans=new ArrayList<>();
        // 按照ListBean里面的时间进行分类
        DayListBean.ListBean detail = new DayListBean.ListBean();
        //listBeans.add(detail);
        boolean b;
        String key;
        float outMoney,inMoney;
        for (int i = 0; i < details.size(); i++) {

                 key= details.get(i).getDate();

                if (detail.getDate() != null && !"".equals(detail.getDate())) {
                    //判断这个Key对象有没有生成,保证是唯一对象.如果第一次没有生成,那么new一个对象,之后同组的其他item都指向这个key

                         b= !key.equals(detail.getDate());
                        if (b) {
                            detail = new DayListBean.ListBean();
                            //listBeans.add(detail);

                    }
                }
                detail.setDate(key);
                List<DayListBean.ListBean> warnDetails = map.get(detail);
                //判断这个key对应的值有没有初始化,若第一次进来,这new一个arrayList对象,之后属于这一天的item都加到这个集合里面
                if (warnDetails == null) {
                    warnDetails = new ArrayList<>();
                }

                warnDetails.add(details.get(i));
                map.put(detail, warnDetails);

        }

        // 用迭代器遍历map添加到list里面
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            DayListBean.ListBean key2 = (DayListBean.ListBean) entry.getKey();
            //key(yyyy-MM-dd)作为标题
            DayListBean dayListBean=new DayListBean();
            dayListBean.setDay(key2.getDate());
            outMoney=outInMoneyDB.getDayOutTotal(key2.getDate());
            inMoney=outInMoneyDB.getDayInTotal(key2.getDate());
            dayListBean.setDay_out(outMoney);
            dayListBean.setDay_in(inMoney);
            List<DayListBean.ListBean> li = (List<DayListBean.ListBean>) entry.getValue();
            for (int i=0;i<li.size();i++){
                li.get(i).setPic(PieChartUtils.getTypeDrawables(li.get(i).getWay()));
                }
            dayListBean.setList(li);

            list.add(dayListBean);
        }
        // 把分好类的hashMap添加到list里面便于显示
        return list;
    }

}


