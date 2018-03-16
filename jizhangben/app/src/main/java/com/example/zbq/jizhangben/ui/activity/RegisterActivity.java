package com.example.zbq.jizhangben.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.Bean.User;
import com.example.zbq.jizhangben.ui.Dao.LoginDB;
import com.example.zbq.jizhangben.ui.widget.VerifyCodeButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zbq on 18-1-23.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.reg_edit_account)
    EditText name;
    @BindView(R.id.reg_edit_pwd)
    EditText password;
    @BindView(R.id.bt_reg)
    Button reg;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        unbinder = ButterKnife.bind(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LoginDB loginDB = new LoginDB(RegisterActivity.this);
        switch (v.getId()) {
            case R.id.bt_reg:
                User user1 = new User();
                user1.setUserName(name.getText().toString());
                user1.setUserPwd(password.getText().toString());
                Boolean isreg = loginDB.register(user1);
                if (isreg) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

        }

    }
}
