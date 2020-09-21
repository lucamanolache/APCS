import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class Filter {

    /**
     * The String returned from the method contains all (and only) the characters found in the first String for which
     * {@link String#compareTo(String)} returns a negative value.
     * @param str The string to be compared
     * @param check The string its being compared against
     * @return the characters found in the first String for which the compareTo method returns a negative value
     */
    public String filter(String str, String check) {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < str.length(); i += check.length()) {
            String substr = str.substring(i, i + check.length());
            if (substr.compareTo(check) < 0) {
                out.append(substr);
            }
        }

        return out.toString();
    }

    @Test
    public void testFilter() {
        assertEquals("Hell ld!", filter("Hello world!", "o"));
    }
}
