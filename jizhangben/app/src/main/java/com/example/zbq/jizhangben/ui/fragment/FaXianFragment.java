package com.example.zbq.jizhangben.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.activity.KeChengBiaoActivity;
import com.example.zbq.jizhangben.ui.adapter.QuanBuAdapter;
import com.example.zbq.jizhangben.ui.view.RecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zbq on 18-3-14.
 */

public class FaXianFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    @BindView(R.id.grid_item)
    GridView gridView;
    private QuanBuAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu_faxian;
    }

    @Override
    protected void initEventAndData() {
        adapter=new QuanBuAdapter(getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0://課程表
                Intent intent=new Intent(getActivity(),KeChengBiaoActivity.class);
                startActivity(intent);
                break;

        }
    }
}
