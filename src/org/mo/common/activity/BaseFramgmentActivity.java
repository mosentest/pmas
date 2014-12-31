package org.mo.common.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
}
