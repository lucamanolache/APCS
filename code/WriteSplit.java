import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class BoyerMoore {
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

class KMP {
    private static final int R = 128; // size of our alphabet.

    private String pattern;
    private int[][] dfa;

    public KMP(String pattern) {
        // Building the DFA from our pattern
        this.pattern = pattern;
        int patternSize = pattern.length();
        this.dfa = new int[R][patternSize];

       dfa[pattern.charAt(0)][0] = 1;
       int i = 0;
        for (int j = 1; j < patternSize; j++) {
            // fill in at dfa[][j]
            for (int k = 0; k < R; k++) {
                dfa[k][j] = dfa[k][i];
            }

            dfa[pattern.charAt(j)][j] = j + 1;
            i = dfa[pattern.charAt(j)][i];
        }
    }

    /**
     * Use the pattern to search through String str
     * @param str The string to be searched
     * @return The first location of the pattern or -1, if not found
     */
    public int search(String str, int i) {
        int j = 0;
        int stringLength = str.length();
        int patternLength = pattern.length();

        while (i < stringLength && j < patternLength) {
            j = dfa[str.charAt(i)][j];
            i++;
        }
        if (j == patternLength) {
            // Found
            return i - patternLength;
        } else {
            // not found
            return -1;
        }
    }

    public int search(String str) {
        return search(str, 0);
    }

    public String getPattern() {
        return pattern;
    }
}

public class WriteSplit {

    private final BiFunction<String, String, String[]> bruteForceSubstring = this::bruteForceSubstringSearchSplit;
    private final BiFunction<String, String, String[]> KMPSubstring = this::knuthMorisPrattSubstringSearchSplit;
    private final BiFunction<String, String, String[]> boyerMooreSubstring = this::boyerMooreSubstringSearchSplit;

    private final BiFunction<String, String, String[]> defaultBackend = boyerMooreSubstring;

    /**
     * To keep to the spirit of coding the split method from scratch, I am also coding the substring search from scratch.
     * Should do the same thing as {@link String#indexOf(String, int)}.
     */
    private int bruteForceSubstringSearch(String str, String regex, int start) {
        for (int i = start; i <= str.length() - regex.length(); i++) {
            for (int j = 0; j <= regex.length(); j++) {
                if (regex.charAt(j) != str.charAt(i + j)) {
                    break;
                } else if (j == regex.length() - 1) {
                    return i;
                }
            }
        }
        // The regex was not found
        return -1;
    }

    /**
     * Returns what a regular str.split does, taking the same inputs.
     * The algorithms used works like this, it uses {@link #bruteForceSubstringSearch(String, String, int)} to find the
     * first occurrence of the regex, and the puts the substring from the last split (or 0 if it is the first split) to
     * the location of the substring into an {@link ArrayList<String>}. At the end the {@link ArrayList<String>} is
     * turned into a String[] and trimmed to return the same value as the split method in Strings.
     */
    private String[] bruteForceSubstringSearchSplit(String str, String regex) {
        ArrayList<String> split = new ArrayList<>();

        int i = 0;
        int regexFound;
        while (true) {
            regexFound = bruteForceSubstringSearch(str, regex, i);
            // Break out of the loop if there are no more cases of the regex
            if (regexFound == -1) {
                break;
            }
            // Split the string at the first occurrence of the regex
            split.add(str.substring(i, regexFound));
            // Increase the current letter we are at at to after where the previous regex occurrence was
            i = regexFound + regex.length();
        }

        // If we have not gotten to the end of the string, add it as well
        if (i < str.length()) {
            split.add(str.substring(i));
        } else {
            // Remove strings of size 0 at the to match the output of str.split
            for (int j = split.size() - 1; j >= 0; j--) {
                if (split.get(j).length() == 0) {
                    split.remove(j);
                } else {
                    break;
                }
            }
        }

        return split.toArray(String[]::new);
    }

    private String[] knuthMorisPrattSubstringSearchSplit(String str, String regex) {
        return  knuthMorisPrattSubstringSearchSplit(str, new KMP(regex));
    }

    /**
     * Same algorithm as {@link #bruteForceSubstringSearchSplit(String, String)} except uses
     */
    private String[] knuthMorisPrattSubstringSearchSplit(String str, KMP regex) {
        ArrayList<String> split = new ArrayList<>();

        int i = 0;
        int regexFound;
        while (true) {
            regexFound = regex.search(str, i);
            // Break out of the loop if there are no more cases of the regex
            if (regexFound == -1) {
                break;
            }
            // Split the string at the first occurrence of the regex
            split.add(str.substring(i, regexFound));
            // Increase the current letter we are at at to after where the previous regex occurrence was
            i = regexFound + regex.getPattern().length();
        }

        // If we have not gotten to the end of the string, add it as well
        if (i < str.length()) {
            split.add(str.substring(i));
        } else {
            // Remove strings of size 0 at the to match the output of str.split
            for (int j = split.size() - 1; j >= 0; j--) {
                if (split.get(j).length() == 0) {
                    split.remove(j);
                } else {
                    break;
                }
            }
        }

        return split.toArray(String[]::new);
    }

    private String[] boyerMooreSubstringSearchSplit(String str, String regex) {
        return boyerMooreSubstringSearchSplit(str, new BoyerMoore(regex));
    }

    private String[] boyerMooreSubstringSearchSplit(String str, BoyerMoore regex) {
        ArrayList<String> split = new ArrayList<>();

        int i = 0;
        int regexFound;
        while (true) {
            regexFound = regex.search(str, i);
            // Break out of the loop if there are no more cases of the regex
            if (regexFound == -1) {
                break;
            }
            // Split the string at the first occurrence of the regex
            split.add(str.substring(i, regexFound));
            // Increase the current letter we are at at to after where the previous regex occurrence was
            i = regexFound + regex.getPattern().length();
        }

        // If we have not gotten to the end of the string, add it as well
        if (i < str.length()) {
            split.add(str.substring(i));
        } else {
            // Remove strings of size 0 at the to match the output of str.split
            for (int j = split.size() - 1; j >= 0; j--) {
                if (split.get(j).length() == 0) {
                    split.remove(j);
                } else {
                    break;
                }
            }
        }

        return split.toArray(String[]::new);
    }

    /**
     * Should do the same thing as {@link String#split(String)}. Uses {@link WriteSplit#defaultBackend} as the backend
     * @param str The string to be split
     * @param regex The pattern to match (does not work with regex at the moment, needs to be a String of any length)
     * @return An String array of the substrings split at every occurrence of the regex
     */
    public String[] split(String str, String regex) {
        return split(str, regex, defaultBackend);
    }

    /**
     * Splits a string using a custom backend
     * @param str The string to be split
     * @param regex The pattern to match (does not work with regex at the moment, needs to be a String of any length)
     * @param backend The function to use to split it
     * @return An String array of the substrings split at every occurrence of the regex
     */
    public String[] split(String str, String regex, BiFunction<String, String, String[]> backend) {
        return backend.apply(str, regex);
    }

    @Test
    public void testSplit() {
        // TODO: add manual test cases

        for (int i = 0; i < 1000; i++) {
            String randomString = generateRandomString(100);

            String regex1 = generateRandomString(1);
            assertArrayEquals(randomString.split(regex1), split(randomString, regex1));


            String regex2 = generateRandomString(2);
            if (!Arrays.equals(randomString.split(regex2), split(randomString, regex2))) {
                System.out.println(randomString);
                System.out.println(regex2);

                System.out.println(Arrays.toString(randomString.split(regex2)));
                System.out.println(Arrays.toString(split(randomString, regex2)));
            }

            assertArrayEquals(randomString.split(regex2), split(randomString, regex2));
        }

        // Larger test cases
        for (int i = 0; i < 100000; i += 10) {
            String randomString = generateRandomString(i);

            String regex1 = generateRandomString(i / 10 + 1);
            assertArrayEquals(randomString.split(regex1), split(randomString, regex1));
        }
    }

    /**
     * Generates a random string from the alphabet provided.
     * @param length The length of the random string to be generated
     * @param alphabet The alphabet to use
     * @return the array of strings computed by splitting this string around matches of the given regular expression
     */
    private String generateRandomString(int length, String alphabet) {
        Random random = new Random();

        StringBuilder string = new StringBuilder();

        for (int i = 0; i < length; i++) {
            string.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return string.toString();
    }

    /**
     * A function used for testing, generates a random string with the lowercase letters of the alphabet
     * @param length The length of the string
     * @return The random string
     */
    private String generateRandomString(int length) {
        return generateRandomString(length, "qwertyuiopasdfghjklzxcvbnm");
    }

    @Test
    public void testSearch() {
        String pattern = "cat";
        KMP kmp = new KMP(pattern);
        BoyerMoore boyer = new BoyerMoore(pattern);

        String[] testStrings = {"The short brown cat jumps over the fence",
                "The two cats like to be cats because they are cats",
                "A cat is a cat I guess"};
        for (String i : testStrings) {
            assertEquals(i.indexOf(pattern), kmp.search(i));
            assertEquals(i.indexOf(pattern), boyer.search(i));
            assertEquals(i.indexOf(pattern), bruteForceSubstringSearch(i, pattern, 0));
        }

        for (int i = 0; i < 100000; i += 10) {
            String randomString = generateRandomString(i);
            String regex = generateRandomString(i / 10 + 1);

            kmp = new KMP(regex);
            boyer = new BoyerMoore(regex);

            assertEquals(randomString.indexOf(pattern), kmp.search(randomString));
            assertEquals(randomString.indexOf(pattern), boyer.search(randomString));
            assertEquals(randomString.indexOf(pattern), bruteForceSubstringSearch(randomString, pattern, 0));
        }
    }

    @Test
    public void testSpeed() {
        final int tests = 1000;
        final int functions = 3;

        long[][] timesSplit = new long[tests][functions];
        long[][] timesSearch = new long[tests][functions];
        for (int i = 0; i < tests; i++) {
            String string = generateRandomString(100000);
            String regex = generateRandomString(1000);

            long startTime;
            long endTime;

            startTime = System.nanoTime();
            KMP test = new KMP(regex);
            knuthMorisPrattSubstringSearchSplit(string, test);
            endTime = System.nanoTime();
            timesSplit[i][0] = endTime - startTime;

            startTime = System.nanoTime();
            BoyerMoore boyerTest = new BoyerMoore(regex);
            boyerMooreSubstringSearchSplit(string, boyerTest);
            endTime = System.nanoTime();
            timesSplit[i][1] = endTime - startTime;

            startTime = System.nanoTime();
            bruteForceSubstringSearchSplit(string, regex);
            endTime = System.nanoTime();
            timesSplit[i][2] = endTime - startTime;

            startTime = System.nanoTime();
            string.split(regex);
            endTime = System.nanoTime();
            timesSplit[i][3] = endTime - startTime;
        }

        double[] average = new double[functions];
        for (int i = 0; i < functions; i++) {
            long sum = 0;
            for (int j = 0; j < tests; j++) {
                sum += timesSplit[j][i];
            }
            average[i] = (double) sum / tests;
        }
        System.out.println(Arrays.toString(average));

        // Tests that our algorithm is faster than String.split
//        assertTrue(average[functions - 1] - average[0] > 0);

        average = new double[functions];
        for (int i = 0; i < functions; i++) {
            long sum = 0;
            for (int j = 0; j < tests; j++) {
                sum += timesSearch[j][i];
            }
            average[i] = (double) sum / tests;
        }
        System.out.println(Arrays.toString(average));
    }
}
