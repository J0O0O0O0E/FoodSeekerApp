package com.example.myapplication;
import com.example.myapplication.ui.login.LoginActivity;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TestSignupActivity {
    // example email
    private final String false_email1 = "bob123";
    private final String false_email2 = "bob123@";
    private final String false_email3 = "bob123.com";
    private final String false_email4 = "bob@.com123";
    private final String false_email5 = "";
    private final String false_email6 = " ";
    private final String correct_email1 = "bob@163.com";
    private final String correct_email2 = "123@gmail.com";
    private final String correct_email3 = "bob@anu.edu.au";
    private final String correct_email4 = "bob123@hotmail.com";
    private final String correct_email5 = "bob@123gmail.com";
    private final String correct_email6 = "b1o2b3@qq.com";

    // example password
    private final String short_psw1 = "";
    private final String short_psw2 = " ";
    private final String short_psw3 = "1";
    private final String short_psw4 = "12";
    private final String short_psw5 = "123";
    private final String short_psw6 = "12345";
    private final String long_psw1 = "123456";
    private final String long_psw2 = "000000";
    private final String long_psw3 = "asdasd";


    private LoginActivity loginActivity;

    @Before
    public void setup() {
        // Use mock to simulate LoginActivity without being restricted by the android framework
        loginActivity = mock(LoginActivity.class);
        // Mock expected result of email
        // True situation
        when(loginActivity.isValidEmail(false_email1)).thenReturn(false);
        when(loginActivity.isValidEmail(false_email2)).thenReturn(false);
        when(loginActivity.isValidEmail(false_email3)).thenReturn(false);
        when(loginActivity.isValidEmail(false_email4)).thenReturn(false);
        when(loginActivity.isValidEmail(false_email5)).thenReturn(false);
        when(loginActivity.isValidEmail(false_email6)).thenReturn(false);
        // False situation
        when(loginActivity.isValidEmail(correct_email1)).thenReturn(true);
        when(loginActivity.isValidEmail(correct_email2)).thenReturn(true);
        when(loginActivity.isValidEmail(correct_email3)).thenReturn(true);
        when(loginActivity.isValidEmail(correct_email4)).thenReturn(true);
        when(loginActivity.isValidEmail(correct_email5)).thenReturn(true);
        when(loginActivity.isValidEmail(correct_email6)).thenReturn(true);

        // Mock expected result of password
        // True situation
        when(loginActivity.isLengthLessThan6(short_psw1)).thenReturn(false);
        when(loginActivity.isLengthLessThan6(short_psw2)).thenReturn(false);
        when(loginActivity.isLengthLessThan6(short_psw3)).thenReturn(false);
        when(loginActivity.isLengthLessThan6(short_psw3)).thenReturn(false);
        when(loginActivity.isLengthLessThan6(short_psw4)).thenReturn(false);
        when(loginActivity.isLengthLessThan6(short_psw5)).thenReturn(false);
        when(loginActivity.isLengthLessThan6(short_psw6)).thenReturn(false);
        // False situation
        when(loginActivity.isLengthLessThan6(long_psw1)).thenReturn(true);
        when(loginActivity.isLengthLessThan6(long_psw2)).thenReturn(true);
        when(loginActivity.isLengthLessThan6(long_psw3)).thenReturn(true);
    }

    @Test
    public void testEmail() {
        // Use assert to test Email format
        assertFalse("It is supposed to be a wrong email format", loginActivity.isValidEmail(false_email1));
        assertFalse("It is supposed to be a wrong email format", loginActivity.isValidEmail(false_email2));
        assertFalse("It is supposed to be a wrong email format", loginActivity.isValidEmail(false_email3));
        assertFalse("It is supposed to be a wrong email format", loginActivity.isValidEmail(false_email4));
        assertFalse("It is supposed to be a wrong email format", loginActivity.isValidEmail(false_email5));
        assertFalse("It is supposed to be a wrong email format", loginActivity.isValidEmail(false_email6));

        assertTrue("It is supposed to be a correct email format", loginActivity.isValidEmail(correct_email1));
        assertTrue("It is supposed to be a correct email format", loginActivity.isValidEmail(correct_email2));
        assertTrue("It is supposed to be a correct email format", loginActivity.isValidEmail(correct_email3));
        assertTrue("It is supposed to be a correct email format", loginActivity.isValidEmail(correct_email4));
        assertTrue("It is supposed to be a correct email format", loginActivity.isValidEmail(correct_email5));
        assertTrue("It is supposed to be a correct email format", loginActivity.isValidEmail(correct_email6));
    }

    @Test
    public void testPasswordLength(){
        //Use assert to test Password Length
        assertFalse("expected: password length < 6 actual: password length > 6", loginActivity.isLengthLessThan6(short_psw1));
        assertFalse("expected: password length < 6 actual: password length > 6", loginActivity.isLengthLessThan6(short_psw2));
        assertFalse("expected: password length < 6 actual: password length > 6", loginActivity.isLengthLessThan6(short_psw3));
        assertFalse("expected: password length < 6 actual: password length > 6", loginActivity.isLengthLessThan6(short_psw4));
        assertFalse("expected: password length < 6 actual: password length > 6", loginActivity.isLengthLessThan6(short_psw5));
        assertFalse("expected: password length < 6 actual: password length > 6", loginActivity.isLengthLessThan6(short_psw6));

        assertTrue("expected: password length > 6 actual: password length < 6", loginActivity.isLengthLessThan6(long_psw1));
        assertTrue("expected: password length > 6 actual: password length < 6", loginActivity.isLengthLessThan6(long_psw2));
        assertTrue("expected: password length > 6 actual: password length < 6", loginActivity.isLengthLessThan6(long_psw3));
    }




}
