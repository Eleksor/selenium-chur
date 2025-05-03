package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    protected WebDriver driver;
    protected static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    By title = By.className("display-6");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Geting subpage title")
    public WebElement getTitle() {
        return driver.findElement(title);
    }

    @Step("Getting current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
