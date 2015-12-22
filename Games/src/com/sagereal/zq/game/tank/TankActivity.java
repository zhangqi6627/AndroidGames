package com.sagereal.zq.game.tank;

import com.sagereal.zq.game.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * @自定义一个方向盘，用于控制坦克的方向？？？
 * @发射按钮如何控制连发？？？？
 * 
 * @添加上背景地图
 * @道具
 * @生命
 * @背景音乐
 * @哈哈
 */
public class TankActivity extends Activity {
	private Context mContext = TankActivity.this;
	private View wheel;
	private TankGameView tankGameView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tank);
		wheel = findViewById(R.id.wheel);
		tankGameView = (TankGameView) findViewById(R.id.gameView);
		// wheel.findViewById(R.id.btn_up).setOnClickListener(onClickListener);
		// wheel.findViewById(R.id.btn_down).setOnClickListener(onClickListener);
		// wheel.findViewById(R.id.btn_left).setOnClickListener(onClickListener);
		// wheel.findViewById(R.id.btn_right).setOnClickListener(onClickListener);
		findViewById(R.id.btn_shoot).setOnClickListener(onClickListener);
		wheel.findViewById(R.id.btn_up).setOnTouchListener(onTouchListener);
		wheel.findViewById(R.id.btn_down).setOnTouchListener(onTouchListener);
		wheel.findViewById(R.id.btn_left).setOnTouchListener(onTouchListener);
		wheel.findViewById(R.id.btn_right).setOnTouchListener(onTouchListener);
		// findViewById(R.id.btn_shoot).setOnTouchListener(onTouchListener);
	}
	OnTouchListener onTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
				case R.id.btn_up:
					tankGameView.moveUp();
					break;
				case R.id.btn_down:
					tankGameView.moveDown();
					break;
				case R.id.btn_left:
					tankGameView.moveLeft();
					break;
				case R.id.btn_right:
					tankGameView.moveRight();
					break;
				case R.id.btn_shoot:
					tankGameView.shoot();
					break;
			}
			return false;
		}
	};
	OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_up:
					tankGameView.moveUp();
					break;
				case R.id.btn_down:
					tankGameView.moveDown();
					break;
				case R.id.btn_left:
					tankGameView.moveLeft();
					break;
				case R.id.btn_right:
					tankGameView.moveRight();
					break;
				case R.id.btn_shoot:
					tankGameView.shoot();
					break;
			}
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tankGameView.setAvailable(false);
	}
}