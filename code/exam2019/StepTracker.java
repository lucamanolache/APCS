package exam2019;

public class StepTracker {

    private final int minimumSteps;

    private int days;
    private int activeDays;
    private double averageSteps;

    public StepTracker(int minimumSteps) {
        this.minimumSteps = minimumSteps;
        this.days = 0;
        this.activeDays = 0;
        this.averageSteps = 0.0;
    }

    public void addDailySteps(int steps) {
        days++;
        if (steps >= minimumSteps) {
            activeDays++;
        }
        averageSteps = (averageSteps * (days - 1) + steps) / days;
    }

    public int activeDays() {
        return activeDays;
    }

    public double averageSteps() {
        return averageSteps;
    }
}
