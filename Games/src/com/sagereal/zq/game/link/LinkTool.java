package com.sagereal.zq.game.link;

import java.util.ArrayList;
import com.sagereal.zq.game.LogUtil;

public class LinkTool {
	/**
	 * 
	 * 这里该如何返回连线的点的列表？？？？
	 * 
	 */
	public static boolean link(int[][] array, Piece p1, Piece p2) {
		// 判断是否为同一个方块
		if (p1.equals(p2)) {
			LogUtil.e("同一个方块");
			return false;
		}
		// 判断图片是否相同
		if (p1.isSameImage(p2)) {
			// 图片相同
			// piece2在piece1的左边
			if (p2.getX() < p1.getX()) {
				return link(array, p2, p1);
			}
			// 判断是否在同一行
			if (p1.getY() == p2.getY()) {
				// 挨着的
				if (Math.abs(p1.getX() - p2.getX()) == 1) {
					return true;
				}
				// 判断两点之间是否有障碍
				// for (int x = p1.getX() + 1; x < p2.getX(); x++) {
				// if (array[p1.getY()][x] == 0 && x == p2.getX() - 1) {
				// return true;
				// } else if (array[p1.getY()][x] != 0) {
				// break;
				// }
				// }
				if (!isXBlocked(array, new Point(p1.getX(), p1.getY()), new Point(p2.getX(), p2.getY()))) {
					return true;
				}
			}
			// 判断是否在同一列
			if (p1.getX() == p2.getX()) {
				// 挨着的
				if (Math.abs(p1.getY() - p2.getY()) == 1) {
					return true;
				}
				// 判断两点之间是否有障碍
				// if (p1.getY() < p2.getY()) {
				// // p1在p2之上
				// for (int y = p1.getY() + 1; y < p2.getY(); y++) {
				// if (array[y][p1.getX()] == 0 && y == p2.getY() - 1) {
				// return true;
				// } else if (array[y][p1.getX()] != 0) {
				// break;
				// }
				// }
				// } else {
				// // p1在p2之下
				// for (int y = p2.getY() + 1; y < p1.getY(); y++) {
				// if (array[y][p1.getX()] == 0 && y == p1.getY() - 1) {
				// return true;
				// } else if (array[y][p1.getX()] != 0) {
				// break;
				// }
				// }
				// }
				if (!isYBlocked(array, new Point(p1.getX(), p1.getY()), new Point(p2.getX(), p2.getY()))) {
					return true;
				}
			}
			// 找到piece1的上下左右所有空格
			ArrayList<Point> hPoints1 = new ArrayList<Point>();
			hPoints1.add(new Point(p1.getX(), p1.getY()));
			// 左
			for (int x = p1.getX() - 1; x >= 0; x--) {
				if (array[p1.getY()][x] == 0) {
					hPoints1.add(new Point(x, p1.getY()));
				} else {
					break;
				}
			}
			// 右
			for (int x = p1.getX() + 1; x < array[0].length; x++) {
				if (array[p1.getY()][x] == 0) {
					hPoints1.add(new Point(x, p1.getY()));
				} else {
					break;
				}
			}
			ArrayList<Point> sPoints1 = new ArrayList<Point>();
			sPoints1.add(new Point(p1.getX(), p1.getY()));
			// 上
			for (int y = p1.getY() - 1; y >= 0; y--) {
				if (array[y][p1.getX()] == 0) {
					hPoints1.add(new Point(p1.getX(), y));
				} else {
					break;
				}
			}
			// 下
			for (int y = p1.getY() + 1; y < array.length; y++) {
				if (array[y][p1.getX()] == 0) {
					hPoints1.add(new Point(p1.getX(), y));
				} else {
					break;
				}
			}
			// 找到piece2的上下左右所有空格
			ArrayList<Point> hPoints2 = new ArrayList<Point>();
			hPoints2.add(new Point(p2.getX(), p2.getY()));
			// 左
			for (int x = p2.getX() - 1; x >= 0; x--) {
				if (array[p2.getY()][x] == 0) {
					hPoints2.add(new Point(x, p2.getY()));
				} else {
					break;
				}
			}
			// 右
			for (int x = p2.getX() + 1; x < array[0].length; x++) {
				if (array[p2.getY()][x] == 0) {
					hPoints2.add(new Point(x, p2.getY()));
				} else {
					break;
				}
			}
			ArrayList<Point> sPoints2 = new ArrayList<Point>();
			sPoints2.add(new Point(p2.getX(), p2.getY()));
			// 上
			for (int y = p2.getY() - 1; y >= 0; y--) {
				if (array[y][p2.getX()] == 0) {
					hPoints2.add(new Point(p2.getX(), y));
				} else {
					break;
				}
			}
			// 下
			for (int y = p2.getY() + 1; y < array.length; y++) {
				if (array[y][p2.getX()] == 0) {
					hPoints2.add(new Point(p2.getX(), y));
				} else {
					break;
				}
			}
			// 遍历横向的点
			for (Point point1 : hPoints1) {
				for (Point point2 : hPoints2) {
					if (point1.getX() == point2.getX()) {
						if (point1.getY() == point2.getY()) {
							return true;
						}
						// 判断这两个点之间是否有障碍（Y轴方向）
						if (!isXBlocked(array, point1, point2)) {
							return true;
						} else {
							continue;
						}
					}
				}
			}
			// 遍历纵向的点
			for (Point point1 : sPoints1) {
				for (Point point2 : sPoints2) {
					if (point1.getY() == point2.getY()) {
						if (point1.getX() == point2.getX()) {
							return true;
						}
						// 判断这两个点之间是否有障碍（X轴方向）
						if (!isYBlocked(array, point1, point2)) {
							return true;
						} else {
							continue;
						}
					}
				}
			}
		} else {
			// 图片不相同
			LogUtil.e("图片不相同");
			return false;
		}
		LogUtil.e("无法连接");
		return false;
	}
	/** 判断两个点之间是否有障碍（X轴方向） */
	private static boolean isXBlocked(int[][] array, Point point1, Point point2) {
		if (point1.getX() < point2.getX()) {
			for (int x = point1.getX() + 1; x < point2.getX(); x++) {
				if (array[point1.getY()][x] != 0) {
					return true;
				} else if (array[point1.getY()][x] == 0 && x == (point2.getX() - 1)) {
					LogUtil.e("X轴方向有障碍");
					return false;
				}
			}
		} else {
			for (int x = point2.getX() + 1; x < point1.getX(); x++) {
				if (array[point1.getY()][x] != 0) {
					return true;
				} else if (array[point1.getY()][x] == 0 && x == (point1.getX() - 1)) {
					LogUtil.e("X轴方向有障碍");
					return false;
				}
			}
		}
		return false;
	}
	/** 判断两个点之间是否有障碍（Y轴方向） */
	private static boolean isYBlocked(int[][] array, Point point1, Point point2) {
		if (point1.getY() < point2.getY()) {
			for (int y = point1.getY() + 1; y < point2.getY(); y++) {
				if (array[y][point1.getX()] != 0) {
					return true;
				} else if (array[y][point1.getX()] == 0 && y == point2.getY() - 1) {
					LogUtil.e("Y轴方向有障碍");
					return false;
				}
			}
		} else {
			for (int y = point2.getY() + 1; y < point1.getY(); y++) {
				if (array[y][point1.getX()] != 0) {
					return true;
				} else if (array[y][point1.getX()] == 0 && y == point1.getY() - 1) {
					LogUtil.e("Y轴方向有障碍");
					return false;
				}
			}
		}
		return false;
	}
}
