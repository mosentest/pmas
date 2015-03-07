package org.mo.pmas.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mo.common.util.ConfigContract;
import org.mo.common.util.EncryptUtils;
import org.mo.common.util.HttpURLTools;
import org.mo.pmas.activity.AboutActivity;
import org.mo.pmas.activity.AttendanceActivity;
import org.mo.pmas.activity.LoginActivity;
import org.mo.pmas.activity.PeripheryActivity;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.ScoreActivity;
import org.mo.pmas.activity.SetMyInfoActivity;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.util.SharePreferenceUtil;
import org.mo.znyunxt.entity.UserDetail;

import java.io.IOException;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private static SettingFragment mSettingFragment;
    private SharePreferenceUtil mSharedUtil;
    private Context mContext;
    private Button m_btn_setting_nologin;
    private TextView m_tv_set_name;
    //    private ImageView m_iv_open_voice;
//    private ImageView m_iv_close_voice;
//    private ImageView m_iv_open_vibrate;
//    private ImageView m_iv_close_vibrate;
    private RelativeLayout m_layout_info;
    private RelativeLayout layout_score_search;
    private RelativeLayout layout_kaoqin_search;
    private RelativeLayout m_layout_neighborhood;
    //    private RelativeLayout m_rl_switch_voice;
//    private RelativeLayout m_rl_switch_vibrate;
    private RelativeLayout m_layout_about;
    private LinearLayout m_ll_setting_loginned;
    private LinearLayout ll_setting_loginned;
    private SharedPreferences preferences;
    private String username;


    public synchronized static SettingFragment getInstance(Context context) {
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment(context);
        }
        return mSettingFragment;
    }

    public SettingFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedUtil = PmasAppliaction.getInstance().getSpUtil();
        preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = preferences.getString("username", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        m_btn_setting_nologin = (Button) findViewById(R.id.btn_setting_nologin);
        m_layout_info = (RelativeLayout) findViewById(R.id.layout_info);
        layout_score_search = (RelativeLayout) findViewById(R.id.layout_score_search);
        layout_kaoqin_search = (RelativeLayout) findViewById(R.id.layout_kaoqin_search);
        m_layout_neighborhood = (RelativeLayout) findViewById(R.id.layout_neighborhood);
        m_layout_about = (RelativeLayout) findViewById(R.id.layout_about);
        m_ll_setting_loginned = (LinearLayout) findViewById(R.id.ll_setting_loginned);
        m_tv_set_name = (TextView) findViewById(R.id.tv_set_name);
        m_btn_setting_nologin.setOnClickListener(this);
        m_layout_info.setOnClickListener(this);
        layout_score_search.setOnClickListener(this);
        layout_kaoqin_search.setOnClickListener(this);
        m_layout_neighborhood.setOnClickListener(this);
        m_layout_about.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (username == null) {
            m_btn_setting_nologin.setVisibility(View.VISIBLE);
            m_ll_setting_loginned.setVisibility(View.GONE);
        } else {
            m_ll_setting_loginned.setVisibility(View.VISIBLE);
            m_btn_setting_nologin.setVisibility(View.GONE);
            //设置用户名
            m_tv_set_name.setText(username);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_setting_nologin:
                login();
                break;
            case R.id.layout_info:
                //TODO 跳转页面
                intent = new Intent(getActivity(), SetMyInfoActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.layout_about:
                intent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.layout_neighborhood:
                intent = new Intent(getActivity(), PeripheryActivity.class);
                getActivity().startActivity(intent);
                break;
//            case R.id.rl_switch_voice:
//                if (m_iv_open_voice.getVisibility() == View.VISIBLE) {
//                    m_iv_open_voice.setVisibility(View.INVISIBLE);
//                    m_iv_close_voice.setVisibility(View.VISIBLE);
//                    mSharedUtil.setAllowVoiceEnable(false);
//                } else {
//                    m_iv_open_voice.setVisibility(View.VISIBLE);
//                    m_iv_close_voice.setVisibility(View.INVISIBLE);
//                    mSharedUtil.setAllowVoiceEnable(true);
//                }
//                break;
//            case R.id.rl_switch_vibrate:
//                if (m_iv_open_vibrate.getVisibility() == View.VISIBLE) {
//                    m_iv_open_vibrate.setVisibility(View.INVISIBLE);
//                    m_iv_close_vibrate.setVisibility(View.VISIBLE);
//                    mSharedUtil.setAllowVibrateEnable(false);
//                } else {
//                    m_iv_open_vibrate.setVisibility(View.VISIBLE);
//                    m_iv_close_vibrate.setVisibility(View.INVISIBLE);
//                    mSharedUtil.setAllowVibrateEnable(true);
//                }
//                break;
            case R.id.layout_score_search:
                intent = new Intent(getActivity(), ScoreActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.layout_kaoqin_search:
                intent = new Intent(getActivity(), AttendanceActivity.class);
                getActivity().startActivity(intent);
                break;
            default:
                ShowToast("错误");
        }
    }

    private void login() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        getActivity().startActivity(intent);
        //TODO 这里销毁主界面，导致很多问题，暂时不知道怎么处理
        getActivity().finish();
    }
}
