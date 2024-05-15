package com.example.myapplication;
import com.example.myapplication.utils.LocationChecker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class contains unit tests for the {@link LocationChecker} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu
 */

public class TestLocationChecker {

    private int state1,state2, state3, state4, state5, state6, state7 = 0;


    @Before
    public void setUp(){
        state1 = LocationChecker.determineState(-41.055, 145.903);//TAS
        state2 = LocationChecker.determineState(-31.946, 115.812);//WA
        state3 = LocationChecker.determineState(-34.993, 138.511);//SA
        state4 = LocationChecker.determineState(-33.829, 150.966);//NSW
        state5 = LocationChecker.determineState(-27.566, 152.936);//QLD
        state6 = LocationChecker.determineState(-37.76, 144.964);//VIC
        state7 = LocationChecker.determineState(-12.442, 130.921);//NT
        //The algorithm is a general one, cannot handle some border situations and ACT region.
    }

    @Test
    public void testDetermineState(){
        assertEquals(state1, 1);
        assertEquals(state2, 2);//No more error here
        assertEquals(state3, 3);
        assertEquals(state4, 4);
        assertEquals(state5, 5);
        assertEquals(state6, 6);
        assertEquals(state7, 7);
    }


}
