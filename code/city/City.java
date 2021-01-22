package city;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class City {

    private static ArrayList<ArrayList<Road>> adj;
    private static int cities;

    static {
        cities = 0;
        adj = new ArrayList<>();
    }

    private String name;
    private int number;


    static class Road implements Comparable<Road> {
        private double distance;
        private City destination;

        public Road(City destination, double distance) {
            this.distance = distance;
            this.destination = destination;
        }

        @Override
        public int compareTo(Road o) {
            return Double.compare(this.distance, o.distance);
        }
    }

    public City(String name, ArrayList<Road> roads) {
        number = cities;
        adj.add(number, roads);
        this.name = name;

        cities++; // needed for Dijkstra's
    }

    public City(String name, Road... roads) {
        this(name, new ArrayList<>(Arrays.asList(roads)));
    }

    public void addRoads(ArrayList<Road> roads) {
        adj.get(number).addAll(roads);
    }

    public void addRoads(Road... roads) {
        addRoads(new ArrayList<>(Arrays.asList(roads)));
    }

    public double distanceTo(City city) {
        PriorityQueue<Road> distances = new PriorityQueue<>(); // O(log n) insertion and removing iirc
        double[] distancesTo = new double[cities];
        boolean[] visited = new boolean[cities];

        Arrays.fill(distancesTo, Double.POSITIVE_INFINITY);
        Arrays.fill(visited, false);

        distancesTo[number] = 0;
        distances.add(new Road(this, 0));

        for (int i = 0; i < cities - 1; i++) {
            if (distances.isEmpty()) break;
            Road r = distances.remove();
            if (visited[r.destination.number]) continue;

            visited[r.destination.number] = true;

            for (Road d : adj.get(r.destination.number)) {
                if (visited[d.destination.number]) continue;

                double distanceTo = distancesTo[r.destination.number] + d.distance;
                if (distanceTo < distancesTo[d.destination.number]) {
                    distancesTo[d.destination.number] = distanceTo;
                    distances.add(new Road(d.destination, distanceTo));
                }
            }
        }

        return distancesTo[city.number];
    }

    public String getName() {
        return name;
    }
}
