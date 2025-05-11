package fluent;

import components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginFormPageFluent extends BasePageFluent{
    @FindBy(id = "username")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button")
    private WebElement submitButton;

    HeaderComponent header;

    public LoginFormPageFluent(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver);
    }

    @Step("Ввод логина")
    public LoginFormPageFluent inputUsername(String username) {
        loginField.sendKeys(username);
        return this;
    }

    @Step("Ввод пароля")
    public LoginFormPageFluent inputPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Нажимаем кнопку Submit")
    public LoginFormSubmitPageFluent clickSubmitButton() {
        submitButton.click();
        return new LoginFormSubmitPageFluent(driver);
    }

    @Step("Возвращение на главную страницу нажатием на лого в хэдере")
    public HomePageFluent returnToHomePage() {
        header.clickLogo();
        return new HomePageFluent(driver);
    }
}
