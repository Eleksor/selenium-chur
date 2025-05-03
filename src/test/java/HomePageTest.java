import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.HomePage;
import pom.WebFormPage;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static constants.Constants.BASE_URL;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePageTest extends BaseTest {
    HomePage homePage;

    @BeforeEach
    void setup2() {
        homePage = new HomePage(driver);
    }

    @Test
    void firstTest() {
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='card-body']/h5[contains(@class, 'card-title') and  text() = 'Chapter 3. WebDriver Fundamentals']/../a"));

        for (WebElement webElement : list) {
            System.out.println(webElement.getDomAttribute("href"));
        }
    }

    @Test
    void secondTest() {
        String title = homePage.getWebTitle();

        assertEquals("Hands-On Selenium WebDriver with Java", title);
    }

    @Test
    void thirdTest() {
        homePage.open();
        driver.findElement(By.xpath("//div[@class='card-body']/a[1]")).click();
        String webFormUrl = "web-form.html";
        String titleOfChapter =  driver.findElement(By.xpath("//h1[@class = 'display-6']")).getText();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("=========================" + titleOfChapter);

        assertEquals("Web form", titleOfChapter);
        assertEquals(this.baseUrl + webFormUrl, currentUrl);
    }

    @Test
    void signInTest() {
        driver.findElement(By.xpath("//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and  text() = 'Chapter 7. The Page Object Model (POM)']/../a[1]")).click();
        driver.findElement(By.id("username")).sendKeys(testConfig.getUsername());
        driver.findElement(By.id("password")).sendKeys(testConfig.getPassword());
        driver.findElement(By.xpath("//button")).click();

        assertEquals("Login successful", driver.findElement(By.id("success")).getText());
    }

    @Test
    void cookiesTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='container']"))));
        sleep(1000);
        Set<Cookie> cookies = driver.manage().getCookies();
        Cookie cookie1 = driver.manage().getCookieNamed("username");
        for (Cookie cookie : cookies) {
            System.out.println("Cookie is: " + cookie.getName());
        }

        System.out.println(cookie1);
    }

    @Test
    void openWebFormTest() {
        //homePage.open();
        WebFormPage webFormPage = homePage.openWebFormPage();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String currentUrl = webFormPage.getCurrentUrl();
        WebElement title = webFormPage.getTitle();
        String webFormUrl = webFormPage.getUrl();

        System.out.println(webFormPage.getTitle() + ": " + webFormPage.getCurrentUrl());

        assertEquals(BASE_URL + webFormUrl, currentUrl);
        assertEquals("Web form", title.getText());

        webFormPage.clickSubmitForm();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //40 минута
}
