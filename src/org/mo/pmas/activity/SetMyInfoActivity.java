package org.mo.pmas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ConfigContract;
import org.mo.common.util.EncryptUtils;
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.znyunxt.entity.UserDetail;

/**
 * Created by moziqi on 2015/1/8 0008.
 */
public class SetMyInfoActivity extends BaseFramgmentActivity implements View.OnClickListener {

    private Button btn_set_my_info_logout;
    private AsyncHttpClient instance;
    private SharedPreferences preferences;
    private String username;

    private TextView tv_username;
    private TextView tv_name;
    private TextView tv_depart_name;
    private TextView tv_sex;
    private TextView tv_role_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_my_info);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
        preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

    }

    private void initUI() {
        btn_set_my_info_logout = (Button) findViewById(R.id.btn_set_my_info_logout);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_depart_name = (TextView) findViewById(R.id.tv_depart_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_role_name = (TextView) findViewById(R.id.tv_role_name);
        setOnClick();

        instance = HttpURLTools.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        username = preferences.getString(ConfigContract.USERNAME, null);
        if (username != null) {
            tv_username.setText(username);
            try {
                String encrypt3DES = EncryptUtils.Encrypt3DES(username, ConfigContract.CODE);
                String url = ConfigContract.SERVICE_SCHOOL + "loginController.do?getUserInfo";
                RequestParams params = new RequestParams();
                params.put("loginname", encrypt3DES);
                showErrorIms("加密后：" + encrypt3DES + "加密前:" + username);
                instance.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        showErrorIms(i + "--" + s);
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
//                        String s = new String(bytes);
                        Log.e(ConfigContract.CMD, s);
                        if (i == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String attributes = jsonObject.getString("attributes");
                                UserDetail userDetail = new UserDetail(attributes);
                                Log.e(ConfigContract.CMD, userDetail.toString());
                                if (userDetail != null) {
                                    tv_name.setText(userDetail.getName());
                                    tv_depart_name.setText(userDetail.getDepartname());
                                    if (userDetail.getSex() != null) {
                                        tv_sex.setText(Integer.parseInt(userDetail.getSex()) == 0 ? "男" : "女");
                                    }
                                    tv_role_name.setText(userDetail.getRolename());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(ConfigContract.CMD, e.getMessage());
                            }
                        } else {
                            showErrorIms(ConfigContract.GET_USER_INFO_ERROR);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ShowToast(ConfigContract.GET_USER_INFO_ERROR);
        }
    }

    public void setOnClick() {
        btn_set_my_info_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_my_info_logout:
                logout();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return true;
        }
    }

    private void logout() {
        HttpURLTools.closeClient();
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.commit();
        PmasAppliaction.getInstance().exit();
        Intent intent = new Intent(SetMyInfoActivity.this, EnterActivity.class);
        startActivity(intent);
        finish();
    }

}