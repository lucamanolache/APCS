import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class WriteSplit {

    public String[] bruteForce(String str) {
        return null;
    }

    public String[] split(String str) {
        return null;
    }

    public String[] split(String str, Function<String, String[]> backend) {
        return backend.apply(str);
    }

    @Test
    public void testSplit() {
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
