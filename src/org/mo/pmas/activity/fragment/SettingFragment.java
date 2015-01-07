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
import org.mo.pmas.activity.EnterActivity;
import org.mo.pmas.activity.LoginActivity;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.application.PmasAppliaction;
import org.mo.pmas.bmob.entity.MyUser;
import org.mo.pmas.util.SharePreferenceUtil;

/**
 * Created by moziqi on 2015/1/4 0004.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private static SettingFragment mSettingFragment;
    private SharePreferenceUtil mSharedUtil;
    private View mRootView;
    private Context mContext;
    private Button m_btn_Logout;
    private Button m_btn_setting_login;
    private TextView m_tv_set_name;
    private TextView m_tv_set_back_list;
    private TextView m_tv_set_weather;
    private TextView m_tv_set_neighborhood;
    private TextView m_tv_set_about;
    private ImageView m_iv_open_voice;
    private ImageView m_iv_close_voice;
    private ImageView m_iv_open_vibrate;
    private ImageView m_iv_close_vibrate;
    private RelativeLayout m_layout_info;
    private RelativeLayout m_layout_blacklist;
    private RelativeLayout m_layout_weather;
    private RelativeLayout m_layout_neighborhood;
    private RelativeLayout m_rl_switch_voice;
    private RelativeLayout m_rl_switch_vibrate;
    private LinearLayout m_line1;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_setting, null);
//        m_btn_Logout = (Button) mRootView.findViewById(R.id.btn_logout);
//        m_btn_setting_login = (Button) mRootView.findViewById(R.id.btn_setting_login);
        m_layout_info = (RelativeLayout) mRootView.findViewById(R.id.layout_info);
        m_layout_blacklist = (RelativeLayout) mRootView.findViewById(R.id.layout_blacklist);

        String currentUserName = PmasAppliaction.getInstance().getCurrentUserName();
        if (currentUserName.equals("请登录")) {
//            m_btn_Logout.setVisibility(View.GONE);
//            m_layout_info.setVisibility(View.GONE);
//            m_layout_blacklist.setVisibility(View.GONE);
//            m_line1.setVisibility(View.GONE);
        } else {
          //  m_btn_setting_login.setVisibility(View.GONE);
        }
//        m_btn_setting_login.setOnClickListener(this);
//        m_btn_Logout.setOnClickListener(this);
        return mRootView;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_logout:
//                logout();
//                break;
//            case R.id.btn_setting_login:
//                login();
//                break;
//        }
    }

    private void login() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    private void logout() {
        SharedPreferences preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("autoLogin", false);
        edit.commit();
        if (PmasAppliaction.getInstance().isLogOut()) {
            Intent intent = new Intent(getActivity(), EnterActivity.class);
            this.getActivity().startActivity(intent);
            this.getActivity().finish();
        }
    }

}
