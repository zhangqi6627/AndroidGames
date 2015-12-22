package com.sagereal.zq.game.tank;

import java.util.ArrayList;
import com.sagereal.zq.game.AppConfig;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Tank {
	private float x;
	private float y;
	private Direction direction;
	private Paint mPaint;
	private ArrayList<Bullet> bullets;
	/** 速度，默认为1 */
	private int speed = 1;
	/** 坦克的颜色 */
	private int color;
	/** 判断坦克是否还活着 */
	private boolean isTankAlive = true;
	public boolean isTankAlive() {
		return isTankAlive;
	}
	public void setTankAlive(boolean isTankAlive) {
		this.isTankAlive = isTankAlive;
	}
	protected void setColor(int color) {
		this.color = color;
		mPaint.setColor(color);
	}
	public Tank() {
		this(100, 100, Direction.NORTH);
	}
	public Tank(float x, float y, Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		mPaint = new Paint();
		color = Color.YELLOW;
		mPaint.setColor(color);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Style.STROKE);
		// 初始化子弹（最多5个）
		bullets = new ArrayList<Bullet>(5);
		bullets.add(new Bullet(-10, -10, Direction.NORTH));
		bullets.add(new Bullet(-10, -10, Direction.NORTH));
		bullets.add(new Bullet(-10, -10, Direction.NORTH));
		bullets.add(new Bullet(-10, -10, Direction.NORTH));
		bullets.add(new Bullet(-10, -10, Direction.NORTH));
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
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	/** 画坦克 */
	protected void drawTank(Canvas canvas) {
		switch (direction) {
			case NORTH:
				canvas.drawLine(x, y, x, y - 20, mPaint);
				canvas.drawLine(x, y - 20, x - 5, y - 20 + 5, mPaint);
				canvas.drawLine(x, y - 20, x + 5, y - 20 + 5, mPaint);
				// 大铁箱
				canvas.drawRect(x - 10, y - 10, x + 10, y + 10, mPaint);
				// 履带
				canvas.drawRect(x - 20, y - 20, x - 10, y + 20, mPaint);
				canvas.drawRect(x + 20, y - 20, x + 10, y + 20, mPaint);
				// 圆
				canvas.drawCircle(x, y, 7, mPaint);
				break;
			case EAST:
				canvas.drawLine(x, y, x + 20, y, mPaint);
				canvas.drawLine(x + 20, y, x + 20 - 5, y + 5, mPaint);
				canvas.drawLine(x + 20, y, x + 20 - 5, y - 5, mPaint);
				// 大铁箱
				canvas.drawRect(x - 10, y - 10, x + 10, y + 10, mPaint);
				// 履带
				canvas.drawRect(x - 20, y - 20, x + 20, y - 10, mPaint);
				canvas.drawRect(x - 20, y + 20, x + 20, y + 10, mPaint);
				// 圆
				canvas.drawCircle(x, y, 7, mPaint);
				break;
			case SOUTH:
				canvas.drawLine(x, y, x, y + 20, mPaint);
				canvas.drawLine(x, y + 20, x - 5, y + 20 - 5, mPaint);
				canvas.drawLine(x, y + 20, x + 5, y + 20 - 5, mPaint);
				// 大铁箱
				canvas.drawRect(x - 10, y - 10, x + 10, y + 10, mPaint);
				// 履带
				canvas.drawRect(x - 20, y - 20, x - 10, y + 20, mPaint);
				canvas.drawRect(x + 20, y - 20, x + 10, y + 20, mPaint);
				// 圆
				canvas.drawCircle(x, y, 7, mPaint);
				break;
			case WEST:
				canvas.drawLine(x, y, x - 20, y, mPaint);
				canvas.drawLine(x - 20, y, x - 20 + 5, y + 5, mPaint);
				canvas.drawLine(x - 20, y, x - 20 + 5, y - 5, mPaint);
				// 大铁箱
				canvas.drawRect(x - 10, y - 10, x + 10, y + 10, mPaint);
				// 履带
				canvas.drawRect(x - 20, y - 20, x + 20, y - 10, mPaint);
				canvas.drawRect(x - 20, y + 20, x + 20, y + 10, mPaint);
				// 圆
				canvas.drawCircle(x, y, 7, mPaint);
				break;
		}
	}
	/** 移动坦克，需要和setDirection()一起使用 */
	protected void moveTank() {
		moveTank(direction);
	}
	/** 坦克是否能移动 */
	private boolean canMove = true;
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	/** 将moveTank和setDirection方法结合起来 */
	protected void moveTank(Direction direction) {
		if (!canMove) {
			return;
		}
		this.direction = direction;
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
		// 控制坦克不出屏幕
		if (x <= 20) {
			x = 20;
		} else if (x >= AppConfig.winWidth - 20) {
			x = AppConfig.winWidth - 20;
		}
		if (y <= 20) {
			y = 20;
		} else if (y >= AppConfig.winWidth - 20) {
			y = AppConfig.winWidth - 20;
		}
	}
	/** 发射子弹 */
	protected void shoot() {
		System.out.println("发射子弹");
		Bullet bullet = prepareBullet();
		if (bullet == null) {
			System.out.println("没有可用的子弹");
			return;
		}
		switch (direction) {
			case NORTH:
				bullet.setX(x);
				bullet.setY(y - 20);
				break;
			case EAST:
				bullet.setX(x + 20);
				bullet.setY(y);
				break;
			case SOUTH:
				bullet.setX(x);
				bullet.setY(y + 20);
				break;
			case WEST:
				bullet.setX(x - 20);
				bullet.setY(y);
				break;
		}
		bullet.setDirection(getDirection());
		bullet.setBulletAlive(true);
		bullet.moveBullet();
	}
	/** 准备一颗子弹 */
	protected Bullet prepareBullet() {
		System.out.println("准备子弹");
		Bullet pBullet = null;
		for (Bullet bullet : bullets) {
			if (!bullet.isBulletAlive()) {
				pBullet = bullet;
			}
		}
		return pBullet;
	}
	/** 画矩形框 */
	protected RectF getRect() {
		return new RectF(x - 20, y - 20, x + 20, y + 20);
	}
	/** 检测是否接触 */
	public void detectRect(Tank tank) {
		switch (direction) {
			case NORTH:
				if (getRect().intersect(tank.getRect())) {
					setCanMove(false);
				} else {
					setCanMove(true);
				}
				break;
			case WEST:
				if (getRect().intersect(tank.getRect())) {
					setCanMove(false);
				} else {
					setCanMove(true);
				}
				break;
			case SOUTH:
				if (getRect().intersect(tank.getRect())) {
					setCanMove(false);
				} else {
					setCanMove(true);
				}
				break;
			case EAST:
				if (getRect().intersect(tank.getRect())) {
					setCanMove(false);
				} else {
					setCanMove(true);
				}
				break;
		}
	}
}

enum Direction {
	NORTH, /** 0:北 */
	WEST, /** 1:西 */
	SOUTH, /** 2:南 */
	EAST,
	/** 3:东 */
}