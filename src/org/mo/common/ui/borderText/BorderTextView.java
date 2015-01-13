package org.mo.common.ui.borderText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderTextView extends TextView {

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);
        paint.setStyle(Style.STROKE);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(2, 0, this.getWidth() - 2, this.getHeight() - 2);
        canvas.drawRoundRect(rectF, 8, 8, paint);
    }

}
