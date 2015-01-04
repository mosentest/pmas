package org.mo.pmas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import org.mo.common.activity.BaseFramgmentActivity;

import java.io.File;
import java.util.List;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class LoginActivity extends BaseFramgmentActivity implements View.OnClickListener {
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = LoginActivity.this.getPreferences(MODE_PRIVATE);
        final String username = preferences.getString("username", null);
        final String password = preferences.getString("password", null);
        login(username, password);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_login:
                user2login();
                break;
        }
    }

    private void user2login() {
        final String username = mUsername.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        login(username, password);
    }

    private void login(final String username, final String password) {
        final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("正在登录...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(username);
        bmobUser.setPassword(password);
        bmobUser.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                SharedPreferences preferences = LoginActivity.this.getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("username", username);
                edit.putString("password", password);
                edit.commit();
                Intent intent = new Intent(LoginActivity.this, EnterActivity.class);
                startActivity(intent);
                progress.dismiss();
                LoginActivity.this.finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                ShowToast("帐号或密码不正确");
                progress.dismiss();
            }
        });
    }

    private void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void init() {
        mUsername = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        mRegister = (TextView) findViewById(R.id.btn_register);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }
}
