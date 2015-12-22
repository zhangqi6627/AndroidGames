package com.sagereal.zq.game.fish;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class FishActivity extends Activity {
	private Context mContext = FishActivity.this;
	private FishGameView fishGameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		fishGameView = new FishGameView(mContext);
		linearLayout.addView(fishGameView);
		setContentView(linearLayout);
	}
}
