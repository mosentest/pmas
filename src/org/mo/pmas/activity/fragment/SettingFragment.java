package org.mo.pmas.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.bmob.v3.BmobUser;
import org.mo.pmas.activity.*;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.bmob.entity.MyUser;
import org.mo.pmas.util.SharePreferenceUtil;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private static SettingFragment mSettingFragment;
    private SharePreferenceUtil mSharedUtil;
    private Context mContext;
    private Button m_btn_setting_nologin;
    private TextView m_tv_set_name;
    private ImageView m_iv_open_voice;
    private ImageView m_iv_close_voice;
    private ImageView m_iv_open_vibrate;
    private ImageView m_iv_close_vibrate;
    private RelativeLayout m_layout_info;
    //    private RelativeLayout m_layout_blacklist;
    private RelativeLayout m_layout_weather;
    private RelativeLayout m_layout_neighborhood;
    private RelativeLayout m_rl_switch_voice;
    private RelativeLayout m_rl_switch_vibrate;
    private RelativeLayout m_layout_about;
    private LinearLayout m_ll_setting_loginned;


    public static SettingFragment getInstance(Context context) {
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
//        m_layout_blacklist = (RelativeLayout) findViewById(R.id.layout_blacklist);
        m_layout_weather = (RelativeLayout) findViewById(R.id.layout_weather);
        m_layout_neighborhood = (RelativeLayout) findViewById(R.id.layout_neighborhood);
        m_rl_switch_voice = (RelativeLayout) findViewById(R.id.rl_switch_voice);
        m_rl_switch_vibrate = (RelativeLayout) findViewById(R.id.rl_switch_vibrate);
        m_layout_about = (RelativeLayout) findViewById(R.id.layout_about);
        //设置用户名
        m_ll_setting_loginned = (LinearLayout) findViewById(R.id.ll_setting_loginned);
        m_tv_set_name = (TextView) findViewById(R.id.tv_set_name);
        //图片
        m_iv_close_vibrate = (ImageView) findViewById(R.id.iv_close_vibrate);
        m_iv_open_vibrate = (ImageView) findViewById(R.id.iv_open_vibrate);
        m_iv_open_voice = (ImageView) findViewById(R.id.iv_open_voice);
        m_iv_close_voice = (ImageView) findViewById(R.id.iv_close_voice);
        String currentUserName = PmasAppliaction.getInstance().getCurrentUserName();
        if (currentUserName.equals("请登录")) {
            m_btn_setting_nologin.setVisibility(View.VISIBLE);
            m_ll_setting_loginned.setVisibility(View.GONE);
        } else {
            m_ll_setting_loginned.setVisibility(View.VISIBLE);
            m_btn_setting_nologin.setVisibility(View.GONE);
            m_tv_set_name.setText(currentUserName);
        }

        //=======================================================================
        boolean isAllowVoice = mSharedUtil.isAllowVoice();
        if (isAllowVoice) {
            m_iv_open_voice.setVisibility(View.VISIBLE);
            m_iv_close_voice.setVisibility(View.INVISIBLE);
        } else {
            m_iv_open_voice.setVisibility(View.INVISIBLE);
            m_iv_close_voice.setVisibility(View.VISIBLE);
        }
        boolean isAllowVibrate = mSharedUtil.isAllowVibrate();
        if (isAllowVibrate) {
            m_iv_open_vibrate.setVisibility(View.VISIBLE);
            m_iv_close_vibrate.setVisibility(View.INVISIBLE);
        } else {
            m_iv_open_vibrate.setVisibility(View.INVISIBLE);
            m_iv_close_vibrate.setVisibility(View.VISIBLE);
        }
        //==============================================================
        m_btn_setting_nologin.setOnClickListener(this);
        m_layout_info.setOnClickListener(this);
//        m_layout_blacklist.setOnClickListener(this);
        m_layout_weather.setOnClickListener(this);
        m_layout_neighborhood.setOnClickListener(this);
        m_rl_switch_voice.setOnClickListener(this);
        m_rl_switch_vibrate.setOnClickListener(this);
        m_layout_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting_nologin:
                login();
                break;
            case R.id.layout_info:
                Intent intent = new Intent(getActivity(), SetMyInfoActivity.class);
                getActivity().startActivity(intent);
//                getActivity().finish();
                break;
            case R.id.layout_about:
                Intent intent2 = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.layout_neighborhood:
                Intent intent3 = new Intent(getActivity(), PeripheryActivity.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.rl_switch_voice:
                if (m_iv_open_voice.getVisibility() == View.VISIBLE) {
                    m_iv_open_voice.setVisibility(View.INVISIBLE);
                    m_iv_close_voice.setVisibility(View.VISIBLE);
                    mSharedUtil.setAllowVoiceEnable(false);
                } else {
                    m_iv_open_voice.setVisibility(View.VISIBLE);
                    m_iv_close_voice.setVisibility(View.INVISIBLE);
                    mSharedUtil.setAllowVoiceEnable(true);
                }
                break;
            case R.id.rl_switch_vibrate:
                if (m_iv_open_vibrate.getVisibility() == View.VISIBLE) {
                    m_iv_open_vibrate.setVisibility(View.INVISIBLE);
                    m_iv_close_vibrate.setVisibility(View.VISIBLE);
                    mSharedUtil.setAllowVibrateEnable(false);
                } else {
                    m_iv_open_vibrate.setVisibility(View.VISIBLE);
                    m_iv_close_vibrate.setVisibility(View.INVISIBLE);
                    mSharedUtil.setAllowVibrateEnable(true);
                }
                break;
        }
    }

    private void login() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
