import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class Filter {

    /**
     * The String returned from the method contains all (and only) the characters found in the first String for which
     * {@link String#compareTo(String)} returns a negative value. It has a time complexity of O(n) with n as the
     * length of str. This is because it only runs through the String a constant (1) amount of times. This method never
     * returns null and requires that the parameters which are passed are not null.
     * @param str The string to be compared
     * @param check The string its being compared against - should be 1 character
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
        assertEquals("abcde", filter("abcde", "f"));
        assertEquals("", filter("ddddddd", "b"));
        assertEquals("", filter("", "a"));
        assertEquals("abscadwq", filter("abscadwq", "z"));
        assertEquals("aaa", filter("adadaddv", "c"));
        assertEquals("ababab", filter("abcdeabcdealcbe", "c"));
        assertEquals("cl", filter("cool", "o"));
    }
}
