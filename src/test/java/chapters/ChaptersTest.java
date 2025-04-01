package chapters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ChaptersTest {

    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    static int parametrizedCount = 1;
    Actions action;
    WebDriverWait wait2;
    WebDriverWait wait5;
    WebDriverWait wait10;
    Wait<WebDriver> waitFluent;

    @BeforeEach
    void setup() {

        driver = new ChromeDriver();
        action = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait10 = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitFluent = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
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
    void longPageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[1]";
        driver.findElement(By.xpath(nextBtnLocator)).click();
        action
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .perform();

        wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//footer")));

        assertTrue(driver.findElement(By.xpath("//footer")).isDisplayed());
    }

    @Test
    void infinitePageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[2]";
        driver.findElement(By.xpath(nextBtnLocator)).click();
        action
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .sendKeys(Keys.SPACE)
                .perform();

        //waitFluent.until(ExpectedConditions.presenceOfElementLocated(By.className("text-muted")));

        assertThrows(ElementClickInterceptedException.class, () -> driver.findElement(By.className("text-muted")).click());
    }

    @Test
    void shadowDOMPageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[3]";
        driver.findElement(By.xpath(nextBtnLocator)).click();

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("p")));

        WebElement shadowContent = driver.findElement(By.id("content"));
        SearchContext shadowRoot = shadowContent.getShadowRoot();
        WebElement textElementFromShadow = shadowRoot.findElement(By.cssSelector("p"));

        assertEquals("Hello Shadow DOM", textElementFromShadow.getText());
    }

    @Test
    void defaultCookiePageTest() {
        String expectedCookiesList = "username=John Doe\ndate=10/07/2018";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[4]";
        driver.findElement(By.xpath(nextBtnLocator)).click();
        driver.findElement(By.id("refresh-cookies")).click();
        String cookiesList = driver.findElement(By.id("cookies-list")).getText();

        assertEquals(expectedCookiesList, cookiesList);
    }

    @Test
    void addedCookiePageTest() throws InterruptedException {
        String expectedCookiesList = "username=John Doe\ndate=10/07/2018\npsina=sutulaya";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[4]";

        driver.findElement(By.xpath(nextBtnLocator)).click();
        driver.manage().addCookie(new Cookie("psina", "sutulaya"));
        driver.findElement(By.id("refresh-cookies")).click();
        String cookiesList = driver.findElement(By.id("cookies-list")).getText();

        assertEquals(expectedCookiesList, cookiesList);
    }

    @Test
    void deleteCookiePageTest() throws InterruptedException {
        String expectedCookiesList = "";
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[4]";

        driver.findElement(By.xpath(nextBtnLocator)).click();
        driver.manage().deleteAllCookies();
        driver.findElement(By.id("refresh-cookies")).click();
        String cookiesList = driver.findElement(By.id("cookies-list")).getText();

        assertEquals(expectedCookiesList, cookiesList);
    }

    @Test
    void framePageTest() {
        String nextBtnLocator = "//div[@class='card-body']/h5[contains(@class, 'card-title') " +
                "and text() = 'Chapter 4. Browser-Agnostic Features']/../a[5]";

        driver.findElement(By.xpath(nextBtnLocator)).click();
        driver.switchTo().frame("frame-body");
    }

}
