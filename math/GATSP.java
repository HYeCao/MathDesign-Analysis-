package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import jdk.nashorn.internal.parser.Scanner;

/**
 * 遗传算法求解TSP问题
 * @author dell
 *
 */
public class GATSP {

	private final static int M = 10;// 种群规模

	private static int Num; // 城市数量，染色体长度由输入设置

	private int[] fitness; // 个体的适应度

	private float[] Pi; // 个体的累积概率

	private float pCorss; // 交叉概率

	private float pMutate; // 变异概率

	private int t;// 当前代数

	private Random random;

	private int T; // 运行代数

	private int[][] distance; // 距离矩阵

	private int bestDistance; // 最佳长度

	private int[] bestPath; // 最佳路径

	private int[][] oldPopulation; // 父代种群

	private int[][] newPopulation; // 子代种群

	/**
	 * 遗传执行主要函数
	 * 
	 * @param t
	 * @param corss
	 * @param mutate
	 */
	public GATSP(int t, float corss, float mutate) {

		T = t;
		/**
		 * 交叉概率及变异概率的初始化
		 */
		pCorss = corss; // 交叉概率

		pMutate = mutate;// 变异概率
		/*
		 * 由初始定义的城市数来初始化距离矩阵
		 */
		distance = new int[Num][Num];// 距离矩阵[][]
		/**
		 * 随机生成数来构造路径距离矩阵
		 */
		for (int i = 0; i < Num; i++) {
			for (int j = i; j < Num; j++) {
				if (i == j || i == Num || j == 0)
					distance[i][j] = 0;

				else
					distance[i][j] = (int) (Math.random() * 10);

			}
		}
		// 赋值左下
		for (int i = 0; i < Num; i++) {
			for (int j = 0; j < Num; j++) {
				if (distance[i][j] != 0) {
					distance[j][i] = distance[i][j];
				}
			}
		}
		/**
		 * 相关数据的初始化
		 */
		bestDistance = Integer.MAX_VALUE;// 最短路径初始化为一个最大值

		bestPath = new int[Num + 1];// 初始化最佳路径

		newPopulation = new int[M][Num];// 子代种群

		oldPopulation = new int[M][Num];// 父代种群

		fitness = new int[M];// 个体的适应度

		Pi = new float[M];// 个体的累积概率

		random = new Random(System.currentTimeMillis());

	}

	/**
	 * @param args
	 *
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {

		System.out.print("请输入城市数量：");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int input = Integer.parseInt(bufferedReader.readLine());
		Num = input;
		long startTime = System.currentTimeMillis(); // 获取开始时间
		GATSP ga = new GATSP(10, 0.8f, 0.9f);

		ga.run();
		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
	}

	/**
	 * 计算某个染色体的实际距离作为染色体适应度 （染色体指代为一种走的路径） （适应度为求出按照该路径走的路径长度 len ）
	 * 适应度计算规则：染色体代表的路径实际距离作为个体的适应度，如下（distence[x][y]表示城市x到y的距离）
	 * 
	 * @param chromosome
	 * @return 路径长度
	 */
	public int evaluate(int[] chromosome) {

		int len = 0;

		for (int i = 1; i < Num; i++) {

			len += distance[chromosome[i - 1]][chromosome[i]];

		}

		len += distance[chromosome[Num - 1]][chromosome[0]]; // 回到起点

		return len;

	}

	/**
	 * 计算种群中每个个体的累积概率 选择算子采用轮盘赌选择，以每个个体的适应度为基础，为每个个体计算累积概率。
	 * （计算每种路径走的方式中的适应度的值，然后每次都进行筛选，留下那些路径短的，从而实现种群中优秀个体的保存，从而积累概率）
	 */

	void countRate() {

		int k;

		double sumFitness = 0;// 适应度总和

		for (k = 0; k < M; k++) {

			sumFitness += fitness[k];

		}

		Pi[0] = (float) (fitness[0] / sumFitness);

		for (k = 1; k < M; k++) {

			Pi[k] = (float) (fitness[k] / sumFitness + Pi[k - 1]);

		}

	}

	/**
	 * 挑选适应度最高的个体 选出路径最短的一条路径
	 */

	public void selectBestChild() {

		int k, i, maxid;

		int maxevaluation;

		maxid = 0;

		maxevaluation = fitness[0];

		for (k = 1; k < M; k++) {

			if (maxevaluation > fitness[k]) {

				maxevaluation = fitness[k];

				maxid = k;

			}

		}

		if (bestDistance > maxevaluation) {

			bestDistance = maxevaluation;

			for (i = 0; i < Num; i++) {

				bestPath[i] = oldPopulation[maxid][i];

			}

		}

		copyGh(0, maxid); // 将当代种群中适应度最高的染色体k复制到新种群中的第一位

	}
	/**
	 * 
	 * 初始化种群 也就是随机化组合走的城市路径的排列
	 * 
	 */
	void initGroup() {

		int i, j, k;

		for (k = 0; k < M; k++) { // 种群数

			oldPopulation[k][0] = random.nextInt(Num);

			for (i = 1; i < Num;) { // 染色体长度
				/*
				 * nextInt方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，
				 * 包含0而不包含n。
				 */
				oldPopulation[k][i] = random.nextInt(Num);

				for (j = 0; j < i; j++) {

					if (oldPopulation[k][i] == oldPopulation[k][j]) {

						break;

					}

				}

				if (j == i) {

					i++;

				}

			}

		}

	}
	/**
	 * 轮盘赌挑选子代个体
	 * 
	 * 随机生成一个0到1的浮点数f，若 qa < f <= qb，则个体b被选中。
	 * 
	 */

