package com.example.zbq.jizhangben.ui.Bean;

import java.util.List;

/**
 * Created by zbq on 18-2-4.
 */

public class TypeRankBean {

    private float total;//月消费总金额

    private List<TMoneyBean> t_money;

    public void setT_money(List<TMoneyBean> t_money) {
        this.t_money = t_money;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<TMoneyBean> getT_money() {
        return t_money;
    }

    public static class TMoneyBean{
        private float money;//总金额—按种类分类
        private String  type;//类型

        public void setMoney(float money) {
            this.money = money;
        }

        public float getMoney() {
            return money;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
