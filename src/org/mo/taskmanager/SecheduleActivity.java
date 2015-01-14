package org.mo.taskmanager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import org.mo.pmas.activity.R;

public class SecheduleActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule);
        initView();

    }

    private void initView() {
//        titleBar = (TitleBar) findViewById(R.id.myTitleBar);
//        titleBar.setOnTitleBarClickListener(new titleBarClickListener() {
//            @Override
//            public void rightClick() {
//                Intent intent = new Intent(SecheduleActivity.this, AddTaskActivity.class);
//                startActivityForResult(intent, 0);
//            }
//
//            @Override
//            public void leftClick() {
//                // TODO Auto-generated method stub
//            }
//        });
    }
}
