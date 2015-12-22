package com.sagereal.zq.game.fish;

import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.view.MotionEvent;
import com.sagereal.zq.game.BaseView;
import com.sagereal.zq.game.PaintFactory;
import com.sagereal.zq.game.R;

/**
 * @如何添加小鱼游动的动画???
 * @控制小鱼从左右两边出来???
 * @控制小鱼的方向？？？？
 * @控制
 */
public class FishGameView extends BaseView {
	private Bitmap bitmap;
	private Paint rPaint;
	private int bWidth;
	private int bHeight;
	// private float x, y;
	private Fish myFish;
	private FishManager fishManager = FishManager.getInstance();
	public FishGameView(Context context) {
		super(context);
	}
	@Override
	protected void initView() {
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		bWidth = bitmap.getWidth();
		bHeight = bitmap.getHeight();
		rPaint = PaintFactory.getPaint(Color.RED);
		rPaint.setTextSize(20);
		rPaint.setTextAlign(Align.CENTER);
		mPaint.setTextSize(15);
		mPaint.setTextAlign(Align.CENTER);
		myFish = new Fish((winWidth - bWidth) / 2, (winHeight - bHeight) / 2, bitmap, 10, 1);
		// 启动刷新线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (myFish.isAlive()) {
						Thread.sleep(20);
						handler.sendEmptyMessage(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1: {// 定时添加一些小鱼
					if ((++count) % 100 == 0) {
						int size = new Random().nextInt(20);
						fishManager.addFish((new Random().nextFloat()) * (winWidth - bWidth), -bHeight, size);
					}
					postInvalidate();
					break;
				}
			}
		};
	};
	int count = 0;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (myFish != null && myFish.isAlive()) {
			// 画我控制的小鱼
			if (!myFish.isLeft()) {
				//
				Matrix matrix = new Matrix();
				matrix.setRotate(180, myFish.getBitmap().getWidth() / 2, myFish.getBitmap().getHeight() / 2);
				Bitmap mBitmap = Bitmap.createBitmap(myFish.getBitmap(), 0, 0, myFish.getBitmap().getWidth(), myFish.getBitmap().getHeight(), matrix, false);
				canvas.drawBitmap(mBitmap, new Rect(0, 0, bWidth, bHeight), myFish.getRect(), mPaint);
			} else {
				canvas.drawBitmap(myFish.getBitmap(), new Rect(0, 0, bWidth, bHeight), myFish.getRect(), mPaint);
			}
			canvas.drawText(myFish.getSize() + "", myFish.getX() + myFish.getWidth() / 2, myFish.getY() + myFish.getHeight() / 2 - (mPaint.ascent() - mPaint.descent()) / 2, rPaint);
		} else if (myFish != null && !myFish.isAlive()) {
			// showDialog("被吃了");
			showToast("被吃了");
			return;
		}
		// 画其他鱼
		ArrayList<Fish> fishs = fishManager.getAllFishs();
		synchronized (fishs) {
			for (Fish fish : fishs) {
				if (fish.isAlive()) {
					canvas.drawBitmap(fish.getBitmap(), new Rect(0, 0, bWidth, bHeight), fish.getRect(), mPaint);
					canvas.drawText(fish.getSize() + "", fish.getX() + fish.getWidth() / 2, fish.getY() + fish.getHeight() / 2 - (mPaint.ascent() - mPaint.descent()) / 2, mPaint);
					if (myFish.getRect().intersect(fish.getRect())) {
						myFish.eatFish(fish);
					}
				}
			}
		}
	}
	// 控制小鱼的拖动
	private boolean isMoving = false;
	private float dX, dY;
	private float lastY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float xx = event.getX();
		float yy = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (myFish.getX() < xx && xx < myFish.getX() + myFish.getWidth() && myFish.getY() < yy && yy < myFish.getY() + myFish.getHeight()) {
					isMoving = true;
					dX = xx - myFish.getX();
					dY = yy - myFish.getY();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (isMoving) {
					if (event.getY() - lastY > 0) {
						// 下
						myFish.setDirection(false);
					} else if (event.getY() - lastY < 0) {
						// 上
						myFish.setDirection(true);
					}
					lastY = event.getY();
					myFish.setXY(xx - dX, yy - dY);
				} else {
					return false;
				}
				break;
			case MotionEvent.ACTION_UP:
				isMoving = false;
				break;
		}
		postInvalidate();
		return true;
	}
}
