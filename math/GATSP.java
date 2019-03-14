package math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import jdk.nashorn.internal.parser.Scanner;

/**
 * �Ŵ��㷨���TSP����
 * @author dell
 *
 */
public class GATSP {

	private final static int M = 10;// ��Ⱥ��ģ

	private static int Num; // ����������Ⱦɫ�峤������������

	private int[] fitness; // �������Ӧ��

	private float[] Pi; // ������ۻ�����

	private float pCorss; // �������

	private float pMutate; // �������

	private int t;// ��ǰ����

	private Random random;

	private int T; // ���д���

	private int[][] distance; // �������

	private int bestDistance; // ��ѳ���

	private int[] bestPath; // ���·��

	private int[][] oldPopulation; // ������Ⱥ

	private int[][] newPopulation; // �Ӵ���Ⱥ

	/**
	 * �Ŵ�ִ����Ҫ����
	 * 
	 * @param t
	 * @param corss
	 * @param mutate
	 */
	public GATSP(int t, float corss, float mutate) {

		T = t;
		/**
		 * ������ʼ�������ʵĳ�ʼ��
		 */
		pCorss = corss; // �������

		pMutate = mutate;// �������
		/*
		 * �ɳ�ʼ����ĳ���������ʼ���������
		 */
		distance = new int[Num][Num];// �������[][]
		/**
		 * ���������������·���������
		 */
		for (int i = 0; i < Num; i++) {
			for (int j = i; j < Num; j++) {
				if (i == j || i == Num || j == 0)
					distance[i][j] = 0;

				else
					distance[i][j] = (int) (Math.random() * 10);

			}
		}
		// ��ֵ����
		for (int i = 0; i < Num; i++) {
			for (int j = 0; j < Num; j++) {
				if (distance[i][j] != 0) {
					distance[j][i] = distance[i][j];
				}
			}
		}
		/**
		 * ������ݵĳ�ʼ��
		 */
		bestDistance = Integer.MAX_VALUE;// ���·����ʼ��Ϊһ�����ֵ

		bestPath = new int[Num + 1];// ��ʼ�����·��

		newPopulation = new int[M][Num];// �Ӵ���Ⱥ

		oldPopulation = new int[M][Num];// ������Ⱥ

		fitness = new int[M];// �������Ӧ��

		Pi = new float[M];// ������ۻ�����

		random = new Random(System.currentTimeMillis());

	}

	/**
	 * @param args
	 *
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {

		System.out.print("���������������");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int input = Integer.parseInt(bufferedReader.readLine());
		Num = input;
		long startTime = System.currentTimeMillis(); // ��ȡ��ʼʱ��
		GATSP ga = new GATSP(10, 0.8f, 0.9f);

		ga.run();
		long endTime = System.currentTimeMillis(); // ��ȡ����ʱ��
		System.out.println();
		System.out.println("��������ʱ�䣺" + (endTime - startTime) + "ms"); // �����������ʱ��
	}

	/**
	 * ����ĳ��Ⱦɫ���ʵ�ʾ�����ΪȾɫ����Ӧ�� ��Ⱦɫ��ָ��Ϊһ���ߵ�·���� ����Ӧ��Ϊ������ո�·���ߵ�·������ len ��
	 * ��Ӧ�ȼ������Ⱦɫ������·��ʵ�ʾ�����Ϊ�������Ӧ�ȣ����£�distence[x][y]��ʾ����x��y�ľ��룩
	 * 
	 * @param chromosome
	 * @return ·������
	 */
	public int evaluate(int[] chromosome) {

		int len = 0;

		for (int i = 1; i < Num; i++) {

			len += distance[chromosome[i - 1]][chromosome[i]];

		}

		len += distance[chromosome[Num - 1]][chromosome[0]]; // �ص����

		return len;

	}

	/**
	 * ������Ⱥ��ÿ��������ۻ����� ѡ�����Ӳ������̶�ѡ����ÿ���������Ӧ��Ϊ������Ϊÿ����������ۻ����ʡ�
	 * ������ÿ��·���ߵķ�ʽ�е���Ӧ�ȵ�ֵ��Ȼ��ÿ�ζ�����ɸѡ��������Щ·���̵ģ��Ӷ�ʵ����Ⱥ���������ı��棬�Ӷ����۸��ʣ�
	 */

