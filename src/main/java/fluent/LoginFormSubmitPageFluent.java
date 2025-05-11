package fluent;

import components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginFormSubmitPageFluent extends BasePageFluent {
    HeaderComponent header;

    public LoginFormSubmitPageFluent(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver);
    }

    @FindBy(id = "success")
    public WebElement successAlert;

    public String getSuccessMessage() {
        return successAlert.getText().trim();
    }

    @Step("Возвращение на главную страницу нажатием на лого в хэдере")
    public HomePageFluent returnToHomePage() {
        header.clickLogo();
        return new HomePageFluent(driver);
    }
}
