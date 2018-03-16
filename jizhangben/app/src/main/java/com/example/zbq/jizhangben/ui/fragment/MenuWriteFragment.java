package com.example.zbq.jizhangben.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.activity.MyPopupWindow;
import com.example.zbq.jizhangben.ui.adapter.GrideOutAdapter;
import com.example.zbq.jizhangben.ui.adapter.GridinAdapter;
import com.example.zbq.jizhangben.ui.adapter.MyPagerAdapter;
import com.example.zbq.jizhangben.ui.utils.JiZhangManager;
import com.example.zbq.jizhangben.ui.view.BaseBottomBar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zbq on 18-1-14.
 */

public class MenuWriteFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private GridView gridView1;
    private GridView gridView2;
    private ViewPager viewpager;
    private TextView outcome;
    private TextView income;
    private LinearLayout shuru;

    private GrideOutAdapter grideOutAdapter;
    private GridinAdapter gridinAdapter;
    private List<View> list;
    private MyPagerAdapter myPagerAdapter;

    private View view;
    private View view2;
    private MyPopupWindow popupWindow;

    private JiZhangBean jiZhangBean;

    private ImageView imageView;

    //private String type="支出";//标记是支出还是收入
    //private String way;//标记方式
    @Override
    protected int getLayoutId() {

        return R.layout.fragment_menu_write;
    }

    @Override
    protected void initEventAndData() {
        list=new ArrayList<>();
        viewpager=mView.findViewById(R.id.viewpager);
        shuru=mView.findViewById(R.id.shuru);

        view=LayoutInflater.from(getActivity()).inflate(R.layout.grid_out,null);
        view2=LayoutInflater.from(getActivity()).inflate(R.layout.grid_in,null);
        gridView1=view.findViewById(R.id.grid_out);
        gridView2=view2.findViewById(R.id.grid_in);
        list.add(view);
        list.add(view2);

        outcome=mView.findViewById(R.id.tb_note_outcome);
        outcome.setOnClickListener(this);
        income=mView.findViewById(R.id.tb_note_income);
        income.setOnClickListener(this);

        myPagerAdapter=new MyPagerAdapter(list);
        viewpager.setAdapter(myPagerAdapter);

        grideOutAdapter = new GrideOutAdapter(getActivity());
        gridinAdapter=new GridinAdapter(getActivity());

        gridView1.setAdapter(grideOutAdapter);
        gridView1.setOnItemClickListener(this);

        gridView2.setAdapter(gridinAdapter);
        gridView2.setOnItemClickListener(this);

        imageView=mView.findViewById(R.id.back_btn);
        imageView.setOnClickListener(this);

        viewpager.setCurrentItem(0);
        outcome.setSelected(true);

        popupWindow=new MyPopupWindow(getActivity());

        jiZhangBean=JiZhangManager.getInstance();
        jiZhangBean.setType("支出");//默认为支出

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    outcome.setSelected(true);
                    income.setSelected(false);
                    jiZhangBean.setType("支出");
                }
                else{
                    outcome.setSelected(false);
                    income.setSelected(true);
                    jiZhangBean.setType("收入");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tb_note_outcome:
                viewpager.setCurrentItem(0);
                income.setSelected(false);
                outcome.setSelected(true);
                jiZhangBean.setType("支出");
                break;

            case R.id.tb_note_income:
                viewpager.setCurrentItem(1);
                outcome.setSelected(false);
                income.setSelected(true);
                jiZhangBean.setType("收入");
                break;

            case R.id.back_btn:
                BaseBottomBar baseBottomBar=new BaseBottomBar(getActivity());
                baseBottomBar.showTab(0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView;
        ImageView imageView;
        if (parent==gridView1){
            for (int i=0;i<parent.getCount();i++){
                if (i==position){
                    textView=view.findViewById(R.id.text_title);
                    imageView=view.findViewById(R.id.image_icon);

                    jiZhangBean.setWay(textView.getText().toString());//获得选中的item名称
                    //jiZhangBean.setPic(picTobyte(imageView.getResources()));//获得选中的图片

                    view.setSelected(true);

                    //显示窗口
                    popupWindow.showAtLocation(LayoutInflater.from(
                            getActivity()).inflate(R.layout.write_popup,null),
                            Gravity.BOTTOM,0,0);
                    break;
                }

            }

        }
        else if (parent==gridView2){
            for (int i=0;i<parent.getCount();i++){
                if (i==position){
                    textView=view.findViewById(R.id.text_title);
                    imageView=view.findViewById(R.id.image_icon);

                    jiZhangBean.setWay(textView.getText().toString());//获得选中的item名称
                    //jiZhangBean.setPic(picTobyte(imageView.getId()));//获得选中的图片

                    //显示窗口
                    popupWindow.showAtLocation(LayoutInflater.from(
                            getActivity()).inflate(R.layout.write_popup,null),
                            Gravity.BOTTOM,0,0);
                    break;
                }
            }
        }
    }


    /**
     * @param resourceID  图片资源id
     * @return   将图片转化成byte
     */
    private byte[] picTobyte(int resourceID)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(resourceID)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
