package com.example.zbq.jizhangben.ui.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zbq.jizhangben.ui.utils.ConstantUtils;
import com.example.zbq.jizhangben.ui.utils.DbManager;

import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.CLASS;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.JIESHU;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.LOCA;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.TB_NAME;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.TEACHER;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.TIME1;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.TIME2;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.WHICH;
import static com.example.zbq.jizhangben.ui.utils.ConstantUtils.ZHOUSHU;

/**
 * Created by zbq on 18-3-15.
 */

public class KeChengBiaoDB {
    private MysqlliteHelper dbhelper;
    private SQLiteDatabase db;


    public KeChengBiaoDB(Context context){
        dbhelper= DbManager.getInstance(context);
    }


    public Cursor select(int i){
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        Cursor cursor=db.query(TB_NAME[i],null,null,null,null,null,null);
        return cursor;
    }


    public long insert(int i,String cla,String loca,String tea,String zhou,String jie,String time1,String time2,String which){
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(ConstantUtils.CLASS,cla);
        cv.put(LOCA, loca);
        cv.put(TEACHER,tea);
        cv.put(ZHOUSHU,zhou);
        cv.put(JIESHU,jie);
        cv.put(TIME1,time1);
        cv.put(TIME2,time2);
        cv.put(WHICH,which);
        long row=db.insert(TB_NAME[i],null,cv);
        return row;
    }


    public void update(int i,int _id,String cla,String loca,String tea,String zhou,String jie,String time1,String time2,String which){
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String where="_id = ?";
        String[] whereValues={Integer.toString(_id)};
        ContentValues cv=new ContentValues();
        if(!cla.equals("")) cv.put(CLASS,cla);
        if(!loca.equals("")) cv.put(LOCA, loca);
        if(!tea.equals("")) cv.put(TEACHER,tea);
        if(!zhou.equals("")) cv.put(ZHOUSHU,zhou);
        if(!jie.equals("")) cv.put(JIESHU,jie);
        if(!time1.equals("")) cv.put(TIME1,time1);
        if(!time2.equals("")) cv.put(TIME2,time2);
        if(!which.equals("")) cv.put(WHICH,which);
        db.update(TB_NAME[i], cv, where, whereValues);
    }


    public void deleteData(int i,int _id){
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String where="_id = ?";
        String[] whereValues={Integer.toString(_id)};
        ContentValues cv=new ContentValues();
        cv.put("classes","");
        cv.put("location","");
        cv.put("teacher","");
        cv.put("zhoushu","");
        cv.put("jieshu","");
        cv.put("time1","");
        cv.put("time2","");
        cv.put("which","");
        db.update(TB_NAME[i], cv, where, whereValues);
    }


    public void delete(int i,int _id){
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String where="_id = ?";
        String[] whereValues={Integer.toString(_id)};
        db.delete(TB_NAME[i], where, whereValues);
    }


    public void createTable(int j){
        for(int i=1;i<=12;i++)
            insert(j,"", "", "","","","","","");
    }

}
