package com.sagereal.zq.game.popstar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import com.sagereal.zq.game.PaintFactory;
import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PopGameView extends View {
	private Context mContext;
	private Paint mPaint;
	private int winWidth;
	private int dWidth;
	private int[][] data = new int[10][10];
	private int[] colors = new int[] { Color.WHITE, Color.RED, Color.BLUE, Color.CYAN, Color.YELLOW, Color.MAGENTA };
	public PopGameView(Context context) {
		super(context);
		init(context);
	}
	public PopGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	private void init(Context context) {
		this.mContext = context;
		winWidth = mContext.getResources().getDisplayMetrics().widthPixels;
		dWidth = winWidth / 10;
		mPaint = PaintFactory.getPaint(Color.BLACK);
		/*
		 * 随机方块
		 */
		Random random = new Random();
		for (int j = 0; j < data.length; j++) {
			for (int i = 0; i < data[j].length; i++) {
				data[j][i] = random.nextInt(colors.length - 1) + 1;
			}
		}
		/*
		 * for (int j = 0; j < data.length; j++) { for (int i = 0; i < data[j].length; i++) { data[j][i] = i % (colors.length - 1) + 1; } }
		 */
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画横线
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(0, dWidth * i, winWidth, dWidth * i, mPaint);
		}
		// 画竖线
		for (int i = 0; i < 11; i++) {
			canvas.drawLine(dWidth * i, 0, dWidth * i, winWidth, mPaint);
		}
		// 画方块
		for (int j = 0; j < 10; j++) {// 行
			for (int i = 0; i < 10; i++) {// 列
				if (data[j][i] == 0) {
					// 白色空白方块
				} else {
					Paint colorPaint = PaintFactory.getPaint(colors[data[j][i]]);
					colorPaint.setStyle(Style.FILL);
					canvas.drawRect(new Rect(dWidth * i + 2, dWidth * j + 2, dWidth * (i + 1) - 2, dWidth * (j + 1) - 2), colorPaint);
				}
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int tx = (int) event.getX() / dWidth;
		int ty = (int) event.getY() / dWidth;
		if (tx >= 10 || ty >= 10) {
			return true;
		}
		searched = new HashSet<String>();
		ArrayList<HashMap<String, Integer>> result = searchBlock(tx, ty, data[ty][tx]);
		if (result.size() <= 1) {
			return true;
		}
		for (HashMap<String, Integer> hashMap : result) {
			int x = hashMap.get("x");
			int y = hashMap.get("y");
			// Log.e("zhangqi", hashMap.get("x") + ":" + hashMap.get("y"));
			// 将同色图块设置为-1
			data[y][x] = 0;
		}
		// 整个下移一下
		moveDown();
		while (canMoveLeft()) {
			moveLeft();
		}
		postInvalidate();
		return super.onTouchEvent(event);
	}
	/** 判断是否能左移 */
	private boolean canMoveLeft() {
		boolean bRet = false;
		int a = -1, b = -1;
		// i:列
		for (int i = 0; i < 10; i++) {
			if (data[9][i] == 0) {
				if (a == -1) {
					a = i;
					break;
				}
			}
		}
		if (a == -1) {
			return false;
		}
		for (int i = a; i < 10; i++) {
			if (data[9][i] != 0) {
				if (b == -1) {
					b = i;
					break;
				}
			}
		}
		if (b - a > 0) {
			bRet = true;
		}
		return bRet;
	}
	// 下移
	private void moveDown() {
		for (int i = 0; i < 10; i++) {// 列
			int[] temp = new int[10];
			int index = 9;
			for (int j = 9; j >= 0; j--) {
				if (data[j][i] != 0) {
					temp[index] = data[j][i];
					index--;
				}
			}
			for (int j = 0; j < 10; j++) {
				data[j][i] = temp[j];
			}
		}
	}
	// 左移
	private void moveLeft() {
		// i:列 j:行
		for (int i = 0; i < 10; i++) {
			// 去掉空列
			if (data[9][i] == 0) {
				for (int x = i + 1; x < 10; x++) {
					for (int j = 0; j < 10; j++) {
						data[j][x - 1] = data[j][x];
					}
				}// 这里还有点问题，同时消掉两列的时候会有问题？？
				for (int j = 0; j < 10; j++) {
					data[j][9] = 0;// 将最后一列设置为空
				}
			}
		}
	}
	//
	private Set<String> searched = new HashSet<String>();
	private ArrayList<HashMap<String, Integer>> searchBlock(int x, int y, int color) {
		ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		if (x < 0 || x > 9 || y < 0 || y > 9) {
			return list;
		}
		// 白色的地方不需要处理
		if (color == 0) {
			return list;
		}
		// 只有一个方块的地方不需要处理
		// 是否已经搜索过
		if (searched.contains(x + "-" + y)) {
			return list;
		}
		if (color == data[y][x]) {
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			hashMap.put("x", x);
			hashMap.put("y", y);
			list.add(hashMap);
			searched.add(x + "-" + y);
		} else {
			return list;
		}
		list.addAll(searchBlock(x - 1, y, color));
		list.addAll(searchBlock(x + 1, y, color));
		list.addAll(searchBlock(x, y - 1, color));
		list.addAll(searchBlock(x, y + 1, color));
		return list;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(winWidth, winWidth);
	}
}
