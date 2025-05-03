import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pom.HomePage;
import pom.NavigationPage;
import pom.WebFormPage;

import static constants.Constants.BASE_URL;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebFormPageTest extends BaseTest {
    HomePage homePage;

    @BeforeEach
    void setup2() {
        homePage = new HomePage(driver);
    }

    @Test
    void submitFormTest() {
        WebFormPage webFormPage = homePage.openWebFormPage();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        webFormPage.clickSubmitForm();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //40 минута
}
