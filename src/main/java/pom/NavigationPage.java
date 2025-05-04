package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigationPage extends BasePage {
    private static final String NAVIGATION_PAGE_URL = "navigation1.html";

    @FindBy(linkText = "Next")
    private WebElement nextButton;
    //private WebElement nextButton = driver.findElement(By.linkText("Next"));

    public NavigationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Get subpage url")
    public String getUrl() {
        return NAVIGATION_PAGE_URL;
    }

    @Step("Click next")
    public void clickNextButton() {
        nextButton.click();
    }
}
