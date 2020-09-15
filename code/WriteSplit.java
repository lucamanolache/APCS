import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class WriteSplit {

    private final BiFunction<String, String, String[]> defaultBackend = this::bruteForce;;

    public int bruteForceSubstringSearch(String str, String regex, int start) {
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

    public String[] bruteForce(String str, String regex) {
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
        if (i != str.length()) {
            split.add(str.substring(i));
        }

        return split.toArray(String[]::new);
    }

    public String[] split(String str, String regex) {
        return split(str, regex, defaultBackend);
    }

    public String[] split(String str, String regex, BiFunction<String, String, String[]> backend) {
        return backend.apply(str, regex);
    }

    @Test
    public void testSplit() {
        // TODO: add manual test cases

        for (int i = 0; i < 100; i++) {
            String randomString = generateRandomString(100);
            String regex1 = generateRandomString(1);

            if (!Arrays.equals(randomString.split(regex1), split(randomString, regex1))) {
                System.out.println(Arrays.toString(randomString.split(regex1)));
                System.out.println(Arrays.toString(split(randomString, regex1)));
            }

            assertArrayEquals(randomString.split(regex1), split(randomString, regex1));

            String regex2 = generateRandomString(2);

            if (!Arrays.equals(randomString.split(regex2), split(randomString, regex2))) {
                System.out.println(Arrays.toString(randomString.split(regex2)));
                System.out.println(Arrays.toString(split(randomString, regex2)));
            }
            assertArrayEquals(randomString.split(regex2), split(randomString, regex2));
        }
    }

    private String generateRandomString(int length, String alphabet) {
        Random random = new Random();

        StringBuilder string = new StringBuilder();

        for (int i = 0; i < length; i++) {
            string.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return string.toString();
    }

    private String generateRandomString(int length) {
        return generateRandomString(length, "qwertyuiopasdfghjklzxcvbnm");
    }
}
