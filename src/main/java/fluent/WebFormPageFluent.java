package fluent;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WebFormPageFluent extends BasePageFluent {
    private static final String WEB_FORM_URL = "web-form.html";
    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitButton;// = driver.findElement(By.xpath("//button[@type='submit']"));

    public WebFormPageFluent(WebDriver driver) {
        super(driver);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getUrl() {
        return WEB_FORM_URL;
    }

    @Step("Click submit button")
    public void clickSubmitForm() {
        submitButton.click();
    }
}
