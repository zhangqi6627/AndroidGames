package com.sagereal.zq.game;

import java.util.HashMap;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

public class PaintFactory {
	private PaintFactory() {
	}
	public static Paint getPaint(int color) {
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		return paint;
	}
	private static HashMap<Integer, Paint> paints = new HashMap<Integer, Paint>();
	public static Paint getTextPaint(int color) {
		Paint paint = paints.get(color);
		if (paint == null) {
			paint = getPaint(color);
			paint.setTextSize(30);
			paint.setTextAlign(Align.CENTER);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(5);
			paint.setStyle(Style.FILL);
			paints.put(color, paint);// 将paint放大缓存中保存起来
		}
		return paint;
	}
	public static Paint getTextPaint(Integer number){
		
		return null;
	}
}
