package polynomial;

import java.util.Arrays;
import java.util.Vector;

public class Polynomial extends Function {

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
//        poly = new Polynomial(new double[]{1, 2, -3, 5});
//        long time;
//        long endTime;
//        time = System.nanoTime();
//        System.out.println(poly.getSolution(-10, 10, 0.00000001, 0.00000000001));
//        endTime = System.nanoTime();
//        System.out.println(endTime - time);
//        time = System.nanoTime();
//        System.out.println(-0.2990277505003914228721705);
//        endTime = System.nanoTime();
//        System.out.println(endTime - time);
//        System.out.println(poly.evaluate(0)); // Should be 1
//        System.out.println(poly.getSolutionBSearch(-10, 10, 100000));
//
//        System.out.println("=======");
//        poly = new Polynomial(new double[]{1, 2, -4});
//        System.out.println(poly.calculate(1)); // Should be 1
//        System.out.println(poly.getSolutionBSearch(0, 10, 100000));
//        System.out.println(poly.getSolution(0, 10, 0.00000001, 0.00000000001));
//        System.out.println(poly.getSolutions(-10, 10, 0.0001));
//        System.out.println(poly.localExtrema(-10, 10, 100000));
//
//        System.out.println("=======");
//        Sin sin = new Sin(1, 1, Math.PI / 2, 0);
//        System.out.println(sin.calculate(1.57079));
//        System.out.println(sin.getSolution(-1, 4, 0.0000001, 0.00000001));

        poly = new Polynomial(new double[]{1, 4, -3, 2, -4, 5, 8});
        Vector<Double> v = new Vector<>();
        poly.getSolutions(v, -1000, 1000, 100000);
        System.out.println(Arrays.toString(v.toArray()));
    }
}
