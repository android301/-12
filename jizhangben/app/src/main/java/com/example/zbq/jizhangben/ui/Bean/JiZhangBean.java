package com.example.zbq.jizhangben.ui.Bean;

import android.graphics.drawable.Drawable;

/**
 * Created by zbq on 18-1-31.
 */

public class JiZhangBean {
    private int _id;
    private String type;//类型,支出或收入
    private String way;//消费方向
    private String style;//方式
    private float money;//金额
    private String write;//备注
    private String date;//日期
    private Drawable pic;//图片

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setType(String type) {
                this.type = type;
}

    public String getType() {
        return type;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getWay() {
        return way;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public String getWrite() {
        return write;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
    }

    public Drawable getPic() {
        return pic;
    }
}
