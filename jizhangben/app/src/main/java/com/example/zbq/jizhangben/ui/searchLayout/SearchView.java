package com.example.zbq.jizhangben.ui.searchLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Dao.MysqlliteHelper;
import com.example.zbq.jizhangben.ui.adapter.SearchSimpleCursorAdapter;
import com.example.zbq.jizhangben.ui.utils.DbManager;

/**
 * Created by zbq on 18-2-23.
 */

public class SearchView extends LinearLayout{
    /**
     * 初始化成员变量
     * @param context
     */
    private Context context;

    //搜索框组件
    private ImageView searchBack;//返回按键
    private LinearLayout searchLayout;//搜索框布局
    private EditText et_search;//搜索按键
    private TextView tv_clear;//删除历史记录按键

    //ListView列表&适配器
    private SearchListView searchListView;
    private BaseAdapter adapter;

    //数据库变量  存放历史搜索记录
    private MysqlliteHelper mysqlliteHelper;
    private SQLiteDatabase db;

    //回调接口
    private SCallback sCallback;//搜索按键回调接口
    private BCallback bCallback;//返回按键回调接口

    //自定义属性设置
    //1.搜索字体设置：大小，颜色&提示
    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    //2.搜索框设置：大小，颜色
    private int searchLayoutHeight;
    private int searchLayoutColor;


    public SearchView(Context context) {
        super(context);
        this.context=context;
        init();//初始化搜索框
    }


    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initAttrs(context,attrs);//初始化自定义属性
        init();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initAttrs(context,attrs);
        init();
    }

    /**
     * 初始化自定义属性
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {

        //控件资源名称
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.Search_View);

        //搜索框字体大小
        textSizeSearch=typedArray.getDimension(R.styleable.Search_View_textSizeSearch,20);

        //搜索框字体颜色
        int defaultColor=context.getResources().getColor(R.color.text_red);//默认红色
        textColorSearch=typedArray.getColor(R.styleable.Search_View_textColorSearch,defaultColor);

        //搜索框提示内容
        textHintSearch=typedArray.getString(R.styleable.Search_View_textHintSearch);

        //搜索框高度
        searchLayoutHeight=typedArray.getInteger(R.styleable.Search_View_searchLayoutHeight,150);

        //搜索框颜色
        int defaultColor2=context.getResources().getColor(R.color.SlateGray);//默认灰色
        searchLayoutColor=typedArray.getColor(R.styleable.Search_View_searchLayoutColor,defaultColor2);

        //释放资源
        typedArray.recycle();

    }

    /**
     * 初始化搜索框
     */
    private void init() {

        //1.初始化UI组件
        initView();

        //2.实例化数据库对象
        mysqlliteHelper=DbManager.getInstance(context);

        //3.第一次进入时查询所有的历史记录
        queryData("");

        /**
         * 清空搜索历史按钮
         */
        tv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //清空数据库
                deleteData();
                //模糊搜索空字符=显示所有的历史记录（此时没有搜索历史）
                queryData("");
            }
        });

        /**
         * 监听输入键盘更换后的搜索按钮
         * 调用时刻：点击键盘上的搜索键时
         */
        et_search.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN){

                    //1.点击搜索键后，根据搜索字段进行查询
                    //注：此处需求会根据业务不同而不同，所以留出接口，具体逻辑由开发者实现
                    if (!(sCallback==null)){
                        sCallback.SearchAction(et_search.getText().toString());

                    }

                    //2.点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）
                    boolean hasData=hasData(et_search.getText().toString().trim());
                    //3.若存在，则不保存;若不存在，则将该搜索字段插入到数据库，并作为历史记录
                    if (!hasData){
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }
                }
                return false;
            }
        });

        /**
         * 搜索框的文本变化实时监听
         */
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //每次输入后模糊查询数据库&显示
                //注：若搜索框为空，则模糊搜索空字符=显示所有搜索历史
                String tempName=et_search.getText().toString();
                queryData(tempName);
            }
        });


        /**
         * 搜索记录列表（listView）监听
         * 即用户点击搜索历史里的字段后，会直接将结果当作搜索字段搜索
         */
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //获取用户点击列表里的文字，并自动填充到搜索框内

                TextView textView=view.findViewById(R.id.search_list_tv);
                et_search.setText(textView.getText().toString());
                sCallback.SearchAction(et_search.getText().toString());

            }
        });


        /**
         * 点击返回按键后的事件
         */
        searchBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //返回需求根据自身情况不同，此处仅留出接口
                if (bCallback!=null){
                    bCallback.BackAction();
                }
            }
        });
    }


    private void initView() {

        //1.绑定布局文件
        LayoutInflater.from(context).inflate(R.layout.search_layout,this);

        //2.绑定搜索框
        et_search=findViewById(R.id.et_search);
        et_search.setTextSize(textSizeSearch);
        et_search.setTextColor(textColorSearch);
        et_search.setHint(textHintSearch);

        //3.搜索框背景颜色
        searchLayout=findViewById(R.id.search_layout);
        searchLayout.setBackgroundColor(searchLayoutColor);
        LayoutParams params= (LayoutParams) searchLayout.getLayoutParams();
        params.height=searchLayoutHeight;
        searchLayout.setLayoutParams(params);

        //4.历史搜索记录=listView显示
        searchListView=findViewById(R.id.listView);

        //5.删除历史记录，按钮
        tv_clear=findViewById(R.id.tv_clear);
        tv_clear.setVisibility(INVISIBLE);

        //6.返回按键
        searchBack=findViewById(R.id.search_back);

    }

    /**
     * 模糊查询数据&显示到listView列表上
     * @param tempName
     */
    private void queryData(String tempName) {

        //1.模糊搜索
        Cursor cursor=mysqlliteHelper.getWritableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);

        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        adapter = new SearchSimpleCursorAdapter(context,cursor,true);
        // 3. 设置适配器
        searchListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //当输入框为空&数据库中有搜索记录时显示删除按钮
        if (tempName.equals("")&&cursor.getCount()!=0){
            tv_clear.setVisibility(VISIBLE);
        }
        else {
            tv_clear.setVisibility(INVISIBLE);
        }
    }


    /**
     * 清空数据库
     */
    private void deleteData() {
        db=mysqlliteHelper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        tv_clear.setVisibility(INVISIBLE);

    }


    /**
     * 检查数据库中是否已经有该搜索记录
     * @param tempName
     * @return
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = mysqlliteHelper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =" +"\"" +tempName+"\"",null);
        //  判断是否有下一个
        return cursor.moveToNext();
    }


    /**
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = mysqlliteHelper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();

    }

    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     */
    public void setOnClickSearch(SCallback sCallBack){
        this.sCallback = sCallBack;

    }

    /**
     * 点击返回后的操作，用于接口回调
     */
    public void setOnClickBack(BCallback bCallBack){
        this.bCallback = bCallBack;

    }
}
