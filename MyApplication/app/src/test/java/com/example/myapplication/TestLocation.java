package com.example.myapplication;
import com.example.myapplication.model.Location;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class contains unit tests for the {@link Location} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu, u7747847
 */

public class TestLocation {
    private final double latitude1 = -31.946;
    private final double longitude1 = 115.812;
    private final double latitude2 = -36.053;
    private final double longitude2 = 146.927;
    private final double latitude3 = -26.805;
    private final double longitude3 = 153.131;
    private final double latitude4 = -34.874;
    private final double longitude4 = 138.591;
    private final double latitude5 = -33.755;
    private final double longitude5 = 151.293;
    private final double latitude6 = -27.225;
    private final double longitude6 = 153.095;


    private Location location1;
    private Location location2;
    private Location location3;
    private Location location4;
    private Location location5;
    private Location location6;
    private Location userLocation;

    @Before
    public void setUp(){
        location1 = new Location(latitude1, longitude1);
        location2 = new Location(latitude2, longitude2);
        location3 = new Location(latitude3, longitude3);
        location4 = new Location(latitude4, longitude4);
        location5 = new Location(latitude5, longitude5);
        location6 = new Location(latitude6, longitude6);

        userLocation = new Location(-37.887, 144.693); // Assume this is the current location of user.
    }

    @Test
    public void testGetLatitude(){
        assertEquals(latitude1, location1.getLatitude(), 0.001);// Need a tolerance delta
        assertEquals(latitude2, location2.getLatitude(), 0.001);
        assertEquals(latitude3, location3.getLatitude(), 0.001);
        assertEquals(latitude4, location4.getLatitude(), 0.001);
        assertEquals(latitude5, location5.getLatitude(), 0.001);
        assertEquals(latitude6, location6.getLatitude(), 0.001);
    }
    @Test
    public void testGetLongitude(){
        assertEquals(longitude1, location1.getLongitude(), 0.001);// Need a tolerance delta
        assertEquals(longitude2, location2.getLongitude(), 0.001);
        assertEquals(longitude3, location3.getLongitude(), 0.001);
        assertEquals(longitude4, location4.getLongitude(), 0.001);
        assertEquals(longitude5, location5.getLongitude(), 0.001);
        assertEquals(longitude6, location6.getLongitude(), 0.001);

    }

    @Test
    public void testCalculateDistance(){
        assertEquals(2703082.314361225, location1.calculateDistance(userLocation), 0.00000001);// Need a tolerance delta
        assertEquals(284545.7215135569, location2.calculateDistance(userLocation), 0.00000001);
        assertEquals(1463609.409747971, location3.calculateDistance(userLocation), 0.00000001);
        assertEquals(640603.2218857438, location4.calculateDistance(userLocation), 0.00000001);
        assertEquals(751454.1971193920, location5.calculateDistance(userLocation), 0.00000001);
        assertEquals(1421735.376469579, location6.calculateDistance(userLocation), 0.00000001);
    }
}
