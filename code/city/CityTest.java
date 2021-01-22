package city;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Test
    void distanceTo() {
        City la = new City("LA");
        City dc = new City("DC");
        City s = new City("S");
        City sf = new City("SF");
        City t = new City("T");

        la.addRoads(new City.Road(dc, 10), new City.Road(s, 3), new City.Road(sf, 28));
        dc.addRoads(new City.Road(sf, 1), new City.Road(t, 12));
        s.addRoads(new City.Road(sf, 14));
        sf.addRoads(new City.Road(t, 10));

        assertEquals(11, la.distanceTo(sf));
        assertEquals(21, la.distanceTo(t));
    }
}