package org.mo.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import org.mo.common.util.ConfigContract;
import org.mo.pmas.activity.application.PmasAppliaction;

/**
 * Created by moziqi on 2014/12/31 0031.
 */
public abstract class BaseFramgmentActivity extends FragmentActivity {

    private static final String TAG = ConfigContract.CMD;
    private Toast mToast;
    protected int mScreenWidth;
    protected int mScreenHeight;

    @Override
    protected void onResume() {
        super.onResume();
        toUIOper();
    }

    public BaseFramgmentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PmasAppliaction.getInstance().addActivity(this);
        DisplayMetrics metric = new DisplayMetrics();
        //获取屏幕大小
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toInitUI();
    }

    public void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text,
                                Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });

        }
    }

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }


    public void showErrorIms(String i) {
        Log.e(TAG, i);
    }

    protected abstract void toInitUI();

    protected abstract void toUIOper();
}
