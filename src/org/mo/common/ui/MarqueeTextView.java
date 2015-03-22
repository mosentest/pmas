package org.mo.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by moziqi on 2015/3/22 0022.
 */
import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import org.mo.common.util.StringUtil;

public class MarqueeTextView extends TextView {

    /**
     * 是否停止滚动
     */
    private boolean mStopMarquee;
    private String mText;
    private float mCoordinateX;
    private float mTextWidth;

    private int time=3000;

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(String text) {
        this.mText = text;
        mTextWidth = getPaint().measureText(mText);
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0, time);
    }

    @Override
    protected void onAttachedToWindow() {
        mStopMarquee = false;
        if (StringUtil.notEmpty(mText))
            mHandler.sendEmptyMessageDelayed(0, time);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        mStopMarquee = true;
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (StringUtil.notEmpty(mText))
            canvas.drawText(mText, mCoordinateX, 40, getPaint());
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (Math.abs(mCoordinateX) > (mTextWidth + 50)) {
                        mCoordinateX = 0;
                        invalidate();
                        if (!mStopMarquee) {
                            sendEmptyMessageDelayed(0, time);
                        }
                    } else {
                        mCoordinateX -= 1;
                        invalidate();
                        if (!mStopMarquee) {
                            sendEmptyMessageDelayed(0, 60);
                        }
                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };
}