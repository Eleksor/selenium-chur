package base;

import config.TestConfig;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

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
        initDriver();
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

    private void initDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        Allure.addAttachment("remote", remoteUrl);
        if (remoteUrl == null || remoteUrl.isEmpty()) {
            System.out.println("SELENIUM_REMOTE_URL = " + remoteUrl);
            //Allure.addAttachment("remote", remoteUrl);
            if (remoteUrl != null) {
                Allure.addAttachment("RemoteUrl", remoteUrl);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");  // Add headless mode
                options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
                options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
                options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
                options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
                try {
                    driver = new RemoteWebDriver(new URL(remoteUrl), options);
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
                }
            } else {
                driver = new ChromeDriver();
            }
        }

    }
}
