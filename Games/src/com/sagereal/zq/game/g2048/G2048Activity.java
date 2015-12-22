package com.sagereal.zq.game.g2048;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class G2048Activity extends Activity {
	private Context mContext = G2048Activity.this;
	private LinearLayout linearLayout;
	private G2048GameView gameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		gameView = new G2048GameView(mContext);
		linearLayout.addView(gameView);
		setContentView(linearLayout);
	}
}
