package com.example.zbq.jizhangben.ui.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.Dao.MysqlliteHelper;
import com.example.zbq.jizhangben.ui.Dao.OutInMoneyDB;
import com.example.zbq.jizhangben.ui.utils.DbManager;
import com.example.zbq.jizhangben.ui.utils.ExcelUtils;
import com.example.zbq.jizhangben.ui.utils.JiZhangManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zbq on 18-2-27.
 */

public class BeiFenActivity extends AppCompatActivity {

    @BindView(R.id.img_fanhui)
    ImageView imgFanhui;
    @BindView(R.id.bt_beifen)
    Button btBeifen;
    @BindView(R.id.ll_entry)
    LinearLayout llEntry;
    @BindView(R.id.lv_beifen)
    ListView lvBeifen;

    Unbinder unbinder;

    private ArrayList<ArrayList<String>> recordList;
    private ArrayList<JiZhangBean> list;
    private static String[] title = { "支出/消费","类型","方式","金额","备注","时间"};
    private File file;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beifen);
        unbinder = ButterKnife.bind(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick({R.id.img_fanhui, R.id.bt_beifen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_fanhui:
                finish();
                break;

            case R.id.bt_beifen:
                llEntry.setVisibility(View.GONE);
                lvBeifen.setVisibility(View.VISIBLE);
                //从数据库导出数据
                OutInMoneyDB outInMoneyDB=new OutInMoneyDB(this);
                list=outInMoneyDB.queryAll();
                exportExcel();
                break;
        }
    }

    /**
     * 导出excel
     */
    public void exportExcel() {

        file = new File(getSDPath()+"/张炜明");
        Log.v("TAG",file.toString());
        //if (!file.exists()) {
          //  makeDir(file);
        //}
        ExcelUtils.initExcel(file.toString() + "/记账本.xls", title);
        fileName = getSDPath() + "/张炜明/记账本.xls";
        ExcelUtils.writeObjListToExcel(getRecordData(), fileName, this);
    }

    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     * @return
     */
    private  ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i <list.size(); i++) {
            JiZhangBean bean = list.get(i);
            ArrayList<String> beanList = new ArrayList<>();
            beanList.add(bean.getType());
            beanList.add(bean.getWay());
            beanList.add(bean.getStyle());
            beanList.add(String.valueOf(bean.getMoney()));
            beanList.add(bean.getWrite());
            beanList.add(bean.getDate());
            recordList.add(beanList);
        }
        return recordList;
    }


    private  String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public  void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

}
