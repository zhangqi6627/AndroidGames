package com.sagereal.zq.game.fish;

import com.sagereal.zq.game.AppConfig;
import com.sagereal.zq.game.R;
import android.graphics.Bitmap;
import android.graphics.RectF;

public class Fish {
	/** 鱼的坐标 */
	private float x, y;
	/** 鱼的图片 */
	private Bitmap bitmap;
	/** 鱼的大小 */
	private int size = 1;
	/** 小鱼是否活着 */
	private boolean isAlive = true;
	/** 小鱼的朝向 */
	private boolean isLeft = true;
	public Fish(float x, float y, Bitmap bitmap, int size, int type) {
		super();
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
		this.size = size;
		// 1：我的小鱼 2：别人的小鱼
		if (type == 2) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							Thread.sleep(20);
							move();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	/** 移动,这里可以添加动画？？？？？？ */
	public void move() {
		y++;
		if (y > AppConfig.winHeight) {
			isAlive = false;
		}
	}
	/** 吃鱼 */
	public boolean eatFish(Fish fish) {
		if (this.size > fish.getSize()) {
			// 吃
			fish.setAlive(false);
			size += fish.getSize();// 这里可以
			return true;
		} else {
			// 被吃
			isAlive = false;
			return false;
		}
	}
	/** 图片默认，大小默认 */
	public Fish(float x, float y) {
		this(x, y, AppConfig.getBitmapById(R.drawable.icon0), 1, 2);
	}
	/** 大小默认 */
	public Fish(float x, float y, Bitmap bitmap) {
		this(x, y, bitmap, 1, 2);
	}
	public float getX() {
		return x;
	}
	// public void setX(float x) {
	// this.x = x;
	// }
	public float getY() {
		return y;
	}
	// public void setY(float y) {
	// this.y = y;
	// }
	public void setXY(float x, float y) {
		if (x < 0) {
			x = 0;
		} else if (x > AppConfig.winWidth - getWidth()) {
			x = AppConfig.winWidth - getWidth();
		}
		if (y < 0) {
			y = 0;
		} else if (y > AppConfig.winHeight - getHeight()) {
			y = AppConfig.winHeight - getHeight();
		}
		this.x = x;
		this.y = y;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	/** 得到小鱼的边框,用于碰撞检测 */
	public RectF getRect() {
		return new RectF(x, y, x + bitmap.getWidth() - 30 + size, y + bitmap.getHeight() - 30 + size);
	}
	public float getWidth() {
		return bitmap.getWidth() - 30 + size;
	}
	public float getHeight() {
		return bitmap.getHeight() - 30 + size;
	}
	/** 设置小鱼的游动方向 */
	public void setDirection(boolean isLeft) {
		this.isLeft = isLeft;
	}
	/**
	 * @return true:left false:right
	 */
	public boolean isLeft() {
		return this.isLeft;
	}
}
