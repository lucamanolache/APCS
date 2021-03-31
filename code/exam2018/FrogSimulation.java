package exam2018;

public class FrogSimulation {

    private int goalDistance;
    private int maxHops;

    public FrogSimulation(int goalDistance, int maxHops) {
        this.goalDistance = goalDistance;
        this.maxHops = maxHops;
    }

    private int hopDistance() {
        return -1; // Implemented by the APCS Test.
    }

    public boolean simulate() {
        int distance = 0;
        int hops = 0;
        while (distance < goalDistance && distance >= 0 && hops < maxHops) {
            distance += hopDistance();
            hops++;
        }
        return distance >= goalDistance;
    }

    public double runSimulations(int num) {
        int works = 0;
        for (int i = 0; i < num; i++) {
            works += simulate() ? 1 : 0; // I think this might be less efficient than a single if statement
        }
        return (double) works / (double) num; // I can't remember if java requires both to be doubles or does auto casting
    }
}
