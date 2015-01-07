package org.mo.pmas.activity.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import cn.bmob.v3.Bmob;
import org.mo.common.util.FileUtil;
import org.mo.common.util.ToastUtil;
import org.mo.pmas.bmob.entity.MyUser;
import org.mo.pmas.comm.Constant;
import org.mo.pmas.util.SharePreferenceUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by moziqi on 2014/12/26 0026.
 */
public class PmasAppliaction extends Application {
    // 单例模式，才能及时返回数据
    private SharePreferenceUtil mSpUtil;
    public static final String PREFERENCE_NAME = "_sharedinfo";
    private List<Activity> activityList = new LinkedList<Activity>();
    private static PmasAppliaction pmasAppliaction;

    public static PmasAppliaction getInstance() {
        return pmasAppliaction;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pmasAppliaction = this;
        Bmob.initialize(this, Constant.BMOBNAME);
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public synchronized SharePreferenceUtil getSpUtil() {
        if (mSpUtil == null) {
            String sharedName = PREFERENCE_NAME;
            mSpUtil = new SharePreferenceUtil(this, sharedName);
        }
        return mSpUtil;
    }

    public boolean isLogOut() {
        MyUser.logOut(getApplicationContext());//清除缓存用户对象
        MyUser currentUser = MyUser.getCurrentUser(getApplicationContext(), MyUser.class);
        if (currentUser == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 没有登录就返回 请登录,有用户登录就显示当前用户
     *
     * @return
     */
    public String getCurrentUserName() {
        MyUser currentUser = MyUser.getCurrentUser(getApplicationContext(), MyUser.class);
        if (currentUser == null) {
            return "请登录";
        } else {
            return currentUser.getUsername();
        }
    }
}
