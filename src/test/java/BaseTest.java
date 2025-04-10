import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {

    WebDriver driver;
    TestConfig testConfig = new TestConfig();
    String baseUrl = testConfig.getBaseUrl();

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void firstTest() {
        driver.get(baseUrl);
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='card-body']/h5[contains(@class, 'card-title') and  text() = 'Chapter 3. WebDriver Fundamentals']/../a"));

        for (WebElement webElement : list) {
            System.out.println(webElement.getDomAttribute("href"));
        }
    }

    @Test
    void secondTest() {
        driver.get(baseUrl);
        String title = driver.getTitle();

        assertEquals("Hands-On Selenium WebDriver with Java", title);
    }

    @Test
    void thirdTest() {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//div[@class='card-body']/a[1]")).click();
        String webFormUrl = "/web-form.html";
        String titleOfChapter =  driver.findElement(By.xpath("//h1[@class = 'display-6']")).getText();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("=========================" + titleOfChapter);

        assertEquals("Web form", titleOfChapter);
        assertEquals(baseUrl + webFormUrl, currentUrl);
    }

    @Test
    void signInTest() {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and  text() = 'Chapter 7. The Page Object Model (POM)']/../a[1]")).click();
        driver.findElement(By.id("username")).sendKeys(testConfig.getUsername());
        driver.findElement(By.id("password")).sendKeys(testConfig.getPassword());
        driver.findElement(By.xpath("//button")).click();

        assertEquals("Login successful", driver.findElement(By.id("success")).getText());
    }

    @Test
    void testCookies() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='container']"))));
        sleep(1000);
        Set<Cookie> cookies = driver.manage().getCookies();
        Cookie cookie1 = driver.manage().getCookieNamed("");
        for (Cookie cookie : cookies) {
            System.out.println("Cookie is: " + cookie.getName());
        }

        System.out.println(cookie1);
    }
}
