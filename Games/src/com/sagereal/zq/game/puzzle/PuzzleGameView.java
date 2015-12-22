package com.sagereal.zq.game.puzzle;

import java.util.Random;
import com.sagereal.zq.game.LogUtil;
import com.sagereal.zq.game.PaintFactory;
import com.sagereal.zq.game.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class PuzzleGameView extends View {
	private Context mContext;
	private Paint mPaint;
	private int winWidth;
	private int winHeight;
	private int dWidth;
	private int dHeight;
	private int bWidth;
	private int bHeight;
	private Bitmap puzzleBitmap;
	private PuzzlePiece[][] oldPieces;
	private PuzzlePiece[][] newPieces;
	public PuzzleGameView(Context context) {
		super(context);
		init(context);
	}
	public PuzzleGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public PuzzleGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	private void init(Context context) {
		this.mContext = context;
		mPaint = PaintFactory.getPaint(Color.BLACK);
		winWidth = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		puzzleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_wallpaper);
		bWidth = puzzleBitmap.getWidth();
		bHeight = puzzleBitmap.getHeight();
		dWidth = bWidth / 3;
		dHeight = bHeight / 5;
		oldPieces = new PuzzlePiece[5][3];
		// 根据旧图片数组生成新图片数组
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				Bitmap bitmap = Bitmap.createBitmap(puzzleBitmap, dWidth * j, dHeight * i, dWidth, dHeight);
				oldPieces[i][j] = new PuzzlePiece(j, i, bitmap);
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				int x = new Random().nextInt(3);
				int y = new Random().nextInt(5);
				while (x == j && y == i) {
					x = new Random().nextInt(3);
					y = new Random().nextInt(5);
				}
				PuzzlePiece tempPiece = oldPieces[i][j];
				oldPieces[i][j] = oldPieces[y][x];
				oldPieces[y][x] = tempPiece;
			}
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < oldPieces.length; i++) {
			for (int j = 0; j < oldPieces[i].length; j++) {
				if (oldPieces[i][j] != null) {
					PuzzlePiece puzzlePiece = oldPieces[i][j];
					Bitmap bitmap = puzzlePiece.getBitmap();
					Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
					Rect dst = new Rect((int) (dWidth * j / 2) + 2 + (int) puzzlePiece.getXx(), (int) (dHeight * i / 2) + 2 + (int) puzzlePiece.getYy(), (int) (dWidth * (j + 1) / 2) + (int) puzzlePiece.getXx(), (int) (dHeight * (i + 1) / 2) + (int) puzzlePiece.getYy());
					canvas.drawBitmap(bitmap, src, dst, mPaint);
				}
			}
		}
		// 画touchPiece
		if (touchPiece != null) {
			int i = touchPiece.getX();
			int j = touchPiece.getY();
			Bitmap bitmap = touchPiece.getBitmap();
			Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			Rect dst = new Rect((int) (dWidth * i / 2) + 2 + (int) touchPiece.getXx() + (int) (downX % (dWidth / 2)), (int) (dHeight * j / 2) + 2 + (int) touchPiece.getYy() + (int) (downY % (dHeight / 2)), (int) (dWidth * (i + 1) / 2) + (int) touchPiece.getXx() + (int) (downX % (dWidth / 2)), (int) (dHeight * (j + 1) / 2) + (int) touchPiece.getYy() + (int) (downY % (dHeight / 2)));
			canvas.drawBitmap(bitmap, src, dst, mPaint);
			LogUtil.e(touchPiece.getXx() + ":" + touchPiece.getYy());
		}
	}
	private float downX = 1;
	private float downY = 1;
	private PuzzlePiece touchPiece;
	private int pieceX;
	private int pieceY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int xxx = (int) (event.getX() / (dWidth / 2));
		int yyy = (int) (event.getY() / (dHeight / 2));
		if (xxx < 0 || xxx > 2 || yyy < 0 || yyy > 4) {
			return false;
		}
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				// 将点击的块设置为null
				int x = (int) (event.getX() / (dWidth / 2));
				int y = (int) (event.getY() / (dHeight / 2));
				// LogUtil.e(x + ":" + y);
				touchPiece = oldPieces[y][x];
				pieceX = touchPiece.getX();
				pieceY = touchPiece.getY();
				oldPieces[y][x] = null;
				break;
			case MotionEvent.ACTION_MOVE:
				touchPiece.setXxYy(event.getX() - downX, event.getY() - downY);// 这个移动效果没有
				// LogUtil.e((pieceX + event.getX() - downX) + "::" + (pieceY + event.getY() - downY));
				break;
			case MotionEvent.ACTION_UP:
				oldPieces[pieceY][pieceX] = oldPieces[(int) (event.getY() / (dHeight / 2))][(int) (event.getX() / (dWidth / 2))];
				oldPieces[(int) (event.getY() / (dHeight / 2))][(int) (event.getX() / (dWidth / 2))] = touchPiece;
				touchPiece = null;
				break;
		}
		postInvalidate();
		return true;
	}
}
