package com.sagereal.zq.game.fish;

import java.util.ArrayList;

public class FishManager {
	private ArrayList<Fish> fishs = new ArrayList<Fish>();
	// 单例模式
	private FishManager() {
	}
	private static FishManager fishManager;
	public static FishManager getInstance() {
		if (fishManager == null) {
			fishManager = new FishManager();
		}
		return fishManager;
	}
	public ArrayList<Fish> getAllFishs() {
		return fishs;
	}
	/** 添加一条新鱼 */
	public void addFish(float x, float y, int size) {
		Fish fish = getAvailableFish();
		if (fish == null) {
			synchronized (fishs) {
				Fish newFish = new Fish(x, y);
				newFish.setSize(size);
				fishs.add(newFish);
			}
		} else {
			fish.setAlive(true);
			fish.setSize(size);
			fish.setXY(x, y);
		}
	}
	private Fish getAvailableFish() {
		synchronized (fishs) {
			for (Fish fish : fishs) {
				if (!fish.isAlive()) {
					return fish;
				}
			}
		}
		return null;
	}
}
