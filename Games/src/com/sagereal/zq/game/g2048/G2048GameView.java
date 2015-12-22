package com.sagereal.zq.game.g2048;

import java.util.HashMap;
import java.util.Random;
import com.sagereal.zq.game.PaintFactory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class G2048GameView extends View {
	private int winWidth;
	private int winHeight;
	private int dWidth;
	private Paint mPaint;
	private Context mContext;
	private GestureDetector gestureDetector;
	private int[][] array = new int[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
	private HashMap<Integer, String> numColors = new HashMap<Integer, String>();
	public G2048GameView(Context context) {
		super(context);
		init(context);
	}
	private void init(Context context) {
		mContext = context;
		winWidth = context.getResources().getDisplayMetrics().widthPixels;
		winHeight = context.getResources().getDisplayMetrics().heightPixels;
		dWidth = winWidth / 5;
		mPaint = PaintFactory.getPaint(Color.BLACK);
		numColors.put(2, "#17BABA");
		numColors.put(4, "#2BB55E");
		numColors.put(8, "#86B535");
		numColors.put(16, "#D1D042");
		numColors.put(32, "#D8A331");
		numColors.put(64, "#DB7036");
		numColors.put(128, "#D1544E");
		numColors.put(256, "#C8628D");
		numColors.put(512, "#cdcdcd");
		numColors.put(1024, "#ff0000");
		numColors.put(2048, "#00ff00");
		numColors.put(4096, "#0000ff");
		numColors.put(8192, "#ffff00");
		numColors.put(8192 * 2, "#00ffff");
		numColors.put(8192 * 4, "#ff00ff");
		gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				float dx = e1.getX() - e2.getX();
				float dy = e1.getY() - e2.getY();
				boolean canMove = false;
				if (Math.abs(dx) > Math.abs(dy)) {
					if (dx > 0) {
						// 屏蔽不能左移的时候
						canMove = move(0);
					} else {
						canMove = move(1);
					}
				} else {
					if (dy > 0) {
						canMove = move(2);
					} else {
						canMove = move(3);
					}
				}
				if (!canMove) {
					postInvalidate();
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}
	private boolean move(int direction) {
		boolean bRet = true;
		// 拷贝现有数组array到tempArray
		int[][] tempArray = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				tempArray[i][j] = array[i][j];
			}
		}
		switch (direction) {
			case 0:// left
					// 移动
				for (int i = 0; i < 5; i++) {
					int index = 0;
					int[] temp = new int[5];
					for (int j = 0; j < 5; j++) {
						if (array[i][j] != 0) {
							temp[index] = array[i][j];
							index++;
						}
					}
					array[i] = temp;
				}
				// 合体
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 4; j++) {
						if (array[i][j] == array[i][j + 1]) {
							array[i][j] = array[i][j] + array[i][j + 1];
							array[i][j + 1] = 0;
						}
					}
				}
				// 再移动
				for (int i = 0; i < 5; i++) {
					int index = 0;
					int[] temp = new int[5];
					for (int j = 0; j < 5; j++) {
						if (array[i][j] != 0) {
							temp[index] = array[i][j];
							index++;
						}
					}
					array[i] = temp;
				}
				break;
			case 1:// right
					// 移动
				for (int i = 0; i < 5; i++) {
					int index = 4;
					int[] temp = new int[5];
					for (int j = 4; j >= 0; j--) {
						if (array[i][j] != 0) {
							temp[index] = array[i][j];
							index--;
						}
					}
					array[i] = temp;
				}
				// 合体
				for (int i = 0; i < 5; i++) {
					for (int j = 4; j >= 1; j--) {
						if (array[i][j] == array[i][j - 1]) {
							array[i][j] = array[i][j] + array[i][j - 1];
							array[i][j - 1] = 0;
						}
					}
				}
				// 再移动
				for (int i = 0; i < 5; i++) {
					int index = 4;
					int[] temp = new int[5];
					for (int j = 4; j >= 0; j--) {
						if (array[i][j] != 0) {
							temp[index] = array[i][j];
							index--;
						}
					}
					array[i] = temp;
				}
				break;
			case 2:// up
					// 移动
				for (int i = 0; i < 5; i++) {
					int index = 0;
					int[] temp = new int[5];
					for (int j = 0; j < 5; j++) {
						if (array[j][i] != 0) {
							temp[index] = array[j][i];
							index++;
						}
					}
					for (int j = 0; j < 5; j++) {
						array[j][i] = temp[j];
					}
				}
				// 合体
				for (int i = 0; i < 5; i++) {// 列
					for (int j = 0; j < 4; j++) {// 行
						if (array[j][i] == array[j + 1][i]) {
							array[j][i] = array[j][i] + array[j + 1][i];
							array[j + 1][i] = 0;
						}
					}
				}
				// 再移动
				for (int i = 0; i < 5; i++) {
					int index = 0;
					int[] temp = new int[5];
					for (int j = 0; j < 5; j++) {
						if (array[j][i] != 0) {
							temp[index] = array[j][i];
							index++;
						}
					}
					for (int j = 0; j < 5; j++) {
						array[j][i] = temp[j];
					}
				}
				break;
			case 3:// down
					// 移动
				for (int i = 0; i < 5; i++) {
					int index = 4;
					int[] temp = new int[5];
					for (int j = 4; j >= 0; j--) {
						if (array[j][i] != 0) {
							temp[index] = array[j][i];
							index--;
						}
					}
					for (int j = 0; j < 5; j++) {
						array[j][i] = temp[j];
					}
				}
				// 合体
				for (int i = 0; i < 5; i++) {// 列
					for (int j = 4; j >= 1; j--) {// 行
						if (array[j][i] == array[j - 1][i]) {
							array[j][i] = array[j][i] + array[j - 1][i];
							array[j - 1][i] = 0;
						}
					}
				}
				// 再移动
				for (int i = 0; i < 5; i++) {
					int index = 4;
					int[] temp = new int[5];
					for (int j = 4; j >= 0; j--) {
						if (array[j][i] != 0) {
							temp[index] = array[j][i];
							index--;
						}
					}
					for (int j = 0; j < 5; j++) {
						array[j][i] = temp[j];
					}
				}
				break;
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (tempArray[i][j] != array[i][j]) {
					bRet = false;
				}
			}
		}
		// 如何判断两个二维数组是否相同？？？？？
		if (bRet) {
			Toast.makeText(mContext, "没有移动", Toast.LENGTH_SHORT).show();
		}
		return bRet;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 随机生成一个数字
		int row = new Random().nextInt(5);
		int col = new Random().nextInt(5);
		while (array[row][col] != 0) {
			row = new Random().nextInt(5);
			col = new Random().nextInt(5);
			Log.e("zhangqi", "row:" + row + " col:" + col);
		}
		array[row][col] = 4;
		// 画横线
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(0, dWidth * i, winWidth, dWidth * i, mPaint);
		}
		// 画竖线
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(dWidth * i, 0, dWidth * i, winWidth, mPaint);
		}
		// 画数字
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (array[i][j] != 0) {
					Paint paint = PaintFactory.getTextPaint(Color.parseColor(numColors.get(array[i][j])));
					float dy = (paint.getFontMetrics().ascent - paint.getFontMetrics().descent) / 2;
					canvas.drawText(array[i][j] + "", j * dWidth + dWidth / 2, i * dWidth + dWidth / 2 - dy, paint);
				}
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return true;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(winWidth, winWidth + 2);
	}
}
