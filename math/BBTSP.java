package math;

import java.util.Collections;
import java.util.LinkedList;
/*
 * 上界UB：贪心求得 每次走不成环的最小路径
 * 下界DB：邻接矩阵每行最小元素相加/2 相当于每个顶点最小出边入边之和除以2
 *         经过每个定点的最小平均开销之和
 */
/**
 * 分支定界法解决TSP问题
 * @author dell
 *
 */
public class BBTSP {
	
	float[][] a;//有向图邻接矩阵，存储两节点之间的距离
	public BBTSP(float[][] a){
		this.a=a;
	}
	//每个节点的属性有 
	public static class HeapNode implements Comparable{
		float lcost;//子树费用的下界
		float cc;//当前费用
		float rcost;//x[s:n-1]中顶点最小出边费用和
		int s;//根节点到当前节点的路径为x[0:s]
		int[] x;//需要进一步搜索的顶点是x[s+1:n-1]
		
		//构造方法
		public HeapNode(float lc,float ccc,float rc,int ss,int[] xx){
			lcost=lc;
			cc=ccc;
			s=ss;
			x=xx;
		}
		//比较子树费用
		public int compareTo(Object x){
			float xlc=((HeapNode) x).getlocst();//获取比较对象的子树最小值
			if(lcost<xlc) return -1;//下界比子树下界小
			if(lcost==xlc) return 0;
			return 1;//当前下界比子树下界小
		}
		public float getlocst(){
			return lcost;
		}
	}
	
	public float bbTsp(int[] v){
		int n=v.length-1;//节点数
		LinkedList<HeapNode> heap=new LinkedList<HeapNode>();
		//minOut[i]=i的最小出边费用
		float[] minOut=new float[n+1];
		float minSum=0;//最小出边费用和
		for(int i=1;i<=n;i++){//针对每个节点，找到最小出边
			//计算minOut[i]和minSum
			float min=Float.MAX_VALUE;
			for(int j=1;j<=n;j++){
				if(a[i][j]<Float.MAX_VALUE&&a[i][j]<min)
					min=a[i][j];
			}
			if(min==Float.MAX_VALUE)
				return Float.MAX_VALUE;
			minOut[i]=min;
			minSum+=min;
		}
		
		//初始化
		int[] x=new int[n];
		for(int i=0;i<n;i++)
			x[i]=i+1;
		HeapNode enode=new HeapNode(0,0,minSum,0,x);
		float bestc=Float.MAX_VALUE;//最短路径长度
		
		//搜索排列空间树
		while(enode!=null&&enode.s<n-1){
			//非叶节点
			x=enode.x;
			if(enode.s==n-2){
				//当前扩展结点是叶节点的父节点
				//再加两条边构成回路
				//所构成回路是否优于当前最优解
				if(a[x[n-2]][x[n-1]]!=-1&&a[x[n-1]][1]!=-1&&enode.cc+a[x[n-2]][x[n-1]]+a[x[n-1]][1]<bestc){
					//找到费用更小的回路
					bestc=enode.cc+a[x[n-2]][x[n-1]]+a[x[n-1]][1];
					enode.cc=bestc;
					enode.lcost=bestc;
					enode.s++;
					heap.add(enode);
					Collections.sort(heap);
				}
			}else{//内部结点
				//产生当前扩展结点的儿子结点
				for(int i=enode.s+1;i<n;i++){
					if(a[x[enode.s]][x[i]]!=-1){
						//可行儿子结点
						float cc=enode.cc+a[x[enode.s]][x[i]];
						/**
						 * float lcost;//子树费用的下界
		                   float cc;//当前费用
		                   float rcost;//x[s:n-1]中顶点最小出边费用和
						 */
						float rcost=enode.rcost=minOut[x[enode.s]];
						/**
						 * 下界的判别，此处需进行详细的思考
						 */
						float b=cc+rcost;//下界
						if(b<bestc){
							//子树可能含有最优解，结点插入最小堆
							int[] xx=new int[n];
							for(int j=0;j<n;j++)
								xx[j]=x[j];
							xx[enode.s+1]=x[i];
							xx[i]=x[enode.s+1];
							HeapNode node=new HeapNode(b,cc,rcost,enode.s+1,xx);
							heap.add(node);
							Collections.sort(heap);
						}
					}
				}
				
			}
			
			//取下一个扩展结点
			enode=heap.poll();
		}
		//将最优解复制到v[1...n]
		for(int i=0;i<n;i++)
			v[i+1]=x[i];
		return bestc;
	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();    //获取开始时间
		int n=12;
		float[][] a=new float[n+1][n+1];
			//{{0,0,0,0,0},{0,-1,30,6,4},{0,30,-1,5,10},{0,6,5,-1,20},{0,4,10,20,-1}};
		    //a下标从1开始，0用来凑数；-1表示不同，1表示连通
	       //赋值右上一半
		
			for(int i=0;i<=n;i++) {
	        	  for(int j=i;j<=n;j++) {
	        		  if(i==j||i==n||j==0) a[i][j]=0;
	        		  
	        		  else a[i][j]=(int)(Math.random()*10);
	        		       
	        	  }
	          }
			//赋值左下
			for(int i=0;i<=n;i++) {
	      	  for(int j=0;j<=n;j++) {
	      		  if(a[i][j]!=0) {
	      			  a[j][i]=a[i][j];	      		  
	      	  }
	      	  }
	      	  }
		BBTSP b=new BBTSP(a);
		int []v=new int[n+1];
		System.out.println("最短回路长为："+b.bbTsp(v));
		System.out.print("最短回路为：");
		for(int c=1;c<=n;c++){
			System.out.print(v[c]+" ");
		}
	

	long endTime = System.currentTimeMillis();    //获取结束时间

	System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
}
	
}

/*
==============================================================
测试数据：
int n=4;
float[][] a={{0,0,0,0,0},{0,-1,30,6,4},{0,30,-1,5,10},{0,6,5,-1,20},{0,4,10,20,-1}};
输出：
最短回路长为：25.0
最短回路为：1 3 2 4 
===============================================================
测试数据：
int n=5;
float[][] a={{0,0,0,0,0,0},{0,-1,5,-1,7,9},{0,5,-1,10,3,6},{0,-1,10,-1,8,-1},{0,7,3,8,-1,4},{0,9,6,-1,4,-1}};
输出：
最短回路长为：36.0
最短回路为：1 5 2 3 4 
 */ 


