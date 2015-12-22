package com.sagereal.zq.game.block;

import java.util.ArrayList;
import com.sagereal.zq.game.PaintFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * @如何随机生成1个砖块
 * @如何消除砖块
 * @砖块如何变化
 * @添加砖块预览
 * @添加分数
 * @控制下落速度
 * @添加
 */
public class BlockGameView extends View {
	private Context mContext;
	private int winWidth;
	private int winHeight;
	private int dWidth;
	private int dHeight;
	private Paint mPaint;
	private Paint bPaint;
	public static int[][] map = new int[10][10];
	private int countTime;
	// 正在控制的砖块
	private static Block block;
	public BlockGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public BlockGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public BlockGameView(Context context) {
		super(context);
		init(context);
	}
	private void init(Context context) {
		mContext = context;
		winWidth = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		dWidth = winWidth / 10;
		dHeight = dWidth;
		mPaint = PaintFactory.getPaint(Color.BLACK);
		bPaint = PaintFactory.getPaint(Color.BLACK);
		bPaint.setStyle(Style.FILL);
		map[9][0] = 1;
		block = BlockFactory.generateBlock();
		// 启动刷新线程(0.1秒刷新一次)
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(100);
						handler.sendEmptyMessage(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void moveLeft() {
		block.moveLeft();
	}
	public void moveRight() {
		block.moveRight();
	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					countTime++;
					// 这里的5可以控制游戏速度
					if ((++countTime) % 3 == 0) {
						block.moveDown();// 屏幕刷新5次，下落一次
					}
					postInvalidate();
					break;
			}
		}
	};
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画横线
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(0, dWidth * i, winWidth, dWidth * i, mPaint);
		}
		// 画竖线
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(dHeight * i, 0, dHeight * i, winWidth, mPaint);
		}
		// 画小方块
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 1) {
					canvas.drawRect(dWidth * j, dHeight * i, dWidth * (j + 1), dHeight * (i + 1), bPaint);
				}
			}
		}
		// 画砖块
		for (BlockPoint blockPoint : block.getBlockPoints()) {
			canvas.drawRect(dWidth * blockPoint.getX(), dHeight * blockPoint.getY(), dWidth * (blockPoint.getX() + 1), dHeight * (blockPoint.getY() + 1), bPaint);
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(winWidth, winWidth);
	}
	/** 将死掉的砖块设置为1 */
	public static void setBlock(Block block) {
		ArrayList<BlockPoint> blockPoints = block.getBlockPoints();// 这里不对
		for (BlockPoint blockPoint : blockPoints) {
			map[blockPoint.getY()][blockPoint.getX()] = 1;
		}
		// LogUtil.printArray(map);
	}
	public static void newBlock() {
		block = BlockFactory.generateBlock();
	}
}
