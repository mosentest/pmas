package org.mo.common.ui.EditText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by moziqi on 2015/1/28 0028.
 */
public class LinedEditText extends EditText {
    private Paint linePaint;
    private float margin;
    private int paperColor;
    private Rect mRect;

    public LinedEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mRect = new Rect();
        linePaint = new Paint();
//        linePaint.setStyle(Paint.Style.STROKE);
//        linePaint.setColor(paperColor);
//        linePaint.setStrokeWidth(3);
    }

    public LinedEditText(Context context) {
        super(context);
        mRect = new Rect();
        linePaint = new Paint();
    }

    protected void onDraw(Canvas paramCanvas) {
        paramCanvas.drawColor(this.paperColor);
        Rect r = mRect;
        int lineCount = getLineCount();
        int height = getHeight();
        int lineHeight = getLineHeight();
        int m = 1 + height / lineHeight;
        if (lineCount < m)
            lineCount = m;
        int n = getLineBounds(0, r) + 10;
        paramCanvas.drawLine(0.0F, n, getRight(), n, this.linePaint);
        for (int i = 0; ; i++) {
            if (i >= lineCount) {
                setPadding(10 + (int) this.margin, 10, 0, 2);
                super.onDraw(paramCanvas);
                paramCanvas.restore();
                return;
            }
            n += lineHeight;
            paramCanvas.drawLine(0.0F, n, getRight(), n, this.linePaint);
            paramCanvas.save();
        }

    }
}