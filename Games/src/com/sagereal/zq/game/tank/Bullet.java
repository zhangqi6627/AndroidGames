package com.sagereal.zq.game.tank;

import com.sagereal.zq.game.AppConfig;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Bullet {
	private float x;
	private float y;
	private Direction direction;
	private float speed;
	private Paint mPaint;
	public Bullet(float x, float y, Direction direction) {
		this(x, y, direction, 3);
	}
	public Bullet(float x, float y, Direction direction, float speed) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = speed;
		mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Style.FILL);
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	/** 画子弹 */
	public void drawBullet(Canvas canvas) {
		canvas.drawCircle(x, y, 2, mPaint);
	}
	private boolean isBulletAlive = false;
	public boolean isBulletAlive() {
		return isBulletAlive;
	}
	public void setBulletAlive(boolean isBulletAlive) {
		this.isBulletAlive = isBulletAlive;
	}
	/** 移动子弹 */
	public void moveBullet() {
		new Thread(new Runnable() {
			public void run() {
				while (isBulletAlive) {
					try {
						Thread.sleep(20);
						switch (direction) {
							case NORTH:
								y -= speed;
								break;
							case EAST:
								x += speed;
								break;
							case SOUTH:
								y += speed;
								break;
							case WEST:
								x -= speed;
								break;
						}
						// 子弹飞出屏幕
						if (x > AppConfig.winWidth + 1 || x < -1 || y > AppConfig.winWidth + 1 || y < -1) {
							isBulletAlive = false;
							System.out.println("子弹死掉了");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
