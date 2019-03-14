/**
 * 
 */
package math;

/**一个有趣的高精度数
 * @author dell
 *
 */
public class TrackingMaxNumber {
	/*
	 * 初始定义两个全局变量进行数据的存储
	 */
	private static int bigInt=30;
	static int[] a =new int[bigInt];//存储当前值
	static int[] b =new int[bigInt];//存储最优值
	/**
	 * 回溯操作
	 * @param i
	 */
	public static void backTracking4MaxNumber(int i) {
		if(bigThan(a, b)) {
			assignA2B(a, b);
		}
		int j;
		if(i == 1) {j = 1;}//首位不能为0，进行判断
		else j = 0;
		for (; j <= 9; j++) {
			a[i] = j;
			if (OK(a, i)) {
//				System.out.println(a[i]);//单步数据的判断
			    backTracking4MaxNumber(i + 1); 
			    }
			a[i]=-1 ;}
	}
	/**
	 * 判别是否符合数据的要求（结合余数的性质）
	 * @param a
	 * @param n
	 * @return
	 */
	public static boolean OK(int[] a, int n) {
		int r = 0;
		for(int i = 1; i <= n; i++) {
			r = r*10 + a[i];
			r = r % n;
	//整数倍乘以10没用故只用余数，防止溢出
		}
		if(r == 0) {

			return true;
		}

		return false;
	}
	/**
	 * 转换最优值
	 * @param a2
	 * @param b2
	 */
	private static void assignA2B(int[] a2, int[] b2) {
		// TODO Auto-generated method stub
		for(int i=1;i<a.length;i++){
			b[i]=a[i];//将a中的值赋给最优值b
		}
	}
	/**
	 * 判别当前值是否大于最优值
	 * @param a2当前值
	 * @param b2最优值
	 * @return
	 */
	
	private static boolean bigThan(int[] a2, int[] b2) {
		//当前值与最优值之间的比较
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
			a[i]=b[i]=-1;//初始的数据内容定义
		}
		backTracking4MaxNumber(1);//进行回溯操作
		System.out.print("最大的数为：");
		for(int i=1;i<b.length;i++){
			if(b[i]!=-1)
			System.out.print(b[i]);//最终数据的输出
		}
	}
}
