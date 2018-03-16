package com.example.zbq.jizhangben.ui.utils;

import com.example.zbq.jizhangben.ui.Bean.JiZhangBean;

/**
 * Created by zbq on 18-1-28.
 */

public class JiZhangManager {
    private static JiZhangBean jiZhangBean;
    public static JiZhangBean getInstance(){
        if (jiZhangBean==null){
            jiZhangBean=new JiZhangBean();
        }
        return jiZhangBean;
    }
}
