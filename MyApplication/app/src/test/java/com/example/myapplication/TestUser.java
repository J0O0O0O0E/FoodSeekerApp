package com.example.myapplication;
import com.example.myapplication.model.User;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * This class contains unit tests for the {@link User} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu
 */

public class TestUser {
    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUserName("Bob");
        user.setContactNumber("1234567890");
        user.setEmail("Bob@ok.com");
        ArrayList<String> foodBanks = new ArrayList<>();
        foodBanks.add("JERSEY1");
        user.setSubscribedFoodBanks(foodBanks);
    }

    @Test
    public void testGetUserName() {
        assertEquals("Bob", user.getUserName());
    }

    @Test
    public void testGetContactNumber() {
        assertEquals("1234567890", user.getContactNumber());
    }

    @Test
    public void testGetEmail() {
        assertEquals("Bob@ok.com", user.getEmail());
    }

    @Test
    public void testGetSubscribedFoodBanks() {
        assertNotNull(user.getSubscribedFoodBanks());
        assertFalse(user.getSubscribedFoodBanks().isEmpty());
        assertEquals("JERSEY1", user.getSubscribedFoodBanks().get(0));
    }

    @Test
    public void testAddSubscribedFoodBank() {
        user.addSubscribedFoodBank("TARAKAN2");
        assertEquals(2, user.getSubscribedFoodBanks().size());
        assertEquals("TARAKAN2", user.getSubscribedFoodBanks().get(1));
    }
}
