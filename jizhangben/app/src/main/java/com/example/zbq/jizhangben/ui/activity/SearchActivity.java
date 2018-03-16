package com.example.zbq.jizhangben.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.Dao.OutInMoneyDB;
import com.example.zbq.jizhangben.ui.adapter.MyListViewAdapter;
import com.example.zbq.jizhangben.ui.searchLayout.BCallback;
import com.example.zbq.jizhangben.ui.searchLayout.SCallback;
import com.example.zbq.jizhangben.ui.searchLayout.SearchListView;
import com.example.zbq.jizhangben.ui.searchLayout.SearchView;
import com.example.zbq.jizhangben.ui.utils.PieChartUtils;

import java.util.ArrayList;

/**
 * Created by zbq on 18-2-23.
 */

public class SearchActivity extends AppCompatActivity {

    //1.初始化搜索框变量
    private SearchView searchView;
    private OutInMoneyDB db;
    private ArrayList<JiZhangBean> list;
    private SearchListView searchListView;
    private TextView textView;
    private LinearLayout linearLayout;
    private ListView listView;
    private MyListViewAdapter adapter;
    private float sum=0;
    private TextView tv_cashout;
    private TextView tv_cashin;
    private TextView tv_surplus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView=findViewById(R.id.search_view);
        tv_cashout=findViewById(R.id.cash_out);
        tv_cashin=findViewById(R.id.cash_in);
        tv_surplus=findViewById(R.id.cash_surplus);
        searchListView=findViewById(R.id.listView);
        textView=findViewById(R.id.tv_clear);
        linearLayout=findViewById(R.id.ll_search);
        listView=findViewById(R.id.ll_lv);

        db=new OutInMoneyDB(SearchActivity.this);

        list=new ArrayList<>();
        adapter=new MyListViewAdapter(SearchActivity.this,list);
        listView.setAdapter(adapter);

        searchView.setOnClickSearch(new SCallback() {
            @Override
            public void SearchAction(String search) {
                //查询某类型总收入/支出
                list=db.wayQuery(search);
                if (list.size()>0){

                    Drawable drawable=PieChartUtils.getTypeDrawables(list.get(0).getWay());

                    for (int i=0;i<list.size();i++){
                        list.get(i).setPic(drawable);
                        sum+=list.get(i).getMoney();
                    }

                    searchListView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);

                    adapter.setDatas(list);
                    adapter.notifyDataSetChanged();
                     if (sum<0){
                        tv_cashout.setText(String.valueOf(sum));
                        tv_surplus.setText(String.valueOf(sum));
                    }else {
                        tv_cashin.setText(String.valueOf(sum));
                        tv_surplus.setText(String.valueOf(sum));
                    }
                    adapter.notifyDataSetChanged();

                }

                else {
                    Toast.makeText(SearchActivity.this,"未检索到数据,请检查检索条件",Toast.LENGTH_SHORT);
                }

            }
        });

        searchView.setOnClickBack(new BCallback() {
            @Override
            public void BackAction() {
                finish();
            }
        });
    }
}
