package com.example.zbq.jizhangben.ui.utils;

import android.content.Context;

import com.example.zbq.jizhangben.ui.Dao.MysqlliteHelper;

/**
 * Created by zbq on 18-1-22.
 */

public class DbManager {
    private static MysqlliteHelper mysqlliteHelper;

    public static MysqlliteHelper getInstance(Context context) {
        if (mysqlliteHelper == null) {
            mysqlliteHelper = new MysqlliteHelper(context);
        }
        return mysqlliteHelper;
    }


}
