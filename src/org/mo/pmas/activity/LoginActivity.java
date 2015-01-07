package org.mo.pmas.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.bmob.entity.MyUser;
import org.mo.pmas.util.ErrorEnum;
import org.mo.pmas.util.SharePreferenceUtil;

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
    private CheckBox m_cb_remember_pwd;
    private CheckBox m_cb_auto_login;
    private TextView m_tv_forget_pwd;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", MODE_PRIVATE);
        final String username = preferences.getString("username", null);
        final String password = preferences.getString("password", null);
        final boolean autoLogin = preferences.getBoolean("autoLogin", false);
        final boolean rememberPwd = preferences.getBoolean("rememberPwd", false);
        if (autoLogin == true && rememberPwd == true) {
            login(username, password, autoLogin, rememberPwd);
        } else if (rememberPwd == true) {
            setContentView(R.layout.activity_login);
            init();
            mUsername.setText(username);
            mPassword.setText(password);
            m_cb_remember_pwd.setChecked(true);
        } else if (rememberPwd == false) {
            setContentView(R.layout.activity_login);
            init();
            mUsername.setText(username);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_login:
                user2login();
            case R.id.cb_auto_login:
                break;
            case R.id.cb_remember_pwd:
                break;
            case R.id.tv_forget_pwd:
                break;
        }
    }

    private void user2login() {
        final String username = mUsername.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        boolean autoLogin = m_cb_auto_login.isChecked();
        boolean rememberPwd = m_cb_remember_pwd.isChecked();
        login(username, password, autoLogin, rememberPwd);
    }

    private void login(final String username, final String password, final boolean autoLogin, final boolean rememberPwd) {
        progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("正在登录...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        new LoadAsynTask(username, password, autoLogin, rememberPwd, this).execute();

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
        this.m_cb_remember_pwd = (CheckBox) findViewById(R.id.cb_remember_pwd);
        this.m_cb_auto_login = (CheckBox) findViewById(R.id.cb_auto_login);
        this.m_tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    private class LoadAsynTask extends AsyncTask<Void, Void, Void> {
        String username;
        String password;
        boolean autoLogin;
        boolean rememberPwd;
        Context context;

        private LoadAsynTask(String username, String password, boolean autoLogin, boolean rememberPwd, Context context) {
            this.username = username;
            this.password = password;
            this.autoLogin = autoLogin;
            this.rememberPwd = rememberPwd;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            MyUser bmobUser = new MyUser();
            bmobUser.setUsername(username);
            bmobUser.setPassword(password);

            bmobUser.login(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    SharedPreferences preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("username", username);
                    if (rememberPwd == true) {
                        edit.putString("password", password);
                        edit.putBoolean("rememberPwd", true);
                    } else {
                        edit.putString("password", null);
                        edit.putBoolean("rememberPwd", false);
                    }
                    edit.putBoolean("autoLogin", autoLogin);
                    edit.commit();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, EnterActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                            overridePendingTransition(R.anim.myenteranim, R.anim.myexitanim);
                        }
                    }, 0);
                    progress.dismiss();
                }

                @Override
                public void onFailure(int code, String msg) {
                    ErrorEnum ident = ErrorEnum.ident(code);
                    ShowToast(ident.getMessage());
                    progress.dismiss();
                }
            });
            return null;
        }
    }
}
