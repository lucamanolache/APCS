package exam2021;

public class ArrayResizer {

    public static boolean isNonZeroRow(int[][] array2D, int r) {
        for (int i = 0; i < array2D[r].length; i++) {
            if (array2D[r][i] == 0) {
                return false;
            }
        }
        return true;
    }

    public static int numNonZeroRows(int [][] array2D) {
        return -1;
    }

    public static int[][] resize(int[][] array2D) {
        int[][] nonZero = new int[numNonZeroRows(array2D)][array2D[0].length];

        for (int i = 0; i < array2D.length; i++) {
            if (isNonZeroRow(array2D, i)) {
                nonZero[i] = array2D[i];
            }
        }

        return nonZero;
    }
}
