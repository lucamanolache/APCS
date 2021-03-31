package exam2018;

public class ArrayTester {

    public static int[] getColumn(int[][] arr2d, int c) {
        int[] ret = new int[arr2d.length];
        for (int i = 0; i < arr2d.length; i++) {
            ret[i] = arr2d[i][c];
        }
        return ret;
    }

    public static boolean hasAllValues(int[] arr1, int[] arr2) {
        return true;
    }

    public static boolean containsDuplicates(int[] arr1) {
        return true;
    }

    public static boolean isLatin(int[][] square) {
        if (containsDuplicates(square[0])) return false;
        for (int i = 1; i < square.length; i++) {
            if (!hasAllValues(square[i], square[0])) return false;
        }
        for (int i = 0; i < square[0].length; i++) {
            if (!hasAllValues(getColumn(square, i), square[0])) return false;
        }
        return true;
    }
}
