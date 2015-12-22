package com.sagereal.zq.game.piano;

import com.sagereal.zq.game.PaintFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class PianoGameView extends View {
	private Context mContext;
	private Paint mPaint;
	public PianoGameView(Context context) {
		super(context);
		init(context);
	}
	private void init(Context context) {
		mContext = context;
		mPaint = PaintFactory.getPaint(Color.BLACK);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
