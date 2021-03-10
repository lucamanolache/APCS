package util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FastSorting {

    //
    int insertionSortThreshold = 10;

    public void pdqSort(int[] array) {

    }


    private void insertionSort(int[] arr, int s, int e) {
        if (s == e) return; // nothing to sort

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    @Test
    public void testInsertionSort() {
        int[] unsorted;
        unsorted = new int[]{1};
        insertionSort(unsorted, 0, unsorted.length);
        assertArrayEquals(new int[]{1}, unsorted);
        unsorted = new int[]{};
        insertionSort(unsorted, 0, unsorted.length);
        assertArrayEquals(new int[]{}, unsorted);
        unsorted = new int[]{5, 4};
        insertionSort(unsorted, 0, unsorted.length);
        assertArrayEquals(new int[]{4, 5}, unsorted);
        unsorted = new int[]{5, 4, 3, 2, 1};
        insertionSort(unsorted, 0, unsorted.length);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, unsorted);
        unsorted = new int[]{1, 2, 3, 4, 5};
        insertionSort(unsorted, 0, unsorted.length);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, unsorted);

        for (int i = 10; i < 100; i++) {
            unsorted = new int[i];
            int fi = i; // needed for the lambda cus java is weird
            Arrays.setAll(unsorted, operand -> (int) (Math.random() * fi));
            int[] unsortedCopy = Arrays.copyOf(unsorted, unsorted.length);
            Arrays.sort(unsortedCopy);
            insertionSort(unsorted, 0, unsorted.length);
            assertArrayEquals(unsortedCopy, unsorted);
        }
    }

    public static void main(String[] args) {
    }
}
