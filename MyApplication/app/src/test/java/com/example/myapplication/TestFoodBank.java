package com.example.myapplication;
import com.example.myapplication.model.FoodBank;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
public class TestFoodBank {

    private FoodBank foodBank;

    // Initialize a food bank
    @Before
    public void setUp() {
        foodBank = new FoodBank();
    }

    // Test set and get capacity
    @Test
    public void testSetAndGetCapacity() {
        int expected = 500;
        foodBank.setCapacity(expected);
        assertEquals(expected, foodBank.getCapacity());
    }

    // Test set and get country
    @Test
    public void testSetAndGetCountry() {
        String expected = "Australia";
        foodBank.setCountry(expected);
        assertEquals(expected, foodBank.getCountry());
    }

    // Test set and get pasta
    @Test
    public void testSetAndGetFood1_pasta() {
        int expected = 150;
        foodBank.setFood1_pasta(expected);
        assertEquals(expected, foodBank.getFood1_pasta());
    }

    // Test set and get email
    @Test
    public void testSetAndGetEmail() {
        String expected = "contact@foodbank.org";
        foodBank.setEmail(expected);
        assertEquals(expected, foodBank.getEmail());
    }

    // Test set and get name
    @Test
    public void testSetAndGetName() {
        String expected = "Central Food Bank";
        foodBank.setName(expected);
        assertEquals(expected, foodBank.getName());
    }

    // Test set and get open hours
    @Test
    public void testSetAndGetOpenHours() {
        String expected = "MON to FRI 8-18";
        foodBank.setOpen_hours(expected);
        assertEquals(expected, foodBank.getOpen_hours());
    }

    // Test set and get rating
    @Test
    public void testSetAndGetRating() {
        double expected = 4.5;
        foodBank.setRating(expected);
        assertEquals(expected, foodBank.getRating(), 0.01);
    }

    // Test set and get status
    @Test
    public void testSetAndGetStatus() {
        foodBank.setStatus(true);
        assertTrue(foodBank.isStatus());
        foodBank.setStatus(false);
        assertFalse(foodBank.isStatus());
    }
}
