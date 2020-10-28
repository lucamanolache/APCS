package polynomial;

public class Polynomial {

    private final double[] coefficients;
    private Polynomial derivative;

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public double getSolution(double lowerBound, double higherBound, int steps) {
        double guess = lowerBound;
        for (int i = 0; i < steps; i++) {
            guess = (lowerBound + higherBound) / 2;
            if (evaluate(guess) == 0) {
                return guess;
            }
            if (slope(guess, 0.00001) <= 0) {
                if (evaluate(guess) <= 0) {
                    higherBound = guess;
                } else {
                    lowerBound = guess;
                }
            } else {
                if (evaluate(guess) <= 0) {
                    lowerBound = guess;
                } else {
                    higherBound = guess;
                }
            }
        }
        return guess;
    }

    public double slope(double x, double percision) {
        return (evaluate(x) - evaluate(x - percision)) / (percision);
    }

    public double evaluate(double x) {
        double y = 0;
        for (int i = 0; i < coefficients.length; i++) {
            y += coefficients[i] * Math.pow(x, i);
        }
        return y;
    }

    public double localMinima(double lowerBound, double higherBound, int steps) {
        return derivative.getSolution(lowerBound, higherBound, steps);
    }

    public Polynomial getDerivative() {
        return this.derivative == null ? derivative() : this.derivative;
    }

    private Polynomial derivative() {
        double[] newCoefficients = new double[coefficients.length - 1];
        for (int i = 1; i < coefficients.length; i++) {
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
        System.out.println(poly.evaluate(0)); // Should be 1
        System.out.println(poly.getSolution(-10, 10, 100000));
    }
}
