package com.weiwoju.kewuyou.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhangguobing on 2017/4/27.
 */
public class LeftItemTextView extends TextView {

    private final Paint paint;
    private int width;
    private int height;

    public LeftItemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, height - 1, width, height - 1, paint);
        canvas.drawLine(width - 1, height - 1, width - 1, 0, paint);
        canvas.drawLine(0, 0, 0, height-1, paint);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }
}
