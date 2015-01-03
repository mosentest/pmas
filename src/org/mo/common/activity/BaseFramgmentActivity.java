package org.mo.common.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import org.mo.pmas.comm.Constant;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public class BaseFramgmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, Constant.BMOBNAME);
    }

    /**
     * 自定义一个Toast方法
     *
     * @param msg 要输出的提示信息
     */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
