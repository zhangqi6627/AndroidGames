package com.sagereal.zq.game.tank;

import android.graphics.Color;

public class MyTank extends Tank {
	public MyTank() {
		this(200, 100, Direction.SOUTH);
	}
	public MyTank(float x, float y, Direction direction) {
		super(x, y, direction);
		setColor(Color.YELLOW);
		// 设置速度
		setSpeed(3);
	}
	// @Override
	// protected void drawTank(Canvas canvas) {
	// super.drawTank(canvas);
	// }
	// @Override
	// protected void moveTank() {
	// super.moveTank();
	// }
}
