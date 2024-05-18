package UnitTest;
import Controller.Login;
import org.junit.Test;
import junit.framework.TestCase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginTest extends TestCase {
    // Test to verify login with correct password
    @Test
    public void testLoginWithCorrectPassword() {
        Login login = new Login();
        String username = "test";
        String correctPassword = "test";
        boolean loginResult = login.authenticate(username, correctPassword);
        assertTrue(loginResult);
        System.out.println(loginResult);
        System.out.println("Test passed: Able to login with correct password");
    }

    // Test to verify login with incorrect password
    @Test
    public void testLoginWithIncorrectPassword() {
        Login login = new Login();
        String username = "test";
        String correctPassword = "test";
        String incorrectPassword = "incorrectPassword";
        boolean loginResult = login.authenticate(username, incorrectPassword);
        assertFalse(loginResult);
        System.out.println(loginResult);
        System.out.println("Test passed: Unable to login with incorrect password");
    }
}
