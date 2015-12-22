package com.sagereal.zq.game.link;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.sagereal.zq.game.AppConfig;
import com.sagereal.zq.game.LogUtil;
import com.sagereal.zq.game.PaintFactory;
import com.sagereal.zq.game.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class LinkGameView extends View {
	private Context mContext;
	private Paint mPaint;
	private Paint bPaint;
	private Paint nPaint;
	private int winWidth;
	private int winHeight;
	private float dWidth;
	private float dHeight;
	/** 行数 */
	private int rows;
	/** 列数 */
	private int columns;
	/** 图片资源数组 */
	private Integer[] resIds;
	/** 原始矩阵 */
	private int[][] oldMap;
	/** 生成矩阵 */
	private int[][] newMap;
	public LinkGameView(Context context, int rows, int columns) {
		super(context);
		this.rows = rows;
		this.columns = columns;
		init(context);
	}
	public LinkGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	/** 随机生成一张地图 */
	private void generateMap() {
		for (int i = 0; i < oldMap.length; i++) {
			for (int j = 0; j < oldMap[i].length; j++) {
				if (oldMap[i][j] == 0) {
					oldMap[i][j] = new Random().nextInt(resIds.length - 1) + 1;
					while (true) {
						int yy = new Random().nextInt(oldMap.length);
						int xx = new Random().nextInt(oldMap[yy].length);
						if (!(yy == i && xx == j) && oldMap[yy][xx] == 0) {
							oldMap[yy][xx] = oldMap[i][j];
							break;
						}
					}
				} else {
					continue;
				}
			}
		}
		System.out.println("--------Map--------");
		for (int i = 0; i < oldMap.length; i++) {
			for (int j = 0; j < oldMap[i].length; j++) {
				System.out.print(oldMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("--------Map--------");
	}
	/** 根据原始数组生成外围全是0的新数组 */
	private void generateNewMap() {
		for (int i = 0; i < newMap.length; i++) {
			for (int j = 0; j < newMap[i].length - 1; j++) {
				if (i == 0 || i == newMap.length - 1 || j == 0 || j == newMap[i].length - 1) {
					newMap[i][j] = 0;// 外圈全为0
				} else {
					newMap[i][j] = oldMap[i - 1][j - 1];
				}
			}
		}
	}
	private void init(Context context) {
		mContext = context;
		resIds = AppConfig.getResIds();
		resIds = new Integer[] { R.drawable.icon0, R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5, R.drawable.icon6 };
		oldMap = new int[rows][columns];
		generateMap();
		newMap = new int[rows + 2][columns + 2];
		generateNewMap();
		winWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(1);
		bPaint = new Paint();
		bPaint.setColor(Color.RED);
		bPaint.setStrokeWidth(1);
		bPaint.setStyle(Style.STROKE);
		nPaint = new Paint();
		nPaint.setColor(Color.RED);
		nPaint.setStrokeWidth(4);
		nPaint.setStyle(Style.STROKE);
		dWidth = winWidth / (float) (columns + 2);
		dHeight = dWidth;
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon1);
	}
	private Bitmap bitmap;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, (int) dWidth, (int) dHeight), mPaint);
		for (int i = 1; i < columns + 2; i++) {
			canvas.drawLine(dWidth * i, dHeight, dWidth * i, winWidth - dHeight, mPaint);
		}
		for (int i = 1; i < rows + 2; i++) {
			canvas.drawLine(dWidth, dHeight * i, winWidth - dWidth, dHeight * i, mPaint);
		}
		canvas.drawRect(2, 2, winWidth - 2, winWidth - 2, nPaint);// 画边框
		// 画小方块
		System.out.println("--------NewMap--------");
		for (int i = 0; i < newMap.length; i++) {
			for (int j = 0; j < newMap[i].length; j++) {
				// int r = new Random().nextInt(255);
				// int g = new Random().nextInt(255);
				// int b = new Random().nextInt(255);
				// 画带颜色的矩形
				// canvas.drawRect(dWidth * i, dHeight * j, dWidth * (i + 1), dHeight * (j + 1), PaintFactory.getPaint(Color.rgb(r, g, b)));
				// 将图片画到矩形框的正中央
				if (newMap[i][j] != 0) {
					Bitmap temp = AppConfig.getBitmapById(resIds[newMap[i][j]]);
					canvas.drawBitmap(temp, new Rect(0, 0, temp.getWidth(), temp.getHeight()), new Rect((int) (dWidth * j), (int) (dHeight * i), (int) (dWidth * (j + 1)), (int) (dHeight * (i + 1))), mPaint);
					// canvas.drawBitmap(bitmap, dWidth * j + (dWidth - bitmap.getWidth()) / 2, dHeight * i + (dHeight - bitmap.getHeight()) / 2, mPaint);// 这里可以设置bitmap的大小吗？？
				}
				System.out.print(newMap[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("--------NewMap--------");
		// 画选中的小方块1
		if (piece1.getX() != 0) {
			canvas.drawRect(dWidth * piece1.getX(), dHeight * piece1.getY(), dWidth * (piece1.getX() + 1), dHeight * (piece1.getY() + 1), nPaint);
		}
		// 画选中的小方块2
		if (piece2.getX() != 0) {
			canvas.drawRect(dWidth * piece2.getX(), dHeight * piece2.getY(), dWidth * (piece2.getX() + 1), dHeight * (piece2.getY() + 1), nPaint);
			// 在这里判断是否能消除
			if (LinkTool.link(newMap, piece1, piece2)) {
				// 可以消除
				newMap[piece1.getY()][piece1.getX()] = 0;
				newMap[piece2.getY()][piece2.getX()] = 0;
				LogUtil.e("可以消除");
			} else {
				// 不可以消除
				LogUtil.e("不可以消除");
			}
			piece1.init();
			piece2.init();
			postInvalidate();
		}
	}
	private Piece piece1 = new Piece();
	private Piece piece2 = new Piece();
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 列
		int xx = (int) (event.getX() / dWidth);
		// 行
		int yy = (int) (event.getY() / dWidth);
		if (xx < 1 || xx > columns) {
			return false;
		}
		if (yy < 1 || yy > rows) {
			return false;
		}
		if (piece1.getX() == 0) {
			piece1.setXY(xx, yy);
			piece1.setImageId(resIds[newMap[yy][xx]]);
		} else {
			piece2.setXY(xx, yy);
			piece2.setImageId(resIds[newMap[yy][xx]]);
		}
		postInvalidate();
		return super.onTouchEvent(event);
	}
	public void newGame() {
		init(mContext);
		postInvalidate();
	}
	public void print() {
		StringBuilder builder = new StringBuilder();
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		builder.append("--------------问题矩阵开始" + formatter.format(date) + "--------------\n");
		for (int i = 0; i < newMap.length; i++) {
			for (int j = 0; j < newMap[i].length; j++) {
				builder.append(newMap[i][j] + " ");
				System.out.print(newMap[i][j] + " ");
			}
			System.out.println();
			builder.append("\n");
		}
		builder.append("--------------问题矩阵结束" + formatter.format(date) + "--------------\n");
		try {
			File file = new File(Environment.getExternalStorageDirectory() + "/test.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String temp = "";
			while ((temp = bufferedReader.readLine()) != null) {
				builder.append(temp + "\n");
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(builder.toString());
			bufferedWriter.flush();// 这里要flush才会写文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension((int) winWidth, (int) winWidth);
	}
	private Toast mToast;
	private void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}
}
