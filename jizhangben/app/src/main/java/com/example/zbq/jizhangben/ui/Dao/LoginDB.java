package com.example.zbq.jizhangben.ui.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zbq.jizhangben.ui.Bean.User;
import com.example.zbq.jizhangben.ui.utils.ConstantUtils;
import com.example.zbq.jizhangben.ui.utils.DbManager;

/**
 * Created by zbq on 18-1-22.
 */

public class LoginDB {
    private MysqlliteHelper dbhelper;

    public LoginDB(Context context){
        dbhelper=DbManager.getInstance(context);
    }

    //登录用
    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbhelper.getReadableDatabase();
        String sql="select * from "+ConstantUtils.Table_user+" where "
                + ConstantUtils.User_name+"=? and "+ConstantUtils.password+"=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }
    //注册用
    public boolean register(User user){
        SQLiteDatabase sdb=dbhelper.getReadableDatabase();
        String sql="insert into "+ ConstantUtils.Table_user+
                   "("+ConstantUtils.User_name+","+ConstantUtils.password+")"+
                   "values(?,?)";
        Object obj[]={user.getUserName(),user.getUserPwd()};
        sdb.execSQL(sql, obj);
        return true;
    }
}
