package com.sagereal.zq.game.tank;

import java.util.ArrayList;
import com.sagereal.zq.game.LogUtil;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

/**
 * @子弹类型
 * @坦克类型
 */
public class TankGameView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder surfaceHolder;
	private Canvas mCanvas;
	private Paint mPaint;
	/** 我的坦克 */
	private MyTank myTank;
	/** 敌人的坦克 */
	private ArrayList<EnemyTank> enemyTanks;
	/** 我的坦克的子弹打中敌人坦克时候的爆炸效果 */
	private ArrayList<Bomb> bombs;
	/** 游戏得分 */
	private int score;
	private boolean isAvailable = true;
	private Context mContext;
	private int winWidth;
	private int winHeight;
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public TankGameView(Context context) {
		super(context);
		init(context);
	}
	public TankGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public TankGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	private void init(Context context) {
		mContext = context;
		winWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		winHeight = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Style.FILL);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		// 我的坦克
		myTank = new MyTank();
		// 生产敌人的坦克
		enemyTanks = new ArrayList<EnemyTank>(3);
		enemyTanks.add(new EnemyTank(100, 200, Direction.NORTH));
		enemyTanks.add(new EnemyTank(150, 200, Direction.NORTH));
		enemyTanks.add(new EnemyTank(200, 200, Direction.NORTH));
		// 炸弹
		bombs = new ArrayList<Bomb>();
		setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				float x1 = event.getX();
				float y1 = event.getY();
				float x2 = myTank.getX();
				float y2 = myTank.getY();
				float dx1 = x1 - x2;
				float dy1 = y1 - y2;
				if (dx1 - dy1 > 0 && dx1 + dy1 > 0) {
					myTank.setDirection(Direction.EAST);
				} else if (dx1 - dy1 > 0 && dx1 + dy1 < 0) {
					myTank.setDirection(Direction.NORTH);
				} else if (dx1 - dy1 < 0 && dx1 + dy1 > 0) {
					myTank.setDirection(Direction.SOUTH);
				} else if (dx1 - dy1 < 0 && dx1 + dy1 < 0) {
					myTank.setDirection(Direction.WEST);
				}
				myTank.moveTank();
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						myTank.shoot();
						break;
				}
				return true;
			}
		});
	}
	public void moveUp() {
		myTank.moveTank(Direction.NORTH);
	}
	public void moveDown() {
		myTank.moveTank(Direction.SOUTH);
	}
	public void moveLeft() {
		myTank.moveTank(Direction.WEST);
	}
	public void moveRight() {
		myTank.moveTank(Direction.EAST);
	}
	public void shoot() {
		myTank.shoot();
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {
		// 启动刷新线程
		new Thread(new Runnable() {
			public void run() {
				while (isAvailable) {
					try {
						// 刷新频率
						Thread.sleep(20);
						if (surfaceHolder == null) {
							return;
						}
						synchronized (surfaceHolder) {
							mCanvas = surfaceHolder.lockCanvas();
							if (mCanvas == null) {
								return;
							}
							// 清空画板
							mCanvas.drawColor(Color.BLACK);
							/**
							 * 
							 * 定时随机生成一些坦克？？？
							 * 
							 */
							drawView(mCanvas);
							detect(mCanvas);
							detectTank();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (mCanvas != null) {
							synchronized (surfaceHolder) {
								surfaceHolder.unlockCanvasAndPost(mCanvas);
							}
						}
					}
				}
			}
		}).start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	/** 画图 */
	private void drawView(Canvas canvas) {
		// 画我的坦克
		if (myTank.isTankAlive()) {
			myTank.drawTank(canvas);
		}
		// 画敌人的坦克
		for (EnemyTank enemyTank : enemyTanks) {
			if (enemyTank.isTankAlive()) {
				enemyTank.drawTank(canvas);
			}
		}
		// 画子弹
		ArrayList<Bullet> bullets = myTank.getBullets();
		for (Bullet bullet : bullets) {
			if (bullet.isBulletAlive()) {
				bullet.drawBullet(canvas);
			}
		}
		// 画炸弹
		for (Bomb bomb : bombs) {
			if (bomb.isBombAlive()) {
				bomb.drawBomb(canvas);
			}
		}
		paint.setTextSize(15);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);
		// 画得分
		canvas.drawText("你的得分：" + score, 0, 20, paint);
		/**
		 * 
		 * 
		 * 检测敌人的位置和我的坦克的位置，不要横穿过坦克（1:我的坦克和敌人的坦克，2:敌人的坦克和敌人的坦克）??????
		 * 
		 * 
		 */
	}
	Paint paint = new Paint();
	/** 碰撞检测（坦克和子弹） */
	private void detect(Canvas canvas) {
		// 检测我的子弹和敌人的坦克是否发生了碰撞
		ArrayList<Bullet> bullets = myTank.getBullets();
		for (Bullet bullet : bullets) {
			for (EnemyTank tank : enemyTanks) {
				RectF rect = new RectF(tank.getX() - 20, tank.getY() - 20, tank.getX() + 20, tank.getY() + 20);
				if (rect.contains(bullet.getX(), bullet.getY())) {
					LogUtil.e("我的子弹和敌人的坦克发生了碰撞");
					bullet.setBulletAlive(false);// 子弹消失
					tank.setTankAlive(false);// 坦克消失
					score += 50;// 每辆坦克50分
					// 获取一颗炸弹
					getBomb(tank);
					// 把坦克的坐标移出屏幕
					tank.setX(-100);
					tank.setY(-100);
				}
			}
		}
		/**
		 * 
		 * 
		 * 检测敌人的子弹和我的坦克是否发生了碰撞?????
		 * 
		 * 
		 */
	}
	/** 碰撞检测（坦克和坦克） */
	private void detectTank() {
		switch (myTank.getDirection()) {
			case NORTH:
				break;
		}
	}
	/** 按照敌人坦克爆炸的位置获取一个bomb炸弹，如果有可用的炸弹就直接拿来用，如果没有就重新创建一个并添加到数组中 */
	private Bomb getBomb(EnemyTank enemyTank) {
		Bomb pBomb = null;
		for (Bomb bomb : bombs) {
			if (!bomb.isBombAlive()) {
				pBomb = bomb;
				pBomb.setBombAlive(true);
				pBomb.setX(enemyTank.getX());
				pBomb.setY(enemyTank.getY());
				pBomb.setRadius(1);
			}
		}
		if (pBomb == null) {
			pBomb = new Bomb(enemyTank.getX(), enemyTank.getY());
			bombs.add(pBomb);
		}
		LogUtil.e("bombs:" + bombs.size());
		return pBomb;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(winWidth, winWidth);
	}
}
