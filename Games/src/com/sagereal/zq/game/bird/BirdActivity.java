package com.sagereal.zq.game.bird;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class BirdActivity extends Activity {
	private Context mContext = BirdActivity.this;
	private BirdGameView birdGameView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		birdGameView = new BirdGameView(mContext);
		setContentView(birdGameView);
	}
	@Override
	protected void onStop() {
		super.onStop();
		birdGameView.setAvailable(false);
	}
}
