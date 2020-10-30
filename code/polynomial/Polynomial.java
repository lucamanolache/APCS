package polynomial;

public class Polynomial extends Function {

    private final double[] coefficients;
    private Polynomial derivative;

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public double getSolutions(double a, double b, double t, double accuracy) {
        double fa = calculate(a);
        double fb = calculate(b);

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
            if (calculate(a) != calculate(c) && calculate(b) != calculate(c)) {
                double sa = (a * calculate(b) * calculate(c)) /
                            ((calculate(a) - calculate(b)) * (calculate(a) - calculate(c)));
                double sb = (b * calculate(a) * calculate(c)) /
                            ((calculate(b) - calculate(a)) * (calculate(b) - calculate(c)));
                double sc = (c * calculate(a) * calculate(b)) /
                            ((calculate(c) - calculate(a)) * (calculate(c) - calculate(c)));
                s = sa + sb + sc;
            } else {
                s = b - calculate(b) * (b - a) / (calculate(b) - calculate(a));
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
        } if (Math.abs(calculate(s)) < accuracy) {
            return s;
        } else {
            return Double.NaN;
        }
    }

    public double getSolution(double lowerBound, double higherBound, int steps) {
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

    public double localExtrema(double lowerBound, double higherBound, int steps) {
        if (derivative == null) {
            derivative = getDerivative();
        }
        return derivative.getSolution(lowerBound, higherBound, steps);
    }

    public Polynomial getDerivative() {
        return this.derivative == null ? derivative() : this.derivative;
    }

    private Polynomial derivative() {
        double[] newCoefficients = new double[coefficients.length - 1];
        for (int i = 1; i < newCoefficients.length; i++) {
            newCoefficients[i] = coefficients[i] * i;
        }
        return new Polynomial(newCoefficients);
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
        System.out.println(poly.getSolutions(-10, 10, 0.00000001, 0.00000000001));
        endTime = System.nanoTime();
        System.out.println(endTime - time);
        time = System.nanoTime();
        System.out.println(-0.2990277505003914228721705);
        endTime = System.nanoTime();
        System.out.println(endTime - time);
//        System.out.println(poly.evaluate(0)); // Should be 1
        System.out.println(poly.getSolution(-10, 10, 100000));

        System.out.println("=======");
        poly = new Polynomial(new double[]{1, 2, -4});
        System.out.println(poly.calculate(1)); // Should be 1
        System.out.println(poly.getSolution(0, 10, 100000));
//        System.out.println(poly.getSolutions(-10, 10, 0.0001));
//        System.out.println(poly.localExtrema(-10, 10, 100000));
    }
}
