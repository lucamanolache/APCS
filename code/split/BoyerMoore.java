package split;

import java.util.Arrays;

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

    public int search(String str) {
        return search(str, 0);
    }

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

    public String getPattern() {
        return pattern;
    }
}