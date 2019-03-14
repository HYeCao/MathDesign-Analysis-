/**
 * 
 */
package math;

/**
 * @author CHY
 *
 */
final class Placing{
/*
 * 全局变量的定义
 */
	int[] pl;//每一行放置的位置
	int   num;//最终得方法数
	int[][] rot;//针对不同的方法，实现最终走得路径位置
	            //对于老师写得代码中，对于实现得每组路径没有进行保存，而是直接输出，所以此数据没有使用
	int   n;//女皇数
	public Placing(int queenNum) {

		this.pl=new int[queenNum];
		this.num=0;
//		this.rot=null;
		this.n=queenNum;//初始定义女皇数
	}

	public void backtrack(int i) {

		int y=0;
		if(i==n){
			num++;
//			System.out.println("END!!");
				for(y=0;y<n-1;y++){
					System.out.print(pl[y]+"->");
				}
				System.out.println(pl[y]);
		}
		
		else{
			for(int x=0;x<n;x++){
				pl[i]=x;
				if(PlaOK(i))backtrack(i+1);
			}
		}
	}

	private boolean PlaOK(int x) {
		// TODO Auto-generated method stub
		for(int j=0; j<x; j++){
			if(Math.abs(x-j) == Math.abs(pl[x]-pl[j]) || pl[x] == pl[j])//出现在对角线或者同一列的情况（对之前的每一行都要进行判断）对于对角线进行斜率的判断
				return false;			
		}
		return true;
	}

	public int getPlacingNum() {

		return num;
	}
	
	
}
public class Queen {
	public static int nQueen(int queenNum){

		Placing plc = new Placing(queenNum);//构造指定数量的女王

		plc.backtrack(0);//进行回溯操作
		
		return plc.getPlacingNum();//实现回溯后方法数的获取
	}

	public static void main(String[] args){

		final int n = 5;

		int num = nQueen(n);

		System.out.println("The number of placing: " + num);//可实现放置的方法数
	}
}