	void countRate() {

		int k;

		double sumFitness = 0;// ��Ӧ���ܺ�

		for (k = 0; k < M; k++) {

			sumFitness += fitness[k];

		}

		Pi[0] = (float) (fitness[0] / sumFitness);

		for (k = 1; k < M; k++) {

			Pi[k] = (float) (fitness[k] / sumFitness + Pi[k - 1]);

		}

	}

	/**
	 * ��ѡ��Ӧ����ߵĸ��� ѡ��·����̵�һ��·��
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

		copyGh(0, maxid); // ��������Ⱥ����Ӧ����ߵ�Ⱦɫ��k���Ƶ�����Ⱥ�еĵ�һλ

	}
	/**
	 * 
	 * ��ʼ����Ⱥ Ҳ�������������ߵĳ���·��������
	 * 
	 */
	void initGroup() {

		int i, j, k;

		for (k = 0; k < M; k++) { // ��Ⱥ��

			oldPopulation[k][0] = random.nextInt(Num);

			for (i = 1; i < Num;) { // Ⱦɫ�峤��
				/*
				 * nextInt����������������һ�������intֵ����ֵ����[0,n)�����䣬Ҳ����0��n֮������intֵ��
				 * ����0��������n��
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
	 * ���̶���ѡ�Ӵ�����
	 * 
	 * �������һ��0��1�ĸ�����f���� qa < f <= qb�������b��ѡ�С�
	 * 
	 */

	public void selectChild() {

		int k, i, selectId;

		float ran1; // ��ѡ����

		for (k = 1; k < M; k++) {
			/*
			 * nextFloat() �������ڻ�ȡ��һ�������α������������������о��ȷֲ���0.0��1.0֮���floatֵ��
			 */
			ran1 = random.nextFloat();// ��ʼ��һ������ֵ

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
	 * ����Ⱦɫ�壬���µ��Ӵ�
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
	 * ��Ⱥ����
	 */
	public void evolution() {

		int k;

		selectBestChild();

		selectChild();

		float r;

		for (k = 0; k < M; k = k + 2) {

			r = random.nextFloat(); // �������

			if (r < pCorss) { // ����

				orderCrossover(k, k + 1);

			} else {

				r = random.nextFloat(); // �������

				if (r < pMutate) {

					variation(k);

				}

				r = random.nextFloat(); // �������

				if (r < pMutate) {

					variation(k + 1);

				}

			}

		}

	}

	/**
	 * ����ʵ�ֽ������ʱ���б����
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
	 * ˳�򽻲�
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

		for (int i = ran1; i <= ran2; i++) { // �����Ӵ����沿��

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
	 * �����α��� ��������������ж�Σ�ÿ���ڸ������������ѡ������λ�õĻ�����н�����
	 * 
	 * @param k
	 */
	public void variation(int k) {

		int ran1, ran2, temp;

		int count;

		count = random.nextInt(Num); // �������

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

		// ��ʼ����Ⱥ

		initGroup();

		// �����ʼ����Ⱥ��Ӧ�ȣ�Fitness[max]

		for (k = 0; k < M; k++) {

			fitness[k] = evaluate(oldPopulation[k]);

		}

		// �����ʼ����Ⱥ�и���������ۻ����ʣ�Pi[max]

		countRate();

		System.out.println("��ʼ��ȺΪ��");

		for (k = 0; k < M; k++) {

			for (i = 0; i < Num; i++) {

				System.out.printf("%-4d", oldPopulation[k][i]);

			}

			System.out.println();

		}

		for (t = 0; t < T; t++) {

			evolution();

			// ������ȺnewGroup���Ƶ�����ȺoldGroup�У�׼����һ������

			for (k = 0; k < M; k++) {

				for (i = 0; i < Num; i++) {

					oldPopulation[k][i] = newPopulation[k][i];

				}

			}

			// ������Ⱥ��Ӧ��

			for (k = 0; k < M; k++) {

				fitness[k] = evaluate(oldPopulation[k]);

			}

			// ������Ⱥ�и���������ۻ�����

			countRate();

		}

		System.out.println("�����ȺΪ��");

		for (k = 0; k < M; k++) {

			for (i = 0; i < Num; i++) {

				System.out.printf("%-4d", oldPopulation[k][i]);

			}

			System.out.println();

		}

		System.out.println("��С���룺" + bestDistance);

		System.out.print("����·����");

		for (i = 0; i < Num; i++) {

			System.out.printf("%-4d", bestPath[i]);

		}

	}


}