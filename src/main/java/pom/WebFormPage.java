package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebFormPage extends BasePage {
    private static final String WEB_FORM_URL = "web-form.html";
    WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

    public WebFormPage(WebDriver driver) {
        super(driver);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement getTitle() {
        return driver.findElement(title);
    }

    public String getUrl() {
        return WEB_FORM_URL;
    }

    @Step("Click submit button")
    public void clickSubmitForm() {
        submitButton.click();
    }
}
