package math;

import java.math.BigDecimal;

public class GamePassProbability {

	public static double calculatePassProbability(int[] m, int n) {
		if (n == 0) {
			return 1;
		}
		int x = (int) (n * 0.7) + 1;
		// System.out.println(x);//ȷ��������Ӯ�ó���
		double pass = 0;// �����ʤ�ĸ���
		int w = x;
		for (w = x; w < n + 1; w++) {
			pass = win(m, w, n) + pass;// win(m[50,50],2,2)
		}
		return pass;
	}

	/**
	 * �ݹ�ľ���ʵ�� win(int[] m,int x,int y) x=��ʤ������y=�ܳ��� win(m[50,50],2,2)
	 */
	public static double win(int[] m, int x, int y) {
		double win = 0;
		if (x == 0) {
			int x1;
			double y1 = 1;
			for (x1 = x; x1 < y; x1++) {
				y1 = 1 - y1 * m[x1] * 0.01;// �˴�����ֵ�����ã��践��ʣ��ļ���ʧ�ܵĸ��ʺ�
			}
			return y1;
		} else if (x > y)
			return 0;
		else {

			win = (m[y - 1] * 0.01) * win(m, x - 1, y - 1) + (1 - (m[y - 1] * 0.01)) * win(m, x, y - 1);
			/**
			 * Ҫ��y��Ӯx�����������Ϊ ��x��Ӯ*y-1��Ӯx-1��+��x��ûӮ*y-1��Ӯx��;
			 * win(m,n)=p*win(m-1,n-1)+��1-p)*win(m,n-1)
			 */
			BigDecimal b = new BigDecimal(win);
			win = b.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();// �������벢������λС��
		}
		return win;

	}

	public static void main(String[] args) {

		long start = System.currentTimeMillis(); // ��ȡ��ʼʱ��

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
		double f1 = a.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();// �������벢������λС��
//		double f2 = b.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();// �������벢������λС��
		 System.out.println(f1);
//		System.out.println(f2);

		long end = System.currentTimeMillis(); // ��ȡ����ʱ��

		System.out.println("��������ʱ�䣺 " + (end - start) + "ms");
	}

}
