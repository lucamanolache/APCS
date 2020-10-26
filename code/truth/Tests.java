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

    @Test
    public void testLogicalSentence() {
        LogicalSentence ls;
        TruthAssignment assignment = new TruthAssignment(new String[]{"a", "b", "ab"}, new boolean[]{true, false, false});

        ls = new LogicalSentence("a");
        assertEquals("a", ls.toString());
        assertTrue(ls.getValue(assignment));
        ls = new LogicalSentence("ab");
        assertEquals("ab", ls.toString());
        assertFalse(ls.getValue(assignment));
        ls = new LogicalSentence(new LogicalSentence("a"));
        assertEquals("~a", ls.toString());
        assertFalse(ls.getValue(assignment));
        ls = new LogicalSentence(new LogicalSentence(new LogicalSentence("b")));
        assertEquals("~~b", ls.toString());
        assertFalse(ls.getValue(assignment));
        ls = new LogicalSentence("&", new LogicalSentence("a"), new LogicalSentence("b"));
        assertEquals("(a & b)", ls.toString());
        assertFalse(ls.getValue(assignment));
        ls = new LogicalSentence("|", new LogicalSentence("a"), new LogicalSentence("b"));
        assertEquals("(a | b)", ls.toString());
        assertTrue(ls.getValue(assignment));
        ls = new LogicalSentence("=>", new LogicalSentence("a"), new LogicalSentence("b"));
        assertEquals("(a => b)", ls.toString());
        assertFalse(ls.getValue(assignment));
        ls = new LogicalSentence("<=>", new LogicalSentence("a"), new LogicalSentence("b"));
        assertEquals("(a <=> b)", ls.toString());
        assertFalse(ls.getValue(assignment));
        ls = new LogicalSentence("<=>", new LogicalSentence(new LogicalSentence("a")), new LogicalSentence("b"));
        assertEquals("(~a <=> b)", ls.toString());
        assertTrue(ls.getValue(assignment));
        assertThrows(IllegalArgumentException.class,
                     () -> new LogicalSentence("<=?>", new LogicalSentence("b"), new LogicalSentence("b")));
    }
}
