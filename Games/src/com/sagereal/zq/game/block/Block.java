package com.sagereal.zq.game.block;

import java.util.ArrayList;

public class Block {
	private ArrayList<BlockPoint> blockPoints = new ArrayList<BlockPoint>();
	private boolean isAlive = true;
	public boolean isAlive() {
		return isAlive;
	}
	public Block() {
		super();
	}
	public Block(ArrayList<BlockPoint> blockPoints) {
		super();
		this.blockPoints = blockPoints;
	}
	/** 变化 */
	public void turn() {
		/**
		 * 
		 * 这里的业务逻辑该怎么写？？
		 * 
		 */
	}
	/** 向下移动：不要刷新一次向下移动一格 */
	public void moveDown() {
		if (!isAlive) {
			return;
		}
		// 在这里添加业务逻辑，判断是否碰到墙壁，是否落到最下面
		for (BlockPoint blockPoint : blockPoints) {
			// 砖块到最下面
			if (blockPoint.getY() == 9) {
				BlockGameView.setBlock(this);
				isAlive = false;
				BlockGameView.newBlock();
				return;
			}
			// 碰到砖块(矩阵中为1的地方)
			if (BlockGameView.map[blockPoint.getY() + 1][blockPoint.getX()] == 1) {
				BlockGameView.setBlock(this);
				isAlive = false;
				BlockGameView.newBlock();
				return;
			}
		}
		for (BlockPoint blockPoint : blockPoints) {
			blockPoint.moveDown();
		}
	}
	/**  */
	public void moveLeft() {
		if (!isAlive) {
			return;
		}
		for (BlockPoint blockPoint : blockPoints) {
			if (blockPoint.getX() == 0) {
				return;
			}
		}
		for (BlockPoint blockPoint : blockPoints) {
			blockPoint.moveLeft();
		}
	}
	/** 向右移动 */
	public void moveRight() {
		if (!isAlive) {
			return;
		}
		for (BlockPoint blockPoint : blockPoints) {
			if (blockPoint.getX() == 9) {
				return;
			}
		}
		for (BlockPoint blockPoint : blockPoints) {
			blockPoint.moveRight();
		}
	}
	public ArrayList<BlockPoint> getBlockPoints() {
		return blockPoints;
	}
	public void setBlockPoints(ArrayList<BlockPoint> blockPoints) {
		this.blockPoints = blockPoints;
	}
}
