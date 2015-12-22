package com.sagereal.zq.game.tank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Bomb {
	private float x;
	private float y;
	private float radius = 1;
	private boolean isBombAlive = true;
	private Paint mPaint;
	public Bomb(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Style.STROKE);
	}
	public boolean isBombAlive() {
		return isBombAlive;
	}
	public void setBombAlive(boolean isBombAlive) {
		this.isBombAlive = isBombAlive;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
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
	/** 炸弹半径的增加速度 */
	private int i = 1;
	public void drawBomb(Canvas canvas) {
		radius += i++;// 让炸弹的半径的增加速度快速增加
		canvas.drawCircle(x, y, radius, mPaint);
		mPaint.setAlpha(mPaint.getAlpha() - 10);// 降低画笔的透明度
		// 让炸弹不可用，就是让炸弹死掉了
		if (mPaint.getAlpha() < 10) {
			isBombAlive = false;
			System.out.println("炸弹死了");
		}
	}
}
