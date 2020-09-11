import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalOperator {

    // A regular expression to find if a string is a legal logical expression
    private static final String legalSentenceRegex = "([~]*[a-z]{1}){1}((\\s)*(=>|<=|<=*>|[|,&]){1}(\\s)*([~]*[a-z]{1}){1})*";
    private static final String legalSimpleRegex = "[a-z]{1}";
    private static final String negationRegex = "[~]+[a-z]{1}";
    private static final String splitRegex = "[=*>|<=*|<=*>|[|,&]]";

    public static void main(String[] args) {

    }

    /**
     * Returns true if the sentence is a legal complex sentence. It will return true if it is a simple
     * sentence. Some examples of legal complex sentences are ~~a or a => ~~b.
     * @param sentence The sentence to check
     * @return If the sentence is a legal logical sentence
     */
    public static boolean legal(String sentence) {
        // Check if it is a simple sentence or a negation. If so return true.
        if (negation(sentence) || legalSimple(sentence)) {
            return true;
        }

        // We know the sentence is not a negation, therefore it must have a biconditional, implication,
        // conjunction, or a disjunction
        String[] splitComplex = sentence.split(splitRegex, 1); // We split the string into 2 parts

        // Check if both sides of the biconditional, ..., are true
        return legal(splitComplex[0]) || legal(splitComplex[1]);
    }

    /**
     * Finds if a string contains exactly one negation. A negation can be in the form of anything
     * similar to ~a or ~~~~z.
     * @param negation The string to check
     * @return If the string is a negation
     */
    private static boolean negation(String negation) {
        return negation.matches(negationRegex);
    }

    /**
     * Finds if a string is a legal simple sentence. This means it is just a single letter such as a or b.
     * @param simple The simple legal sentence to check
     * @return If it is a simple sentence
     */
    private static boolean legalSimple(String simple) {
        return simple.matches(legalSimpleRegex);
    }

    @Test
    public void testNegation() {
        assertTrue(negation("~~~~m"));
        assertTrue(negation("~~a"));
        assertTrue(negation("~z"));
        assertFalse(negation("b"));
        assertFalse(negation("~A"));
        assertFalse(negation("~A => ~~b"));
        assertFalse(negation("~~"));
    }

    @Test
    public void testSimple() {
        assertTrue(legalSimple("a"));
        assertTrue(legalSimple("z"));
        assertFalse(legalSimple("A"));
        assertFalse(legalSimple("~A"));
        assertFalse(legalSimple("ab"));
        assertFalse(legalSimple("a z"));
    }

    @Test
    public void testLegal() {
        assertTrue(legal("a"));
        assertTrue(legal("~~z"));
        assertTrue(legal("~a ==> ~~z"));
        assertTrue(legal("a <====> b"));
        assertTrue(legal("~~a <= b"));
        assertFalse(legal("a > b"));
        assertFalse(legal("a < ~~b"));
        assertFalse(legal("<==="));
        assertFalse(legal("a ==>"));
    }
}
