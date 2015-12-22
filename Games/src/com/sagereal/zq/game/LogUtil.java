package com.sagereal.zq.game;

import android.util.Log;

public class LogUtil {
	public static void e(String msg) {
		Log.e("zhangqi", msg);
	}
	/** 打印二维数组 */
	public static void printArray(int[][] array) {
		System.out.println("-------打印数组-------");
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
}
