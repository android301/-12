package com.example.zbq.jizhangben.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;

import java.util.List;

/**
 * Created by zbq on 18-2-4.
 */

public class TypeRankAdapter extends RecyclerView.Adapter<TypeRankAdapter.ViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<JiZhangBean> mDatas;


    public TypeRankAdapter(Context context, List<JiZhangBean> datas){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this. mDatas = datas;
    }


    public void setmDatas(List<JiZhangBean> mDatas) {
        this.mDatas=mDatas;
    }


    @Override
    public int getItemCount() {
        return (mDatas== null) ? 0 : mDatas.size();
    }


    @Override
    public TypeRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tallytype_rank, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TypeRankAdapter.ViewHolder holder, int position) {
        holder.rank.setText(position+1+"");
        holder.title.setText(mDatas.get(position).getWay());
        holder.money.setText(String.valueOf(mDatas.get(position).getMoney()));

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView money;
        private TextView rank;

        public ViewHolder(View view){
            super(view);
            title =  view.findViewById(R.id.title);
            money =  view.findViewById(R.id.money);
            rank =  view.findViewById(R.id.rank);

        }



    }

}
