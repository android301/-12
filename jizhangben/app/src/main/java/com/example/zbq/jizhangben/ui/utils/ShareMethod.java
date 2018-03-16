package com.example.zbq.jizhangben.ui.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zbq on 18-3-15.
 */

public class ShareMethod {

    //获取当天是星期几，这里星期七、一......分别为数字0、1......
    public static int getWeekDay(){
        Calendar calendar= Calendar.getInstance();
        Date date=new Date(System.currentTimeMillis());
        calendar.setTime(date);
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK)-1;
        return weekDay;
    }


}
