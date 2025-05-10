package fluent;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePageFluent extends BasePageFluent {
    protected static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    public HomePageFluent(WebDriver driver) {
        super(driver);
        open();
    }

    @Step("Open Home Page")
    public void open() {
        this.driver.get(BASE_URL);
    }

    @Step("Getting web title")
    public String getWebTitle() {
        return driver.getTitle();
    }

    @Step("Open Web Form Page")
    public WebFormPageFluent openWebFormPage() {
        driver.findElement(By.linkText("Web form")).click();
        return new WebFormPageFluent(driver);
    }

    @Step("Open Navigation Page")
    public NavigationPageFluent openNavigationPage() {
        driver.findElement(By.linkText("Navigation")).click();
        return new NavigationPageFluent(driver);
    }

    @Step("Open Login Page")
    public LoginFormPageFluent openLoginPage() {
        driver.findElement(By.linkText("Login form")).click();
        return new LoginFormPageFluent(driver);
    }
}
