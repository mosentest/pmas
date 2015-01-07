package org.mo.pmas.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    private TextView m_tv_set_name;
    private TextView m_tv_set_back_list;
    private ImageView m_iv_open_voice;
    private ImageView m_iv_close_voice;
    private ImageView m_iv_open_vibrate;
    private ImageView m_iv_close_vibrate;


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
        m_btn_Logout = (Button) mRootView.findViewById(R.id.btn_logout);
        m_btn_Logout.setOnClickListener(this);
        return mRootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferences preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("autoLogin", false);
        edit.commit();
        PmasAppliaction.getInstance().exit();
        MyUser.logOut(getActivity().getApplicationContext());//清除缓存用户对象
        Intent intent = new Intent(mContext, LoginActivity.class);
        this.getActivity().startActivity(intent);
        this.getActivity().finish();
    }

}
