package com.sagereal.zq.game.five;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class FiveActivity extends Activity {
	private Context mContext = FiveActivity.this;
	private FiveGameView fiveGameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fiveGameView = new FiveGameView(mContext);
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(fiveGameView);
		setContentView(linearLayout);
	}
}
