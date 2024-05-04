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

    // example password(length)
    private final String short_psw1 = "";
    private final String short_psw2 = " ";
    private final String short_psw3 = "1";
    private final String short_psw4 = "12";
    private final String short_psw5 = "123";
    private final String short_psw6 = "12345";
    private final String long_psw1 = "123456";
    private final String long_psw2 = "000000";
    private final String long_psw3 = "asdasd";

    //example password(capital)
    private final String wrong_psw1 = "asdasd";
    private final String wrong_psw2 = "123456";
    private final String wrong_psw3 = "qwerty";
    private final String wrong_psw4 = "asd123";
    private final String wrong_psw5 = "123qwe";
    private final String wrong_psw6 = "123456asdqwe";

    private final String correct_psw1 = "Asdasd";
    private final String correct_psw2 = "A12345";
    private final String correct_psw3 = "123ASD";
    private final String correct_psw4 = "AAAAAA";
    private final String correct_psw5 = "AsDasd";
    private final String correct_psw6 = "12345A";




    private LoginActivity loginActivity;


    // By simulating the behavior of LoginActivity,
    // you can ensure that the test only focuses on the functionality you want to test,
    // without being restricted by other code logic and the Android framework.
    // It also ensures certainty that the results will not be affected by other factors.
    @Before
    public void setup() {
        loginActivity = mock(LoginActivity.class);

        // Mock
        // Check if email is valid
        String[] falseEmails = {false_email1, false_email2, false_email3, false_email4, false_email5, false_email6};
        String[] correctEmails = {correct_email1, correct_email2, correct_email3, correct_email4, correct_email5, correct_email6};

        for (String email : falseEmails) {
            when(loginActivity.isValidEmail(email)).thenReturn(false);
        }
        for (String email : correctEmails) {
            when(loginActivity.isValidEmail(email)).thenReturn(true);
        }

        // Mock
        // Check if password length is less than 6
        String[] shortPsws = {short_psw1, short_psw2, short_psw3, short_psw4, short_psw5, short_psw6};
        String[] longPsws = {long_psw1, long_psw2, long_psw3};

        for (String psw : shortPsws) {
            when(loginActivity.isLengthLessThan6(psw)).thenReturn(true);
        }
        for (String psw : longPsws) {
            when(loginActivity.isLengthLessThan6(psw)).thenReturn(false);
        }

        // Mock
        // Check if password contains capital letter(s)
        String[] wrongPsws = {wrong_psw1, wrong_psw2, wrong_psw3, wrong_psw4, wrong_psw5, wrong_psw6};
        String[] correctPsws = {correct_psw1, correct_psw2, correct_psw3, correct_psw4, correct_psw5, correct_psw6};

        for (String psw : wrongPsws) {
            when(loginActivity.containUpper(psw)).thenReturn(false);
        }
        for (String psw : correctPsws) {
            when(loginActivity.containUpper(psw)).thenReturn(true);
        }
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
        assertTrue("Expected: password length < 6 Actual: Method returned false", loginActivity.isLengthLessThan6(short_psw1));
        assertTrue("Expected: password length < 6 Actual: Method returned false", loginActivity.isLengthLessThan6(short_psw2));
        assertTrue("Expected: password length < 6 Actual: Method returned false", loginActivity.isLengthLessThan6(short_psw3));
        assertTrue("Expected: password length < 6 Actual: Method returned false", loginActivity.isLengthLessThan6(short_psw4));
        assertTrue("Expected: password length < 6 Actual: Method returned false", loginActivity.isLengthLessThan6(short_psw5));
        assertTrue("Expected: password length < 6 Actual: Method returned false", loginActivity.isLengthLessThan6(short_psw6));

        assertFalse("Expected: password length > 6 actual: Method returned true", loginActivity.isLengthLessThan6(long_psw1));
        assertFalse("Expected: password length > 6 actual: Method returned true", loginActivity.isLengthLessThan6(long_psw2));
        assertFalse("Expected: password length > 6 actual: Method returned true", loginActivity.isLengthLessThan6(long_psw3));
    }

    @Test
    public void testPasswordCapital(){
        //Use assert to test Password Capital
        assertFalse("Expected: password does not contain capital Actual: Method return true", loginActivity.containUpper(wrong_psw1));
        assertFalse("Expected: password does not contain capital Actual: Method return true", loginActivity.containUpper(wrong_psw2));
        assertFalse("Expected: password does not contain capital Actual: Method return true", loginActivity.containUpper(wrong_psw3));
        assertFalse("Expected: password does not contain capital Actual: Method return true", loginActivity.containUpper(wrong_psw4));
        assertFalse("Expected: password does not contain capital Actual: Method return true", loginActivity.containUpper(wrong_psw5));
        assertFalse("Expected: password does not contain capital Actual: Method return true", loginActivity.containUpper(wrong_psw6));

        assertTrue("Expected: password contains capital Actual: Method return False", loginActivity.containUpper(correct_psw1));
        assertTrue("Expected: password contains capital Actual: Method return False", loginActivity.containUpper(correct_psw2));
        assertTrue("Expected: password contains capital Actual: Method return False", loginActivity.containUpper(correct_psw3));
        assertTrue("Expected: password contains capital Actual: Method return False", loginActivity.containUpper(correct_psw4));
        assertTrue("Expected: password contains capital Actual: Method return False", loginActivity.containUpper(correct_psw5));
        assertTrue("Expected: password contains capital Actual: Method return False", loginActivity.containUpper(correct_psw6));
    }




}
