import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {

    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java";

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
        driver.get(BASE_URL);
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='card-body']/h5[contains(@class, 'card-title') and  text() = 'Chapter 3. WebDriver Fundamentals']/../a"));

        for (WebElement webElement : list) {
            System.out.println(webElement.getDomAttribute("href"));
        }
    }

    @Test
    void secondTest() {
        driver.get(BASE_URL);
        String title = driver.getTitle();

        assertEquals("Hands-On Selenium WebDriver with Java", title);
    }

    @Test
    void thirdTest() {
        driver.get(BASE_URL);
        driver.findElement(By.xpath("//div[@class='card-body']/a[1]")).click();
        String webFormUrl = "/web-form.html";
        String titleOfChapter =  driver.findElement(By.xpath("//h1[@class = 'display-6']")).getText();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("=========================" + titleOfChapter);

        assertEquals("Web form", titleOfChapter);
        assertEquals(BASE_URL + webFormUrl, currentUrl);
    }
}
