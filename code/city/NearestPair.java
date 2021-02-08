package city;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NearestPair {

    /**
     * @return Decided to return an Optional instead of a null if the min distance is not found (should always
     * be found though) because I have not used it and wanted to see what Optionals are like.
     */
    public static Optional<String> findMinDistances(ArrayList<String> cities, double[][] distances) {
        double minDist = Double.MAX_VALUE;
        Optional<String> ans = Optional.empty();
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                if (j == i) {
                    // distance to ourselves
                    continue;
                }

                if (distances[i][j] <= minDist) {
                    minDist = distances[i][j];
                    ans = Optional.of(cities.get(i) + "," + cities.get(j));
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        ArrayList<String> cities = new ArrayList<>(List.of("KC","SF","SD","LA"));

        double[][] distances = {{3,1.1,4,1}, {6,9,2,6},{5,3,5,7}, {3,7,2,4}};

        // Optional.get is always going to be non null
        System.out.println("The closest pair of cities are " + findMinDistances(cities, distances).get());
        // would print out "The closest pair of cities are KC,LA"
    }
}
