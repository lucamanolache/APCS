package truth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luca Manolache
 */
public class TruthAssignment {

    HashMap<String, Boolean> data;

    /**
     * The constructor takes an array of names and values to be stored conveniently for easy querying. The querying can
     * be done with {@link #getValue(String)}. The dataset is not immutable and values can be changed and new values can
     * be added using {@link #setValue(String, boolean)} and {@link #addValue(String, boolean)}. Will throw
     * {@link IllegalArgumentException} if the lengths of the arrays differ or if there are duplicate elements in the
     * name array.
     * @param names An array of unique Strings to name each value
     * @param values An array of booleans to act as values to each name
     */
    public TruthAssignment(String[] names, boolean[] values) {
        this.data = new HashMap<>();

        if (names.length != values.length) {
            throw new IllegalArgumentException("Arrays are of different sizes");
        }

        for (int i = 0; i < names.length; i++) {
            if (data.containsKey(names[i])) {
                throw new IllegalArgumentException("First argument contains duplicate keys");
            }
            data.put(names[i], values[i]);
        }
    }

    /**
     * Returns the value of name. If name does not exist, a {@link IllegalArgumentException} will be thrown.
     * @param name The name that should be searched for
     * @return The value given for name
     */
    public boolean getValue(String name) {
        if (!data.containsKey(name)) {
            throw new IllegalArgumentException("Name is not a key");
        }
        return data.get(name);
    }

    /**
     * Sets the value of the previously exiting name. If name does not exist, a {@link IllegalArgumentException} will be
     * thrown. If you get this error, consider using {@link #addValue(String, boolean)}.
     * @param name The name that will get a new name
     * @param value The new value that name should have
     */
    public void setValue(String name, boolean value) {
        if (!data.containsKey(name)) {
            throw new IllegalArgumentException("Name is not a key");
        }
        data.replace(name, value);
    }

    /**
     * Adds a new name and value to the class. If the name already exists, a {@link IllegalArgumentException} will be
     * thrown. If you get this error, consider using {@link #setValue(String, boolean)}.
     * @param name The new name to add to the array
     * @param value The value to give to name
     */
    public void addValue(String name, boolean value) {
        if (data.containsKey(name)) {
            throw new IllegalArgumentException("Name is a duplicate key");
        }
        data.put(name, value);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Test
    public void testTruthAssignment() {
        assertDoesNotThrow(() -> new TruthAssignment(new String[]{"A", "a", "c", "B"}, new boolean[]{true, false, false, true}));
        assertDoesNotThrow(() -> new TruthAssignment(new String[]{}, new boolean[]{}));
        assertDoesNotThrow(() -> new TruthAssignment(new String[]{"aa", "ba", "ac", "d", "AA"}, new boolean[]{false, false, false, false, false}));

        assertThrows(IllegalArgumentException.class, () -> new TruthAssignment(new String[]{"aa", "aa"}, new boolean[]{true, false}));
        assertThrows(IllegalArgumentException.class, () -> new TruthAssignment(new String[]{"a", "A", "a"}, new boolean[]{true, false, true}));

        assertThrows(IllegalArgumentException.class, () -> new TruthAssignment(new String[]{"a", "A"}, new boolean[]{true}));
        assertThrows(IllegalArgumentException.class, () -> new TruthAssignment(new String[]{}, new boolean[]{true}));
    }

    @Test
    public void testMethods() {
        TruthAssignment ta = new TruthAssignment(new String[]{"a", "b", "c"}, new boolean[]{true, false, true});

        assertTrue(ta.getValue("a"));
        assertFalse(ta.getValue("b"));
        assertThrows(IllegalArgumentException.class, () -> ta.getValue("d"));

        assertDoesNotThrow(() -> ta.setValue("a", false));
        assertFalse(ta.getValue("a"));
        assertThrows(IllegalArgumentException.class, () -> ta.setValue("d", true));

        assertDoesNotThrow();
    }
}
