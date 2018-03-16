package com.example.zbq.jizhangben.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by zbq on 18-1-14.
 */

public class MyApplication extends Application {

    public static MyApplication application;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
    }
}