import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class WriteSplit {

    private static final BiFunction<String, String, String[]> defaultBackend = WriteSplit.bruteForceSubstring;

    private static final BiFunction<String, String, String[]> bruteForceSubstring = WriteSplit::bruteForceSubstringSearchSplit;

    /**
     * To keep to the spirit of coding the split method from scratch, I am also coding the substring search from scratch.
     * Should do the same thing as {@link String#indexOf(String, int)}.
     */
    private static int bruteForceSubstringSearch(String str, String regex, int start) {
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
    private static String[] bruteForceSubstringSearchSplit(String str, String regex) {
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

    /**
     * Same algorithm as {@link #bruteForceSubstringSearchSplit(String, String)} except uses
     */
    private static String[] knuthMorisPrattSubstringSearchSplit(String str, String regex) {
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
            assertArrayEquals(randomString.split(regex2), split(randomString, regex2));
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
}
