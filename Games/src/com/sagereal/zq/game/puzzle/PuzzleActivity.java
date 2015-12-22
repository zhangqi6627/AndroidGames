package com.sagereal.zq.game.puzzle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class PuzzleActivity extends Activity {
	private Context mContext = PuzzleActivity.this;
	private PuzzleGameView gameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		gameView = new PuzzleGameView(mContext);
		linearLayout.addView(gameView);
		setContentView(linearLayout);
	}
}
