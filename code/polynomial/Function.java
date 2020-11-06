package polynomial;

import java.util.Vector;

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

    public void getSolutions(Vector<Double> solutions, double lowerBound, double higherBound, int steps) {
        if (steps == 0 || this.calculate(lowerBound) == 0) {
            solutions.add(lowerBound);
        }

        double guess = (lowerBound + higherBound) / 2;
//        if (java.lang.Thread.activeCount() < 10) {
            if (lowerBound * guess <= 0 && guess * higherBound <= 0) {
                // We know that the lower bound and the guess are on opposite sides of the x axis and that guess and the
                // higher bound are on opposite sides of the x axis, therefore there must be a on both sides of guess
                // if the function is continuous.
                Thread lowerThread = new Thread(() -> getSolutions(solutions, lowerBound, guess, steps - 1));
                lowerThread.start();
                Thread higherThread = new Thread(() -> getSolutions(solutions, guess, higherBound, steps - 1));
                higherThread.start();

                // Do not return until both threads are finished
                while (lowerThread.isAlive() || higherThread.isAlive()) {
                }
                return;
            }
//        }

        // There is no longer any need to use threads
        if (lowerBound * guess <= 0) {
            // We know that the lower bound and the guess are on opposite sides of the x axis, therefore there must be a
            // solution in between them if the function is continuous.
            getSolutions(solutions, lowerBound, guess, steps - 1);
        } else if (guess * higherBound <= 0) {
            // We know that the higher bound and the guess are on opposite sides of the x axis, therefore there must be
            // a solution in between them if the function is continuous.
            getSolutions(solutions, guess, higherBound, steps - 1);
        }
    }
}
