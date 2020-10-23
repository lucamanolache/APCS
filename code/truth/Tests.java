package truth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Tests {

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

        assertDoesNotThrow(() -> ta.addValue("f", true));
        assertTrue(ta.getValue("f"));
        assertThrows(IllegalArgumentException.class, () -> ta.addValue("a", true));
        assertFalse(ta.getValue("a"));
    }
}