	public void selectChild() {

		int k, i, selectId;

		float ran1; // 挑选概率

		for (k = 1; k < M; k++) {
			/*
			 * nextFloat() 方法用于获取下一个从这个伪随机数生成器的序列中均匀分布的0.0和1.0之间的float值。
			 */
			ran1 = random.nextFloat();// 初始化一个概率值

			for (i = 0; i < M; i++) {

				if (ran1 <= Pi[i]) {

					break;

				}

			}

			selectId = i;

			copyGh(k, selectId);

		}

	}

	/**
	 * 复制染色体，至新的子代
	 * 
	 * @param k
	 * @param kk
	 */
	public void copyGh(int k, int kk) {

		int i;

		for (i = 0; i < Num; i++) {

			newPopulation[k][i] = oldPopulation[kk][i];

		}

	}

	/**
	 * 种群进化
	 */
	public void evolution() {

		int k;

		selectBestChild();

		selectChild();

		float r;

		for (k = 0; k < M; k = k + 2) {

			r = random.nextFloat(); // 交叉概率

			if (r < pCorss) { // 交叉

				orderCrossover(k, k + 1);

			} else {

				r = random.nextFloat(); // 变异概率

				if (r < pMutate) {

					variation(k);

				}

				r = random.nextFloat(); // 变异概率

				if (r < pMutate) {

					variation(k + 1);

				}

			}

		}

	}

	/**
	 * 用于实现交叉操作时的判别操作
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean hasElement(int[] a, int b) {

		for (int i = 0; i < a.length; i++) {

			if (a[i] == b) {

				return true;

			}

		}

		return false;

	}

	/**
	 * 顺序交叉
	 * 
	 * @param k1
	 * @param k2
	 */
	public void orderCrossover(int k1, int k2) {

		int[] child1 = new int[Num];

		int[] child2 = new int[Num];

		int ran1 = random.nextInt(Num);

		int ran2 = random.nextInt(Num);

		while (ran1 == ran2) {

			ran2 = random.nextInt(Num);

		}

		if (ran1 > ran2) {

			int temp = ran1;

			ran1 = ran2;

			ran2 = temp;

		}

		for (int i = ran1; i <= ran2; i++) { // 生成子代交叉部分

			child1[i] = newPopulation[k1][i];

			child2[i] = newPopulation[k2][i];

		}

		for (int i = 0; i < Num; i++) {

			if (i >= ran1 && i <= ran2) {

				continue;

			}

			for (int j = 0; j < Num; j++) {

				if (!hasElement(child1, newPopulation[k2][j])) {

					child1[i] = newPopulation[k2][j];

					break;

				}

			}

		}

		for (int i = 0; i < Num; i++) {

			if (i >= ran1 && i <= ran2) {

				continue;

			}

			for (int j = 0; j < Num; j++) {

				if (!hasElement(child2, newPopulation[k1][j])) {

					child2[i] = newPopulation[k1][j];

					break;

				}

			}

		}

	}

	/**
	 * 随机多次变异 变异算子随机进行多次，每次在个体基因序列中选择两个位置的基因进行交换。
	 * 
	 * @param k
	 */
	public void variation(int k) {

		int ran1, ran2, temp;

		int count;

		count = random.nextInt(Num); // 变异次数

		for (int i = 0; i < count; i++) {

			ran1 = random.nextInt(Num);

			ran2 = random.nextInt(Num);

			while (ran1 == ran2) {

				ran2 = random.nextInt(Num);

			}

			temp = newPopulation[k][ran1];

			newPopulation[k][ran1] = newPopulation[k][ran2];

			newPopulation[k][ran2] = temp;

		}

	}

	public void run() {

		int i;

		int k;

		// 初始化种群

		initGroup();

		// 计算初始化种群适应度，Fitness[max]

		for (k = 0; k < M; k++) {

			fitness[k] = evaluate(oldPopulation[k]);

		}

		// 计算初始化种群中各个个体的累积概率，Pi[max]

		countRate();

		System.out.println("初始种群为：");

		for (k = 0; k < M; k++) {

			for (i = 0; i < Num; i++) {

				System.out.printf("%-4d", oldPopulation[k][i]);

			}

			System.out.println();

		}

		for (t = 0; t < T; t++) {

			evolution();

			// 将新种群newGroup复制到旧种群oldGroup中，准备下一代进化

			for (k = 0; k < M; k++) {

				for (i = 0; i < Num; i++) {

					oldPopulation[k][i] = newPopulation[k][i];

				}

			}

			// 计算种群适应度

			for (k = 0; k < M; k++) {

				fitness[k] = evaluate(oldPopulation[k]);

			}

			// 计算种群中各个个体的累积概率

			countRate();

		}

		System.out.println("最后种群为：");

		for (k = 0; k < M; k++) {

			for (i = 0; i < Num; i++) {

				System.out.printf("%-4d", oldPopulation[k][i]);

			}

			System.out.println();

		}

		System.out.println("最小距离：" + bestDistance);

		System.out.print("最优路径：");

		for (i = 0; i < Num; i++) {

			System.out.printf("%-4d", bestPath[i]);

		}

	}


}