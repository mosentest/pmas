package org.mo.pmas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.activity.application.PmasAppliaction;

/**
 * Created by moziqi on 2015/1/8 0008.
 */
public class SetMyInfoActivity extends BaseFramgmentActivity implements View.OnClickListener {
    private Button btn_set_my_info_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_my_info);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
    }

    @Override
    protected void toInitUI() {

    }

    @Override
    protected void toUIOper() {

    }

    private void initUI() {
        btn_set_my_info_logout = (Button) findViewById(R.id.btn_set_my_info_logout);
        setOnClick();
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

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(SetMyInfoActivity.this, EnterActivity.class);
//        SetMyInfoActivity.this.startActivity(intent);
        SetMyInfoActivity.this.finish();
    }

    private void logout() {
        SharedPreferences preferences = PmasAppliaction.getInstance().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("autoLogin", false);
        edit.commit();
        if (PmasAppliaction.getInstance().isLogOut()) {
            PmasAppliaction.getInstance().exit();
            Intent intent = new Intent(SetMyInfoActivity.this, EnterActivity.class);
            SetMyInfoActivity.this.startActivity(intent);
        }
    }

}