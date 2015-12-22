package com.sagereal.zq.game.five;

import java.util.ArrayList;
import com.sagereal.zq.game.BaseView;
import com.sagereal.zq.game.LogUtil;
import com.sagereal.zq.game.MyPoint;
import com.sagereal.zq.game.PaintFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

public class FiveGameView extends BaseView {
	public FiveGameView(Context context) {
		super(context);
	}
	/** 单位宽度，单位高度 */
	private float dWidth, dHeight;
	/** 半径 */
	private float radius;
	/** 画笔 */
	private Paint wPaint, bPaint;
	/** 棋子数组 */
	private int[][] array = new int[9][9];
	@Override
	protected void initView() {
		wPaint = PaintFactory.getPaint(Color.GRAY);
		wPaint.setStyle(Style.FILL);
		bPaint = PaintFactory.getPaint(Color.BLACK);
		bPaint.setStyle(Style.FILL);
		dWidth = winWidth / 10;
		dHeight = dWidth;
		radius = dWidth / 2 - 1;
		array = new int[9][9];// 这里为什么还要初始化一遍
		// array[3][3] = 0;
		// getPoints(3, 3, array);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画横线
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(0, dHeight * i, winWidth, dHeight * i, mPaint);
		}
		// 画竖线
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(dWidth * i, 0, dWidth * i, winWidth, mPaint);
		}
		// 画点击的点
		if ((x < 10 && x > 0) && (y > 0 && y < 10)) {
			canvas.drawCircle(x * dWidth, y * dHeight, radius, turn == 1 ? wPaint : bPaint);
		}
		// 画已下的棋子
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array[j].length; i++) {
				if (array[j][i] == 1) {
					// 画白子
					canvas.drawCircle(dWidth * (i + 1), dHeight * (j + 1), radius, wPaint);
				} else if (array[j][i] == 2) {
					// 画黑子
					canvas.drawCircle(dWidth * (i + 1), dHeight * (j + 1), radius, bPaint);
				}
			}
		}
	}
	/** 点击的点的坐标 */
	private int x, y;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x = (int) (event.getX() / dWidth);
		y = (int) (event.getY() / dWidth);
		if (x < 1 || x > 9) {
			return true;
		}
		if (y < 1 || y > 9) {
			return true;
		}
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				if (array[y - 1][x - 1] == 0) {
					array[y - 1][x - 1] = turn;
					// 检测五子连珠
					if (getPoints(x - 1, y - 1, array)) {
						if (turn == 1) {
							showDialog("白棋五子连珠");
						} else if (turn == 2) {
							showDialog("黑棋五子连珠");
						}
					}
					y = 0;
					x = 0;
					if (turn == 1) {
						turn = 2;
					} else if (turn == 2) {
						turn = 1;
					}
				}
				break;
		}
		postInvalidate();
		return true;
	}
	private boolean getPoints(int x, int y, int[][] array) {
		if (array == null) {
			LogUtil.e("数组为空");
		}
		int aa = array[y][x];
		// 左
		int countL = 0;
		for (int i = x - 1; i >= 0; i--) {
			if (array[y][i] == aa) {
				countL++;
			}
		}
		LogUtil.e("左:" + countL);
		// 右
		int countR = 0;
		for (int i = x + 1; i < array.length; i++) {
			if (array[y][i] == aa) {
				countR++;
			}
		}
		LogUtil.e("右:" + countR);
		if (countL + countR >= 4) {
			return true;
		}
		// 上
		int countU = 0;
		for (int j = y - 1; j >= 0; j--) {
			if (array[j][x] == aa) {
				countU++;
			}
		}
		LogUtil.e("上:" + countU);
		// 下
		int countD = 0;
		for (int j = y + 1; j < array.length; j++) {
			if (array[j][x] == aa) {
				countD++;
			}
		}
		LogUtil.e("下:" + countD);
		if (countU + countD >= 4) {
			return true;
		}
		// 左上
		int countLU = 0;
		for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if (array[j][i] == aa) {
				countLU++;
			}
		}
		LogUtil.e("左上:" + countLU);
		// 右下
		int countRD = 0;
		for (int i = x + 1, j = y + 1; i < array.length && j < array.length; i++, j++) {
			if (array[j][i] == aa) {
				countRD++;
			}
		}
		LogUtil.e("右下:" + countRD);
		if (countLU + countRD >= 4) {
			return true;
		}
		// 右上
		int countRU = 0;
		for (int i = x + 1, j = y - 1; i < array.length && j >= 0; i++, j--) {
			if (array[j][i] == aa) {
				countRU++;
			}
		}
		LogUtil.e("右上:" + countRU);
		// 左下
		int countLD = 0;
		for (int i = x - 1, j = y + 1; i >= 0 && j < array.length; i--, j++) {
			if (array[j][i] == aa) {
				countLD++;
			}
		}
		LogUtil.e("左下:" + countLD);
		if (countRU + countLD >= 4) {
			return true;
		}
		return false;
	}
	/** 1:白子 2：黑子 */
	private int turn = 1;
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(winWidth, winWidth + 1);// 控制游戏界面的宽度和高度
	}
}
