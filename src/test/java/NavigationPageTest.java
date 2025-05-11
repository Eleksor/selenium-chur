import base.BaseTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pom.HomePage;
import pom.NavigationPage;

import static constants.Constants.BASE_URL;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Testing Navigation Page")
public class NavigationPageTest extends BaseTest {
    HomePage homePage;

    @BeforeEach
    void setup2() {
        homePage = new HomePage(driver);
    }

    @Test
    void openNavigationPageTest() {
        NavigationPage navigationPage = homePage.openNavigationPage();
//        try {
//            sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        String currentUrl = navigationPage.getCurrentUrl();
        String title = navigationPage.getTitle().getText();
        String navigationUrl = navigationPage.getUrl();

        navigationPage.clickNextButton();
        navigationPage.clickNextButton();

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(navigationPage.getTitle() + ": " + navigationPage.getCurrentUrl());

        assertEquals(BASE_URL + navigationUrl, currentUrl);
        assertEquals("Navigation example", title);
    }
    //40 минута
}
