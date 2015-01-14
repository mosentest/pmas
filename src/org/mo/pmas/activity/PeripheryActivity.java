package org.mo.pmas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.pmas.activity.application.PmasAppliaction;

/**
 * Created by moziqi on 2015/1/14 0014.
 */
public class PeripheryActivity extends BaseFramgmentActivity {
    private Button mButton;
    private TextView mTextView;
    private LocationClient mLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periphery);
    }

    @Override
    protected void toInitUI() {
        mLocationClient = ((PmasAppliaction) getApplication()).mLocationClient;
        mButton = (Button) findViewById(R.id.button_periphery);
        mTextView = (TextView) findViewById(R.id.textView_periphery);
        ((PmasAppliaction)getApplication()).mLocationResult = mTextView;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.start();
            }
        });
    }

    @Override
    protected void toUIOper() {

    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//设置定位模式
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setScanSpan(50000);//设置发起定位请求的间隔时间为5000ms
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onStop() {
        mLocationClient.stop();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }
}