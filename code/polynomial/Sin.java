package polynomial;

public class Sin extends Function {

    private double a, b, c, d;

    public Sin(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double calculate(double x) {
        return a * Math.sin(b * x + c) + d;
    }

    @Override
    public String toString() {
        return String.format("%f sin( %f * x + %f) + %f", a, b, c, d);
    }
}
