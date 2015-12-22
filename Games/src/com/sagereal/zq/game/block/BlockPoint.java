package com.sagereal.zq.game.block;
public class BlockPoint {
	private int x;
	private int y;
	public BlockPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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
	public void moveLeft() {
		x--;
		if (x < 0) {
			x = 0;
		}
	}
	public void moveRight() {
		x++;
		if (x > 9) {
			x = 9;
		}
	}
	public void moveDown() {
		y++;
		if (y > 9) {
			y = 9;
		}
	}
}
