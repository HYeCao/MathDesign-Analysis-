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
 * ȫ�ֱ����Ķ���
 */
	int[] pl;//ÿһ�з��õ�λ��
	int   num;//���յ÷�����
	int[][] rot;//��Բ�ͬ�ķ�����ʵ�������ߵ�·��λ��
	            //������ʦд�ô����У�����ʵ�ֵ�ÿ��·��û�н��б��棬����ֱ����������Դ�����û��ʹ��
	int   n;//Ů����
	public Placing(int queenNum) {

		this.pl=new int[queenNum];
		this.num=0;
//		this.rot=null;
		this.n=queenNum;//��ʼ����Ů����
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
			if(Math.abs(x-j) == Math.abs(pl[x]-pl[j]) || pl[x] == pl[j])//�����ڶԽ��߻���ͬһ�е��������֮ǰ��ÿһ�ж�Ҫ�����жϣ����ڶԽ��߽���б�ʵ��ж�
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

		Placing plc = new Placing(queenNum);//����ָ��������Ů��

		plc.backtrack(0);//���л��ݲ���
		
		return plc.getPlacingNum();//ʵ�ֻ��ݺ󷽷����Ļ�ȡ
	}

	public static void main(String[] args){

		final int n = 5;

		int num = nQueen(n);

		System.out.println("The number of placing: " + num);//��ʵ�ַ��õķ�����
	}
}
