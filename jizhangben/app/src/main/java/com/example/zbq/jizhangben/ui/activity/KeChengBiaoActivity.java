package com.example.zbq.jizhangben.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Dao.KeChengBiaoDB;
import com.example.zbq.jizhangben.ui.utils.ShareMethod;
import com.example.zbq.jizhangben.ui.widget.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zbq on 18-3-14.
 */

public class KeChengBiaoActivity extends AppCompatActivity {

    public ListView list[] = new ListView[7];

    @BindView(R.id.exitButton)
    ImageView exitButton;
    @BindView(R.id.setButton)
    ImageView setButton;
    @BindView(R.id.list0)
    ListView list0;
    @BindView(R.id.list1)
    ListView list1;
    @BindView(R.id.list2)
    ListView list2;
    @BindView(R.id.list3)
    ListView list3;
    @BindView(R.id.list4)
    ListView list4;
    @BindView(R.id.list5)
    ListView list5;
    @BindView(R.id.list6)
    ListView list6;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(R.id.tabhost)
    TabHost tabhost;


    public Cursor[] cursor = new Cursor[7];
    private KeChengBiaoDB db;


    //定义手势检测器实例
    private GestureDetector detector = null;
    //定义手势动作两点之间的最小距离
    private final int FLIP_DISTANCE = 200;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kechengbiao);
        ButterKnife.bind(this);
        db=new KeChengBiaoDB(this);
        //创建手势检测器
        detector = new GestureDetector(this, new DetectorGestureListener());

        //在配置任何的TabSpec之前，必须在TabHost上调用该方法
        tabhost.setup();

        //为主界面注册七个选项卡
        TabHost.TabSpec  spec = null;
        addCard(spec,"tag1",R.id.list0,"日");
        addCard(spec,"tag2",R.id.list1,"一");
        addCard(spec,"tag3",R.id.list2,"二");
        addCard(spec,"tag4",R.id.list3,"三");
        addCard(spec,"tag5",R.id.list4,"四");
        addCard(spec,"tag6",R.id.list5,"五");
        addCard(spec,"tag7",R.id.list6,"六");

        //修改tabHost选项卡中的字体的颜色
        final TabWidget tabWidget = tabhost.getTabWidget();
        for(int i=0;i<tabWidget.getChildCount();i++){
            TextView tv =tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(0xff004499);
        }

        //设置打开时默认的选项卡是当天的选项卡
        tabhost.setCurrentTab(ShareMethod.getWeekDay());

        list[0]=findViewById(R.id.list0);
        list[1]=findViewById(R.id.list1);
        list[2]=findViewById(R.id.list2);
        list[3]=findViewById(R.id.list3);
        list[4]=findViewById(R.id.list4);
        list[5]=findViewById(R.id.list5);
        list[6]=findViewById(R.id.list6);
        //用适配器为各选项卡添加所要显示的内容
        for(int i=0;i<7;i++){
            cursor[i]=db.select(i);

            list[i].setAdapter(adapter(i));
        }

        //为退出按钮绑定监听器
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建AlertDialog.Builder对象，该对象是AlterDialog的创建器，AlterDialog用来创建弹出对话框
                final AlertDialog.Builder builder = new AlertDialog.Builder(KeChengBiaoActivity.this);
                exit(builder);
            }
        });

        for( int day=0;day<7;day++) {
            //为七个ListView绑定触碰监听器，将其上的触碰事件交给GestureDetector处理
            //此监听器是必须的，不然滑动手势只在ListView下的空白区域有效，而在ListView上无效
            list[day].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return detector.onTouchEvent(event);

                }

            });

            //为每个ListView的每个item绑定监听器，点击则弹出由AlertDialog创建的列表对话框进行选择
            list[day].setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        final int id, long arg3) {
                    final int currentDay = tabhost.getCurrentTab();
                    final int n = id;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(KeChengBiaoActivity.this);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setTitle("选择");
                    TextView tv = arg1.findViewById(R.id.ltext0);
                    //如果课程栏目为空就启动添加对话框
                    if ((tv.getText()).toString().equals("")) {
                        //通过数组资源为对话框中的列表添加选项内容，这里只有一个选项
                        builder.setItems(R.array.edit_options1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //如果单击了该列表项，则跳转到编辑课程信息的界面
                                if (which == 0) {
                                    new MyDialog(KeChengBiaoActivity.this).add(currentDay, n);
                                }
                            }
                        });
                        builder.create().show();
                    }
                    //否则启动修改对话框，或直接删除数据
                    else {
                        builder.setItems(R.array.edit_options2, new DialogInterface.OnClickListener() {

                            @SuppressWarnings("deprecation")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //如果单击了该列表项，则跳转到编辑课程信息的界面
                                if (which == 0) {
                                    new MyDialog(KeChengBiaoActivity.this).modify(currentDay, n);
                                }
                                if (which == 1) {
                                    cursor[currentDay].moveToPosition(n);
                                    int n1 = Integer.parseInt(cursor[currentDay].getString(7));//课程的总节数
                                    int n2 = Integer.parseInt(cursor[currentDay].getString(8));//选中的为该课程的第几节
                                    switch (n2) {
                                        case 0:
                                            for (int m = 0; m < n1; m++) {
                                                db.deleteData(currentDay, n + m + 1);
                                            }
                                            break;

                                        case 1:
                                            db.deleteData(currentDay, n);
                                            for (int m = 1; m < n1; m++) {
                                                db.deleteData(currentDay, n + m);
                                            }
                                            break;
                                        case 2:
                                            db.deleteData(currentDay, n - 1);
                                            db.deleteData(currentDay, n);
                                            for (int m = 2; m < n1; m++) {
                                                db.deleteData(currentDay, n + m - 1);
                                            }
                                            break;
                                        case 3:
                                            for (int m = n2; m >= 0; m--) {
                                                db.deleteData(currentDay, n - m + 1);
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                    cursor[currentDay].requery();
                                    list[currentDay].invalidate();
                                }
                            }
                        });
                        builder.create().show();
                    }
                }
            });
        }

    }

    //为主界面添加选项卡
    public void addCard(TabHost.TabSpec spec,String tag,int id,String name){
        spec = tabhost.newTabSpec(tag);
        spec.setContent(id);
        spec.setIndicator(name);
        tabhost.addTab(spec);
    }

    //用来弹出是否退出程序的对话框，并执行执行是否退出操作
    public void exit(AlertDialog.Builder builder){
        //为弹出的对话框设置标题和内容
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("退出程序");
        builder.setMessage("确定要退出课程表吗？");
        //设置左边的按钮为“确定”键，并且其绑定监听器，点击后退出
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //退出应用程序，即销毁地所有的activity
               finish();
            }
        });

        //设置右边的按钮为“取消”键，并且其绑定监听器，点击后仍然停留在当前界面
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //创建并显示弹出的对话框
        builder.create().show();
    }

    /*
    * 为每一个list提供数据适配器
    */
    @SuppressWarnings("deprecation")
    public SimpleCursorAdapter adapter(int i){
        return new SimpleCursorAdapter(this, R.layout.list_v2,cursor[i],new String[]{"_id","classes","location",
                "teacher","zhoushu"},new int[]{R.id.number,R.id.ltext0,R.id.ltext1,R.id.ltext6,R.id.ltext7} );
    }

    private class DetectorGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
