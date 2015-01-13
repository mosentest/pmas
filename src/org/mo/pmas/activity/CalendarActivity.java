package org.mo.pmas.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.ViewFlipper;
import org.mo.common.activity.BaseFramgmentActivity;
import org.mo.common.ui.borderText.BorderText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by moziqi on 2015/1/13 0013.
 */
public class CalendarActivity extends BaseFramgmentActivity implements GestureDetector.OnGestureListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    @Override
    protected void toInitUI() {
    }

    @Override
    protected void toUIOper() {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}