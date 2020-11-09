package polynomial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolynomialTest {

    @Test
    public void testPolynomial() {
        final double delta = 0.0001; // how tight you want the tests to be
        Polynomial poly;

        // because of how doubles work, we only need to check if they are close to the expect answer
        // answers to all of the polynomials were calculated using wolframalpha.com
        poly = new Polynomial(new double[]{1, 5, 3});
        assertEquals(-1.4343, poly.getSolution(-2, -1, 0.00001, 0.00001), delta);
        assertEquals(-0.23241, poly.getSolution(-0.5, 0, 0.00001, 0.00001), delta);
        assertEquals("1.000000 + 5.000000x5.000000x^1 + 3.000000x^2", poly.toString()); // TODO: truncate the result
    }
}
