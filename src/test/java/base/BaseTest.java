package base;

import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected Actions action;
    protected WebDriverWait wait2;
    protected WebDriverWait wait5;
    protected WebDriverWait wait10;
    protected Wait<WebDriver> waitFluent;
    protected TestConfig testConfig = new TestConfig();
    protected String baseUrl = testConfig.getBaseUrl();

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        action = new Actions(driver);
        wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait10 = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitFluent = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        //driver.get(baseUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
