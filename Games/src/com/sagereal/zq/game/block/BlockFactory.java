package com.sagereal.zq.game.block;

import java.util.ArrayList;

public class BlockFactory {
	private BlockFactory() {
	}
	public static Block generateBlock() {
		// 正方形
		ArrayList<BlockPoint> blockPoints = new ArrayList<BlockPoint>();
		blockPoints.add(new BlockPoint(4, 0));
		blockPoints.add(new BlockPoint(5, 0));
		blockPoints.add(new BlockPoint(4, 1));
		blockPoints.add(new BlockPoint(5, 1));
		Block block = new Block(blockPoints);
		return block;
	}
}
