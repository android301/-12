package com.example.zbq.jizhangben.ui.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.zbq.jizhangben.ui.Bean.DayListBean;
import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;
import com.example.zbq.jizhangben.ui.utils.ConstantUtils;
import com.example.zbq.jizhangben.ui.utils.DbManager;

import java.util.ArrayList;


/**
 * Created by zbq on 18-1-28.
 */

public class OutInMoneyDB {
    private MysqlliteHelper dbhelper;
    private SQLiteDatabase db;

    public OutInMoneyDB(Context context){
        dbhelper= DbManager.getInstance(context);
    }

    //插入一条数据
    public void insert(String type,String way,String style,float money,String write,String date){
        db=dbhelper.getWritableDatabase();//以读写方式打开数据库
        String sql="insert into "+ ConstantUtils.Table_money+"(type,way,style,money,write,date) values (?,?,?,?,?,?)";
        Object bindArgs[]={type,way,style,money,write,date};
        db.execSQL(sql,bindArgs);
        db.close();
    }

    //查询所有数据
    public ArrayList<JiZhangBean> queryAll(){
        ArrayList<JiZhangBean> arrayList = new ArrayList<>();
        db = dbhelper.getWritableDatabase();
        String sql = "select * from "+ConstantUtils.Table_money;
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {

            JiZhangBean bean = new JiZhangBean();
            bean.setType(cursor.getString(cursor.getColumnIndex(ConstantUtils.type)));
            bean.setWay(cursor.getString(cursor.getColumnIndex(ConstantUtils.way)));
            bean.setStyle(cursor.getString(cursor.getColumnIndex(ConstantUtils.style)));
            bean.setMoney(cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money)));
            bean.setWrite(cursor.getString(cursor.getColumnIndex(ConstantUtils.write)));
            bean.setDate(cursor.getString(cursor.getColumnIndex(ConstantUtils.date)));
            arrayList.add(bean);
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    // 查询某月所有收支记录
    public ArrayList<DayListBean.ListBean> dbQueryAll(String detail) {
        ArrayList<DayListBean.ListBean> arrayList = new ArrayList<>();
        db = dbhelper.getWritableDatabase();
        String sql = "select * from "+ConstantUtils.Table_money +" where strftime('%Y-%m', date) ="+"\'"+detail+"\'";
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {

            DayListBean.ListBean bean = new DayListBean.ListBean();
            bean.set_id(cursor.getInt(cursor.getColumnIndex(ConstantUtils.id)));
            bean.setType(cursor.getString(cursor.getColumnIndex(ConstantUtils.type)));
            bean.setWay(cursor.getString(cursor.getColumnIndex(ConstantUtils.way)));
            bean.setStyle(cursor.getString(cursor.getColumnIndex(ConstantUtils.style)));
            bean.setMoney(cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money)));
            bean.setWrite(cursor.getString(cursor.getColumnIndex(ConstantUtils.write)));
            bean.setDate(cursor.getString(cursor.getColumnIndex(ConstantUtils.date)));
            //bean.setPic(cursor.getBlob(cursor.getColumnIndex(ConstantUtils.pic)));
            arrayList.add(bean);
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    // 查询某月收入/支出记录
    public ArrayList<JiZhangBean> dbQueryAll(String detail,String type) {
        ArrayList<JiZhangBean> arrayList = new ArrayList<>();
        db = dbhelper.getWritableDatabase();
        String sql = "select * from "+ConstantUtils.Table_money +" where strftime('%Y-%m', date) ="+"\'"+detail+"\'"+" and type="+"\""+type+"\"";
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {

            JiZhangBean bean = new JiZhangBean();
            bean.setType(cursor.getString(cursor.getColumnIndex(ConstantUtils.type)));
            bean.setWay(cursor.getString(cursor.getColumnIndex(ConstantUtils.way)));
            bean.setStyle(cursor.getString(cursor.getColumnIndex(ConstantUtils.style)));
            bean.setMoney(cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money)));
            bean.setWrite(cursor.getString(cursor.getColumnIndex(ConstantUtils.write)));
            bean.setDate(cursor.getString(cursor.getColumnIndex(ConstantUtils.date)));
            //bean.setPic(cursor.getBlob(cursor.getColumnIndex(ConstantUtils.pic)));
            arrayList.add(bean);

        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public ArrayList<JiZhangBean> dbQueryAsc(String detail,String type) {
        ArrayList<JiZhangBean> arrayList = new ArrayList<>();
        db = dbhelper.getWritableDatabase();
        String sql = "select * from "+ConstantUtils.Table_money +" where strftime('%Y-%m', date) ="+"\'"+detail+"\'"+" and type="+"\""+type+"\""+" order by money asc";
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {

            JiZhangBean bean = new JiZhangBean();
            bean.setType(cursor.getString(cursor.getColumnIndex(ConstantUtils.type)));
            bean.setWay(cursor.getString(cursor.getColumnIndex(ConstantUtils.way)));
            bean.setStyle(cursor.getString(cursor.getColumnIndex(ConstantUtils.style)));
            bean.setMoney(cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money)));
            bean.setWrite(cursor.getString(cursor.getColumnIndex(ConstantUtils.write)));
            bean.setDate(cursor.getString(cursor.getColumnIndex(ConstantUtils.date)));
            //bean.setPic(cursor.getBlob(cursor.getColumnIndex(ConstantUtils.pic)));
            arrayList.add(bean);

        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public ArrayList<JiZhangBean> dbQueryDesc(String detail,String type) {
        ArrayList<JiZhangBean> arrayList = new ArrayList<>();
        db = dbhelper.getWritableDatabase();
        String sql = "select * from "+ConstantUtils.Table_money +" where strftime('%Y-%m', date) ="+"\'"+detail+"\'"+" and type="+"\""+type+"\""+" order by money desc";
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {

            JiZhangBean bean = new JiZhangBean();
            bean.setType(cursor.getString(cursor.getColumnIndex(ConstantUtils.type)));
            bean.setWay(cursor.getString(cursor.getColumnIndex(ConstantUtils.way)));
            bean.setStyle(cursor.getString(cursor.getColumnIndex(ConstantUtils.style)));
            bean.setMoney(cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money)));
            bean.setWrite(cursor.getString(cursor.getColumnIndex(ConstantUtils.write)));
            bean.setDate(cursor.getString(cursor.getColumnIndex(ConstantUtils.date)));
            //bean.setPic(cursor.getBlob(cursor.getColumnIndex(ConstantUtils.pic)));
            arrayList.add(bean);

        }
        cursor.close();
        db.close();
        return arrayList;
    }


    // 查询某月某类型收入/支出总金额
    public float dbQueryAll(String detail,String type,String way) {
        float totalGet = 0;
        db = dbhelper.getWritableDatabase();
        String sql = "select money from "+ConstantUtils.Table_money +
                " where strftime('%Y-%m', date) ="+"\'"+detail+"\'"+" and type="+"\""+type+"\""
                +" and way="+"\""+way+"\"";
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {

            totalGet+=cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money));
        }
        cursor.close();
        db.close();
        return totalGet;
    }


    // 查询某类型支出/收入
    public ArrayList<JiZhangBean> wayQuery(String way) {
        ArrayList<JiZhangBean> arrayList = new ArrayList<>();
        db = dbhelper.getWritableDatabase();
        String sql = "select * from "+ConstantUtils.Table_money +" where way ="+"\'"+way+"\'";
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {

            JiZhangBean bean = new JiZhangBean();
            bean.setType(cursor.getString(cursor.getColumnIndex(ConstantUtils.type)));
            bean.setWay(cursor.getString(cursor.getColumnIndex(ConstantUtils.way)));
            bean.setStyle(cursor.getString(cursor.getColumnIndex(ConstantUtils.style)));
            bean.setMoney(cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money)));
            bean.setWrite(cursor.getString(cursor.getColumnIndex(ConstantUtils.write)));
            bean.setDate(cursor.getString(cursor.getColumnIndex(ConstantUtils.date)));
            //bean.setPic(cursor.getBlob(cursor.getColumnIndex(ConstantUtils.pic)));
            arrayList.add(bean);
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    // 查询某类型支出/收入
    public float getStyleTotal(String style) {
        float totalGet=0;
        db = dbhelper.getWritableDatabase();
        String sql = "select money from "+ConstantUtils.Table_money +" where style ="+"\'"+style+"\'";
        Cursor cursor = db.rawQuery(sql, null);
        // 游标从头读到尾
        while (cursor.moveToNext()) {
            totalGet+=cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money));
        }
        cursor.close();
        db.close();
        return totalGet;
    }

    //获得总支出
    public float getOutTotal() {
        float totalGet = 0;
        db = dbhelper.getWritableDatabase();
        String sql="select money from "+ConstantUtils.Table_money +" where type="+"\""+"支出"+"\"";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            totalGet+=cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money));
        }
        cursor.close();
        db.close();
        return totalGet;
    }

    //获得总收入
    public float getInTotal() {
        float totalGet = 0;
        db = dbhelper.getWritableDatabase();
        String sql="select money from "+ConstantUtils.Table_money +" where type="+"\""+"收入"+"\"";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            totalGet+=cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money));
        }
        cursor.close();
        db.close();
        return totalGet;
    }
    //获得该日总支出
    public float getDayOutTotal(String day) {
        float totalGet = 0;
        db = dbhelper.getWritableDatabase();
        String sql="select money from "+ConstantUtils.Table_money +" where type="+"\""+"支出"+"\""+" and strftime('%Y-%m-%d', date) ="+"\'"+day+"\'";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            totalGet+=cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money));
        }
        cursor.close();
        db.close();
        return totalGet;
    }


    //获得该日总收入
    public float getDayInTotal(String day) {
        float totalGet = 0;
        db = dbhelper.getWritableDatabase();
        String sql="select money from "+ConstantUtils.Table_money +" where type="+"\""+"收入"+"\""+" and strftime('%Y-%m-%d', date) ="+"\'"+day+"\'";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            totalGet+=cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money));
        }
        cursor.close();
        db.close();
        return totalGet;
    }


    //获得该月总共的支出
    public float getMonthOut(String month) {
        float totalGet = 0;
        db = dbhelper.getWritableDatabase();
        String sql="select * from "+ConstantUtils.Table_money +" where type="+"\""+"支出"+"\""+ " and strftime('%Y-%m', date) ="+"\'"+month+"\'";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                totalGet = totalGet + cursor.getFloat(cursor.getColumnIndex(ConstantUtils.money));
            }
        }
        cursor.close();
        db.close();
        return totalGet;
    }

    //获得该月总共的收入
    public float getMonthIn(String month) {
        int totalOut = 0;
        db = dbhelper.getWritableDatabase();
        String sql="select * from "+ConstantUtils.Table_money +" where type="+"\""+"收入"+"\""+" and strftime('%Y-%m', date) ="+"\'"+month+"\'";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                totalOut = totalOut
                        + cursor.getInt(cursor.getColumnIndex(ConstantUtils.money));
            }
        }
        cursor.close();
        db.close();
        return totalOut;
    }

    public void delete(int id){
        db=dbhelper.getWritableDatabase();
        String sql="delete from "+ConstantUtils.Table_money+" where _id="+id;
        db.execSQL(sql);
        db.close();
    }

}
