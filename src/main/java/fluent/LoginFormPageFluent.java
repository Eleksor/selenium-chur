package fluent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginFormPageFluent extends BasePageFluent{
    public LoginFormPageFluent(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "username")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button")
    private WebElement submitButton;

    public LoginFormPageFluent inputUsername(String username) {
        loginField.sendKeys(username);
        return this;
    }

    public LoginFormPageFluent inputPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public LoginFormSubmitPageFluent clickSubmitButton() {
        submitButton.click();
        return new LoginFormSubmitPageFluent(driver);
    }
}
