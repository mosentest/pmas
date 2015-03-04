package org.mo.pmas.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ClientUitl;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.bmob.entity.MyUser;
import org.mo.pmas.util.ErrorEnum;
import org.mo.pmas.util.SharePreferenceUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

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
    private EditText et_code;
    private ImageView iv_code;
    private KJBitmap kjb;

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

        et_code = (EditText) findViewById(R.id.et_code);
        iv_code = (ImageView) findViewById(R.id.iv_code);

        iv_code.setOnClickListener(this);

        kjb = KJBitmap.create();
        kjb.display(iv_code, ClientUitl.VCODE_URL);
        kjb.setCallback(new BitmapCallBack() {
            @Override
            public void onSuccess(View view, Bitmap bitmap) {
                super.onSuccess(view, bitmap);
                iv_code.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPreLoad(View view) {
                super.onPreLoad(view);
                iv_code.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

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
            case R.id.iv_code:
                kjb.removeCacheAll();
                kjb.display(iv_code, ClientUitl.VCODE_URL);
                kjb.setCallback(new BitmapCallBack() {
                    @Override
                    public void onSuccess(View view, Bitmap bitmap) {
                        super.onSuccess(view, bitmap);
                        iv_code.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPreLoad(View view) {
                        super.onPreLoad(view);
                        iv_code.setVisibility(View.GONE);
                    }
                });
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
            try {
                progress.dismiss();
                Map<String, String> varValue = ClientUitl.getVarValue();
                if (varValue == null) {
                    ShowToast("获取失败");
                }
                Log.v("strResult", varValue.get(ClientUitl.LOGIN_TICKET));
                Log.v("strResult", varValue.get(ClientUitl.EXECUTION));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;


        }
    }
}
