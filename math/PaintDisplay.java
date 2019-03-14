/**
 * 
 */
package math;


import java.util.Scanner;

/**
 * �������������ظ����ӣ�
 * @author dell
 *
 */
public class PaintDisplay {
    /**
     * ����һ��int���͵Ķ�ά����
     */
    static int[][] array;

    /**
     * ��ʼ����ά����
     * @param length
     * @param width
     */
    public static int[][] new_2Array(int length,int width){
        array = new int[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                array[i][j] = 0;
            }
        }
        return array;
    }

    /**
     * �жϴ�λ���Ƿ���Է��þ���
     * @param i
     * @param j
     * @return
     */
    public static boolean isPut(int i,int j){
        if (array[i][j] == 0 && (i-1 < 0 || array[i - 1][j] != 1) && (j-1 < 0 ||
                array[i][j - 1] != 1) && (i+1 >= array.length || array[i + 1][j] != 1)  &&
                (j+1 >= array[i].length || array[i][j + 1] != 1)){
            return true;
        }
        return false;
    }

    /**
     * ���þ������Ҹı���Ӧ��ֵ
     * @param i
     * @param j
     */
    public static void set(int i,int j){
        // ��λ�÷��þ���
        array[i][j] = 2;
        //�ı���þ�������⵽λ�õ�ֵ
        //��
        if (i > 0 && array[i - 1][j] == 0)	{
            array[i - 1][j] = 1;
        }
        //��
        if (j > 0 && array[i][j - 1] == 0){
            array[i][j - 1] = 1;
        }
        //��
        if (j+1 < array[i].length && array[i][j + 1] == 0)	{
            array[i][j + 1] = 1;
        }
        //��
        if (i+1 < array.length && array[i + 1][j] == 0)	 {
            array[i + 1][j] = 1;
        }
    }

    /**
     * ����Ƿ�ȫ������
     * @param length
     * @param width
     * @return
     */
    public static boolean check(int length,int width){
        for (int i = 0; i < length;++i){
            for (int k = 0; k < width; ++k){
                if (array[i][k] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ȫ������
     * @param length
     * @param width
     */
    public static void goBack(int length,int width){
        for (int i = 0; i < length; ++i){
            for (int k = 0; k < width; ++k){
                array[i][k] = 0;
            }
        }
    }

    /**
     * ����λ�õ�����
     * @param x
     * @param y
     * @param length
     * @param width
     */
    public static void insert(int x,int y,int length,int width){
        //�жϴ�λ���Ƿ�Խ��
        if (x >= length || y >= width){
            return;
        }
        //�жϴ�λ���Ƿ���ʷ���
        if (isPut(x, y)){
            set(x, y); //������ʣ����þ������Ҹı���Ӧ��ֵ
        }
        //����һ�н��ŷ���
        if (y == width-1){
            insert(x+1,0,length,width);
        }else{
            insert(x,y+1,length,width);
        }
    }

    /**
     * ��һ���þ���
     * @param length
     * @param width
     */
    public static void insert1(int length,int width){
        for (int i = 0; i < length;++i){
            for (int j = 0; j < width;++j){
                //��i,jλ�����
                insert(i,j,length,width);
                //�ж��Ƿ�����
                if (check(length,width)){
                    break;
                }else {
                    //����
                    goBack(length, width);
                }
            }
        }
    }
    
    /**
     * ��ӡ��ά����Ԫ��
     */
    public static void show2(){
        for(int i = 0;i < array.length;i++) {
            for(int j = 0;j < array[i].length;j++) {
                System.out.print(array[i][j]+"\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        for(;;) {
        	 System.out.println("˵����2 ��ʾ����λ��  1 ��ʾ�޾���λ��  0 ��ʾ�޷�ʵ��");
            System.out.print("��������m: ");
            Scanner sc1 = new Scanner(System.in);
            int m = Integer.parseInt(sc1.next());
            System.out.print("��������n: ");
            sc1 = new Scanner(System.in);
            int n = Integer.parseInt(sc1.next());
            new_2Array(m, n);
            insert1(m, n);
            show2();
        }
    }
}

