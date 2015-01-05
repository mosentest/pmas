package org.mo.common.ui.switchview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import org.mo.pmas.activity.LoginActivity;
import org.mo.pmas.activity.R;
import org.mo.pmas.activity.application.PmasAppliaction;

/**
 * Created by moziqi on 2015/1/5 0005.
 */
public class SwitchViewActivity extends Activity implements
        OnViewChangeListener, View.OnClickListener {

    private MyScrollLayout mScrollLayout;
    private ImageView[] mImageViews;
    private int mViewCount;
    private int mCurSel;
    private Button m_btn_to_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences guide = PmasAppliaction.getInstance().getSharedPreferences("guide", Context.MODE_PRIVATE);
        boolean first = guide.getBoolean("first", false);
        if(first){
            Intent intent = new Intent(SwitchViewActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            setContentView(R.layout.activity_switch_view);
            init();
        }
    }

    private void init() {
        mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llayout);
        //如果不想写死在布局文件里也可以动态添加，这里动态添加一个作为示例
        // 动态添加一个layout控件
//        LinearLayout layout = new LinearLayout(this);
//        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//        layout.setBackgroundResource(R.drawable.mm_1);
//        mScrollLayout.addView(layout);

        // 动态添加一个imageView控件
//        ImageView imageView = new ImageView(this);
//        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT);
//        lp.gravity = Gravity.CENTER;
//        imageView.setLayoutParams(lp);
//        imageView.setPadding(15, 15, 15, 15);
//        imageView.setImageResource(R.drawable.white_dot);
//        linearLayout.addView(imageView);

        mViewCount = mScrollLayout.getChildCount();
        mImageViews = new ImageView[mViewCount];
        for (int i = 0; i < mViewCount; i++) {
            mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
            mImageViews[i].setEnabled(true);
            mImageViews[i].setOnClickListener(this);
            mImageViews[i].setTag(i);
        }
        mCurSel = 0;
        mImageViews[mCurSel].setEnabled(false);
        mScrollLayout.SetOnViewChangeListener(this);

        m_btn_to_login = (Button) findViewById(R.id.btn_to_login);
        m_btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences guide = PmasAppliaction.getInstance().getSharedPreferences("guide", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = guide.edit();
                edit.putBoolean("first",true);
                edit.commit();
                Intent intent = new Intent(SwitchViewActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setCurPoint(int index) {
        if (index < 0 || index > mViewCount - 1 || mCurSel == index) {
            return;
        }

        mImageViews[mCurSel].setImageResource(R.drawable.white_dot);
        mImageViews[index].setImageResource(R.drawable.green_dot);

        mImageViews[mCurSel].setEnabled(true);
        mImageViews[index].setEnabled(false);
        mCurSel = index;
    }

    @Override
    public void OnViewChange(int view) {
        setCurPoint(view);
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) (v.getTag());
        setCurPoint(pos);
        mScrollLayout.snapToScreen(pos);
    }
}