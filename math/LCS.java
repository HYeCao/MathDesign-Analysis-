/**
 * 
 */
package math;

/**
 * @author dell
 *
 */
public class LCS {
	public static void main(String[] args) {

		char[] s1 = { 'a', 'b', 'c', 'b', 'd', 'a', 'b' };
		char[] s2 = { 'b', 'd', 'c', 'a', 'b', 'a' };
		// String seq1 = "abcbdab";
		// String seq2 = "bdcaba";

		// char[] s1 = {'1', '2', '3', '4', '5', '6', '7', '8'};
		// char[] s2 = {'1', '4', '2', '3', '6', '8', '5', '7'};

		System.out.print("sequence1: ");
		for (int i = 0; i < s1.length; i++) {
			System.out.print(s1[i] + " ");
		}
		System.out.println();
		System.out.print("sequence2: ");
		for (int i = 0; i < s2.length; i++) {
			System.out.print(s2[i] + " ");
		}
		System.out.println();

		int[][] resultMatrix = new int[s1.length + 1][s2.length + 1];
		int[][] traceMatrix = new int[s1.length + 1][s2.length + 1];

		System.out.println(
				"The length of the longest common sequence is: " + longestComSeq(s1, s2, resultMatrix, traceMatrix));

		// System.out.println("The length of the longest common sequence is: " +
		// longestComSeq(seq1, seq2, traceMatrix));

		System.out.print("The longest common sequece is: ");
		traceback(s1, traceMatrix, s1.length, s2.length);
	}

	private static void traceback(char[] s1, int[][] traceMatrix, int length, int length2) {
		// TODO Auto-generated method stub
		
		
	}

	private static String longestComSeq(char[] s1, char[] s2, int[][] resultMatrix, int[][] traceMatrix) {
		// TODO Auto-generated method stub
		return null;
	}
}
