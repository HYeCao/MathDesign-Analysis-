package math;

import java.math.BigDecimal;

public class GamePassProbability {

	public static double calculatePassProbability(int[] m, int n) {
		if (n == 0) {
			return 1;
		}
		int x = (int) (n * 0.7) + 1;
		// System.out.println(x);//确定出最少赢得场数
		double pass = 0;// 定义获胜的概率
		int w = x;
		for (w = x; w < n + 1; w++) {
			pass = win(m, w, n) + pass;// win(m[50,50],2,2)
		}
		return pass;
	}

	/**
	 * 递归的具体实现 win(int[] m,int x,int y) x=获胜场数，y=总场数 win(m[50,50],2,2)
	 */
	public static double win(int[] m, int x, int y) {
		double win = 0;
		if (x == 0) {
			int x1;
			double y1 = 1;
			for (x1 = x; x1 < y; x1++) {
				y1 = 1 - y1 * m[x1] * 0.01;// 此处返回值的设置，需返回剩余的几场失败的概率和
			}
			return y1;
		} else if (x > y)
			return 0;
		else {

			win = (m[y - 1] * 0.01) * win(m, x - 1, y - 1) + (1 - (m[y - 1] * 0.01)) * win(m, x, y - 1);
			/**
			 * 要求y场赢x场，问题理解为 第x场赢*y-1场赢x-1场+第x场没赢*y-1场赢x场;
			 * win(m,n)=p*win(m-1,n-1)+（1-p)*win(m,n-1)
			 */
			BigDecimal b = new BigDecimal(win);
			win = b.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();// 四舍五入并保留五位小数
		}
		return win;

	}

	public static void main(String[] args) {

		long start = System.currentTimeMillis(); // 获取开始时间

		double pass = 0.0d;
		double pass1 = 0.0d;
		int[] p0={50};
		int[] p1 = { 50, 60 };
		int[] p3 = { 50, 50, 50 };
		int[] p4 = { 10, 60, 70, 80 ,50,60,70,80};
		int[] p2 = { 80, 80, 90, 90, 99 };
		int[] p5={10,20,30,40,50,60,70,80,90,100,20,30,40,50,60,70,10,20,30,40,50,60,23,24,50,60,70,80,100,90,70,60};
		pass = calculatePassProbability(p5, 32);
//		pass1 = calculatePassProbability(p5, 12);
		BigDecimal a = new BigDecimal(pass);
//		BigDecimal b = new BigDecimal(pass1);
		double f1 = a.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();// 四舍五入并保留五位小数
//		double f2 = b.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();// 四舍五入并保留五位小数
		 System.out.println(f1);
//		System.out.println(f2);

		long end = System.currentTimeMillis(); // 获取结束时间

		System.out.println("程序运行时间： " + (end - start) + "ms");
	}

}
