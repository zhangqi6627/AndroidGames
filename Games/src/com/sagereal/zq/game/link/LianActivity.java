package com.sagereal.zq.game.link;

import com.sagereal.zq.game.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class LianActivity extends Activity {
	private Context mContext = LianActivity.this;
	private LinkGameView gameView;
	private Button btn_new;
	private Button btn_print;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new LinkGameView(mContext, 6, 6);
		btn_new = new Button(mContext);
		btn_new.setText("重新开始");
		btn_new.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gameView.newGame();
			}
		});
		btn_print = new Button(mContext);
		btn_print.setText("打印问题矩阵");
		btn_print.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gameView.print();
			}
		});
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(gameView);
		linearLayout.addView(btn_new);
		linearLayout.addView(btn_print);
		setContentView(linearLayout);
		testLink();
	}
	private void testLink() {
		int[][] array = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 2, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 2, 0, 0, 0, 0, 0, 0 }, { 0, 2, 2, 2, 2, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };
		boolean linkable = LinkTool.link(array, new Piece(5, 1, R.drawable.ic_launcher), new Piece(6, 6, R.drawable.ic_launcher));
		Log.e("zhangqi", "linkable:" + linkable);
	}
}
