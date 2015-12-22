package com.sagereal.zq.game;

import java.util.HashMap;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;

public class AppConfig extends Application {
	private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();
	/** 屏幕宽度和高度 */
	public static int winWidth, winHeight;
	@Override
	public void onCreate() {
		super.onCreate();
		addBitmap(R.drawable.icon0);
		addBitmap(R.drawable.icon1);
		addBitmap(R.drawable.icon2);
		addBitmap(R.drawable.icon3);
		addBitmap(R.drawable.icon4);
		addBitmap(R.drawable.icon5);
		addBitmap(R.drawable.icon6);
		winWidth = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		winHeight = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
	}
	public static Integer[] getResIds() {
		return AppConfig.bitmaps.keySet().toArray(new Integer[AppConfig.bitmaps.keySet().size()]);
	}
	/** 加载图片 */
	private void addBitmap(int res) {
		bitmaps.put(res, BitmapFactory.decodeResource(getResources(), res));
	}
	/** */
	public static Bitmap getBitmapById(int resId) {
		return bitmaps.get(resId);
	}
}
