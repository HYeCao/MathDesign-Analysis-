package math;

import java.util.Collections;
import java.util.LinkedList;
/*
 * �Ͻ�UB��̰����� ÿ���߲��ɻ�����С·��
 * �½�DB���ڽӾ���ÿ����СԪ�����/2 �൱��ÿ��������С�������֮�ͳ���2
 *         ����ÿ���������Сƽ������֮��
 */
/**
 * ��֧���編���TSP����
 * @author dell
 *
 */
public class BBTSP {
	
	float[][] a;//����ͼ�ڽӾ��󣬴洢���ڵ�֮��ľ���
	public BBTSP(float[][] a){
		this.a=a;
	}
	//ÿ���ڵ�������� 
	public static class HeapNode implements Comparable{
		float lcost;//�������õ��½�
		float cc;//��ǰ����
		float rcost;//x[s:n-1]�ж�����С���߷��ú�
		int s;//���ڵ㵽��ǰ�ڵ��·��Ϊx[0:s]
		int[] x;//��Ҫ��һ�������Ķ�����x[s+1:n-1]
		
		//���췽��
		public HeapNode(float lc,float ccc,float rc,int ss,int[] xx){
			lcost=lc;
			cc=ccc;
			s=ss;
			x=xx;
		}
		//�Ƚ���������
		public int compareTo(Object x){
			float xlc=((HeapNode) x).getlocst();//��ȡ�Ƚ϶����������Сֵ
			if(lcost<xlc) return -1;//�½�������½�С
			if(lcost==xlc) return 0;
			return 1;//��ǰ�½�������½�С
		}
		public float getlocst(){
			return lcost;
		}
	}
	
	public float bbTsp(int[] v){
		int n=v.length-1;//�ڵ���
		LinkedList<HeapNode> heap=new LinkedList<HeapNode>();
		//minOut[i]=i����С���߷���
		float[] minOut=new float[n+1];
		float minSum=0;//��С���߷��ú�
		for(int i=1;i<=n;i++){//���ÿ���ڵ㣬�ҵ���С����
			//����minOut[i]��minSum
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
		
		//��ʼ��
		int[] x=new int[n];
		for(int i=0;i<n;i++)
			x[i]=i+1;
		HeapNode enode=new HeapNode(0,0,minSum,0,x);
		float bestc=Float.MAX_VALUE;//���·������
		
		//�������пռ���
		while(enode!=null&&enode.s<n-1){
			//��Ҷ�ڵ�
			x=enode.x;
			if(enode.s==n-2){
				//��ǰ��չ�����Ҷ�ڵ�ĸ��ڵ�
				//�ټ������߹��ɻ�·
				//�����ɻ�·�Ƿ����ڵ�ǰ���Ž�
				if(a[x[n-2]][x[n-1]]!=-1&&a[x[n-1]][1]!=-1&&enode.cc+a[x[n-2]][x[n-1]]+a[x[n-1]][1]<bestc){
					//�ҵ����ø�С�Ļ�·
					bestc=enode.cc+a[x[n-2]][x[n-1]]+a[x[n-1]][1];
					enode.cc=bestc;
					enode.lcost=bestc;
					enode.s++;
					heap.add(enode);
					Collections.sort(heap);
				}
			}else{//�ڲ����
				//������ǰ��չ���Ķ��ӽ��
				for(int i=enode.s+1;i<n;i++){
					if(a[x[enode.s]][x[i]]!=-1){
						//���ж��ӽ��
						float cc=enode.cc+a[x[enode.s]][x[i]];
						/**
						 * float lcost;//�������õ��½�
		                   float cc;//��ǰ����
		                   float rcost;//x[s:n-1]�ж�����С���߷��ú�
						 */
						float rcost=enode.rcost=minOut[x[enode.s]];
						/**
						 * �½���б𣬴˴��������ϸ��˼��
						 */
						float b=cc+rcost;//�½�
						if(b<bestc){
							//�������ܺ������Ž⣬��������С��
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
			
			//ȡ��һ����չ���
			enode=heap.poll();
		}
		//�����Ž⸴�Ƶ�v[1...n]
		for(int i=0;i<n;i++)
			v[i+1]=x[i];
		return bestc;
	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();    //��ȡ��ʼʱ��
		int n=12;
		float[][] a=new float[n+1][n+1];
			//{{0,0,0,0,0},{0,-1,30,6,4},{0,30,-1,5,10},{0,6,5,-1,20},{0,4,10,20,-1}};
		    //a�±��1��ʼ��0����������-1��ʾ��ͬ��1��ʾ��ͨ
	       //��ֵ����һ��
		
			for(int i=0;i<=n;i++) {
	        	  for(int j=i;j<=n;j++) {
	        		  if(i==j||i==n||j==0) a[i][j]=0;
	        		  
	        		  else a[i][j]=(int)(Math.random()*10);
	        		       
	        	  }
	          }
			//��ֵ����
			for(int i=0;i<=n;i++) {
	      	  for(int j=0;j<=n;j++) {
	      		  if(a[i][j]!=0) {
	      			  a[j][i]=a[i][j];	      		  
	      	  }
	      	  }
	      	  }
		BBTSP b=new BBTSP(a);
		int []v=new int[n+1];
		System.out.println("��̻�·��Ϊ��"+b.bbTsp(v));
		System.out.print("��̻�·Ϊ��");
		for(int c=1;c<=n;c++){
			System.out.print(v[c]+" ");
		}
	

	long endTime = System.currentTimeMillis();    //��ȡ����ʱ��

	System.out.println("��������ʱ�䣺" + (endTime - startTime) + "ms");    //�����������ʱ��
}
	
}

/*
==============================================================
�������ݣ�
int n=4;
float[][] a={{0,0,0,0,0},{0,-1,30,6,4},{0,30,-1,5,10},{0,6,5,-1,20},{0,4,10,20,-1}};
�����
��̻�·��Ϊ��25.0
��̻�·Ϊ��1 3 2 4 
===============================================================
�������ݣ�
int n=5;
float[][] a={{0,0,0,0,0,0},{0,-1,5,-1,7,9},{0,5,-1,10,3,6},{0,-1,10,-1,8,-1},{0,7,3,8,-1,4},{0,9,6,-1,4,-1}};
�����
��̻�·��Ϊ��36.0
��̻�·Ϊ��1 5 2 3 4 
 */ 


