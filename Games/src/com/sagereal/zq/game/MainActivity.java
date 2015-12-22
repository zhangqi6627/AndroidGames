package com.sagereal.zq.game;

import com.sagereal.zq.game.bird.BirdActivity;
import com.sagereal.zq.game.block.BlockActivity;
import com.sagereal.zq.game.chess.ChessActivity;
import com.sagereal.zq.game.fish.FishActivity;
import com.sagereal.zq.game.five.FiveActivity;
import com.sagereal.zq.game.g2048.G2048Activity;
import com.sagereal.zq.game.link.LianActivity;
import com.sagereal.zq.game.piano.PianoActivity;
import com.sagereal.zq.game.popstar.PopStarActivity;
import com.sagereal.zq.game.puzzle.PuzzleActivity;
import com.sagereal.zq.game.tank.TankActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Context mContext = this;
	private Button btn_bird;
	private Button btn_chess;
	private Button btn_fish;
	private Button btn_block;
	private Button btn_lian;
	private Button btn_tank;
	private Button btn_puzzle;
	private Button btn_five;
	private Button btn_2048;
	private Button btn_popstar;
	private Button btn_piano;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// piano
		btn_piano = (Button) findViewById(R.id.btn_piano);
		btn_piano.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, PianoActivity.class));
			}
		});
		// popStar
		btn_popstar = (Button) findViewById(R.id.btn_popstar);
		btn_popstar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, PopStarActivity.class));
			}
		});
		btn_2048 = (Button) findViewById(R.id.btn_2048);
		btn_2048.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, G2048Activity.class));
			}
		});
		// FlappyBird
		btn_bird = (Button) findViewById(R.id.btn_bird);
		btn_bird.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, BirdActivity.class));
			}
		});
		// 象棋
		btn_chess = (Button) findViewById(R.id.btn_chess);
		btn_chess.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, ChessActivity.class));
			}
		});
		// 大鱼吃小鱼
		btn_fish = (Button) findViewById(R.id.btn_fish);
		btn_fish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, FishActivity.class));
			}
		});
		// 俄罗斯方块
		btn_block = (Button) findViewById(R.id.btn_block);
		btn_block.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, BlockActivity.class));
			}
		});
		// 连连看游戏
		btn_lian = (Button) findViewById(R.id.btn_lian);
		btn_lian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, LianActivity.class));
			}
		});
		// 坦克大战
		btn_tank = (Button) findViewById(R.id.btn_tank);
		btn_tank.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, TankActivity.class));
			}
		});
		// 拼图
		btn_puzzle = (Button) findViewById(R.id.btn_puzzle);
		btn_puzzle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, PuzzleActivity.class));
			}
		});
		// 五子棋
		btn_five = (Button) findViewById(R.id.btn_five);
		btn_five.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, FiveActivity.class));
			}
		});
	}
}