package org.mo.pmas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ValidateUtil;
import org.mo.pmas.util.ErrorEnum;

import java.util.List;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class RegisterActivity extends BaseFramgmentActivity implements View.OnClickListener {
    private EditText mAccount;
    private EditText mPassword;
    private EditText mRepassword;
    private EditText mEmail;
    private Button mRegister;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        register();
    }

    void register() {
        mAccount = (EditText) findViewById(R.id.et_register_username);
        mPassword = (EditText) findViewById(R.id.et_register_password);
        mRepassword = (EditText) findViewById(R.id.et_register_repassword);
        mEmail = (EditText) findViewById(R.id.et_register_email);
        mRegister = (Button) findViewById(R.id.btn_register2);
        //加监听
        mRegister.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                RegisterActivity.this.finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register2:
                checkUserInfo();
                break;
        }
    }

    void checkUserInfo() {
        final String account = mAccount.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        String repassword = mRepassword.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ShowToast("帐号不能为空");
            return;
        }
        if (account.length() < 6) {
            ShowToast("帐号不能小于6位");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ShowToast("密码不能为空");
            return;
        }
        if (password.length() < 6) {
            ShowToast("密码不能小于6位");
            return;
        }
        if (!password.equals(repassword)) {
            ShowToast("两次密码输入不相同");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ShowToast("邮箱不能为空");
            return;
        }
        if (!ValidateUtil.isEmail(email)) {
            ShowToast("邮箱格式不正确" + ValidateUtil.isEmail(email));
            return;
        }
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("username", account);
        query.findObjects(this, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> bmobUsers) {
                if (bmobUsers.size() > 0) {
                    ShowToast(account + "已经存在");
                    flag = false;
                } else {
                    registerUser(account, password, email);
                }
            }

            @Override
            public void onError(int i, String s) {
                ErrorEnum ident = ErrorEnum.ident(i);
                ShowToast(ident.getMessage());
            }
        });
    }

    void registerUser(String account, String password, String email) {
        final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
        progress.setMessage("正在注册...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(account);
        bmobUser.setPassword(password);
        bmobUser.setEmailVerified(true);
        bmobUser.setEmail(email);
        bmobUser.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ShowToast("注册成功");
                progress.dismiss();
                Intent intent = new Intent(RegisterActivity.this, EnterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                ErrorEnum ident = ErrorEnum.ident(i);
                ShowToast(ident.getMessage());
                progress.dismiss();
            }
        });
    }

}
