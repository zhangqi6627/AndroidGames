package com.sagereal.zq.game.popstar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

public class PopStarActivity extends Activity {
	private LinearLayout linearLayout;
	private Context mContext = PopStarActivity.this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		linearLayout = new LinearLayout(mContext);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		PopGameView popGameView = new PopGameView(mContext);
		linearLayout.addView(popGameView);
		setContentView(linearLayout);
	}
}
