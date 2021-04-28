package exam2019;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    // TODO: all of these should be the other way around assertEquals(expected, actual) however ill probably do that later.

    @Test
    public void testGetDelimitersList() {
        var delimiter = new Delimiters("(", ")");

        assertEquals(delimiter.getDelimitersList(new String[]{"(", ")"}), new ArrayList<>(List.of("(", ")")));
        assertEquals(delimiter.getDelimitersList(new String[]{"(", "abc", ")"}), new ArrayList<>(List.of("(", ")")));
        assertEquals(delimiter.getDelimitersList(new String[]{"(", ")", ")"}), new ArrayList<>(List.of("(", ")", ")")));
        assertEquals(delimiter.getDelimitersList(new String[]{")"}), new ArrayList<>(List.of(")")));
        assertEquals(delimiter.getDelimitersList(new String[]{}), new ArrayList<>(List.of()));
    }

    @Test
    public void testIsBalanced() {
        var delimiter = new Delimiters("(", ")");

        assertEquals(delimiter.isBalanced(new ArrayList<>(List.of())), true);
        assertEquals(delimiter.isBalanced(new ArrayList<>(List.of("(", ")"))), true);
        assertEquals(delimiter.isBalanced(new ArrayList<>(List.of("(","(",")","(",")",")"))), true);
        assertEquals(delimiter.isBalanced(new ArrayList<>(List.of(")","(",")","(",")",")", "("))), false);
        assertEquals(delimiter.isBalanced(new ArrayList<>(List.of(")", "("))), false);
        assertEquals(delimiter.isBalanced(new ArrayList<>(List.of(")"))), false);
        assertEquals(delimiter.isBalanced(new ArrayList<>(List.of("(", "(", ")"))), false);
    }
}
