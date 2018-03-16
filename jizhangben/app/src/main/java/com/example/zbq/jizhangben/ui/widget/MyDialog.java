package com.example.zbq.jizhangben.ui.widget;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Dao.KeChengBiaoDB;
import com.example.zbq.jizhangben.ui.activity.KeChengBiaoActivity;
import java.util.Calendar;

import butterknife.BindView;


/**
 * Created by zbq on 18-3-15.
 */

public class MyDialog {

    @BindView(R.id.editText1)
    EditText course_name;
    @BindView(R.id.editText2)
    EditText course_address;
    @BindView(R.id.editText3)
    EditText course_teacher;
    @BindView(R.id.editText4)
    EditText course_week;
    @BindView(R.id.jieshu)
    EditText course_count;
    @BindView(R.id.time1)
    Button time1;
    @BindView(R.id.time2)
    Button time2;

    private LayoutInflater inflater;
    private Context context;
    private View view;
    private AlertDialog.Builder builder;
    private KeChengBiaoDB db;
    private KeChengBiaoActivity activity;
    String s1="",s2="",s3="",s4="",s5="",s6="",s7="";


    public MyDialog(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        db=new KeChengBiaoDB(context);
        activity=(KeChengBiaoActivity) context;

    }

    /*
    * 点击未编辑的课程列表跳出”添加课程“对话框
    */
    public void add(final int day, final int n) {
        //填装对话框的view
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.edit_shedule, null);
        final Button course_time1 = view.findViewById(R.id.time1);
        final Button course_time2 = view.findViewById(R.id.time2);
        //为两个输入时间的按钮绑定监听器
        course_time1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimeSet_Dialog(course_time1);
            }
        });
        course_time2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimeSet_Dialog(course_time2);
            }
        });

        builder = new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("编辑课程信息")
                .setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!(s1 = course_name.getText().toString()).equals(""))
                            s1 = "课程: " + s1;
                        if (!(s2 = course_address.getText().toString()).equals(""))
                            s2 = "地点: " + s2;
                        if (!(s3 = course_teacher.getText().toString()).equals(""))
                            s3 = "老师: " + s3;
                        if (!(s4 = course_week.getText().toString()).equals(""))
                            s4 = "周数: " + s4;
                        if (!(s6 = course_time1.getText().toString()).equals(""))
                            s6 = "时间: " + s6;
                        if (!(s7 = course_time2.getText().toString()).equals("")) ;

                        if ((s5 = course_count.getText().toString()).equals("") || s1.equals("")) {
                            Toast.makeText(context, "请正确输入课程及节数！", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            int i = Integer.parseInt(s5.trim());//i为节数
                            for (int m = 0; m < i; m++) {
                                db.update(day, n + m + 1, s1, s2, s3, s4, s5, s6, s7, Integer.toString(m));
                            }

                        }

                        activity.cursor[day].requery();
                        activity.list[day].invalidate();
                    }

                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();

    }

    public void TimeSet_Dialog(final TextView text){
        Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来。
        new TimePickerDialog(context,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker tp, int hourOfDay, int minute){
                        //获取完整的时间，在只有一位的数字前面加0
                        StringBuffer s_hour = new StringBuffer();
                        StringBuffer s_minute = new StringBuffer();
                        s_hour.append(hourOfDay);
                        s_minute.append(minute);
                        if(hourOfDay<10){
                            s_hour.insert(0,"0");
                        }
                        if(minute<10){
                            s_minute.insert(0,"0");
                        }
                        //将结果显示在edit中
                        text.setText(s_hour.toString() + ":" + s_minute.toString());
                    }
                }
                //设置初始时间
                , c.get(Calendar.HOUR_OF_DAY)
                , c.get(Calendar.MINUTE)
                //true表示采用24小时制
                , true).show();
    }


    /*
    * 点击已编辑的课程列表跳出”修改课程信息或删除课程信息“对话框
    */
    public void modify(final int day,final int n){
        //填装对话框的view
        inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.edit_shedule,null);
        //为两个输入时间的按钮绑定监听器
        time1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimeSet_Dialog(time1);
            }
        });
        time2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimeSet_Dialog(time2);
            }
        });

        //从数据库取出旧数据
        activity.cursor[day].moveToPosition(n);
        final String [] temp=new String[8];

        for(int i=0;i<8;i++) {
            temp[i]=activity.cursor[day].getString(i+1);
        }

        //将旧数据显示在编辑对话框
        if(!temp[0].equals("")) course_name.setText(temp[0].substring(temp[0].indexOf(":")+2));
        if(!temp[1].equals("")) course_address.setText(temp[1].substring(temp[1].indexOf(":")+2));
        if(!temp[2].equals("")) course_teacher.setText(temp[2].substring(temp[2].indexOf(":")+2));
        if(!temp[3].equals("")) course_week.setText(temp[3].substring(temp[3].indexOf(":")+2));
        if(!temp[4].equals("")) course_count.setText(temp[4].substring(temp[4].indexOf(":")+2));
        time1.setText(temp[5]);
        time2.setText(temp[6]);
        view.invalidate();


        builder=new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("修改课程信息")
                .setView(view)
                .setPositiveButton("确认",new DialogInterface.OnClickListener(){

                    @SuppressWarnings("deprecation")
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(!(s1=course_name.getText().toString()).equals("")) s1="课程: "+s1;
                        if(!(s2=course_address.getText().toString()).equals("")) s2="地点: "+s2;
                        if(!(s3=course_teacher.getText().toString()).equals("")) s3="老师: "+s3;
                        if(!(s4=course_week.getText().toString()).equals("")) s4="周数: "+s4;
                        if(!(s6=time1.getText().toString()).equals(""))s6="时间: "+s6;
                        if(!(s7=time2.getText().toString()).equals(""));
                        s5=course_count.getText().toString();
                        activity.cursor[day].moveToPosition(n);
                        int n1=Integer.parseInt(activity.cursor[day].getString(7).trim());//课程的总节数
                        int n2=Integer.parseInt(activity.cursor[day].getString(8).trim());//选中的为该课程的第几节
                        Log.i("kkk",activity.cursor[day].getString(7));
                        //如果没有再次输入节数或节数没有变化，根据选中的为第几节更新前后节的数据即可
                        if(s5.equals("")||n1==Integer.parseInt(s5.trim())) {
                            switch(n2){
                                case 0:
                                    for(int m=0;m<n1;m++){
                                        db.update(day,n+m+1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                    }
                                    break;

                                case 1:
                                    db.update(day,n,s1,s2,s3,s4,s5,s6,s7,"0");
                                    for(int m=1;m<n1;m++){
                                        db.update(day,n+m,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                    }
                                    break;
                                case 2:
                                    db.update(day,n-1,s1,s2,s3,s4,s5,s6,s7,"0");
                                    db.update(day,n,s1,s2,s3,s4,s5,s6,s7,"1");
                                    for(int m=2;m<n1;m++){
                                        db.update(day,n+m-1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                    }
                                    break;
                                case 3:
                                    for(int m=n2;m>=0;m--){
                                        db.update(day,n-m+1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(n2-m));
                                    }
                                    break;
                            }

                        }
                        //若节数有变化，先确定新节数并赋予旧的数据再更新数据
                        else{
                            int n3=Integer.parseInt(s5.trim());
                            //扩充节数
                            if(n3>n1){

                                switch(n2){//更新数据
                                    case 0:
                                        for(int m=0;m<n3;m++){
                                            db.update(day,n+m+1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                        }
                                        break;

                                    case 1:
                                        db.update(day,n,s1,s2,s3,s4,s5,s6,s7,"0");
                                        for(int m=1;m<n3;m++){
                                            db.update(day,n+m,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                        }
                                        break;
                                    case 2:
                                        db.update(day,n-1,s1,s2,s3,s4,s5,s6,s7,"0");
                                        db.update(day,n,s1,s2,s3,s4,s5,s6,s7,"1");
                                        for(int m=2;m<n3;m++){
                                            db.update(day,n+m-1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                        }
                                        break;
                                    case 3:
                                        for(int m=n2;m>=0;m--){
                                            db.update(day,n-m+1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(n2-m));
                                        }
                                        break;
                                }

                            }
                            //缩减节数：删除旧数据再根据新的节数赋予旧数据最后更新新数据
                            if(n3<n1){
                                switch(n2){//删除
                                    case 0:
                                        for(int m=0;m<n1;m++){
                                            db.deleteData(day,n+m+1);
                                        }
                                        break;

                                    case 1:
                                        db.deleteData(day,n);
                                        for(int m=1;m<n1;m++){
                                            db.deleteData(day,n+m);
                                        }
                                        break;
                                    case 2:
                                        db.deleteData(day,n-1);
                                        db.deleteData(day,n);
                                        for(int m=2;m<n1;m++){
                                            db.deleteData(day,n+m-1);
                                        }
                                        break;
                                    case 3:
                                        for(int m=n2;m>=0;m--){
                                            db.deleteData(day,n-m+1);
                                        }
                                        break;
                                    default:
                                        Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
                                        break;
                                }


                                switch(n2){//更新数据
                                    case 0:
                                        for(int m=0;m<n3;m++){
                                            db.update(day,n+m+1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                        }
                                        break;

                                    case 1:
                                        db.update(day,n,s1,s2,s3,s4,s5,s6,s7,"0");
                                        for(int m=1;m<n3;m++){
                                            db.update(day,n+m,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                        }
                                        break;
                                    case 2:
                                        db.update(day,n-1,s1,s2,s3,s4,s5,s6,s7,"0");
                                        db.update(day,n,s1,s2,s3,s4,s5,s6,s7,"1");
                                        for(int m=2;m<n3;m++){
                                            db.update(day,n+m-1,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                        }
                                        break;
                                    case 3:
                                        for(int m=0;m<n3;m++){
                                            db.update(day,n+m-2,s1,s2,s3,s4,s5,s6,s7,Integer.toString(m));
                                        }
                                        break;
                                }

                            }
                        }
                        activity.cursor[day].requery();
                        activity.list[day].invalidate();

                    }

                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }

                });
        builder.create().show();

    }

}
