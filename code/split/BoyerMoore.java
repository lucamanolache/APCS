package split;

import java.util.Arrays;

/**
 * An implementation of Boyer-Moore substring search which I coded some time ago.
 *
 * @author luca
 */
public class BoyerMoore {
    private final int alphabetSize = 256;

    private int[] right;
    private String pattern;
    private int patternSize;

    BoyerMoore(String pattern) {
        this.pattern = pattern;
        this.patternSize = pattern.length();

        right = new int[alphabetSize];
        Arrays.fill(right, -1);
        for (int i = 0; i < patternSize; i++) {
            right[pattern.charAt(i)] = i;
        }
    }

    /**
     * Use the pattern to search through String str
     * @param str The string to be searched
     * @param i The starting location for the search to begin at
     * @return The first location of the pattern or -1, if not found
     */
    public int search(String str, int i) {
        int stringLength = str.length();
        int skip;

        for (; i <= stringLength - patternSize; i += skip) {
            skip = 0;

            for (int j = patternSize - 1; j >= 0; j--) {
                if (pattern.charAt(j) != str.charAt(i + j)) {
                    skip = j - right[str.charAt(i + j)];
                    if (skip < 1) {
                        skip = 1;
                    }
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Use the pattern to search through String str, starts at index 0
     * @param str The string to be searched
     * @return The first location of the pattern or -1, if not found
     */
    public int search(String str) {
        return search(str, 0);
    }

    public String getPattern() {
        return pattern;
    }
}