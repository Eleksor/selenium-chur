package chapters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChaptersTest {

    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    static int parametrizedCount = 1;

    @BeforeEach
    void setup() {

        driver = new ChromeDriver();
        Actions action = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.get(BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void chapter3Test() {
        String chapterContent = "//div[@class='card-body']/h5[contains(@class, 'card-title') and  " +
                "text() = 'Chapter 3. WebDriver Fundamentals']/../a";
        Map<String, String> elementUrlPair = new HashMap<>();
        List<WebElement> list = driver.findElements(By.xpath(chapterContent));

        for (WebElement webElement : list) {
            elementUrlPair.put(webElement.getDomAttribute("href"), webElement.getText());
        }

        for (Map.Entry<String, String> entry : elementUrlPair.entrySet()) {
            driver.findElement(By.xpath("//a[text() = '" + entry.getValue() + "']")).click();

            assertEquals(BASE_URL + entry.getKey(), driver.getCurrentUrl());
            if (entry.getValue().equals("Navigation")) {
                assertEquals(entry.getValue() + " example",
                        driver.findElement(By.xpath("//h1[@class='display-6']")).getText());
            } else if (entry.getValue().equals("Draw in canvas")) {
                assertEquals("Drawing in canvas", driver.findElement(By.xpath("//h1[@class='display-6']")).getText());
            } else {
                assertEquals(entry.getValue(), driver.findElement(By.xpath("//h1[@class='display-6']")).getText());
            }
            driver.navigate().back();
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "long-page.html,Long page,This is a long page",
            "infinite-scroll.html,Infinite scroll,Infinite scroll",
            "shadow-dom.html,Shadow DOM,Shadow DOM",
            "cookies.html,Cookies,Cookies",
            "frames.html,Frames,Frames",
            "iframes.html,IFrames,IFrame",
            "dialog-boxes.html,Dialog boxes,Dialog boxes",
            "web-storage.html,Web storage,Web storage"},
            ignoreLeadingAndTrailingWhitespace = false)
    void chapter4Test(String urlButt, String nameButt, String nameHead) {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[" + parametrizedCount + "]";
        parametrizedCount++;

        WebElement currentElement = driver.findElement(By.xpath(nextBtnLocator));
        String actualBtnUrl = currentElement.getDomAttribute("href");
        String actualBtnName = currentElement.getText();

        driver.findElement(By.xpath(nextBtnLocator)).click();
        String actualUrl = driver.getCurrentUrl();
        String headerLocator = "//h1[@class='display-6']";

        try {
            String actualHead = driver.findElement(By.xpath(headerLocator)).getText();
            assertEquals(nameHead, actualHead);
        } catch (NoSuchElementException e) {
            driver.switchTo().frame(driver.findElement(By.name("frame-header")));
            String actualHead = driver.findElement(By.xpath(headerLocator)).getText();
            assertEquals(nameHead, actualHead);
        }

        assertEquals(BASE_URL + urlButt, actualUrl);
        assertEquals(nameButt, actualBtnName);
    }

    @Test
    void longPageTest() throws InterruptedException {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[1]";
        driver.findElement(By.xpath(nextBtnLocator)).click();
        Thread.sleep(1000);
    }

}
