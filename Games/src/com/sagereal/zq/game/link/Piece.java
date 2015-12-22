package com.sagereal.zq.game.link;

import android.graphics.Bitmap;

public class Piece {
	private int x;
	private int y;
	private Bitmap bitmap;
	private int imageId;
	public Piece() {
		super();
	}
	public Piece(int x, int y, int imageId) {
		super();
		this.x = x;
		this.y = y;
		this.imageId = imageId;
	}
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
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void init() {
		this.x = 0;
		this.y = 0;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public int getImageId() {
		return this.imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	@Override
	public boolean equals(Object o) {
		Piece piece = (Piece) o;
		if (piece.getX() == x && piece.getY() == y) {
			return true;
		}
		return false;
	}
	public boolean isSameImage(Piece piece) {
		return this.imageId == piece.getImageId();
	}
}
