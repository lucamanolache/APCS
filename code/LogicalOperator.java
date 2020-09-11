import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalOperator {

    // A regular expression to find if a string is a legal logical expression
    private static final Pattern legalSentenceRegex = Pattern.compile("([~]*[a-z]{1}){1}((\\s)*(=>|<=|<=*>|[|,&]){1}(\\s)*([~]*[a-z]{1}){1})*");
    private static final Pattern legalSimpleRegex = Pattern.compile("\\s*[a-z]{1}\\s*");
    private static final Pattern negationRegex = Pattern.compile("\\s*[~]+[a-z]{1}\\s*");
    private static final Pattern splitImplicationsRegex = Pattern.compile("[=+>|<=+|<=+>]");
    private static final Pattern splitConjunctionsRegex = Pattern.compile("[|,&]]");
    private static final Pattern removeImplicationsRegex = Pattern.compile("[=*>|<=*|<=*>]");

    public static void main(String[] args) {
        System.out.println(removeImplicationsRegex.matcher("asd =====> adlk ==> a").start());
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

        // Find out if the first split is an conjunctions/disjunction or a implication
        int fistConjunction = sentence.indexOf(splitConjunctionsRegex);
        int firstImplication = sentence.indexOf(splitImplicationsRegex);

        // split complex will always be defined to before its first use
        String[] splitComplex = null;
        if (fistConjunction > 0) {
            // We know we have at least one conjunction/disjunction
            if (firstImplication > 0 && firstImplication > fistConjunction) {
                // The first place to split is a conjunction/disjunction.

                splitComplex = sentence.split(splitConjunctionsRegex, 2);
                // Conjunctions and disjunctions are 1 char long so there is no mess to remove
            } else if (firstImplication > 0) {
                splitComplex = sentence.split(splitImplicationsRegex, 2);

                // Because implications and biconditionals are longer than 1 char, we have to clean up behind them
                splitComplex[1] = splitComplex[1].replaceFirst(removeImplicationsRegex, "");
            }
        } else if (firstImplication > 0) {
            // We know we have at least one implication and that we dont have any conjunctions

            splitComplex = sentence.split(splitImplicationsRegex, 2);

            // Because implications and biconditionals are longer than 1 char, we have to clean up behind them
            splitComplex[1] = splitComplex[1].replaceFirst(removeImplicationsRegex, "");
        } else {
            // We no longer have any implications/conditionals or conjunctions/disjunctions and we know it is not a
            // simple sentence or negation
            return false;
        }

        // Check if both sides of the biconditional, ..., are true
        assert splitComplex != null;
        return legal(splitComplex[0]) || legal(splitComplex[1]);
    }

    private static boolean complex() {

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
        assertTrue(negation("~~a   "));
        assertTrue(negation("~z"));
        assertTrue(negation("  ~m"));
        assertTrue(negation("~~m  "));
        assertFalse(negation("b"));
        assertFalse(negation("~A"));
        assertFalse(negation("~A => ~~b"));
        assertFalse(negation("~~"));
        assertFalse(negation(""));
        assertFalse(negation(" "));
    }

    @Test
    public void testSimple() {
        assertTrue(legalSimple("a"));
        assertTrue(legalSimple("z "));
        assertTrue(legalSimple("  z"));
        assertTrue(legalSimple(" q  "));
        assertFalse(legalSimple("A"));
        assertFalse(legalSimple("~A"));
        assertFalse(legalSimple(" ab"));
        assertFalse(legalSimple("a z"));
        assertFalse(legalSimple(""));
        assertFalse(legalSimple(" "));
    }

    @Test
    public void testLegal() {
        assertTrue(legal("  a "));
        assertTrue(legal("~~z  "));
        assertTrue(legal("~a   ==> ~~z"));
        assertTrue(legal("a  <====> b"));
        assertTrue(legal("~~a <=   b"));
        assertFalse(legal("  a >   b  "));
        assertFalse(legal("a < ~~b"));
        assertFalse(legal("<==="));
        assertFalse(legal("a ==>"));
    }
}
