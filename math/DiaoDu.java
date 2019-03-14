/**
 * 
 */
package math;

/**调度问题
 * @author dell
 *
 */
public class DiaoDu {
	private static int M[][];//任务完成时间矩阵
	private static int bestx[];//存储完成时间最短的加工顺序
	private static int bestf=1000;//存储当前最短完工时间
	private static int cf=0;//存储当前完成时间
	private static int f1=0;//机器1完成时间
	private static int f2[];//机器2完成时间
	private static int n;//任务数
	private static int x[];//第i个组件
	public static void backtrack(int t){
		if (t > n) {
				bestf = cf; bestx = x;}
			   else {
			      for (int j = t; j<=n; j++){
			         f1 += M[x[j]][1];
				f2[t] = max(f2[t-1], f1) + M[x[j]][2];
			         cf += f2[t];
//			         System.out.println(cf);
			 	 if(check(bestf, cf) ) {//检查bound
			             swap(t,j);
			             backtrack(t+1);
			             swap(t,j); //恢复现场
			          f1 -= M[x[j]][1];
			          cf -= f2[t];
			     }   
			      }
			   }
	}
	/**
	 * 实现两个数的交换
	 * @param t
	 * @param j
	 */
	      private static void swap(int t,int j) {
		// TODO Auto-generated method stub
		int temp;
		temp=x[t];
		x[t]=x[j];
		x[j]=temp;
		
	}
	      /**
	       * 获取两个值的最大值
	       * @param i
	       * @param f12
	       * @return
	       */
		private static int max(int i, int f12) {
		// TODO Auto-generated method stub
			if(i>f12)return i;
			else return f12;
	}
		/**
		 * 检查bound
		 * @param bestf2
		 * @param cf2
		 * @return
		 */
		private static boolean check(int bestf2, int cf2) {
		// TODO Auto-generated method stub
			if(bestf2>cf2)return true;
			else return false;
	}
		public static void main(String[] args){
	  	   int m=3;
	  	   n=m;
	  	   M = new int[n+1][3];
	  	   M[1][1]=2;M[1][2]=1;
	  	   M[2][1]=3;M[2][2]=1;
	  	   M[3][1]=2;M[3][2]=3;
	  	   //先构造出一个对应机器的运行时间矩阵，并且对于全局变量进行初始化
	  	   bestx=new int[n];
	  	   x=new int[n+1];
	  	   for(int i=0;i<m+1;i++){
	  		   x[i]=i;
	  	   }
	  	   f2=new int[n+2];
	  	   f2[n+1]=0;
	  	   backtrack(1);//进行回溯
	  	   System.out.println("运行最短时间："+bestf);//输出最短时间
	  	   
	  	 
	  	}
	      
}
