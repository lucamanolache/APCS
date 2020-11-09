package polynomial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Polynomial {

    private final double[] coefficients;

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public double calculate(double x) {
        double y = 0;
        for (int i = 0; i < coefficients.length; i++) {
            y += coefficients[i] * Math.pow(x, i);
        }
        return y;
    }

    /**
     * My implementation of Brent's method of finding roots. A description of the algorithm can be found here:
     * https://en.wikipedia.org/wiki/Brent%27s_method. The method assumes you have found the solution to a reasonable
     * degree. For example if I have 1 + 5x + 3x^2 as my function, I should know that the answer should be somewhere
     * in the range of -2 to -1. In this example, with a being -2, b being -1, t and accuracy being 0.0001 the method
     * would return the solution of -1.4343
     * @param a lower limit
     * @param b upper limit
     * @param t Tolerance
     * @param accuracy How accurate should it be
     * @return the first solution found
     */
    public double getSolution(double a, double b, double t, double accuracy) {
        double fa = calculate(a);
        double fb = calculate(b);
        double fc;
        double fs;

        if (fa * fb >= 0) {
            return Double.NaN;
        }
        if (Math.abs(fa) < Math.abs(fb)) {
            // Swap a and b
            double swap = a;
            a = b;
            b = swap;
        }

        double c = a;
        double s = b;
        double d = 0;
        boolean flag = true;
        while (!(calculate(a) == 0 || calculate(b) == 0 || Math.abs(b - a) <= accuracy)) {
            fa = calculate(a);
            fb = calculate(b);
            fc = calculate(c);
            if (fa != fc && fb != fc) {
                double sa = (a * fb * fc) /
                        ((fa - fb) * (fa - fc));
                double sb = (b * fa * fc) /
                        ((fb - fa) * (fb - fc));
                double sc = (c * fa * fb) /
                        ((fc - fa) * (fc - fc));
                s = sa + sb + sc;
            } else {
                s = b - fb * (b - a) / (fb - fa);
            }
            if (!(s > (3 * a + b) && s < (b)) ||
                    (flag && Math.abs(s - b) >= Math.abs(b - c) / 2) ||
                    (!flag && Math.abs(s - b) >= Math.abs(c - d) / 2) ||
                    (flag && Math.abs(b - c) < t) ||
                    (!flag && Math.abs(c - d) < t)) {
                s = (a + b) / 2;
                flag = true;
            } else {
                flag = false;
            }
            d = c;
            c = b;
            if (calculate(a) * calculate(s) < 0) {
                b = s;
            } else {
                a = s;
            }
            if (Math.abs(calculate(a)) < Math.abs(calculate(b))) {
                double swap = a;
                a = b;
                b = swap;
            }
        }
        if (Math.abs(calculate(b)) < accuracy) {
            return b;
        } else {
            return s;
        }
    }

    /**
     * My implementation of a getSolution method. Does not work in all cases as it might get "stuck" in an area. This
     * solutions could be fixed by instead of taking the slope, seeing if the middle and the end point are on opposite
     * sides of the x axis. This could then also be used in order to find multiple solutions.
     * @deprecated does not work
     */
    public double getSolutionBSearch(double lowerBound, double higherBound, int steps) {
        double guess = lowerBound;
        for (int i = 0; i < steps; i++) {
            guess = (lowerBound + higherBound) / 2;
            if (calculate(guess) == 0) {
                return guess;
            }
            if (slope(guess, 0.00001) <= 0) {
                if (calculate(guess) <= 0) {
                    higherBound = guess;
                } else {
                    lowerBound = guess;
                }
            } else {
                if (calculate(guess) <= 0) {
                    lowerBound = guess;
                } else {
                    higherBound = guess;
                }
            }
        }
        return guess;
    }

    public double slope(double x, double percision) {
        return (calculate(x) - calculate(x - percision)) / (percision);
    }

    public int getOrder() {
        return coefficients.length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (i != 0) {
                builder.append(" + ");
            }
            double coefficient = coefficients[i];
            if (coefficient == 1) {
                builder.append(String.format("x^%d", i));
            } else if (coefficient != 0){
                builder.append(String.format("%fx^%d", coefficient, i));
            }
        }

        return builder.toString();
    }

    /**
     * Adds two polynomials and returns a third. The polynomials given will not be changed
     * @return sum of the two polynomials
     */
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        double[] newPoly = new double[Math.max(p1.getOrder(), p2.getOrder())];
        for (int i = 0; i < Math.min(p1.getOrder(), p2.getOrder()); i++) {
            newPoly[i] = p1.coefficients[i] + p2.coefficients[i];
        }
        for (int i = Math.min(p1.getOrder(), p2.getOrder()); i < Math.max(p1.getOrder(), p2.getOrder()); i++) {
            newPoly[i] = p1.getOrder() < p2.getOrder() ? p2.coefficients[i] : p1.coefficients[i];
        }
        return new Polynomial(newPoly);
    }

    @Test
    public void testPolynomial() {
        final double delta = 0.0001; // how tight you want the tests to be
        Polynomial poly;

        // because of how doubles work, we only need to check if they are close to the expect answer
        // answers to all of the polynomials were calculated using wolframalpha.com
        poly = new Polynomial(new double[]{1, 5, 3});
        assertEquals(-1.4343, poly.getSolution(-2, -1, 10000, 10000), delta);
        assertEquals(-0.23241, poly.getSolution(-0.5, 0, 10000, 10000), delta);

        assertEquals(-1.4343, poly.getSolution(-2, -1, 10000, 10000), delta);
        assertEquals(-1.4343, poly.getSolution(-2, -1, 10000, 10000), delta);
    }
}
