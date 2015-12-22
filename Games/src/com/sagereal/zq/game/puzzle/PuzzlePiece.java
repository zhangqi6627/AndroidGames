package com.sagereal.zq.game.puzzle;

import android.graphics.Bitmap;

public class PuzzlePiece {
	// 图片当前位置
	private int x;
	private int y;
	private Bitmap bitmap;
	public PuzzlePiece(int x, int y, Bitmap bitmap) {
		super();
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
	}
	private float xx;
	private float yy;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public void setXxYy(float xx, float yy) {
		this.xx = xx;
		this.yy = yy;
	}
	public float getXx() {
		return xx;
	}
	public void setXx(float xx) {
		this.xx = xx;
	}
	public float getYy() {
		return yy;
	}
	public void setYy(float yy) {
		this.yy = yy;
	}
}
