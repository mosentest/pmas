package org.mo.pmas.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ValidateUtil;

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

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

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

    }

}
