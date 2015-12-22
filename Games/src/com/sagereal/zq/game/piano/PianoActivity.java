package com.sagereal.zq.game.piano;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class PianoActivity extends Activity {
	private LinearLayout linearLayout;
	private Context mContext = PianoActivity.this;
	private PianoGameView gameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		gameView = new PianoGameView(mContext);
		linearLayout.addView(gameView);
		setContentView(linearLayout);
	}
}
