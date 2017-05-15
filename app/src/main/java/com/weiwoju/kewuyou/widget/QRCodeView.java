package com.weiwoju.kewuyou.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class QRCodeView extends View
{
	private static final long ANIMATION_DELAY=150L;
	private static final int COLOR=0xFF3B91CD;
	private static final int CORNER_WIDTH=5;
	private static final int CORNER_HEIGHT=36;

	private final Paint paint;
	private final int DEFAULT_WIDTH=15;
	private int top=DEFAULT_WIDTH;

	public QRCodeView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		paint=new Paint();
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		int width=canvas.getWidth();
		int height=canvas.getHeight();

		paint.setColor(COLOR);
		canvas.drawRect(0, 0, CORNER_HEIGHT, CORNER_WIDTH, paint);
		canvas.drawRect(width - CORNER_HEIGHT, 0, width, CORNER_WIDTH, paint);
		canvas.drawRect(0, 0, CORNER_WIDTH, CORNER_HEIGHT, paint);
		canvas.drawRect(0, height - CORNER_HEIGHT, CORNER_WIDTH, height, paint);
		canvas.drawRect(width - CORNER_WIDTH, 0, width, CORNER_HEIGHT, paint);
		canvas.drawRect(width - CORNER_WIDTH, height - CORNER_HEIGHT, width, height, paint);
		canvas.drawRect(0, height - CORNER_WIDTH, CORNER_HEIGHT, height, paint);
		canvas.drawRect(width - CORNER_HEIGHT, height - CORNER_WIDTH, width, height, paint);

		if(height>40) {
			canvas.drawRect(DEFAULT_WIDTH, top, width - DEFAULT_WIDTH, top + CORNER_WIDTH, paint);
			top=top+CORNER_WIDTH*3;
			if((top+DEFAULT_WIDTH)>height)
				top=DEFAULT_WIDTH;
		}

		postInvalidateDelayed(ANIMATION_DELAY, 0, 0, width, height);
	}
}
