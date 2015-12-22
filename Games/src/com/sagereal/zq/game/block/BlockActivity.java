package com.sagereal.zq.game.block;

import com.sagereal.zq.game.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class BlockActivity extends Activity {
	private Context mContext = BlockActivity.this;
	private BlockGameView gameView;
	private Button btn_start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		gameView = new BlockGameView(mContext);
		linearLayout.addView(gameView);
		btn_start = new Button(mContext);
		btn_start.setText("开始游戏");
		btn_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		// linearLayout.addView(btn_start);
		View view_wheel = LayoutInflater.from(mContext).inflate(R.layout.view_wheel, linearLayout);
		view_wheel.findViewById(R.id.btn_left).setOnClickListener(onClickListener);
		view_wheel.findViewById(R.id.btn_up).setOnClickListener(onClickListener);
		view_wheel.findViewById(R.id.btn_right).setOnClickListener(onClickListener);
		view_wheel.findViewById(R.id.btn_down).setOnClickListener(onClickListener);
		setContentView(linearLayout);
	}
	OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_up:
					// 变化
					break;
				case R.id.btn_left:
					// 向左移动
					gameView.moveLeft();
					break;
				case R.id.btn_down:
					// 加速下落
					break;
				case R.id.btn_right:
					// 向右移动
					gameView.moveRight();
					break;
			}
		}
	};
}
