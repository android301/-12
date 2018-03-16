package com.example.zbq.jizhangben.ui.Bean;


import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by zbq on 18-1-28.
 */

public class DayListBean {
        private String day;//具体某一天
        private float day_in;//日收入
        private float day_out;//日支出
        private List<ListBean> list;


        public void setDay(String day) {
            this.day = day;
        }

        public String getDay() {
            return day;
        }

        public List<ListBean> getList() {
            return list;
        }

        public float getDay_in() {
            return day_in;
        }

        public void setDay_in(float day_in) {
            this.day_in = day_in;
        }

        public float getDay_out() {
            return day_out;
        }

        public void setDay_out(float day_out) {
            this.day_out = day_out;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }


        public static class ListBean {
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
}
