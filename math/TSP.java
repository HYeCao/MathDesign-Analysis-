/**
 * 
 */
package math;

/**
 * 回溯实现
 * TSP难点：1.路径矩阵的构造 1.1 初始数据的定义 2.回溯的条件设定（check）3.swap的交换位置 4.最终结束时最佳路径的获取
 * 
 * @author dell
 *
 */
public class TSP {
	private static int NoEdge = 0;
	private static int n;// n个城市
	private static int MaxInt = 10000;
	private static int a[][];// 邻接矩阵，存储任意两个城市间的代价
	private static int bestx[];// 存储当前最小代价对应的路线；
	private static int bestc = MaxInt;// 存储当前最小代价
	private static int x[];// 存储当前走过得路径

	/**
	 * 回溯函数
	 * 
	 * @param t
	 */
	public static void Backtrack(int t) { // t的初值为2；
		if (t > n) {
			if (cost(x) < bestc) {
				bestc = cost(x);
				bestx = x;
			}
		} else {
			for (int j = t; j < n + 1; j++) {

				if (check(x, t, a, n)) {
					swap(t, j);

					Backtrack(t + 1);
//					 swap(t, j);//此处的交换需要再考虑，必须要弄懂这里
				}

			}
		}

	}

	/**
	 * 实现两个位置的交换
	 * 
	 * @param t
	 * @param j
	 */
	private static void swap(int t, int j) {
		// TODO Auto-generated method stub
		int temp;
		temp = x[t];
		x[t] = x[j];
		x[j] = temp;
	}

	/**
	 * 算出当前所走路径的耗费
	 * 
	 * @param x1
	 * @return
	 */
	private static int cost(int[] x1) {

		int cost = 0;
		for (int i = 1; i < n; i++) {
			cost = cost + a[x[i]][x[i + 1]];
		}
		cost = cost + a[x[1]][x[n]];

		return cost;

	}

	/**
	 * 检查路径是否走通
	 * 
	 * @param x
	 * @param pos
	 * @param a
	 * @param n
	 * @return
	 */
	private static boolean check(int[] x, int pos, int[][] a, int n) {
		if (pos < 2)
			return true;
		if (pos < n && a[x[pos - 1]][x[pos]] != 0) {
			// System.out.println(a[x[pos - 1]][x[pos]]);
			return true;
		}
		if (pos == n && a[x[pos - 1]][x[pos]] != 0 && a[x[1]][x[pos]] != 0) {
			// System.out.println(a[x[pos - 1]][x[pos]]);
			return true;
		}

		else
			return false;

	}

	/*
	 * 重复走的路径检查，需要再考虑考虑是否需要设置 private static boolean noRepeated(int[] x, int
	 * pos) { // TODO Auto-generated method stub return false; }
	 */
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();    //获取开始时间
		n = 15;//在此处设置城市数量
		x = new int[n + 1];
		/*
		 * 知识点：初始化后的数组的初值均为空
		 */
		for (int m = 1; m < n + 1; m++) {
			x[m] = m;
		}
	
		/**
		 * 以下为随机生成的路径矩阵
		 */

		x = new int[n + 1];
		bestx = new int[n + 1];
		for (int m = 1; m < n + 1; m++) {
			x[m] = m;
		}
		a = new int[n + 1][n + 1];
	    //a下标从1开始，0用来凑数；-1表示不同，1表示连通
		// 赋值右上一半
		for (int i = 0; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				if (i == j || i == n || j == 0)
					a[i][j] = 0;

				else
					a[i][j] = (int) (Math.random() * 10);

			}
		}
		// 赋值左下
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				if (a[i][j] != 0) {
					a[j][i] = a[i][j];
				}
			}
		}

		Backtrack(2);
		for (int i = 1; i < n; i++) {
			System.out.print(bestx[i] + "->");
		}
		System.out.println(bestx[n]);
		System.out.println(bestc);
		long endTime = System.currentTimeMillis();    //获取结束时间

		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");  //输出程序运行时间
		if((endTime - startTime)>600000){
			System.out.println("运行时间超过了600s");
		}
	}
}
