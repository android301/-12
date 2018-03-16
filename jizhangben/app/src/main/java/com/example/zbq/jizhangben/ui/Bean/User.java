package com.example.zbq.jizhangben.ui.Bean;

/**
 * Created by zbq on 18-1-22.
 */

public class User {
    //private String userId;//用户Id
    private String userPwd;//用户密码
    private String userName;//用户名


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserPwd() {
        return userPwd;
    }
}
