package com.sagereal.zq.game.tank;

import java.util.Random;
import android.graphics.Color;

public class EnemyTank extends Tank {
	public EnemyTank() {
		this(100, 200, Direction.NORTH);
	}
	public EnemyTank(float x, float y, Direction direction) {
		super(x, y, direction);
		setColor(Color.CYAN);
		setSpeed(1);
		steps = new Random().nextInt(50) + 25;// 0.5 - 1.5秒换一次方向
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(20);
						// 重新设置方向
						if ((++countStep) % steps == 0) {
							countStep = 0;
							int iDirection = new Random().nextInt(4);
							switch (iDirection) {
								case 0:
									setDirection(Direction.NORTH);
									break;
								case 1:
									setDirection(Direction.WEST);
									break;
								case 2:
									setDirection(Direction.SOUTH);
									break;
								case 3:
									setDirection(Direction.EAST);
									break;
							}
						}
						moveTank();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	private int countStep;
	private int steps;
	// @Override
	// protected void drawTank(Canvas canvas) {
	// super.drawTank(canvas);
	// }
	// @Override
	// protected void moveTank() {
	// super.moveTank();
	// }
}
