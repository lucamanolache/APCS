package polynomial;

public class Polynomial extends Function {

    private final double[] coefficients;

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * My implementation of Brent's method of finding roots. A description of the algorithm can be found here:
     * https://en.wikipedia.org/wiki/Brent%27s_method.
     * @param a Can not have the same sign as b
     * @param b Can not have the same sign as a
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
     * My implementation of a getSolution method. Does not work in all cases as it might get "stuck" in an area.
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

    public double calculate(double x) {
        double y = 0;
        for (int i = 0; i < coefficients.length; i++) {
            y += coefficients[i] * Math.pow(x, i);
        }
        return y;
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

    public static void main(String[] args) {
        Polynomial poly;

        // 1 + 2x
//        poly = new Polynomial(new double[]{1, 2});
//        System.out.println(poly.evaluate(0)); // Should be 1
//        System.out.println(poly.slope(1, 0.00001));
//        System.out.println(poly.getSolution(-10, 10, 1000));

        // 1 + 2x - 3x^2 + 5x^3
        poly = new Polynomial(new double[]{1, 2, -3, 5});
        long time;
        long endTime;
        time = System.nanoTime();
        System.out.println(poly.getSolution(-10, 10, 0.00000001, 0.00000000001));
        endTime = System.nanoTime();
        System.out.println(endTime - time);
        time = System.nanoTime();
        System.out.println(-0.2990277505003914228721705);
        endTime = System.nanoTime();
        System.out.println(endTime - time);
//        System.out.println(poly.evaluate(0)); // Should be 1
        System.out.println(poly.getSolutionBSearch(-10, 10, 100000));

        System.out.println("=======");
        poly = new Polynomial(new double[]{1, 2, -4});
        System.out.println(poly.calculate(1)); // Should be 1
        System.out.println(poly.getSolutionBSearch(0, 10, 100000));
        System.out.println(poly.getSolution(0, 10, 0.00000001, 0.00000000001));
//        System.out.println(poly.getSolutions(-10, 10, 0.0001));
//        System.out.println(poly.localExtrema(-10, 10, 100000));
    }
}
