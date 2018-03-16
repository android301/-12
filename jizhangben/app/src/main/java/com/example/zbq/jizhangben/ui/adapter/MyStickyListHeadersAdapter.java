package com.example.zbq.jizhangben.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.DayListBean;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.stickyHeader.StickyHeaderGridAdapter;
import com.example.zbq.jizhangben.ui.view.SwipeMenuView;

import java.util.ArrayList;



/**
 * Created by zbq on 18-1-30.
 */


public class MyStickyListHeadersAdapter extends StickyHeaderGridAdapter {
    private Context mContext;
    private ArrayList<DayListBean> arrayList;
    private LayoutInflater inflater;
    private OnStickyHeaderClickListener onStickyHeaderClickListener;

    public void setDatas(ArrayList<DayListBean> arrayList){
        this.arrayList=arrayList;
    }

    public MyStickyListHeadersAdapter(Context context, ArrayList<DayListBean> arrayList){
        this.mContext=context;
        this.arrayList=arrayList;
        inflater=LayoutInflater.from(mContext);

    }

    public void setOnStickyHeaderClickListener(OnStickyHeaderClickListener listener) {
        if (onStickyHeaderClickListener == null)
            this.onStickyHeaderClickListener = listener;
    }

    /**
     * 自定义编辑、删除接口
     */
    public interface OnStickyHeaderClickListener {
        void OnDeleteClick(int id, int section, int offset);
        void OnEditClick(JiZhangBean item, int section, int offset);
    }


    @Override
    public int getSectionCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return (arrayList == null||arrayList.get(section).getList()==null) ? 0 : arrayList.get(section).getList().size();
    }
    @Override
    public StickyHeaderGridAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        View view=inflater.inflate(R.layout.item_list_header,parent,false);
        return new HeaderViewHolder(view);
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        View view=inflater.inflate(R.layout.item_list_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(StickyHeaderGridAdapter.HeaderViewHolder viewHolder, int section) {
        final HeaderViewHolder headerViewHolder=(HeaderViewHolder)viewHolder;

        headerViewHolder.textDate.setText(arrayList.get(section).getDay());
        headerViewHolder.textMoney.setText("支出:"+arrayList.get(section).getDay_out()+"  收入:"+arrayList.get(section).getDay_in());

    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int section, final int position) {
        final ViewHolder holder=(ViewHolder)viewHolder;

        holder.imageView.setImageDrawable(arrayList.get(section).getList().get(position).getPic());
        holder.textWrite.setText(arrayList.get(section).getList().get(position).getWrite());
        holder.textMoney.setText(String.valueOf(arrayList.get(section).getList().get(position).getMoney()));
        //监听侧滑删除事件
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());

                //确认删除
                new AlertDialog.Builder(mContext).setTitle("是否删除此条记录")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onStickyHeaderClickListener.OnDeleteClick(
                                        arrayList.get(section).getList().get(offset).get_id(), section, position);
                            }
                        })
                        .show();
            }
        });

    }

    public class HeaderViewHolder extends StickyHeaderGridAdapter.HeaderViewHolder{
        private TextView textDate;
        private TextView textMoney;

        HeaderViewHolder(View view){
            super(view);
            textDate=view.findViewById(R.id.header_date);
            textMoney=view.findViewById(R.id.header_money);
        }

    }

    public class ViewHolder extends ItemViewHolder{
        private ImageView imageView;
        private TextView textWrite;
        private TextView textMoney;
        private Button item_delete;
        private RelativeLayout item_layout;
        private SwipeMenuView mSwipeMenuView;

        ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.item_img);
            textWrite=view.findViewById(R.id.item_title);
            textMoney=view.findViewById(R.id.item_money);
            item_delete = view.findViewById(R.id.item_delete);
            item_layout = view.findViewById(R.id.item_layout);
            mSwipeMenuView =view.findViewById(R.id.swipe_menu);
        }
    }
}
