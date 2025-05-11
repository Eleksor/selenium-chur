package fluent;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void returnOnHomePageAfterSuccessfulLoginTest() {
        LoginFormSubmitPageFluent loginFormSubmitPageFluent = new HomePageFluent(driver)
                .openLoginPage()
                .inputUsername("resu")
                .inputPassword("resu")
                .clickSubmitButton();

        assertTrue(driver.findElement(By.id("invalid")).isDisplayed());
    }
}
