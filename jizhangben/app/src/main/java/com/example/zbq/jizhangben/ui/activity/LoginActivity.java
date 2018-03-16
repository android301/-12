package com.example.zbq.jizhangben.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Dao.LoginDB;
import com.example.zbq.jizhangben.ui.widget.OwlView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;

/**
 * Created by zbq on 18-1-22.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_tv_sign)
    TextView register;
    @BindView(R.id.login_btn_login)
    Button login;
    @BindView(R.id.login_et_username)
    EditText user;
    @BindView(R.id.login_et_password)
    EditText password;
    @BindView(R.id.login_tv_forget)
    TextView loginTvForget;

    private Unbinder unbinder;
    private OwlView owlView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        unbinder = ButterKnife.bind(this);
        owlView=new OwlView(this);

    }


    @OnFocusChange({R.id.login_et_password})
    public void onFocusChange(View v,boolean b){
        if (b){
            owlView.close();
        }else {
            owlView.open();
        }
    }


    @OnClick({R.id.login_btn_login, R.id.login_tv_sign, R.id.login_tv_forget})
    public void doLogin(View v) {
        LoginDB loginDB = new LoginDB(LoginActivity.this);
        switch (v.getId()) {
            case R.id.login_btn_login:
                Boolean islogin = loginDB.login(user.getText().toString(), password.getText().toString());
                if (islogin) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.login_tv_sign:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }
}
