package city;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * A city class that can connect to other cities by 1 directional roads (might be changed to go in both directions).
 * This class is not safe more use with multiple threads as the functions are not synchronized. Might add that later.
 * To create one you do not need to give it any {@link Road} originally as they can be added later using
 * {@link #addRoads(ArrayList)} or {@link #addRoads(Road...)}. To create a {@link Road} you need to give it an already
 * defined city and a distance in the form of a double. To get the distance to another city use {@link #distanceTo(City)}.
 * This function uses the Dijkstra’s shortest path algorithm. This means that an infinite loop will be produced if
 * there are any negative loops. As such in order to be safe give it only positive distances. The constructor will
 * not throw an error if a negative number is given as a negative number does not mean that a negative loop exists.
 * @author luca
 */
public class City {

    private static ArrayList<ArrayList<Road>> adj;
    private static int cities;

    static {
        cities = 0;
        adj = new ArrayList<>();
    }

    private String name;
    private int number;


    /**
     * A class in order to link two cities with a distance.
     */
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

    /**
     * Create a city with a name and an {@link ArrayList<Road>} connected to it. This is harder to write manually
     * but easier to use in code.
     * @param name The name of the city.
     * @param roads An {@link ArrayList<Road>}
     * @see #City(String, Road...)
     */
    public City(String name, ArrayList<Road> roads) {
        number = cities;
        adj.add(number, roads);
        this.name = name;

        cities++; // needed for Dijkstra's
    }

    /**
     * Create a city with zero, one, or more {@link Road} connected to it.
     * @param name The name of the city.
     * @param roads The roads that connect to other cities. Can be zero or more comma separated roads.
     * @see #City(String, ArrayList)
     */
    public City(String name, Road... roads) {
        this(name, new ArrayList<>(Arrays.asList(roads)));
    }

    /**
     * Add {@link Road} to an already existing city.
     * @param roads the roads to be added
     * @see #addRoads(Road...)
     */
    public void addRoads(ArrayList<Road> roads) {
        adj.get(number).addAll(roads);
    }

    /**
     * Add {@link Road} to an already existing city.
     * @param roads the roads to be added
     * @see #addRoads(ArrayList)
     */
    public void addRoads(Road... roads) {
        addRoads(new ArrayList<>(Arrays.asList(roads)));
    }

    /**
     * This function uses Dijkstra’s shortest path algorithm. This algorithm is a greedy algorithm that always takes
     * shortest path it can currently take. This algorithm starts off with only one path it can take, which is to the
     * starting city, and then looks in a static adjacency matrix for all connections this city has and adds them to a
     * priority queue to find the shortest distance in O(log n). The shortest distances found are marked off in a
     * boolean array that tracks which cities have had the shortest path already found. This process repeats until all
     * of the cities have a shortest path found to them. This is not the most optimized as it can stop earlier and
     * creates many classes for the GC to deal with. It could probably be a lot more optimized in a different language
     * with manual memory allocation like c.
     * @param city A city to go to. Would be a String, however that would cause issues if two cities have the same name.
     * @return A double which represents the shortest path from this to the city parameter.
     *         Will return {@link Double#POSITIVE_INFINITY} if no path is found.
     */
    public double distanceTo(City city) {
        PriorityQueue<Road> distances = new PriorityQueue<>(); // O(log n) insertion and removing iirc
        double[] distancesTo = new double[cities];
        boolean[] visited = new boolean[cities];

        Arrays.fill(distancesTo, Double.POSITIVE_INFINITY);
        Arrays.fill(visited, false);

        // adding the current city as we know the distance from a city to itself is always 0
        distancesTo[number] = 0;
        distances.add(new Road(this, 0));

        for (int i = 0; i < cities - 1; i++) {
            // if we no longer have any cities to look for, break
            if (distances.isEmpty()) break;
            // remove the city with the shortest distance to, this will be the final distance to that city
            Road r = distances.remove();
            // if we have already visited that city we cross it out because we have already found the shortest
            // distance there
            if (visited[r.destination.number]) continue;

            visited[r.destination.number] = true; // mark it as visited to not set it to a different value

            for (Road d : adj.get(r.destination.number)) {
                // no need to look at stuff we have already determined the shortest distance to, we still need to
                // have a continue above because we may have a road to that city in the priority queue before they are
                // all removed.
                if (visited[d.destination.number]) continue;

                // calculate the distance here so we do not have to copy this code inside the if statement
                double distanceTo = distancesTo[r.destination.number] + d.distance;
                if (distanceTo < distancesTo[d.destination.number]) {
                    // we have found a shorter distance to that city, now we add it to the priority queue and set it in
                    // our distanceTo array
                    distancesTo[d.destination.number] = distanceTo;
                    distances.add(new Road(d.destination, distanceTo));
                }
            }
        }

        // return a specific distance in our distanceTo array. somewhat unoptimized since the distanceTo array will
        // have the shortest distance to every city, not just the one we need. Will be Double.POSITIVE_INFINITY if not
        // found.
        return distancesTo[city.number];
    }

    public String getName() {
        return name;
    }
}
