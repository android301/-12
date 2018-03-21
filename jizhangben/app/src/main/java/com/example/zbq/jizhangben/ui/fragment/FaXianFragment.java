package com.example.zbq.jizhangben.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.NewsBean;
import com.example.zbq.jizhangben.ui.Dao.OutInMoneyDB;
import com.example.zbq.jizhangben.ui.activity.BianJiYuSuanPopup;
import com.example.zbq.jizhangben.ui.activity.BianLiTieActivity;
import com.example.zbq.jizhangben.ui.activity.KeChengBiaoActivity;
import com.example.zbq.jizhangben.ui.activity.NewsDisplayActivity;
import com.example.zbq.jizhangben.ui.adapter.QuanBuAdapter;
import com.example.zbq.jizhangben.ui.adapter.ReDianAdapter;
import com.example.zbq.jizhangben.ui.view.CircleProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by zbq on 18-3-14.
 */

public class FaXianFragment extends BaseFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.grid_item)
    GridView gridView;
    @BindView(R.id.bt_yusuan)
    Button btYusuan;

    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_bianjiyusuan)
    TextView tvBianjiyusuan;
    @BindView(R.id.cicle_pro_bar)
    CircleProgressBar cicleProBar;
    @BindView(R.id.tv_shengyu)
    TextView tvShengyu;
    @BindView(R.id.tv_yusuan)
    TextView tvYusuan;
    @BindView(R.id.tv_zhichu)
    TextView tvZhichu;
    @BindView(R.id.ll_yusuan)
    LinearLayout llYusuan;
    @BindView(R.id.ll_redian)
    ListView llRedian;
    @BindView(R.id.swiprl)
    SwipeRefreshLayout swiprl;
    Unbinder unbinder1;

    private ProgressBar pgBar;

    private QuanBuAdapter adapter;
    private ReDianAdapter reDianAdapter;
    private OutInMoneyDB db;
    private String date;

    private ArrayList<NewsBean> newsList = new ArrayList<>();

    private BianJiYuSuanPopup popup;
    private Handler mHandler;

    int visibleItemcount;
    int visibleLastIndex;
    float out;

    private String yusuan;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu_faxian;
    }


    @Override
    protected void initEventAndData() {
        db = new OutInMoneyDB(getActivity());
        adapter = new QuanBuAdapter(getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        btYusuan.setOnClickListener(this);
        tvBianjiyusuan.setOnClickListener(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        date = sdf.format(new Date());

        //第一次进入时判断当月是否有支出，若有则显示，若没有则显示设置预算按钮
        out = db.getMonthOut(date);
        Log.v("out",String.valueOf(out));
        if (out==0){
            showButton();

        }else {
            yusuan=tvYusuan.getText().toString();
            //从数据库查找本月支出
            tvZhichu.setText(String.valueOf(out - 2 * out));//除去符号
            tvShengyu.setText(String.valueOf(Float.parseFloat(yusuan) + out));
            //设置进度最大值
            cicleProBar.setMax(Integer.parseInt(yusuan));
            //设置进度
            cicleProBar.setProgress((int) (out - 2 * out));

        }
        getNews();//获取网页新闻

        swiprl.setOnRefreshListener(this);
        //设置刷新颜色
        swiprl.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置向下拉多少出现刷新
        swiprl.setDistanceToTriggerSync(200);
        //设置刷新出现的位置
        swiprl.setProgressViewEndTarget(false, 200);


        View bottomView = getLayoutInflater().inflate(R.layout.progressbar, null);
        pgBar = bottomView.findViewById(R.id.pg_bar);

        llRedian.addFooterView(bottomView);   //设置列表底部视图

        //切换到主线程更新UI
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        if (reDianAdapter == null) {
                            reDianAdapter = new ReDianAdapter(getActivity(), newsList);
                            llRedian.setAdapter(reDianAdapter);
                            llRedian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    NewsBean news = newsList.get(position);
                                    Intent intent = new Intent(getActivity(), NewsDisplayActivity.class);
                                    intent.putExtra("news_url", news.getNewsUrl());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            reDianAdapter.setNews(newsList);
                            reDianAdapter.notifyDataSetChanged();
                        }

                        break;
                }
                return false;
            }
        });

        /**
         * 监听，滑动到底部时请求数据
         */
        llRedian.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int itemsLastIndex = reDianAdapter.getCount() - 1;    //数据集最后一项的索引
                int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
                    //在这里放置异步加载数据的代码
                    Log.i("LOADMORE", "loading...");
                    getNews();

                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                visibleItemcount = visibleItemCount;
                visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

                //解决滑动冲突
                if (firstVisibleItem == 0)
                    swiprl.setEnabled(true);
                else
                    swiprl.setEnabled(false);
            }
        });

    }

    public void showButton() {
        llYusuan.setVisibility(View.GONE);
        btYusuan.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_yusuan:
                showdialog();

                break;

            case R.id.tv_bianjiyusuan:
                showPopuWindow();

                break;
        }
    }

    public void showdialog() {
        final EditText editText = new EditText(getActivity());

        new AlertDialog.Builder(getActivity())
                .setTitle("设置预算")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获得输入框的内容
                        yusuan = editText.getText().toString();
                        llYusuan.setVisibility(View.VISIBLE);
                        btYusuan.setVisibility(View.GONE);
                        tvYusuan.setText(yusuan);
                        //从数据库查找本月支出
                        //float out = db.getMonthOut(date);
                        tvZhichu.setText(String.valueOf(out - 2 * out));//除去符号
                        tvShengyu.setText(String.valueOf(Float.parseFloat(yusuan) + out));
                        //设置进度最大值
                        cicleProBar.setMax(Integer.parseInt(yusuan));
                        //设置进度
                        cicleProBar.setProgress((int) (out - 2 * out));
                    }
                }).setNegativeButton("取消", null).create()
                .show();
    }

    private void showPopuWindow() {
        popup=new BianJiYuSuanPopup(getActivity(),btYusuan,llYusuan,out,tvYusuan,tvShengyu,tvZhichu,cicleProBar);

        //显示窗口
        popup.showAtLocation(LayoutInflater.from(
                getActivity()).inflate(R.layout.popup_bianjiyusuan,null),
                Gravity.CENTER, 0,0);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0://課程表
                Intent kechengbiao = new Intent(getActivity(), KeChengBiaoActivity.class);
                startActivity(kechengbiao);
                break;
            case 1://便利贴
                Intent bianlitie = new Intent(getActivity(), BianLiTieActivity.class);
                startActivity(bianlitie);
                break;

        }
    }

    static int i;

    //利用Jsoup爬取秋成理财社区网页数据
    public void getNews() {
        //由于是网络请求,必须放到子线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //爬取主页的详情页url
                    Document doc = Jsoup.connect("http://bbs.qiuchenglicai.com/portal.php?&page=" + i).get();
                    //获取每条新闻的标题及链接Integer.toString(i))
                    Elements titlelink = doc.select(".recommend_article_list_content");
                    Elements contentlink = doc.select(".recommend_article_list_simple");
                    Elements timelink = doc.select(".recommend_article_list_info");
                    for (int j = 0; j < titlelink.size(); j++) {

                        String url = "http://bbs.qiuchenglicai.com/" + titlelink.get(j).select(".list_title").select("a").attr("href");
                        String title = titlelink.get(j).select(".list_title").select("a").text();
                        String content = contentlink.get(j).text();
                        String author = timelink.get(j).select(".colorlink").text();
                        String time = timelink.get(j).text();
                        String source = timelink.get(j).select(".mr10").text();
                        String num = timelink.get(j).select(".icon16link").text();
                        NewsBean bean = new NewsBean(title, url, content, time, author, source, num);
                        newsList.add(bean);
                        i++;

                        //Log.v("链接", url);
                        //Log.v("标题", title);
                        //Log.v("内容", content);
                        //Log.v("作者", author);
                        //Log.v("时间", time);
                        //Log.v("来源", source);
                        //Log.v("阅读数量", num);
                        //Log.v("size", String.valueOf(newsList.size()));

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //在子线程发送一个消息。
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }).start();
    }


    @Override
    public void onRefresh() {
        //结束后停止刷新
        swiprl.setRefreshing(false);
        //从数据库查找本月支出
        float out = db.getMonthOut(date);
        tvZhichu.setText(String.valueOf(out - 2 * out));//除去符号
        tvShengyu.setText(String.valueOf(Float.parseFloat(yusuan) + out));
        //设置进度
        cicleProBar.setProgress((int) (out - 2 * out));

    }


}
