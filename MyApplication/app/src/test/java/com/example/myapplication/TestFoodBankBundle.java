package com.example.myapplication;
import android.os.Bundle;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.utils.FoodBankBundle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the {@link FoodBankBundle} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu, u7747847
 */

public class TestFoodBankBundle {
    @Mock
    private Bundle mockBundle;

    private FoodBank foodBank;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        foodBank = new FoodBank();
        foodBank.setCapacity(10);
        foodBank.setCountry("AU");
        foodBank.setDoe("2024/11/14");
        foodBank.setEmail("JERSEY1@zane.com");
        foodBank.setFood1_pasta(47);
        foodBank.setFood2_bread(63);
        foodBank.setFood3_milk(26);
        foodBank.setFood4_pie(51);
        foodBank.setFood5_vet(119);
        foodBank.setId(1);
        foodBank.setIndex(0);
        foodBank.setLat(-31.946);
        foodBank.setLon(115.812);
        foodBank.setName("JERSEY1");
        foodBank.setOpen_hours("Weekdays,08:00-19:00 Weekends,12:00-18:00");
        foodBank.setPostcode("6014");
        foodBank.setPrefix("JERSEY1");
        foodBank.setSuffix("@zane.com");
        foodBank.setRating(4.6);
        foodBank.setRegion("WA");
        foodBank.setStatus(true);
        foodBank.setDistanceToUser(0.000);
        foodBank.setStreet("JERSEY STREET");
        foodBank.setSuburb("JOLIMONT");

        // Mock the behavior of the bundle
        when(mockBundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_CAPACITY)).thenReturn(10);
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_COUNTRY)).thenReturn("AU");
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_FOUND_DATE)).thenReturn("2024/11/14");
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_EMAIL)).thenReturn("JERSEY1@zane.com");
        when(mockBundle.getInt(FoodBankBundle.KEY_PASTA)).thenReturn(47);
        when(mockBundle.getInt(FoodBankBundle.KEY_BREAD)).thenReturn(63);
        when(mockBundle.getInt(FoodBankBundle.KEY_MILK)).thenReturn(26);
        when(mockBundle.getInt(FoodBankBundle.KEY_PIE)).thenReturn(51);
        when(mockBundle.getInt(FoodBankBundle.KEY_VEGETABLE)).thenReturn(119);
        when(mockBundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_FOOD_BANK_ID)).thenReturn(1);
        //when(mockBundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_INDEX)).thenReturn(0);
        when(mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_LATITUDE)).thenReturn(-31.946);
        when(mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_LONGITUDE)).thenReturn(115.812);
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_NAME)).thenReturn("JERSEY1");
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_OPEN_HOURS)).thenReturn("Weekdays,08:00-19:00 Weekends,12:00-18:00");
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_POSTCODE)).thenReturn("6014");
        //when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_PREFIX)).thenReturn("JERSEY1");
        //when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_SUFFIX)).thenReturn("@zane.com");
        when(mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_RATE)).thenReturn(4.6);
        //when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_REGION)).thenReturn("WA");
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_STATE)).thenReturn("open");
        when(mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_DISTANCE)).thenReturn(0.000);
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_STREET)).thenReturn("JERSEY STREET");
        when(mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_CITY)).thenReturn("JOLIMONT");
    }

    @Test
    public void testBundle(){
        assertEquals(10, mockBundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_CAPACITY));
        assertEquals("AU", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_COUNTRY));
        assertEquals("2024/11/14", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_FOUND_DATE));
        assertEquals("JERSEY1@zane.com", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_EMAIL));
        assertEquals(47, mockBundle.getInt(FoodBankBundle.KEY_PASTA));
        assertEquals(63, mockBundle.getInt(FoodBankBundle.KEY_BREAD));
        assertEquals(26, mockBundle.getInt(FoodBankBundle.KEY_MILK));
        assertEquals(51, mockBundle.getInt(FoodBankBundle.KEY_PIE));
        assertEquals(119, mockBundle.getInt(FoodBankBundle.KEY_VEGETABLE));
        assertEquals(1, mockBundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_FOOD_BANK_ID));
        //assertEquals(0, mockBundle.getInt(FoodBankBundle.KEY_FOODBANKBUNDLE_INDEX)); // added
        assertEquals(-31.946, mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_LATITUDE), 0.001);
        assertEquals(115.812, mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_LONGITUDE), 0.001);
        assertEquals("JERSEY1", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_NAME));
        assertEquals("Weekdays,08:00-19:00 Weekends,12:00-18:00", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_OPEN_HOURS));
        assertEquals("6014", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_POSTCODE));
        //assertEquals("JERSEY1", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_PREFIX)); // added
        //assertEquals("@zane.com", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_SUFFIX)); // added
        assertEquals(4.6, mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_RATE), 0.1);
        //assertEquals("WA", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_REGION)); // added
        assertEquals("open", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_STATE));
        assertEquals(0.000, mockBundle.getDouble(FoodBankBundle.KEY_FOODBANKBUNDLE_DISTANCE), 0.01);
        assertEquals("JERSEY STREET", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_STREET));
        assertEquals("JOLIMONT", mockBundle.getString(FoodBankBundle.KEY_FOODBANKBUNDLE_CITY));
    }
}
