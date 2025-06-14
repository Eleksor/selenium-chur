import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pom.HomePage;
import pom.WebFormPage;

public class WebFormPageTest extends BaseTest {
    HomePage homePage;

    @BeforeEach
    void setup2() {
        homePage = new HomePage(getDriver());
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
}
