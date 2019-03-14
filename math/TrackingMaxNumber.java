/**
 * 
 */
package math;

/**һ����Ȥ�ĸ߾�����
 * @author dell
 *
 */
public class TrackingMaxNumber {
	/*
	 * ��ʼ��������ȫ�ֱ����������ݵĴ洢
	 */
	private static int bigInt=30;
	static int[] a =new int[bigInt];//�洢��ǰֵ
	static int[] b =new int[bigInt];//�洢����ֵ
	/**
	 * ���ݲ���
	 * @param i
	 */
	public static void backTracking4MaxNumber(int i) {
		if(bigThan(a, b)) {
			assignA2B(a, b);
		}
		int j;
		if(i == 1) {j = 1;}//��λ����Ϊ0�������ж�
		else j = 0;
		for (; j <= 9; j++) {
			a[i] = j;
			if (OK(a, i)) {
//				System.out.println(a[i]);//�������ݵ��ж�
			    backTracking4MaxNumber(i + 1); 
			    }
			a[i]=-1 ;}
	}
	/**
	 * �б��Ƿ�������ݵ�Ҫ�󣨽�����������ʣ�
	 * @param a
	 * @param n
	 * @return
	 */
	public static boolean OK(int[] a, int n) {
		int r = 0;
		for(int i = 1; i <= n; i++) {
			r = r*10 + a[i];
			r = r % n;
	//����������10û�ù�ֻ����������ֹ���
		}
		if(r == 0) {

			return true;
		}

		return false;
	}
	/**
	 * ת������ֵ
	 * @param a2
	 * @param b2
	 */
	private static void assignA2B(int[] a2, int[] b2) {
		// TODO Auto-generated method stub
		for(int i=1;i<a.length;i++){
			b[i]=a[i];//��a�е�ֵ��������ֵb
		}
	}
	/**
	 * �б�ǰֵ�Ƿ��������ֵ
	 * @param a2��ǰֵ
	 * @param b2����ֵ
	 * @return
	 */
	
	private static boolean bigThan(int[] a2, int[] b2) {
		//��ǰֵ������ֵ֮��ıȽ�
		int la=0;
		int lb=0;
		for(int i=1;i<30;i++){
			la++;
			if(a[i]==-1)break;
		}
		for(int i=1;i<30;i++){
			lb++;
			if(b[i]==-1)break;
		}
		
		if(lb>la){
			return false;
		}
		else if(lb<la){
			return true;
		}
		else if(lb==la){
			for(int i=1;i<la;i++){
				if(a[i]>b[i]){
					return true;
				}
				else if(a[i]<b[i]){
					return false;
				}
			}
		}
		return false;
	}
	public static void main(String[] args){
		for(int i=0;i<30;i++){
			a[i]=b[i]=-1;//��ʼ���������ݶ���
		}
		backTracking4MaxNumber(1);//���л��ݲ���
		System.out.print("������Ϊ��");
		for(int i=1;i<b.length;i++){
			if(b[i]!=-1)
			System.out.print(b[i]);//�������ݵ����
		}
	}
}
