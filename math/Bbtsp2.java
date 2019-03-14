package math;

import java.util.Scanner;

public class Bbtsp2 {

	// Main
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		int n = 0;// ���ĸ���
		String line = s.nextLine();// ����n
		n = Integer.parseInt(line);
		a = new float[n][n];
		int[] vv = new int[n];

		for (int i = 0; i < n; i++) {
			line = s.nextLine();
			String[] sArray = line.split(" ");
			for (int j = 0; j < sArray.length; j++) {
				a[i][j] = Integer.parseInt(sArray[j]);
			}
		}
		System.out.println(bbTsp(vv));
	}// Main

	static float[][] a;// ͼ���ڽӾ���
	// static float a[][]={{-1,-1,-1,2},{2,-1,-1,-1},{1,3,-1,-1},{-1,-1,1,-1}};
	// static float a[][]={{-1,30,6,4},{30,-1,5,10},{6,5,-1,20},{4,10,20,-1}};
	// static float a[][]={{5,5,5,5},{5,5,5,5},{5,5,5,5},{5,5,5,5}};

	private static class HeapNode implements Comparable {
		float lcost, // ���������½�
				cc, // ��ǰ����
				rcost;// X[s:n-1]�ж�����С���߷��ú�
		int s;// ���ڵ㵽��ǰ����·��ΪX[0:s]
		int[] x;// ��Ҫ��һ�������Ľ����x[s+1:n-1]

		// HeapNode�Ĺ��캯��
		HeapNode(float lc, float ccc, float rc, int ss, int[] xx) {
			lcost = lc;
			cc = ccc;
			s = ss;
			x = xx;
		}// HeapNode ���캯��

		public int compareTo(Object x) {
			float xlc = ((HeapNode) x).lcost;
			if (lcost < xlc)
				return -1;
			if (lcost == xlc)
				return 0;
			return 1;
		}

	}// class HeapNode

	public static int bbTsp(int[] v) {
		int n = v.length;
		MinHeap heap = new MinHeap(100);
		float[] minOut = new float[n];// minOut[i]�Ƕ���i����С���߷���
		float minSum = 0;// ��С���߷��ú�

		// ������С���߷��ú�
		for (int i = 0; i < n; i++) {
			float min = Float.MAX_VALUE;
			for (int j = 0; j < n; j++) {
				if (a[i][j] != -1 && a[i][j] < min)
					min = a[i][j];// �л�·
			} // for j

			if (min == Float.MAX_VALUE) {
				return -1;// �޻�·
			} // if

			minOut[i] = min;
			minSum += min;
		} // for i

		// ��ʼ��
		int[] x = new int[n];
		for (int i = 0; i < n; i++) {
			x[i] = i;
		}
		HeapNode enode = new HeapNode(0, 0, minSum, 0, x);
		float bestc = Float.MAX_VALUE;

		// �������пռ���
		while (enode != null && enode.s < n - 1) {
			// System.out.println(bestc);
			x = enode.x;
			if (enode.s == n - 2)// Ҷ�ӽ��
			{
				if (a[x[n - 2]][x[n - 1]] != -1 && a[x[n - 1]][1] != -1 || bestc == Float.MAX_VALUE)// ��ǰ���Ž⻹�����ڵ����
				{
					bestc = enode.cc + a[x[n - 2]][x[n - 1]] + a[x[n - 1]][0];
					enode.cc = bestc;
					enode.lcost = bestc;
					enode.s++;
					heap.put(enode);
				}
			} // if(enode.s==n-2)

			// if(enode.s!=n-2)
			else {
				for (int i = enode.s + 1; i < n; i++) {
					if (a[x[enode.s]][x[i]] != -1) {
						float cc = enode.cc + a[x[enode.s]][x[i]];
						float rcost = enode.rcost - minOut[x[enode.s]];
						float b = cc + rcost;
						if (b < bestc) {
							int[] xx = new int[n];
							for (int j = 0; j < n; j++)
								xx[j] = x[j];
							xx[enode.s + 1] = x[i];
							xx[i] = x[enode.s + 1];
							HeapNode node = new HeapNode(b, cc, rcost, enode.s + 1, xx);
							heap.put(node);
						} // if(b<bestc)
					} // if ���ж��ӽ��
				} // for
			} // else,if(enode.s!=n-2)

			enode = (HeapNode) heap.removeMin();
		} // while
		for (int i = 0; i < n; i++)
			v[i] = x[i];
		return (int) bestc;
	}// Class bbTsp

	// ������С��
	public static class MinHeap {
		private HeapNode[] heapArray; // ������
		private int maxSize; // �ѵ�����С
		private int currentSize = 0; // �Ѵ�С

		// ���캯��
		public MinHeap(int _maxSize) {
			maxSize = _maxSize;
			heapArray = new HeapNode[maxSize];
			currentSize = 0;
		}

		// ���϶��µ���
		public void filterDown(int start, int endOfHeap) {
			int i = start;
			int j = 2 * i + 1; // j��i������Ůλ��
			HeapNode temp = heapArray[i];

			while (j <= endOfHeap) { // ����Ƿ����λ��
				if (j < endOfHeap // ��jָ������Ů�е�С��
						&& heapArray[j].cc > heapArray[j + 1].cc) {
					j++;
				}
				if (temp.cc <= heapArray[j].cc) { // С��������
					break;
				} else { // ����С�����ƣ�i��j�½�
					heapArray[i] = heapArray[j];
					i = j;
					j = 2 * j + 1;
				}
			}
			heapArray[i] = temp;
		}// filterDown

		// ���¶��ϵĵ���:�ӽ��start��ʼ��0Ϊֹ���������ϱȽϣ������Ů��ֵС��˫�׽���ֵ���ཻ��
		public void filterUp(int start) {
			int j = start;
			int i = (j - 1) / 2;
			HeapNode temp = heapArray[j];

			while (j > 0) { // ��˫�׽��·������ֱ����ڵ�
				if (heapArray[i].cc <= temp.cc) {// ˫�׽��ֵС��������
					break;
				} else {// ˫�׽��ֵ�󣬵���
					heapArray[j] = heapArray[i];
					j = i;
					i = (i - 1) / 2;
				}
				heapArray[j] = temp; // ����
			}
		}// filterUp

		// ������
		public void put(HeapNode node) {
			HeapNode newNode = node;
			heapArray[currentSize] = newNode;
			filterUp(currentSize);
			currentSize++;

		}// put

		// ɾ�����е���Сֵ
		public HeapNode removeMin() {
			HeapNode root = heapArray[0];
			heapArray[0] = heapArray[currentSize - 1];
			currentSize--;
			filterDown(0, currentSize - 1);
			return root;
		}
	}// class MinHeap
}// class Main