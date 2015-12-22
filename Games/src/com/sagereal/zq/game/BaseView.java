package com.sagereal.zq.game;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public abstract class BaseView extends View {
	protected Context mContext;
	protected Paint mPaint;
	protected int winWidth = AppConfig.winWidth;
	protected int winHeight = AppConfig.winHeight;
	public BaseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public BaseView(Context context) {
		super(context);
		init(context);
	}
	private void init(Context context) {
		mContext = context;
		mPaint = PaintFactory.getPaint(Color.BLACK);
		initView();
	}
	protected abstract void initView();
	private Toast mToast;
	protected void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
		}
		mToast.setText(msg);
		mToast.show();
	}
	protected void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", null);
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
}
