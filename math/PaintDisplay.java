/**
 * 
 */
package math;


import java.util.Scanner;

/**
 * 世界名画（不重复监视）
 * @author dell
 *
 */
public class PaintDisplay {
    /**
     * 声明一个int类型的二维数组
     */
    static int[][] array;

    /**
     * 初始化二维数组
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
     * 判断此位置是否可以放置警卫
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
     * 设置警卫并且改变相应的值
     * @param i
     * @param j
     */
    public static void set(int i,int j){
        // 此位置放置警卫
        array[i][j] = 2;
        //改变放置警卫能侦测到位置的值
        //上
        if (i > 0 && array[i - 1][j] == 0)	{
            array[i - 1][j] = 1;
        }
        //左
        if (j > 0 && array[i][j - 1] == 0){
            array[i][j - 1] = 1;
        }
        //右
        if (j+1 < array[i].length && array[i][j + 1] == 0)	{
            array[i][j + 1] = 1;
        }
        //下
        if (i+1 < array.length && array[i + 1][j] == 0)	 {
            array[i + 1][j] = 1;
        }
    }

    /**
     * 检查是否全部填满
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
     * 全部回退
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
     * 警卫位置的填入
     * @param x
     * @param y
     * @param length
     * @param width
     */
    public static void insert(int x,int y,int length,int width){
        //判断此位置是否越界
        if (x >= length || y >= width){
            return;
        }
        //判断此位置是否合适放置
        if (isPut(x, y)){
            set(x, y); //如果合适，设置警卫并且改变相应的值
        }
        //到下一行接着放置
        if (y == width-1){
            insert(x+1,0,length,width);
        }else{
            insert(x,y+1,length,width);
        }
    }

    /**
     * 逐一添置警卫
     * @param length
     * @param width
     */
    public static void insert1(int length,int width){
        for (int i = 0; i < length;++i){
            for (int j = 0; j < width;++j){
                //往i,j位置添加
                insert(i,j,length,width);
                //判断是否填满
                if (check(length,width)){
                    break;
                }else {
                    //回退
                    goBack(length, width);
                }
            }
        }
    }
    
    /**
     * 打印二维数组元素
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
        	 System.out.println("说明：2 表示警卫位置  1 表示无警卫位置  0 表示无法实现");
            System.out.print("设置行数m: ");
            Scanner sc1 = new Scanner(System.in);
            int m = Integer.parseInt(sc1.next());
            System.out.print("设置列数n: ");
            sc1 = new Scanner(System.in);
            int n = Integer.parseInt(sc1.next());
            new_2Array(m, n);
            insert1(m, n);
            show2();
        }
    }
}

