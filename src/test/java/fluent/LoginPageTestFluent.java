package fluent;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPageTestFluent extends BaseTest {
    @Test
    void successfulLoginTest() {
        String successMessage = new HomePageFluent(driver)
                .openLoginPage()
                .inputUsername("user")
                .inputPassword("user")
                .clickSubmitButton()
                .getSuccessMessage();

        assertEquals("Login successful", successMessage);
    }
}
