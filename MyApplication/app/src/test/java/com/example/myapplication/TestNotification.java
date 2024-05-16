package com.example.myapplication;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import com.example.myapplication.model.FoodBank;
import com.example.myapplication.model.Notification;
import com.example.myapplication.parser.BusinessHours;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class contains unit tests for the {@link Notification} class.
 * It tests various methods of the User class to ensure they function correctly.
 *
 * @author Haoxuan Xu, u7747847
 */

public class TestNotification {

    private FoodBank mockFoodBank;
    private BusinessHours mockBusinessHours;

    @Before
    public void setUp() {
        mockFoodBank = Mockito.mock(FoodBank.class);
        mockBusinessHours = Mockito.mock(BusinessHours.class);

        Mockito.when(mockFoodBank.getName()).thenReturn("Test FoodBank");
        Mockito.when(mockFoodBank.getBusinessHours()).thenReturn(mockBusinessHours);
    }

    @Test
    public void testNotificationConstructor() {
        LocalDateTime testTime = LocalDateTime.of(2024, 5, 16, 10, 0);
        Notification notification = new Notification(mockFoodBank, testTime);

        assertNotNull(notification);
        assertEquals("Test FoodBank", notification.getFoodBankName());
        assertEquals(testTime, notification.getNotificationTime());
    }

    @Test
    public void testGetNotifyMessage_FoodBankClosed() {
        LocalDateTime testTime = LocalDateTime.of(2024, 5, 16, 23, 0);
        Mockito.when(mockBusinessHours.isFoodBankClosed(testTime)).thenReturn(true);

        Notification notification = new Notification(mockFoodBank, testTime);
        String message = notification.getNotifyMessage();

        assertEquals("Test FoodBank is now closed", message);
    }

    @Test
    public void testGetNotifyMessage_FoodBankOpen() {
        LocalDateTime testTime = LocalDateTime.of(2024, 5, 16, 10, 0);
        Mockito.when(mockBusinessHours.isFoodBankClosed(testTime)).thenReturn(false);

        Notification notification = new Notification(mockFoodBank, testTime);
        String message = notification.getNotifyMessage();

        assertEquals("Test FoodBank is now open", message);
    }
}
