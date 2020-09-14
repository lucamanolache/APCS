import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

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

    private String generateRandomString(int length) {
        return null;
    }
}
