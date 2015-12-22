package com.sagereal.zq.game.bird;

import com.sagereal.zq.game.PaintFactory;
import com.sagereal.zq.game.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class BirdGameView extends SurfaceView implements Callback {
	private Thread thread;
	private Paint mPaint;
	private SurfaceHolder sfh;
	private Bitmap bitmap;
	private boolean isAvailable = true;
	private int gravity = 1;
	private int speed = 0;
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public BirdGameView(Context context) {
		super(context);
		mPaint = PaintFactory.getPaint(Color.BLUE);
		sfh = getHolder();
		sfh.addCallback(this);
		setKeepScreenOn(true);
		x = winWidth / 2;
		y = winHeight / 2;
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		// 创建刷新线程
	}
	private void drawView() {
		Canvas canvas = null;
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(bitmap, x, y, mPaint);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}
	private float x;
	private float y;
	private float winWidth;
	private float winHeight;
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		winWidth = getWidth();
		winHeight = getHeight();
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isAvailable) {
					try {
						Thread.sleep(20);
						drawView();
						speed += gravity;
						y += speed;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		speed = -10;// 这里只需要控制小鸟的速度就可以了
		return super.onTouchEvent(event);
	}
}
