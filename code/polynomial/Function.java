package polynomial;

public abstract class Function {

    public abstract double calculate(double x);
    public abstract String toString();

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

    public double slope(double x, double percision) {
        return (calculate(x) - calculate(x - percision)) / (percision);
    }
}
