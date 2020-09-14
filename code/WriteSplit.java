import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class WriteSplit {

    public String[] bruteForce(String str, String regex) {
        return null;
    }

    public String[] split(String str, String regex) {
        return null;
    }

    public String[] split(String str, String regex, BiFunction<String, String, String[]> backend) {
        return backend.apply(str, regex);
    }

    @Test
    public void testSplit() {
        // TODO: add manual test cases

        for (int i = 0; i < 25; i++) {
            String randomString = generateRandomString(100);
            String regex = generateRandomString(1);
            assertArrayEquals(randomString.split(regex), split(randomString, regex));
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
