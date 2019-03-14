/**
 * 
 */
package math;

/**
 * @author dell
 *
 */
public class RecurMatrixChain {
	public static int minMatrixChain(int[] i, int[][] j) {
		int min = recMatrix(i, j, 1, i.length - 1);
		// i ��ʼ����ľ��� j ���ڻ�ȡ�м�ֵ��׷�پ���
		// 1 ��ʼλ�� i.length-1 ��ʼ�����βλ��
		return min;

	}

	private static int recMatrix(int[] p, int[][] t, int i, int j) {
		if (i == j)
			return 0;

		int minValue = recMatrix(p, t, i, i) + recMatrix(p, t, i + 1, j) + p[i - 1] * p[i] * p[j];
		t[i][j] = i;
		for (int k = i; k < j; k++) {
			int temp = recMatrix(p, t, i, k) + recMatrix(p, t, k + 1, j) + p[i - 1] * p[k] * p[j];
			if (temp < minValue) {
				minValue = temp;
				t[i][j] = k;
			}
		}
		return minValue;
	}

	/**
	 * @param i 
	 * @param j 
	 * @param t ׷�پ���
	 * �÷������ڹ������Ž�
	 */
	public static void traceback(int i, int j, int[][] t) {
		if(i == j)	return;

		traceback(i, t[i][j], t);
		traceback(t[i][j]+1, j, t);

		System.out.print(i + "," + t[i][j] + "and");
		System.out.print(t[i][j]+1);
		System.out.print("," + j);
		System.out.println();
	}

	public static void main(String[] args) {

		int[] matrixChain = { 30, 35, 15, 5, 10, 20, 25 };// �ȹ���������ľ���
		int matrixLength = matrixChain.length - 1;
		int[][] traceMatrix = new int[matrixLength + 1][matrixLength + 1];// ׷�پ���

		System.out.println("Matrix: ");
		System.out.print(matrixChain[0] + "��" + matrixChain[1]);
		for (int i = 2; i <= matrixLength; i++) {
			System.out.print("," + matrixChain[i - 1] + "��" + matrixChain[i]);
		}
		System.out.println();

		// int minMultiplyNum = recurMatrixChain(matrixChain, traceMatrix, 1,
		// matrixLength);
		// int minMultiplyNum = matrixChain(matrixChain, traceMatrix);
		int minMultiplyNum = minMatrixChain(matrixChain, traceMatrix);
		System.out.println("The min multiply num: " + minMultiplyNum);

		traceback(1, matrixLength, traceMatrix);
	}

}
