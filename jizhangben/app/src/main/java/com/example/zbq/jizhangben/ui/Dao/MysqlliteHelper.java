package com.example.zbq.jizhangben.ui.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zbq.jizhangben.ui.utils.ConstantUtils;

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
 * Created by zbq on 18-1-22.
 */

public class MysqlliteHelper extends SQLiteOpenHelper {

    //创建用户user表
    private static final String DB_USER = "CREATE TABLE " + ConstantUtils.Table_user + " ("
            + ConstantUtils.User_name + " varchar(20) primary key,"
            + ConstantUtils.password + " varchar(20))";

    //创建支出收入表
    private static final String DB_IN_OUT = "create table " + ConstantUtils.Table_money + " ("
            + ConstantUtils.id + " integer primary key autoincrement,"
            + ConstantUtils.type + " varchar(20),"
            + ConstantUtils.way + " varchar(20),"
            + ConstantUtils.style + " varchar(20),"
            + ConstantUtils.money + " float,"
            + ConstantUtils.write + " varchar(50),"
            + ConstantUtils.date + " varchar(20))";

    //将提醒时间,内容存入数据库
    public static String Remind_data = "create table Remind_data("
            + "id integer primary key autoincrement, "
            + "remindTime long,"
            + "content text,"
            + "title text" + ")";

    //課程表


    public MysqlliteHelper(Context context) {
        super(context, ConstantUtils.Database_name, null, ConstantUtils.Database_version);
    }

    /**
     * 在数据库第一次创建的时候会调用这个方法
     * 根据需要对传入的SQLiteDatabase 对象填充表和初始化数据。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_USER);
        db.execSQL(DB_IN_OUT);
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
        db.execSQL(Remind_data);
        for (int i = 0; i < 7; i++) {
            String sql = "CREATE TABLE " + TB_NAME[i] +
                    " (_id INTEGER primary key autoincrement," +
                    "classes varchar(70)," +
                    "location varchar(70)," +
                    "teacher varchar(70)," +
                    "zhoushu varchar(70)," +
                    "time1 varchar(70)," +
                    "time2 varchar(70)," +
                    "jieshu varchar(70)," +
                    "which varchar(70))";
            db.execSQL(sql);
        }

    }
    /**
     * 当数据库需要修改的时候（两个数据库版本不同），Android系统会主动的调用这个方法。
     * 一般我们在这个方法里边删除数据库表，并建立新的数据库表.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i=0;i<7;i++){
            String sql="DROP TABLE IF EXISTS "+ TB_NAME[i];
            db.execSQL(sql);
        }
        onCreate(db);
    }



}
