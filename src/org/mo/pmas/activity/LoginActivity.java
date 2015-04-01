package org.mo.pmas.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.util.ClientUitl;
import org.mo.common.util.ConfigContract;
import org.mo.common.util.EncryptUtils;
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.znyunxt.entity.UserDetail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class LoginActivity extends BaseFramgmentActivity implements View.OnClickListener {
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private ProgressDialog progress;
    private EditText et_code;
    private ImageView iv_code;
    private AsyncHttpClient instance;


    String strs[] = new String[2];

    private static final String service = "http://www.znyunxt.cn";
    private SharedPreferences preferences;
    private HttpContext httpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        et_code = (EditText) findViewById(R.id.et_code);
        iv_code = (ImageView) findViewById(R.id.iv_code);


        if (mLogin != null) {
            mLogin.setOnClickListener(this);
        }

        preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", MODE_PRIVATE);
        final String username = preferences.getString("username", null);
        final String password = preferences.getString("password", null);

        //TODO 设置默认值
//        mUsername.setText(username);
//        mPassword.setText(password);
        mUsername.setText(ConfigContract.teacher_username);
        mPassword.setText(ConfigContract.teacher_password);

        iv_code.setOnClickListener(this);
        iv_code.setTag("vocde");

        //初始化
        instance = HttpURLTools.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String loginUrl = service + "/cas/login";
        String _services = null;
        try {
            _services = "service=" + URLEncoder.encode(ConfigContract.SERVICE_SCHOOL + "cloudLoginController.do?cloudlogin", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String ltUrl = loginUrl + "?" + _services + "&get-lt=true&n=" + new Date().getTime() + "&randomNum=" + new Random().nextDouble();
        ltUrl += "&username=" + mUsername.getText().toString().trim() + "&password=" + mPassword.getText().toString().trim();
        instance.get(ltUrl, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, byte[] bytes) {

//                //保持jssesionid
//                PersistentCookieStore myCookieStore = new PersistentCookieStore(LoginActivity.this);
//                instance.setCookieStore(myCookieStore);
//                httpContext = instance.getHttpContext();
//                CookieStore cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
//                if (cookies != null) {
//                    for (Cookie c : cookies.getCookies()) {
//                        showErrorIms("成功登录--" + c.getName() + "--" + c.getValue());
//                    }
//                } else {
//                    showErrorIms("loginActivity no cookies");
//                    SharedPreferences.Editor edit = preferences.edit();
//                    edit.clear().commit();
//                }

                //解析字符串
                String s = new String(bytes);
                strs = s.split("\n");
                Pattern p = Pattern.compile("\"([^\"]+)\"");
                Matcher m = null;
                for (int i = 0; i < strs.length; i++) {
                    m = p.matcher(strs[i]);
                    if (m.find()) {
                        String group = m.group(1);
                        strs[i] = group;
                        Log.e("moziqi:", group);
                    }
                }

                //显示验证码
                instance.get(ClientUitl.VCODE_URL, new FileAsyncHttpResponseHandler(LoginActivity.this) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        ShowToast("网络问题，验证码无法显示");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File response) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bm = BitmapFactory.decodeFile(response.getPath(), options);
                        iv_code.setImageBitmap(bm);
                    }
                });
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ShowToast("请打开网络");
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
            case R.id.btn_login:
                user2login();
                break;
            case R.id.iv_code:
                //显示验证码
                instance.get(ClientUitl.VCODE_URL, new FileAsyncHttpResponseHandler(this) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                        ShowToast("网络问题，验证码无法显示");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File response) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bm = BitmapFactory.decodeFile(response.getPath(), options);
                        iv_code.setImageBitmap(bm);
                    }
                });
                break;
        }
    }

    private void user2login() {
        Log.e("moziqi", strs.toString());
        progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("正在登录...");
        progress.setCanceledOnTouchOutside(false);
        final String username2 = mUsername.getText().toString().trim();
        final String password2 = mPassword.getText().toString().trim();
        String loginUrl = service + "/cas/login";
        //这里才是登录模块主要功能
        RequestParams params = new RequestParams();
        try {
            params.put("service", URLEncoder.encode(ConfigContract.SERVICE_SCHOOL + "cloudLoginController.do?cloudlogin", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("username", username2);
        params.put("password", password2);
        params.put("lt", strs[0]);
        params.put("execution", strs[1]);
        params.put("_eventId", "submit");
        params.put("vcode", et_code.getText().toString().trim());
        Log.e(ConfigContract.CMD, loginUrl + params.toString());
        instance.setEnableRedirects(false);
        instance.post(loginUrl, params, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                if (!LoginActivity.this.isFinishing()) {
                    try {
                        progress.show();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int code, Header[] headers, String s, Throwable throwable) {
                Log.e(ConfigContract.CMD, "登录onFailure:" + code);
                if (progress != null) {
                    progress.dismiss();
                }
                if (code == 302) {
                    //TODO 重新设置为可以重定向
                    instance.setEnableRedirects(true);
                    instance.post("http://www.znyunxt.cn:9080/school/cloudLoginController.do?applogin",null,new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            showErrorIms(i+"");
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            showErrorIms(i+"");
                        }
                    });
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(ConfigContract.USERNAME, username2);
                    edit.putString(ConfigContract.PASSWORD, password2);
                    edit.commit();
                    LoginActivity.this.finish();
                    overridePendingTransition(R.anim.myenteranim, R.anim.myexitanim);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progress != null) {
                    progress.dismiss();
                }
            }

            @Override
            public void onSuccess(int code, Header[] headers, String s) {
                Log.e(ConfigContract.CMD, "onSuccess" + code + "" + s);
            }
        });
    }

}
